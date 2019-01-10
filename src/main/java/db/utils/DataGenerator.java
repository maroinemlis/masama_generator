/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author amirouche
 */
public final class DataGenerator {

    /**
     *
     *
     */
    public static List<String> generateUniqueInt(List<String> preDate, int from, int to, int size) {
        //System.out.println("list begin: " + preDate);
        Random random = new Random();
        List<String> result = new ArrayList<>();
        if (preDate.size() > size) {
            result = preDate.stream().limit(size).collect(Collectors.toList());
        } else {
            result.addAll(preDate);
            int i = from;
            System.out.println("size : " + size);
            while (result.size() < size) {
                if (!result.contains(String.valueOf(i))) {
                    result.add(String.valueOf(i));
                }
                i++;
            }
        }
        //System.out.println("list end: " + result);
        return result;
    }

    public static List<String> generateUniqueIntTest(List<String> preDate, int from, int to, int size) {
        //System.out.println("list begin: " + preDate);
        Random random = new Random();
        List<String> result = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            result = new ArrayList<>();
            int jumper = i;//random.nextInt(to - from);
            System.out.println("jumper :" + jumper);
            if (preDate.size() > size) {
                result = preDate.stream().limit(size).collect(Collectors.toList());
            } else {
                result.addAll(preDate);
                int j = from;
                while (result.size() < size) {
                    j += jumper;
                    j = (j > to) ? j % to : j;
                    System.out.println("j :" + j);
                    if (!result.contains(String.valueOf(j))) {
                        j = (j < from) ? j + from : j;
                        if (!result.contains(String.valueOf(j))) {
                            result.add(String.valueOf(j));
                        }
                        jumper--;
                        jumper = (jumper == 0) ? to - from : jumper;
                        System.out.println("jumper :" + jumper);
                    }
                }
            }
            System.out.println(result);
        }
        //System.out.println("list end: " + result);
        return result;
    }

}
