package com.angelo.gitapplication.feature;

import java.util.IntSummaryStatistics;
import java.util.List;

/**
 * author: Angelo.Luo
 * date : 08/29/2024 11:18 AM
 * description:
 */
public class StreamDemo {
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 34, 4, 5, 6);
        //对数据进行统计
        IntSummaryStatistics intSummaryStatistics = list.stream().mapToInt(it -> it).summaryStatistics();
        System.out.println(intSummaryStatistics.getAverage());
        System.out.println(intSummaryStatistics.getMax());
        System.out.println(intSummaryStatistics.getMin());
        System.out.println(intSummaryStatistics.getCount());
        System.out.println(intSummaryStatistics.getSum());

        intSummaryStatistics.accept(10);//新增一个数据(会对里面的数据进行更改')
        System.out.println(intSummaryStatistics.getSum());
        System.out.println(intSummaryStatistics);

    }
}
