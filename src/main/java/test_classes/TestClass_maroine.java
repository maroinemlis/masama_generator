/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_classes;

import db.bean.SQLSchema;
import db.connection.SQLConnection;
import static db.validation.PreCondetion.CHECKED_TRUE;

/**
 *
 * @author amirouche
 */
public class TestClass_maroine {

    SQLSchema sqlSchema;

    public void main() throws Exception {

        SQLConnection cnx = new SQLConnection("C:\\Users\\tamac\\OneDrive\\Desktop\\test.sql", "SQLite", false);
        sqlSchema = new SQLSchema();

        sqlSchema.getTables().get(0).setHowMuch(3);
        System.out.println("end");

        long startTime = System.nanoTime();

        sqlSchema.startToGenerateInstances();

        long elapsedTime = System.nanoTime() - startTime;

        System.out.println("Total execution time to create 1000K objects in Java in millis: "
                + elapsedTime / 1000000);

    }

}
