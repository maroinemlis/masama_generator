/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_classes;

import db.bean.SQLSchema;
import db.bean.Table;
import db.connection.SQLConnection;
import db.save_and_load.SaveProject;
import db.validation.PreCondetion;
import static db.validation.PreCondetion.CHECKED_TRUE;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sqlite.SQLiteException;

/**
 *
 * @author amirouche
 */
public class TestClass_amirouche {

    SQLSchema sqlSchema;

    public void main() throws Exception {
        SQLConnection cnx = new SQLConnection(
                "/home/amirouche/NetBeansProjects/masama_generator/SQL/3_tables.sql", "SQLite", false);
        int nbrRow = 5;
        sqlSchema = new SQLSchema();
        sqlSchema.getTables().get(0).setHowMuch(nbrRow);
        sqlSchema.getTables().get(0).getAttributes().get(3).getDataFaker().setFrom("1");
        sqlSchema.getTables().get(0).getAttributes().get(3).getDataFaker().setTo("7");
        sqlSchema.getTables().get(1).setHowMuch(nbrRow);
        sqlSchema.getTables().get(2).setHowMuch(nbrRow);
        sqlSchema.startToGenerateInstances();
        for (Table table : sqlSchema.getTables()) {
            table.show();
        }
        //testPrecondetion();
    }

    private void testPrecondetion() throws Exception {

        PreCondetion preCondetion = new PreCondetion(sqlSchema);
        String msgCheck = preCondetion.checkSqlSchema();
        if (msgCheck.equals(CHECKED_TRUE)) {
            System.out.println("we can generate");
            if (!isErrorInTable()) {
                sqlSchema.startToGenerateInstances();
                List<Table> tables = sqlSchema.getTables();
            }
        } else {
            System.out.println(msgCheck);
        }

    }

    private boolean isErrorInTable() {
        for (Table table : sqlSchema.getTables()) {
            if (table.getIsErrorInTable()) {
                return true;
            }
        }
        return false;
    }

}
