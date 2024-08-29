package com.angelo.gitapplication.feature;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * author: Angelo.Luo
 * date : 08/28/2024 5:12 PM
 * description:
 */
public class ZoneDateTimeDemo {
    public static void main(String[] args) {
        //当前时区时间
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println("当前时区时间：" + zonedDateTime);

        //东京时间
        ZoneId zoneId = ZoneId.of(ZoneId.SHORT_IDS.get("JST"));
        ZonedDateTime tokyoTime = zonedDateTime.withZoneSameInstant(zoneId);
        System.out.println("东京时间：" + tokyoTime);


        ZonedDateTime beiJinTime = zonedDateTime.withZoneSameInstant(ZoneId.of(ZoneId.SHORT_IDS.get("CTT")));
        System.out.println("北京时间：" + beiJinTime);

        LocalDateTime localDateTime = beiJinTime.toLocalDateTime();
        System.out.println("ZonedDateTime => LocalDateTime：" + localDateTime);

        ZonedDateTime zonedDateTime1 = localDateTime.atZone(ZoneId.systemDefault());
        System.out.println("LocalDateTime => ZonedDateTime：" + zonedDateTime1);
        ZonedDateTime zonedDateTime2 = localDateTime.atZone(ZoneId.of(ZoneId.SHORT_IDS.get("MIT")));
        System.out.println("LocalDateTime => ZonedDateTime：" + zonedDateTime2);


    }
}
