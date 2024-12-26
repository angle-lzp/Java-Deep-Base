package com.angelo.gitapplication.influxDB

/**
 * author: Angelo.Luo
 * date : 11/01/2024 3:23 PM
 * description:
 */
import com.influxdb.client.InfluxDBClientFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun main() {
    // InfluxDB连接配置
    val url = "http://10.64.18.35:8086"
    val token = "H0bVxWQB7LJVaYctPb5DgCcOIuJO_M55a6kWiWVs4nlO81rweQ2IMlPxbg_58lnobOemqHdb9npeEjvHVgL40Q=="
    val org = "tti"
    val bucket = "iot"

    // 创建InfluxDB客户端
    val influxDBClient = InfluxDBClientFactory.create(url, token.toCharArray(), org)
    val queryApi = influxDBClient.queryApi

    // 获取当前时间和24小时前的时间
    val now = Instant.now()
    val start = now.minusSeconds(24 * 60 * 60)  // 24小时前

    // 定义查询语句 |> range(start: 2024-11-25T14:00:00Z, stop: 2024-11-26T11:00:00Z)
    val query = """
        from(bucket: "$bucket")
        |> range(start: 2024-11-26T06:45:00Z, stop: 2024-11-26T16:00:00Z)
        |> filter(fn: (r) => r["_measurement"] == "lift")
        |> filter(fn: (r) => r["_field"] == "accelX")
        |> filter(fn: (r) => r["deviceId"] == "WT6300003435")
        |> timeShift(duration: 8h)
        |> sort(columns:["_time"], desc: true)
    """.trimIndent()

    // 执行查询
    val tables = queryApi.query(query)

    // 创建Excel工作簿和工作表
    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("Lift Data")

    // 写入表头
    val headerRow = sheet.createRow(0)
    headerRow.createCell(0).setCellValue("时间")
    headerRow.createCell(1).setCellValue("X轴数据")
    headerRow.createCell(2).setCellValue("日期")

    // 写入数据行
    var rowIndex = 1
    for (table in tables) {
        for (record in table.records) {

            //如果有其他的字段_field，需要加一个判断
            //if("accelX".equals(record.field)){}
            val instant = Instant.parse(record.time.toString())
            val row = sheet.createRow(rowIndex++)
            row.createCell(0).setCellValue(
                instant.atZone(ZoneId.of("UTC")).toLocalDateTime().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                )
            )
            row.createCell(1).setCellValue(record.value.toString())
            row.createCell(2).setCellValue(
                instant.atZone(ZoneId.of("UTC")).toLocalDateTime().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd/HH")
                )
            )
        }
    }

    // 保存Excel文件
    val fileOut = FileOutputStream("lift_data.xlsx")
    workbook.write(fileOut)
    fileOut.close()
    workbook.close()

    // 关闭InfluxDB客户端
    influxDBClient.close()
}