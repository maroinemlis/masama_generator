/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_classes;

import beans.SQLSchema;
import connection.SQLConnection;

/**
 *
 * @author amirouche
 */
public class TestClass_maroine {

    SQLSchema sqlSchema;

    public void main() throws Exception {
        SQLConnection.getInstance().connect("C:\\Users\\tamac\\OneDrive\\Desktop\\test.sql", "SQLite", false);
        sqlSchema = SQLSchema.getInstance();
        sqlSchema.constructSchema();
        //sqlSchema.getTables().forEach(t -> t.setHowMuch(10));
        sqlSchema.getTables().get(0).setHowMuch(5);
        sqlSchema.getTables().get(1).setHowMuch(12);

        sqlSchema.startToGenerateInstances();

        sqlSchema.getTables().forEach(t -> t.show());

    }

}
