/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import java.util.ArrayList;
import java.util.List;
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
        List<String> result = new ArrayList<>();
        if (preDate.size() > size) {
            result = preDate.stream().limit(size).collect(Collectors.toList());
        } else {
            result.addAll(preDate);
            /*for (int i = from; i <= to; i++) {
                if (result.size() <= size) {
                    if (!result.contains(String.valueOf(i))) {
                        result.add(String.valueOf(i));
                    }
                } else {
                    break;
                }
            }*/
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
}
