package com.yibh.mytest;

import android.util.Log;

/**
 * Created by y on 2016/5/24.
 * 排序
 */
public class SortTest {

    /**
     * 冒泡排序
     */
    public static void bubbleSort() {
        int[] test = {1, 99, 30, 21, 50, 60, 3, 0, 55};

        System.out.print("排序前");
        for (int i = 0; i < test.length; i++) {
            System.out.print(test[i]);
        }

        int temp = 0; //中转用
        for (int i = 0; i < test.length - 1; i++) { //执行次数
            for (int j = 0; j < test.length - 1 - i; j++) { //排序,相邻两数字之间进行比较
                if (test[j] < test[j + 1]) {
                    temp = test[j];
                    test[j] = test[j + 1];
                    test[j + 1] = temp;
                }
            }
        }

        System.out.print("排序后");
        for (int i = 0; i < test.length; i++) {
            System.out.print(test[i]);
            Log.w("",test[i]+"");
        }
    }

}
