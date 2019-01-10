/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_classes;

import db.utils.DataGenerator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amirouche
 */
public class TestClass_saad {

    public void main() {
        List<String> l = new ArrayList<>();
        l = DataGenerator.generateUniqueIntTest(l, 1, 10, 9);
        System.out.println("list = " + l);
        System.out.println("SAAD test");
    }
    
}
