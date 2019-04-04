package com.zxd.xiaoabapp.ui;

/**
 * Created by Administrator on 2018/3/29.
 */

public class Demo {
    public static void main(String[] str){
        A ab = new B();
        ab = new B();
    }


}


class A{
    static {
        System.out.print("1");
    }

    public A(){
        System.out.print("2");
    }
}

class B extends A{
    static {
        System.out.print("a");
    }

    public B(){
        System.out.print("b");
    }
}