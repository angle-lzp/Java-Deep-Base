## InfluxDB 常见代码

### InfluxDB 语法解释

#### 方法签名解释

> (<-arr: [dynamic], fn: (x: dynamic) => int) => [int]
>
> (<-rows: [{_value: int}]) => stream[{_value: int}] ==同理

```text

这是一个 Flux 函数类型签名，语法是：(参数: 类型) => 返回类型。[Function signatures]

(<-arr: [dynamic], fn: (x: dynamic) => int) => [int] 可以逐段解释为：

整体结构

这是一个函数，它：
接收两个参数：arr 和 fn
返回一个结果：[int]（整数数组）
(<-arr: [dynamic], ...)

arr 参数类型是 [dynamic]：即 dynamic 类型元素组成的数组。[Dynamic types]
前面的 <- 表示这是 pipe‑receive 参数，也就是可以用管道 |> 把数组传进来的那个参数。[Signature structure]
fn: (x: dynamic) => int

fn 是一个函数参数：
输入：x: dynamic（单个 dynamic 值）
输出：int（整数）
也就是说，fn 描述“如何把一个 dynamic 值转换成一个 int”。
=> [int]

整个函数的返回类型是 [int]：整数数组。

```

#### 函数写法解释

```text

array.from(
    rows: array.map(arr: intArr, fn: (x) => {return {_value: x}})
)
//1.{_value: x}这个是record expression(记录表达式，相当于表中的一条记录),加上(),那么就是直接将这个record expression直接返回
//2.如果不加上()，那么就是代码块(Block),当作代码来执行，例如：fn: (x) => {return {_value: x}}，模板：fn: (r) => {代码块}，就和Java中的lambda表达式一样了

```

#### array.from 返回类型说明（也可以查看官方文档）

```text
(<-rows: [A]) => stream[A] where A: Record      （Record其实就可以理解为JSON对象）

要求：A: Record
说明A必须是Record类型，否则会报错

```

#### 字符串中包含双引号问题，需要使用\"进行转义

```text
jsonStr =
    "[
        {\"first-name\": \"John\", \"last-name\": \"Doe\", \"age\": 42},
        {\"first-name\": \"Jane\", \"last-name\": \"Doe\"},
        {\"first-name\": \"James\", \"last-name\": \"Doe\", \"age\": 15}
    ]"

//如上面代码可知，jsonStr是一个字符串，需要使用\"进行转义
//Flux 目前没有像某些语言那样的“原始字符串”语法（比如不转义的单引号/三引号字符串）；字符串字面量只有双引号形式，内部 " 必须写成 \"
```

### 动态类型(dynamic type)

#### 案例 1：动态类型引用值

```flux

import "array"
import "experimental/dynamic"

record = {one: 1, two: 2, three: 3}
arr = ["one", "two", "three"]

dynamicRecord = dynamic.dynamic(v:record)       //返回的结果是动态类型的record
dynamicArr = dynamic.asArray(v:dynamic.dynamic(v:arr))  //返回的结果是动态类型的Arrays

array.from(
    rows:[
        {
            one:int(v:dynamicRecord["two"]),    //其中获取到的值也是动态类型，所以需要转换成对应的型
            two:string(v: dynamicArr[1])        //同理
        }
    ]
)

```

#### 案例 2：确保动态类型包含非空值(非空的数据)

```flux

import "array"
import "experimental/dynamic"
import "internal/debug"

record = {one:1,two:2,three:3}

dynamicArr = dynamic.asArray(v:dynamic.dynamic(v:arr))

dynamicNull = dynamic.dynamic(v: debug.null())

dynamicRecord.one
// Returns dynamic(1)   （dynamic(1)这种就是动态类型的值，它的类型就是：动态类型）

dynamicRecord.four
// Returns dynamic(<null>)      （dynamic(null)这种就是动态类型的值，它的类型就是：动态类型）

dynamicNull.one
// Error: cannot access property "one" on value of type invalid

exists dynamicRecord
// Returns true

exists dynamicNull
// Returns false

array.from(
    rows:[
        {
            recordExists: exists dynamicRecord,
            recordNull: exists dynamicNull
        }
    ]
)

```

#### 案例 3：动态类型转换成 flux 类型

```flux

import "experimental/dynamic"

# string
s = string(v:dynamic.dynamic(v:"hello"))

# int
i = int(v:dynamic.dynamic(v:1))

# float
f = float(v:dynamic.dynamic(v:1.1))

# bool
b = bool(v:dynamic.dynamic(v:true))

# time
t = time(v:dynamic.dynamic(v:2020-01-01T00:00:00Z))

# duration
d = duration(v:dynamic.dynamic(v:1d))

# bytes
bs = bytes(v:dynamic.dynamic(v:1))

# uint
u = uint(v:dynamic.dynamic(v:1))

# regexp 正则表达式
r = regexp(v:dynamic.dynamic(v:"^[abc][123]{1,}"))
regexp.compile(v: r)    // Returns /^[abc][123]{1,}/

```

#### 案例 4：Json 数组转换成 Flux 数组

