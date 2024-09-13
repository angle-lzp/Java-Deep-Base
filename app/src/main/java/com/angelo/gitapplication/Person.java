package com.angelo.gitapplication;

/**
 * author: Angelo.Luo
 * date : 05/20/2024 3:52 PM
 * description:
 */
public class Person {
    public static void main(String[] args) {
        getI();
        getNum();
    }

    public static void getI() {
        int i = 1;
        int j = i++ * 2 + 3 * --i;
        //第一步：i++取值i为1，1*2=2
        //第二部：i++加1，i=2；
        //第三步：--i减1，i=1；
        //第四步：--i取值为1，1*3=3
        //第五步：求和2+3=5
        //i=1，j=5
        System.out.println(i + "-" + j);
    }

    public static void getNum() {

        int num = 0;
        for (int i = 0; i < 100; i++) {
            num = num++;
            //第一步：num++中的num = 0；
            //第二步：此时num++中的num加1，此时num=1；
            //第三步：最后将第一步得出的0复制给左边的num，所以最终num=0；
            //最后num还是被赋值成了0，永远都是0
        }
        System.out.println(num);
    }
}
