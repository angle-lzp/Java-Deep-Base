## InfluxDB Query Data Sources

### Query Data Sources

#### InfluxDB

```flux

from(bucket: "example-bucket")
  |> range(start: -1h)

```

#### SQL Databases

```flux

import "sql"

sql.from(
    driverName: "postgres",
    dataSourceName: "postgresql://user:password@localhost",
    query:"SELECT * FROM TestTable",
)

```

#### CSV

```flux

import "csv"

csvData =
    "
#group,false,false,true,true,true,false,false
#datatype,string,long,string,string,string,long,double
#default,_result,,,,,,
,result,table,dataset,metric,sensorID,timestamp,value
,,0,air-sensors,humidity,TLM0100,1627049400000000000,34.79
,,0,air-sensors,humidity,TLM0100,1627049700000000000,34.65
,,1,air-sensors,humidity,TLM0200,1627049400000000000,35.64
,,1,air-sensors,humidity,TLM0200,1627049700000000000,35.67
"

csv.from(csv: csvData)

```

#### Google Cloud BigTable

```flux

import "experimental/bigtable"

bigtable.from(url: "http://example.com/metrics")

```

### Query InfluxDB

- InfluxDB 查询要求必须要有时间限制，所以 from()后面必须有 range()

#### InfluxDB 远程查询需要的参数

```flux

from(
    bucket: "example-bucket",       // bucket
    host: "http://localhost:8086",  // host
    org: "example-org",               // org
    token: "mYSup3r5Ecr3T70keN",        // token
)

```

#### Results Structure

1. from() 和 range() 返回一个 stream of tables，按系列分组（测量、标签集和字段）。 每个表格包含以下列：

- \_start ：查询范围的开始时间（由 range() 定义）
- \_stop ：查询范围停止时间（由 range() 定义）
- \_time ：数据时间戳
- \_measurement ：测量名称
- \_field ：字段键
- \_value ：字段值
- 标签列 ：每个标签对应一列，列标签为标签键，列值为标签值。

==带有下划线的列：为系统列，某系 Flux 函数需要这些列。==

==结构化结果，例如 influxQL：InfluxQL 将每个字段作为一列返回，其中列标签是字段键，列值是字段值。要在 Flux 中以类似的方式组织结果，请使用 pivot()和 schema.fieldsAsCols()将字段透视成列。==

#### Query PostgreSQL

```flux

//在Go语言中，PostgreSQL驱动（lib/pq）支持两种连接字符串格式：URL格式和键值对格式。

//jdbc:postgresql://vnsiotdbtest01.vn.globaltti.net:5432/iot_access_control?currentSchema=base_data     --JDBC驱动 使用currentSchema=base_data来设置模式
//postgresql://${username}:${password}@vnsiotdbtest01.vn.globaltti.net:5432/iot_access_control?sslmode=disable&search_path=base_data    --lib/pq驱动 使用search_path=base_data来设置模式


import "sql"
import "influxdata/influxdb/secrets"

username = "admin"
password = "!@jcesk86s62sdf"

//实际生产建议使用zheird secrets管理隐私数据
//username = secrets.get(key: "POSTGRES_USER")
//password = secrets.get(key: "POSTGRES_PASS")

//方式一：URL格式
sql.from(
    driverName: "postgres",
    dataSourceName: "postgresql://${username}:${password}@vnsiotdbtest01.vn.globaltti.net:5432/iot_access_control?sslmode=disable&search_path=base_data",
    //dataSourceName: "postgresql://${username}:${password}@vnsiotdbtest01.vn.globaltti.net:5432?dbname=iot_access_control&sslmode=disable&search_path=base_data",  //同理；将dbname放在参数位置也是okde
    query: "SELECT * FROM system_users",
)
    |> filter(fn: (r) => r.username =~ /^admin/)    //使用正则表达式过滤username以admin开头的行

//方式二：键值对格式
// sql.from(
//     driverName: "postgres",
//     dataSourceName: "host=vnsiotdbtest01.vn.globaltti.net port=5432 user=admin password='!@jcesk86s62sdf' dbname=iot_access_control search_path=base_data sslmode=disable",
//     query: "SELECT * FROM system_users",
// )

```

#### 将敏感凭证存储为密钥

> 如果使用 InfluxDB Cloud 或 InfluxDB OSS 2.x ，我们建议将 DSN 凭据存储为 InfluxDB 密钥 。使用 secrets.get() 从 InfluxDB 密钥 API 检索密钥。

#### 自定义函数

```flux

import "array"

// dmeo 1
pow = (n, p=10) => n ^ p

pow(n: 2)


// demo 2
// 定义计算函数
// tables=<- 上一个stream of table 的输出将做为这个参数的stream of table输入
speed = (tables=<-, unit="m") =>
    tables
        |> map(
            fn: (r) => {
                // 如果输入是 duration 类型，直接转 int；如果是字符串，需用 duration(v: r.elapsed)
                // 这里假设输入是 duration 字面量
                elapsedHours = float(v: int(v: duration(v: r.elapsed))) / float(v: int(v: 1h))
                distance = float(v: r.distance)
                s = distance / elapsedHours

                return {r with speed: "${s} ${unit}ph"}
            },
        )

// 构建数据并调用
array.from(
    rows: [
        {id: 1, elapsed: "1h20m", distance: 100.3},
        {id: 2, elapsed: "3h20m", distance: 800.2},
        {id: 3, elapsed: "1h50m", distance: 2300.4},
        {id: 4, elapsed: "2h20m", distance: 190.2}
    ]
) |> speed()

```

#### 定义带有作用域变量的函数

```flux

//作用域变量：函数body包裹在block块中
//作用： 1：实现复杂的条件逻辑 (if-else)    （处理单行表达式无法完成的操作）
//      2：在函数内引用复杂的数据结构或包   （引用大量变量等）

import "array"
import "dict"

hexName = (hex) => {
    hexNames =
        dict.fromList(
            pairs: [
                {key: "#00ffff", value: "Aqua"},
                {key: "#000000", value: "Black"},
                {key: "#0000ff", value: "Blue"},
                {key: "#ff00ff", value: "Fuchsia"},
                {key: "#808080", value: "Gray"},
                {key: "#008000", value: "Green"},
                {key: "#00ff00", value: "Lime"},
                {key: "#800000", value: "Maroon"},
                {key: "#000080", value: "Navy"},
                {key: "#808000", value: "Olive"},
                {key: "#800080", value: "Purple"},
                {key: "#ff0000", value: "Red"},
                {key: "#c0c0c0", value: "Silver"},
                {key: "#008080", value: "Teal"},
                {key: "#ffffff", value: "White"},
                {key: "#ffff00", value: "Yellow"},
            ],
        )
    name = dict.get(dict: hexNames, key: hex, default: "No known name")

    return name
}

hexName(hex: "#000000")
// Returns "Black"

hexName(hex: "#8b8b8b")
// Returns "No known name"
array.from(
    rows:[
        {one: hexName(hex: "#000000"),two: hexName(hex: "#8b8b8b")}
    ]
)

```
