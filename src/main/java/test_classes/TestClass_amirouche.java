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
        //amirouche I am in branch mastre
        //todo :: le clé prémair sont il possible de generé pour les chane
        //todo :: test the code withe the complicate schema SQLITE
        //todo :: if the float or double we don't to make intervale
        //todo :: add exeption if the sql file is error (show msg with error sql)
        //todo :: add exeption(message) when we reference table not exist
        //  (beceaus sqlite don't check this)
        //  try with file
        //todo :: add exeption(message) when we drop table not exist
        //todo :: check the spealing of message text in String Util.java
        //todo :: the tables is generated in alphabitique order
        //todo :: tow tuple foreign key reference to on tuple
        //todo :: virifie l'exmple de profe

        //Résolu :: setHowMuch ne fonction pas avec les table
        //    pour spicéfai le how much il faut le donné au primery key
        //Résolu :: les préCondetion ne marchent plus c'est le howMush de table ne marche pas
        //todo:: c'est l'un des exeptions gérér est survicu ne continuit pas
        //dans la génération c'est
        //todo :: it is possible to have more then one tuple primary key
        SQLConnection cnx = new SQLConnection(
                "/home/amirouche/NetBeansProjects/masama_generator/SQL/errorSchema.sql", "SQLite", false);
        sqlSchema = new SQLSchema();

        /*
        sqlSchema.getTables().get(0).setHowMuch(3);
        sqlSchema.getTables().get(1).setHowMuch(3);
        sqlSchema.getTables().get(2).setHowMuch(3);
        sqlSchema.getTables().get(3).setHowMuch(3);
        sqlSchema.getTables().get(4).setHowMuch(3);
        sqlSchema.getTables().get(5).setHowMuch(3);
        sqlSchema.getTables().get(6).setHowMuch(3);
        sqlSchema.getTables().get(7).setHowMuch(3);
        sqlSchema.getTables().get(8).setHowMuch(3);
        sqlSchema.getTables().get(9).setHowMuch(3);
         */
        //sqlSchema.getTables().get(3).setHowMuch(3);
        //sqlSchema.getTables().get(0).getAttributes().get(0).getDataFaker().setHowMuch(3);
        //sqlSchema.getTables().get(0).getAttributes().get(1).getDataFaker().setHowMuch(3);

        /*sqlSchema.getTables().get(0).setHowMuch(3);
        sqlSchema.getTables().get(1).setHowMuch(3);
        sqlSchema.getTables().get(2).setHowMuch(30);
        sqlSchema.getTables().get(3).setHowMuch(3);*/
        //sqlSchema.getTables().get(2).setHowMuch(3);
        //sqlSchema.getTables().get(3).setHowMuch(3);
        //sqlSchema.getTables().get(3).setHowMuch(3);
        //sqlSchema.getTables().get(4).setHowMuch(3);
        //sqlSchema.getTables().get(0).getAttributes().get(2).getDataFaker().setFrom("3");
        //sqlSchema.getTables().get(0).getAttributes().get(2).getDataFaker().setTo("4");
        //sqlSchema.getTables().get(1).setHowMuch(3);
        /*sqlSchema.getTables().get(2).setHowMuch(5);
        sqlSchema.getTables().get(0).setHowMuch(0);

        sqlSchema.getTables().get(1).setHowMuch(10);*/

 /*  sqlSchema.getTables().get(1).getAttributes().get(1).getDataFaker().setNullsRate(50);
            sqlSchema.getTables().get(1).setNullsRate(50);
            sqlSchema.getTables().get(2).setHowMuch(4);
         */
        testPrecondetion();
        //testSaveProject();
    }

    private void testPrecondetion() throws Exception {

        PreCondetion preCondetion = new PreCondetion(sqlSchema);
        String msgCheck = preCondetion.checkSqlSchema();
        if (msgCheck.equals(CHECKED_TRUE)) {
            System.out.println("we can generate");
            if (!isErrorInTable()) {
                sqlSchema.startToGenerateInstances();
                List<Table> tables = sqlSchema.getTables();
                for (Table table : tables) {
                    table.show();
                }
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

    private void testSaveProject() {
        //j'ai decomenté la method toString de Attribure
        /*
         j'ai suprimmer de la class DataFaker
        *   protected Faker faker;
        *
         j'ai suprimmer de la class Table
        *   private JFXTreeTableView<AttributeModel> tableView;
        *   private SwingNode insertsView = null;
        *
         j'ai suprémir (commenté)quellque line dans dans MainController
        * cherche sur delete amirouche
        *
        *
         */

        sqlSchema.startToGenerateInstances();
        SaveProject saveProject = new SaveProject(sqlSchema);
        String path = "/home/amirouche/NetBeansProjects/masama_generator/SaveProject";
        saveProject.saveSQLSchema(path);
    }

}
