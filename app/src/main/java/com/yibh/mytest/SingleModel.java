package com.yibh.mytest;

/**
 * Created by y on 2016/5/20.
 * java 单利模式
 * 参考:http://www.jianshu.com/p/0dd09187baeb
 */
public class SingleModel {

    /**
     * 单例模式:懒汉式 ,用到时才实例化
     * 加入双重检查锁
     */
    public static class Singleton1 {
        private Singleton1() {
        }

        private static Singleton1 singleton1 = null;

        public static Singleton1 getInstance() {
            if (singleton1 == null) {
                synchronized (Singleton1.class) {
                    if (singleton1 == null) {
                        singleton1 = new Singleton1();
                    }
                }
            }
            return singleton1;
        }

    }

    /**
     * 单例模式:饿汉式 ,提前实例化
     */
    public static class Singleton2 {
        private Singleton2() {
        }

        private Singleton2 singleton2 = new Singleton2();

        public Singleton2 getInstance() {
            return singleton2;
        }

    }


}
