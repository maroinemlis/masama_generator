/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import db.bean.Attribute;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amirouche
 */
public final class ShemaUtil {

    private static List<Attribute> listAttributes = new ArrayList<>();

    public static boolean isCirculerAndEmpty(Attribute attribute) {
        boolean result = false;
        if (listAttributes.contains(attribute)) {
            listAttributes.clear();
            return true;
        } else if (!attribute.getReferences().isEmpty() && !attribute.getReferences().get(0).getInstances().isEmpty()) {
            listAttributes.clear();
            return false;
        } else {
            listAttributes.add(attribute);
            if (!attribute.getReferences().isEmpty()) {
                result = result || isCirculerAndEmpty(attribute.getReferences().get(0));
            }
        }
        listAttributes.clear();
        return result;
    }

    public static boolean isCirculer(Attribute attribute) {
        boolean result = false;
        if (listAttributes.contains(attribute)) {
            listAttributes.clear();
            return true;
        } else {
            listAttributes.add(attribute);
            if (!attribute.getReferences().isEmpty()) {
                result = result || isCirculer(attribute.getReferences().get(0));
            }
        }
        listAttributes.clear();
        return result;
    }

}