```flux

import "array"
import "experimental/dynamic"

arr = bytes(v: "[\"3\", 2, true]")

dynamicArr = dynamic.jsonParse(data: arr)

//此时的类型是int类型的数据，而不是stream table[int]
intArr = dynamicArr
            |> dynamic.asArray()
            |> array.map(fn: (x) => int(v: x))

array.from(
    rows: array.map(arr: intArr, fn: (x) => {return {_value: x}})
)
//{_value: x}这个是record expression(记录表达式，相当于表中的一条记录),加上(),那么就是直接将这个record expression直接返回
//如果不加上()，那么就是代码块(Block),当作代码来执行，例如：fn: (x) => {return {_value: x}}，模板：fn: (r) => {代码块}，就和Java中的lambda表达式一样了

```

#### 案例 5：Json Record 转换成 Flux 数组

```flux

import "array"
import "experimental/dynamic"


jsonStr =
    "[
        {\"first-name\": \"John\", \"last-name\": \"Doe\", \"age\": 42},
        {\"first-name\": \"Jane\", \"last-name\": \"Doe\"},
        {\"first-name\": \"James\", \"last-name\": \"Doe\", \"age\": 15}
    ]"

arr = bytes(v: jsonStr)

dynamicArr = dynamic.jsonParse(data: arr)

//此时的类型是int类型的数据，而不是stream table[int]
intArr = dynamicArr
            |> dynamic.asArray()
            |> array.map(fn: (x) => ({
            fname: string(v: x["first-name"]),
            lname: string(v: x["last-name"]),
            age: int(v: x.age)
        }))

array.from(
    rows: array.map(arr: intArr, fn: (x) => {return {fname: x["fname"],lname: x["lname"], age: x["age"]}})
)

//同理
//array.from(
//    rows: array.map(arr: intArr, fn: (x) => ({fname: x["fname"],lname: x["lname"], age: x["age"]}))
//)

```

#### 案例 6：Json Record 转换成 Flux Record

```flux

import "experimental/dynamic"
import "array"
jsonStr = bytes(v: "{\"first-name\": \"John\", \"last-name\": \"Doe\", \"age\": 42}")

parsed = dynamic.jsonParse(data: jsonStr)

recordData = {
    fname: string(v: parsed["first-name"]),
    lname: string(v: parsed["last-name"]),
    age: int(v: parsed.age)
}

array.from(rows: [recordData])

```

#### 案例 7：dynamic.isType()

```flux
import "experimental/dynamic"
import "array"
import "http/requests"

// {
//     "genus": "Malus",
//     "name": "Apple",
//     "id": 6,
//     "family": "Rosaceae",
//     "order": "Rosales",
//     "nutritions": {
//         "carbohydrates": 11.4,
//         "protein": 0.3,
//         "fat": 0.4,
//         "calories": 52,
//         "sugar": 10.3
//     }
// }

response = requests.get(url: "https://www.fruityvice.com/api/fruit/apple")

body = dynamic.jsonParse(data :response.body)

array.from(rows: [{
    a: dynamic.isType(v: body.order,type: "string"),
    b: dynamic.isType(v: body.nutritions,type: "record"),
    c: dynamic.isType(v: body.nutritions,type: "object")
}])

```

#### 案例 8：在表格中包含动态类型（Flux 表不支持动态类型，所以需要将动态类型转换成 Flux 基本类型）

```fulx

import "array"
import "experimental/dynamic"

dynamicString = dynamic.dynamic(v: "one")
dynamicInt = dynamic.dynamic(v: 1)
dynamicFloat = dynamic.dynamic(v: 1.0)
dynamicBool = dynamic.dynamic(v: true)

array.from(
    rows: [
        {
            string: string(v: dynamicString),
            int: int(v: dynamicInt),
            float: float(v: dynamicFloat),
            bool: bool(v: dynamicBool),
        },
    ],
)

```

#### 案例 9：JSON 数组转换成 Flux 表

```flux

//requests获取数据 -> JSONParse -> asArray -> Map

import "http/requests"
import "experimental/dynamic"
import "array"

response = requests.get(url: "https://www.fruityvice.com/api/fruit/all")
body = dynamic.jsonParse(data: response.body)
fruit = dynamic.asArray(v: body)

fruidRecord =
            fruit
                |> array.map(fn:
                    (x) => ({
                        name: string(v: x.name),
                        calories: int(v: x.nutritions.calories),
                        fat: float(v: x.nutritions.fat),
                        sugar: float(v: x.nutritions.sugar)
                    })
                )

array.from(rows: fruidRecord)

```

#### 案例 10：动态类型编码为 JSON

```flux

import "experimental/dynamic"
import "array"

dynamicRecord = dynamic.dynamic(v: {one: 1, two: 2, three: 3})
dynamicStr = dynamic.jsonEncode(v: dynamicRecord)

array.from(rows: [{dynamicStr: display(v: dynamicStr)}])

```

#### 案例 11：多动态类型编码为 JSON

```flux

import "experimental/dynamic"
import "array"

arr =
    dynamic.dynamic(
        v: [
            dynamic.dynamic(v: "three"),
            dynamic.dynamic(v: 2),
            dynamic.dynamic(v: true)
        ],
    )

moreDynamicType = dynamic.jsonEncode(v: arr)

array.from(rows: [{moreDynamicType: display(v: moreDynamicType)}])
```