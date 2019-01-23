package test_classes;

import db.bean.Attribute;
import db.bean.SQLSchema;
import db.bean.Table;
import db.connection.ExecuteQuery;
import db.connection.SQLConnection;
import db.utils.DataGenerator;
import db.utils.StringUtil;
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
    SQLConnection cnx;

    public void main() throws Exception {
        //bugs************************************
        //todo: db.utils.ShemaUtil.whichIsCircular() || resolve problem of get(0)
        //todo : in configuration : if one of feild is empty is make ERROR
        //              -improved the interface
        //todo :improved the interface and fix the hes bugs
        //todo :refactor the code of the mainControler
        //todo : pré condetion check if boolean is unique then 2 value max
        //todo : l'app quand il y a une valeur REAL ,BLOB,Boolean elle ne peut pas afficher (avec le type double elle affiche bien)
        //todo : take in interface the case of pre data
        //todo : circular scehema withe and one of the attribute referance to autre attribute
        //bugs************************************;
        /*System.out.println(10 % 4);
        //List l = Arrays.asList("4", "3");
        List<String> l = new ArrayList<>();
        l = DataGenerator.generateUniqueIntTest(l, 10, 15, 5);
        System.out.println("list = " + l);
        System.out.println("Amirouche Test");*/

        cnx = new SQLConnection("/home/amirouche/NetBeansProjects/masama_generator/SQL/"
                //+ "theCompleteTest.sql", "SQLite", false);
                + "3_tables_inheritance.sql", "SQLite", false);

        int nbrRow = 5;
        sqlSchema = new SQLSchema(true, cnx);
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
            String querys = StringUtil.getInsert(sqlSchema);
            System.out.println(querys);
            ExecuteQuery eq = new ExecuteQuery(cnx.getConnection());
            System.out.println(eq.executeUpdateOrInsert(querys));
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
