package test_classes;

import db.bean.Attribute;
import db.bean.SQLSchema;
import db.bean.Table;
import db.connection.SQLConnection;
import db.utils.DataGenerator;
import db.validation.PreCondetion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author amirouche
 */
public class TestClass_amirouche {

    SQLSchema sqlSchema;

    public void main() throws Exception {
        //bugs************************************
        //todo: db.utils.ShemaUtil.whichIsCircular() || resolve problem of get(0)
        //todo: whene took the pre data check the unique contrainte
        //resolved: one table to Tow table the value of to table must have the same
        //todo : one table to Tow table and one of this table refere to auther table "oneToTowTablesToOne.sql"
        //todo : in configuration : if one of feild is empty is make ERROR
        //              -improved the interface
        //todo :improved the interface and fix the hes bugs
        //todo :refactor the code of the mainControler
        //resolver :réfléchi au problème de la génération aléatoire quand on veut des valeurs distinctes
        //todo : pré condetion check if boolean is unique then 2 value max
        //todo : l'app quand il y a une valeur REAL ,BLOB,Boolean elle ne peut pas afficher (avec le type double elle affiche bien)
        //todo : take in interface the case of pre data
        //bugs************************************;
        /*System.out.println(10 % 4);
        //List l = Arrays.asList("4", "3");
        List<String> l = new ArrayList<>();
        l = DataGenerator.generateUniqueIntTest(l, 10, 15, 5);
        System.out.println("list = " + l);
        System.out.println("Amirouche Test");*/
        SQLConnection cnx = new SQLConnection("/home/amirouche/NetBeansProjects/masama_generator/SQL/"
                + "theCompleteTest.sql", "SQLite", false);
        //+ "table_withe_data.sql", "SQLite", false);

        int nbrRow = 5;
        sqlSchema = new SQLSchema(false, cnx);
        for (Table table : sqlSchema.getTables()) {
            table.setHowMuch(nbrRow);
            for (Attribute attribute : table.getAttributes()) {
                //attribute.getDataFaker().setTo(to);
            }
        }

        //test here :réfléchi au problème de la génération aléatoire quand on veut des valeurs distinctes
        //schema.getTables().get(0).getAttributes().get(3).getDataFaker().setFrom("1");
        //schema.getTables().get(0).getAttributes().get(3).getDataFaker().setTo("7");
        testPrecondetion();
    }

    private void testPrecondetion() throws Exception {

        PreCondetion preCondetion = new PreCondetion(sqlSchema);
        boolean isNoContradiction = preCondetion.checkSqlSchema();
        if (isNoContradiction) {
            System.out.println("we can generate");
            sqlSchema.startToGenerateInstances();
        } else {
            System.out.println(preCondetion.getMsgError());
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
