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
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.TitledPane;

/**
 *
 * @author amirouche
 */
public class TestClass_amirouche {

    SQLSchema sqlSchema;

    public void main() throws Exception {
        //amirouche I am in branch ma
        //todo :: le clé prémair sont il possible de generé pour les chane
        SQLConnection cnx = new SQLConnection("/home/amirouche/NetBeansProjects/MASAMA/mySQL/test.sql", "SQLite", false);
        sqlSchema = new SQLSchema();
        sqlSchema.getTables().get(0).setHowMuch(0);
        sqlSchema.getTables().get(0).getAttributes().get(1).getDataFaker().setFrom("2");
        sqlSchema.getTables().get(0).getAttributes().get(1).getDataFaker().setTo("4");
        sqlSchema.getTables().get(1).setHowMuch(10);

        /*sqlSchema.getTables().get(1).getAttributes().get(1).getDataFaker().setNullsRate(50);
        sqlSchema.getTables().get(1).setNullsRate(50);
        sqlSchema.getTables().get(2).setHowMuch(4);*/
        testPrecondetion();
        //testSaveProject();
    }

    private void testPrecondetion() throws Exception {

        PreCondetion preCondetion = new PreCondetion(sqlSchema);
        String msgCheck = preCondetion.checkSqlScema();
        if (msgCheck.equals(CHECKED_TRUE)) {
            System.out.println("we can generate");
            sqlSchema.startToGenerateInstances();
            for (Table table : sqlSchema.getTables()) {
                table.show();
            }
        } else {
            System.out.println(msgCheck);
        }
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
