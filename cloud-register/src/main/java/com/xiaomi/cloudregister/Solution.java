package com.xiaomi.cloudregister;

import java.util.*;
//问题：给定一个范围，找出所有的步进数。步进数是指从左到右或者从右到左，相邻数字的差值都是1的数。例如，123和321都是步进数。
public class Solution {

    public ArrayList<Integer> countSteppingNumbers (int low, int high) {
        // 返回步进数
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = low; i <= high; i++) {
            if (isUpSteppingNumber(i)|| isDownSteppingNumber(i)) {
                result.add(i);
            }
        }
        return result;
    }

    private boolean isUpSteppingNumber(int num) {
        if (num <= 10) {
            return true;
        }
        int last = num % 10;
        int cur = 0;
        while (num > 0) {
            num /= 10;
            cur = num % 10;
            if (last - cur != -1) {
                return false;
            }
            last = cur;
        }
        return true;
    }
    private boolean isDownSteppingNumber(int num) {
        if (num <= 10) {
            return true;
        }
        int last = num % 10;
        int cur = 0;
        while (num > 0) {
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