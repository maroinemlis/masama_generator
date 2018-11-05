/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_classes;

import com.github.javafaker.Faker;
import db.utils.DataFakerWraper;
import java.util.ArrayList;

/**
 *
 * @author Maroine
 */
public class TestClass_maroine {

    public static void test() {
        DataFakerWraper fk = new DataFakerWraper("TEXT");
        ArrayList<String> values = fk.values();
        System.out.println(values);
    }
}
