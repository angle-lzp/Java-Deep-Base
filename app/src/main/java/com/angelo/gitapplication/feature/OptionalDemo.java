package com.angelo.gitapplication.feature;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * author: Angelo.Luo
 * date : 08/29/2024 9:31 AM
 * description:
 */
public class OptionalDemo {
    public static void main(String[] args) {
        //创建值方式一：
        Optional<Integer> optional = Optional.of(123);
        //创建值方式二：
        Optional<Object> optional1 = Optional.ofNullable(Set.of(1, 2, 3, 5, 6, 7));
        //如果值为null直接抛出异常，不建议使用get方法
        //Object o = optional1.get();
        //如果为null返回另外一个自定义的Optional对象
        //Optional<Object> or = optional1.or(() -> Optional.of("没有值！"));
        //获取值，为null返回other值
        Object orElse = optional1.orElse("没有值!");
        System.out.println(orElse);
        //获取值，为null返回函数式接口Supplier返回的值
        Object orElseGet = optional1.orElseGet(() -> "没有值！");
        System.out.println(orElseGet);
        //获取值，为null抛出异常
        //Object orElseThrow = optional1.orElseThrow();
        //获取值，为null抛出自定义异常
        Object orElseThrow = optional1.orElseThrow(NullPointerException::new);
        Optional<Object> filterOp = optional1.filter(value -> value instanceof List && ((List<?>) value).size() > 2);
        System.out.println(filterOp.orElse("搞没值了！"));
        //判断是否为null
        //boolean empty = optional1.isEmpty();
        //判断是否存在（是否不为null）
        boolean present = optional1.isPresent();
        //如果存在那么执行函数式接口Consumer中的方法
        optional1.ifPresent(System.out::println);

        //常用方法
        Optional<List<Optional<Computer>>> optionals = Optional.of(List.of(Optional.of(new Computer(Optional.of("angelo"), 1)),
                Optional.of(new Computer(Optional.of("luo"), 2))));
        Object oredElse = optionals.map(value -> value.get(0)).flatMap(t -> t).map(Computer::getUserName).flatMap(t -> t).orElse("没值了！");
        System.out.println(oredElse);

    }
}

class Computer {

    private Optional userName;

    private Integer age;

    public Computer(Optional userName, Integer age) {
        this.userName = userName;
        this.age = age;
    }

    public void setUserName(Optional userName) {
        this.userName = userName;
    }

    public Optional getUserName() {
        return userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
