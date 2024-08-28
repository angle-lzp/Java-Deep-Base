package com.angelo.gitapplication.feature;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * author: Angelo.Luo
 * date : 08/28/2024 4:08 PM
 * description:
 */
public class LocalDateTimeDemo02 {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();

        //这个月第70个星期五的日期（70个明显是超过了，他就会往后找，九月十月...）
        LocalDate with = localDate.with(TemporalAdjusters.dayOfWeekInMonth(70, DayOfWeek.THURSDAY));
        //1:第一个；DayOfWeek.THURSDAY：星期五；这个月的第一个星期五的日期
        //LocalDate with2 = localDate.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.THURSDAY));
        System.out.println(with);

        //其实就是传入一个日期调整器，然后就会返回一个被调整后的日期
        //TemporalAdjusters.ofDateAdjuster(date -> date.plusDays(7))：创建了一个自定义的日期调整器（这里是将日期向后移动七天）
        LocalDate with1 = localDate.with(TemporalAdjusters.ofDateAdjuster(date -> date.plusDays(7)));
        System.out.println(with1);

        System.out.println("------------------First Day-----------------------");
        System.out.println(localDate.with(TemporalAdjusters.firstDayOfMonth()));//这个月的第一天
        System.out.println(localDate.with(TemporalAdjusters.firstDayOfNextMonth()));//下一个月的第一天
        System.out.println(localDate.with(TemporalAdjusters.firstDayOfYear()));//这一年的第一天
        System.out.println(localDate.with(TemporalAdjusters.firstDayOfNextYear()));//下一年的第一天
        System.out.println(localDate.with(TemporalAdjusters.firstInMonth(DayOfWeek.FRIDAY)));//这个月第一个周五

        System.out.println("------------------Last Day-----------------------");
        System.out.println(localDate.with(TemporalAdjusters.lastDayOfMonth()));//这个月的最后一天
        System.out.println(localDate.with(TemporalAdjusters.lastDayOfYear()));//这年的最后一天
        System.out.println(localDate.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY)));//这个月的最后一个周一

        System.out.println("------------------Next-----------------------");
        System.out.println(localDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)));//下一个周五(如果今天是周五，还是找下一个周五)
        System.out.println(localDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY)));//上一个周五(如果今天是周五，还是找下一个周五)
        System.out.println(localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)));//下一个周五，如果今天是周五，就返回今天的日期
        System.out.println(localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.FRIDAY)));//上一个周五，如果今天是周五，就返回今天的日期


    }
}
