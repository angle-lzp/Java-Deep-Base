package com.angelo.gitapplication.spring;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * author: Angelo.Luo
 * date : 08/26/2024 3:37 PM
 * description:
 */
public class StringUtilsDemo {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.version"));
        // todo Assert
        Assert.hasLength("angelo", "字符串不能为空！");
        //Assert.doesNotContain("angelo.luo","luo","字符串：angelo.luo不能包含luo字符串！");

        // todo StringUtils
        String listStr = StringUtils.collectionToCommaDelimitedString(Arrays.asList("angelo", "luo", "lucy"));
        System.out.println(listStr);
        System.out.println(StringUtils.collectionToDelimitedString(new ArrayList<String>() {{
            add("angelo");
            add("luo");
        }}, "|"));

        //判断开头结尾忽略大小写
        String msgStr = "Angelo.luo";
        System.out.println(StringUtils.startsWithIgnoreCase(msgStr, "ang"));
        System.out.println(StringUtils.endsWithIgnoreCase(msgStr, "UO"));
        System.out.println(StringUtils.trimAllWhitespace(" ange uo luo "));//去除所有的空格
        System.out.println(StringUtils.delete("angelo.luo", "luo"));//删除对应的字符串
        System.out.println(StringUtils.deleteAny("angelo.luo", "luo"));//删除：'l','u','o'匹配的字符

        // todo CollectionUtils
        System.out.println(CollectionUtils.arrayToList(new String[]{"angelo", "luo", "lucy", "making"}));
        System.out.println(CollectionUtils.arrayToList(new String[]{"angelo", "luo", "lucy", "making"}) instanceof List<?>);

        List<String> list = new ArrayList<>();
        System.out.println(CollectionUtils.isEmpty(list));
        Map<String, String> map = new HashMap<>();
        System.out.println(CollectionUtils.isEmpty(map));

        List<String> list1 = new ArrayList<>() {{
            add("angelo");
            add("luo");
            add("luo");
        }};

        List<String> list2 = new ArrayList<>() {{
            add("lucy");
            add("making");
        }};
        System.out.println(CollectionUtils.containsAny(list1, list2));
        System.out.println(CollectionUtils.findCommonElementType(list1));
        String[] strArr = new String[list1.size()];
//        System.out.println(Arrays.toString(CollectionUtils.toArray(list1,strArr)));

        // todo ObjectUtils
        System.out.println(ObjectUtils.isEmpty(null));//听说ObjectUtils.isEmpty的判空很厉害

        Person p1 = new Person("angelo", 123);
        Person p2 = new Person("luo", 123);
        Person p3 = null;
        System.out.println(ObjectUtils.nullSafeEquals(p1, p2));

        String[] str1 = new String[]{"123"};
        String[] str2 = new String[]{"123"};
        System.out.println(ObjectUtils.nullSafeEquals(str1, str2));

        //获取对象十六进制的hashCode
        System.out.println(ObjectUtils.getIdentityHexString("angelo"));//String类型


        // todo ClassUtils
        //获取对象的所有接口
        Class<?>[] allInterfaces = ClassUtils.getAllInterfaces(new ArrayList<>());
        for (Class<?> allInterface : allInterfaces) {
            System.out.println(allInterface.getName());
        }
        System.out.println("获取Person类的包名：" + ClassUtils.getPackageName(Person.class));
        System.out.println("是否是内部类：" + ClassUtils.isInnerClass(Person.class));
        System.out.println("是否是内部类：" + ClassUtils.isInnerClass(new ArrayList<String>() {{
        }}.getClass()));
        System.out.println("是否是代理对象：" + ClassUtils.isCglibProxy(Person.class));
        System.out.println("默认构造器：" + ClassUtils.getDefaultClassLoader());
        System.out.println(ClassUtils.addResourcePathToPackagePath(Person.class, "a.txt"));
        System.out.println(ClassUtils.classPackageAsResourcePath(Person.class));
        System.out.println("类名：" + ClassUtils.classNamesToString(Person.class));
        //其实就是类全限定名和资源路径名的相互转换（还是很实用的）
        System.out.println("Class -> Resource：" + ClassUtils.convertClassNameToResourcePath("com.example.navigationapplication.spring" +
                ".Person"));
        System.out.println("Resource -> Class：" + ClassUtils.convertResourcePathToClassName("com/example/navigationapplication/spring" +
                "/Person"));
        System.out.println("Type：" + ClassUtils.getDescriptiveType(new Person("angelo", 23)));//输出该类的全限定名称
        System.out.println("Method：" + ClassUtils.getMethod(Person.class, "getUsername"));//方法
        System.out.println("Class File Name: " + ClassUtils.getClassFileName(Person.class));//直接返回Person.class
        System.out.println("Qualified name：" + ClassUtils.getQualifiedName(Person.class));//不还是全限定类名
        System.out.println("User Class：" + ClassUtils.getUserClass(Person.class));//返回该类的字节码对象

