/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amirouche
 */
public class ListUtils {
      public List<String> generateInstanceForFogeignkey(List<String> instances,int howMuch) {
        ArrayList<String> list=new ArrayList<>();
         for (int i = 0; i < howMuch; i++) {
             list.add(instances.get(i));
         }                
         return list;
    }
}
