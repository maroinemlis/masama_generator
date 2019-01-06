/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_classes;

import db.bean.SQLSchema;
import db.bean.Table;
import db.connection.SQLConnection;
import db.validation.PreCondetion;
import java.util.List;

/**
 *
 * @author amirouche
 */
public class TestClass_amirouche {

    SQLSchema sqlSchema;

    public void main() throws Exception {
        //todo: db.utils.ShemaUtil.whichIsCircular() || resolve problem of get(0)
        //todo: whene took the pre data check the unique contrainte
        SQLConnection cnx = new SQLConnection(
                "/home/amirouche/NetBeansProjects/masama_generator/SQL/oneToTowTables.sql", "SQLite", false);
        int nbrRow = 5;
        sqlSchema = new SQLSchema(true, cnx);
        for (Table table : sqlSchema.getTables()) {
            table.setHowMuch(nbrRow);
        }
        //schema.getTables().get(0).getAttributes().get(3).getDataFaker().setFrom("1");
        //schema.getTables().get(0).getAttributes().get(3).getDataFaker().setTo("7");
        testPrecondetion();
    }

    private void testPrecondetion() throws Exception {

        PreCondetion preCondetion = new PreCondetion(sqlSchema);
        String msgCheck = preCondetion.checkSqlSchema();

        if (msgCheck.equals(PreCondetion.CHECKED_TRUE)) {
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