        //todo BeanUtils
        Person person = new Person("lucy", 45);
        Person person1 = new Person();
        //深拷贝：引用类型拷贝的是值，不是引用（只拷贝引用的是浅拷贝）
        BeanUtils.copyProperties(person, person1);

        person1.setUsername("angelo");

        System.out.println(person1);
        System.out.println(person);
        //通过通过反射实例化一个类对象（通过字节码对象创建该类的实例）
        System.out.println(BeanUtils.instantiateClass(person.getClass()));
        //获取指定类的指定方法
        Method getAge = BeanUtils.findDeclaredMethod(Person.class, "getAge");
        System.out.println(getAge);
        //获取指定方法的参数
        Method setAge = BeanUtils.findDeclaredMethod(person.getClass(), "setAge", Integer.class);
        /*java.beans.PropertyDescriptor propertyForMethod = BeanUtils.findPropertyForMethod(setAge);
        System.out.println(propertyForMethod.getName());*/

        //todo ReflectionUtils
        //获取方法
        Method method = ReflectionUtils.findMethod(person.getClass(), "getAge");
        System.out.println(method);
        //获取字段
        Field username = ReflectionUtils.findField(person1.getClass(), "username");
        System.out.println(username);
        //获取可访问的构造器
        try {
            Constructor<? extends Person> constructor = ReflectionUtils.accessibleConstructor(person1.getClass());
            Person p = constructor.newInstance();
            System.out.println(p);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        //执行方法
        assert method != null;
        ReflectionUtils.makeAccessible(method);//需要加入这行，不然invokeMethod的时候抛出异常IllegalStateException，没有访问权限
        System.out.println(ReflectionUtils.invokeMethod(method, person));
        //判断字段是否是常量
        System.out.println(ReflectionUtils.isPublicStaticFinal(username));
        //判断是否是equals方法
        System.out.println(ReflectionUtils.isEqualsMethod(method));

        //todo Base64Utils
        String data = "angelo";
        String encode = new String(Base64Utils.encode(data.getBytes(StandardCharsets.UTF_8)));
        System.out.println("编码数据：" + encode);
        String decode = new String(Base64Utils.decode(encode.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        System.out.println("解码后的数据：" + decode);

        //todo SerializationUtils 序列化和反序列化（以后可以不用实现Serializable这个接口了）
        Map<String, String> serializeData = new HashMap<>() {{
            put("a", "1");
            put("b", "2");
            put("c", "3");
        }};
        byte[] serialize = SerializationUtils.serialize(serializeData);
        Object deserialize = SerializationUtils.deserialize(serialize);
        System.out.println(deserialize);

        //todo HttpStatus 导入依赖：org.springframework:spring-web
        System.out.println(HttpStatus.OK);

        //todo HtmlUtils 对用户输入的信息进行转义，防止SQL注入，或者XSS攻击
        String specialStr = """
                <div id="testDiv">test1;test2</div>
                """;
        String s = HtmlUtils.htmlEscape(specialStr);
        System.out.println(s);
    }
}

class Person {

    private String username;
    private int age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person() {
    }

    public Person(String username, int age) {
        this.username = username;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(username, person.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
