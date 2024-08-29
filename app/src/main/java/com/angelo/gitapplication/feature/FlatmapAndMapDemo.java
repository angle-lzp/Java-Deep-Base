package com.angelo.gitapplication.feature;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * author: Angelo.Luo
 * date : 08/28/2024 10:43 AM
 * description:flatMap和map的区别
 */
public class FlatmapAndMapDemo {
    public static void main(String[] args) {

        City city = new City("angelo", 26);
        Nation nation = new Nation(Optional.of(city));

        Optional<Nation> angelo = Optional.ofNullable(nation);
        String data = angelo.flatMap(Nation::getCity).map(City::getUsername).orElse("Error");

        Optional<City> city3 = nation.getCity();

        //这里需要注意下：
        //flatMap：直接返回的是值，不会对数据进行封装（在Function中返回什么就是什么）
        //flatMap:函数值接口的Function的入参就是这个Optional中的value
        Optional<City> city2 = angelo.flatMap(Nation::getCity);
        //map：返回的是Optional中，会对数据进行封装
        //map:函数值接口的Function的入参就是这个Optional中的value(同上)
        Optional<Optional<City>> city1 = angelo.map(Nation::getCity);

        System.out.println(DateFormat.getDateInstance().format(new Date()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

    }
}


class Nation {
    private Optional<City> city;

    public Nation() {

    }

    public Nation(Optional<City> city) {
        this.city = city;
    }

    public Optional<City> getCity() {
        return city;
    }

    public void setCity(Optional<City> city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Nation{" +
                "city=" + city +
                '}';
    }
}

class City {
    private String username;
    private Integer age;

    public City() {

    }

    public City(String username, Integer age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "City{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
