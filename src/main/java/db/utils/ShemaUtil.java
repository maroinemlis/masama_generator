/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.utils;

import db.bean.Attribute;
import db.bean.SQLSchema;
import db.bean.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amirouche
 */
public final class ShemaUtil {

    private static List<Attribute> listAttributes = new ArrayList<>();

    private static List<Attribute> listAttributes2 = new ArrayList<>();

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
        //System.out.println("boo: " + result);
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

    public static List<Attribute> whichIsCircular(Attribute attribute) {
        if (listAttributes.contains(attribute)) {
            List<Attribute> result = listAttributes;
            listAttributes.clear();
            return result;
        } else {
            listAttributes.add(attribute);
            if (!attribute.getReferences().isEmpty()) {
                whichIsCircular(attribute.getReferences().get(0));
            }
        }
        List<Attribute> result = listAttributes;
        listAttributes.clear();
        return result;
    }

    public static boolean isAttrCirculairesEquals(SQLSchema sqlSchema) {
        List<Attribute> listAttributes = new ArrayList<>();
        for (Table table : sqlSchema.getTables()) {
            for (Attribute attribute : table.getAttributes()) {
                if (isCirculer(attribute)) {
                    listAttributes = whichIsCircular(attribute);
                    if (!isHaveSameRaws(listAttributes)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean isHaveSameRaws(List<Attribute> listAttributes) {
        return listAttributes.stream().map(a -> a.getDataFaker().getHowMuch())
                .distinct()
                .limit(2)
                .count()
                <= 1;
    }
    static List<String> list;

    public static void generateSameValueToReferences(Attribute attribute) {
        list = attribute.getReferences().get(0).getInstances();
        attribute.setInstances(list);
        for (Attribute reference : attribute.getReferences()) {
            if (!reference.getReferences().isEmpty()) {
                generateForParents(reference);
            } else {
                reference.setInstances(list);
            }
        }

    }

    private static void generateForParents(Attribute reference) {
        if (reference.getReferences().isEmpty()) {
            reference.setInstances(list);
        } else {
            for (Attribute reference1 : reference.getReferences()) {
                generateForParents(reference1);
                reference.setInstances(list);
            }
        }
    }

    public static void generateAllCirculer(Attribute attribute, List<String> instances) {
        attribute.setInstances(instances);
        if (listAttributes2.contains(attribute)) {
            listAttributes2.clear();
        } else {
            listAttributes2.add(attribute);
            for (Attribute reference : attribute.getReferences()) {
                generateAllCirculer(reference, instances);
            }

        }
        listAttributes2.clear();
    }
}
