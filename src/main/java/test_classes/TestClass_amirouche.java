/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_classes;

import db.bean.Attribute;
import db.bean.SQLSchema;
import db.bean.Table;
import db.connection.SQLConnection;
import db.validation.PreCondetion;
import static db.validation.PreCondetion.CHECKED_TRUE;
import java.util.List;

/**
 *
 * @author amirouche
 */
public class TestClass_amirouche {

    SQLSchema sqlSchema;

    public void main() throws Exception {

        SQLConnection cnx = new SQLConnection(
                "/home/amirouche/NetBeansProjects/masama_generator/SQL/table_withe_data.sql", "SQLite", false);
        int nbrRow = 3;
        sqlSchema = new SQLSchema(true, cnx);
        sqlSchema.getTables().get(0).setHowMuch(nbrRow);
        sqlSchema.getTables().get(1).setHowMuch(nbrRow);
        sqlSchema.startToGenerateInstances();
        for (Table table : sqlSchema.getTables()) {
            table.show();
        }
        //sqlSchema.getTables().get(0).getAttributes().get(3).getDataFaker().setFrom("1");
        //sqlSchema.getTables().get(0).getAttributes().get(3).getDataFaker().setTo("7");
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
