/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_classes;

import com.github.javafaker.Faker;
import db.bean.SQLSchema;
import db.connection.SQLConnection;
import java.util.ArrayList;

/**
 *
 * @author Maroine
 */
public class TestClass_maroine {

    public void main() throws Exception {

        SQLConnection cnx = new SQLConnection("C:\\netbeansProjects\\masama_generator\\SQL\\4_tables.sql", "SQLite", false);
        SQLSchema sqlSchema = new SQLSchema();

        sqlSchema.getTables().get(0).setHowMuch(3);
        sqlSchema.getTables().get(1).setHowMuch(3);
        sqlSchema.getTables().get(2).setHowMuch(3);
        sqlSchema.getTables().get(3).setHowMuch(3);

    }
}
