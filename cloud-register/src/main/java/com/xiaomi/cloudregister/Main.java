package com.xiaomi.cloudregister;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> res = countSteppingNumbers(10,100);
        System.out.println(res);
    }
    public static ArrayList<Integer> countSteppingNumbers (int low, int high) {
        // 返回步进数
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = low; i <= high; i++) {
            if (isUpSteppingNumber(i)|| isDownSteppingNumber(i)) {
                result.add(i);
            }
        }
        return result;
    }

    public static boolean isUpSteppingNumber(int num) {
        if (num <= 10) {
            return true;
        }
        int last = num % 10;
        int cur = 0;
        while (num > 10) {
            num /= 10;
            cur = num % 10;
            if (last - cur != -1) {
                return false;
            }
            last = cur;
        }
        return true;
    }
    public static boolean isDownSteppingNumber(int num) {
        if (num <= 10) {
            return true;
        }
        int last = num % 10;
        int cur = 0;
        while (num > 10) {
            num /= 10;
            cur = num % 10;
            if (last - cur != 1) {
                return false;
            }
            last = cur;
        }
        return true;
    }

}