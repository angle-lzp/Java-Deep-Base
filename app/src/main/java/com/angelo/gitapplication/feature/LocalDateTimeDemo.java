package com.angelo.gitapplication.feature;

import android.annotation.SuppressLint;
import android.os.Build;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

/**
 * author: Angelo.Luo
 * date : 08/28/2024 1:46 PM
 * description:
 */
public class LocalDateTimeDemo {
    public static void main(String[] args) throws ParseException {
        LocalDate localDate = LocalDate.now();
        System.out.printf("LocalDate：%s%n", localDate);
        LocalTime localTime = LocalTime.now().withNano(0);
        System.out.printf("LocalTime：%s%n", localTime);
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf("LocalDateTime：%s%n", localDateTime.format(dateTimeFormatter));

        LocalDate localDate2 = LocalDate.of(2018, 11, 23);
        LocalDate localDate1 = LocalDate.parse("2018-02-23");
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate parse = LocalDate.parse("2019/12/02", dateTimeFormatter1);
        System.out.println("解析后的数据：" + parse);

        //LocalTime:同上
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse("2021-01-26");

        LocalDate localDate3 = LocalDate.now();
        LocalDate localDate4 = localDate3.plus(1, ChronoUnit.WEEKS);//日期往后加一周
        LocalDate localDate5 = localDate3.plusDays(7);//日期往后加7天
        //加年月日同上
        System.out.printf("now：%s,after seven days：%s,other after seven days：%s%n", localDate3, localDate4, localDate5);
        LocalTime localTime1 = LocalTime.now();
        LocalTime localTime2 = localTime1.plusHours(1);//时间往后加一个小时
        //加时分秒毫秒同上

        LocalDateTime localDateTime1 = LocalDateTime.now();
        LocalDateTime localDateTime2 = localDateTime1.plusDays(1);//日期时间往后加一个小时
        //加年月日时分秒毫秒星期同上：是LocalDate和LocalTime plusXXX的集合

        //判断两个时间相隔的数据
        LocalDateTime beginDateTime = LocalDateTime.of(2019, 2, 23, 12, 23, 45);
        LocalDateTime endDateTime = LocalDateTime.of(2019, 12, 20, 11, 23, 35);
        Period period = Period.between(beginDateTime.toLocalDate(), endDateTime.toLocalDate());
        //表示相差0年9月27天：period.getDays()这个天是值除去月份不足整月剩余的天
        System.out.printf("Year：%s  Month：%s    Day：%s%n", period.getYears(), period.getMonths(), period.getDays());
        //要求相差总天数：
        System.out.printf("Total days difference：%s%n", endDateTime.toLocalDate().toEpochDay() - beginDateTime.toLocalDate().toEpochDay());

        Duration duration = Duration.between(beginDateTime, endDateTime);
        //Day：299  Hour：7198    Minute：431939 Second：25916390;这里相差的天、小时、分钟指的是总的相差是单独的个体
        //相差299天(后面可能还有余0.8天给省略了)，相差7198小时(后面可能还有余0.7小时给省略了)，431939分钟（同理），25916390秒（同理，可以还余0.5秒）
        System.out.printf("Day：%s  Hour：%s    Minute：%s Second：%s%n", duration.toDays(), duration.toHours(), duration.toMinutes(),
                duration.getSeconds());

        //Duration好像不是使用LocalDate就是不能使用日期类型的数据，可以使用日期时间、时间
        //Duration duration1 = Duration.between(beginDateTime.toLocalDate(), endDateTime.toLocalDate());
        //System.out.println("Days：" + duration1.toDays());

        //可用于计算时分秒
        Duration duration2 = Duration.between(beginDateTime.toLocalTime(), endDateTime.toLocalTime());
        System.out.printf("Day：%s  Hour：%s    Minute：%s Second：%s%n", duration2.toDays(), duration2.toHours(), duration2.toMinutes(),
                duration2.getSeconds());


        //todo 注：如果要比较日期使用Period：Period.between(beginDateTime.toLocalDate(), endDateTime.toLocalDate());
        //todo 如果要比较时间使用duration：Duration.between(beginDateTime, endDateTime);
    }

}
