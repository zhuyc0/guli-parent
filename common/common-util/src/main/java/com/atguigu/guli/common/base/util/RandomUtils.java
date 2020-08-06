package com.atguigu.guli.common.base.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author <a href="mailto:zyc199777@gmail.com">Zhu yc</a>
 * @version 1.0
 * @date 2020年07月28日
 */
public class RandomUtils {
    private static final Random RANDOM = new Random();

    private static final DecimalFormat FOURDF = new DecimalFormat("0000");

    private static final DecimalFormat SIXDF = new DecimalFormat("000000");

    public static String getFourBitRandom() {
        return FOURDF.format(RANDOM.nextInt(10000));
    }

    public static String getSixBitRandom() {
        return SIXDF.format(RANDOM.nextInt(1000000));
    }

    /**
     * 给定数组，抽取n个数据
     */
    public static <E> ArrayList<E> getRandom(List<E> list, int n) {

        Random random = new Random();

        HashMap<Integer, Integer> hashMap = new HashMap<>();

        // 生成随机数字并存入HashMap
        for (int i = 0; i < list.size(); i++) {

            int number = random.nextInt(100) + 1;

            hashMap.put(number, i);
        }

        // 从HashMap导入数组
        Object[] robjs = hashMap.values().toArray();

        ArrayList<E> r = new ArrayList<>();

        // 遍历数组并打印数据
        for (int i = 0; i < n; i++) {
            r.add(list.get((int) robjs[i]));
            System.out.print(list.get((int) robjs[i]) + "\t");
        }
        System.out.print("\n");
        return r;
    }
}
