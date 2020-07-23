package com.yuan.redis.test;

import com.rabbitmq.tools.json.JSONUtil;
import org.w3c.dom.ls.LSOutput;

public class Sort {


    /**
     * 冒泡算法
     *
     * @param arr
     */
    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 选择排序
     *
     * @param arr
     */
    public static void chooseSort(int[] arr) {
        int index = 0;
        int max = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (max < arr[j]) {
                    max = arr[j];
                    index = j;
                }
            }
            int temp = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = arr[index];
            arr[index] = temp;
            max = 0;
            index = 0;
        }
    }


    public static void show(int[] arr) {
        for (int a : arr) {
            System.out.print(a + "   ");
        }
    }

    public static void main(String[] args) {
        int[] a = {3, 1, 88, 53, 23, 3, 12, 123, 444};
//        bubbleSort(a);
        chooseSort(a);
        show(a);

    }


}
