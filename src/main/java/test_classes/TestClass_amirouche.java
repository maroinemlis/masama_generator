package test_classes;

import beans.Attribute;
import beans.SQLSchema;
import beans.Table;
import connection.SQLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import pre_condition.PreCondetion;

/**
 *
 * @author amirouche
 */
public class TestClass_amirouche {

    SQLSchema sqlSchema;

    public void main() throws Exception {
        //is unique ne march pas
        SQLConnection cnx = new SQLConnection("/home/amirouche/NetBeansProjects/masama_generator/SQL/"
                + "circular.sql", "SQLite", false);
        sqlSchema = SQLSchema.getInstance();
        sqlSchema.constructSchema();
        for (Table table : sqlSchema.getTables()) {
            for (Attribute attribute : table.getAttributes()) {
                attribute.getDataFaker().setFrom("1");
                attribute.getDataFaker().setTo("10000");

            }
            table.setHowMuch(5000);
        }
        sqlSchema.getTables().get(0).getAttributes().get(1).getDataFaker().setFrom("1");
        sqlSchema.getTables().get(0).getAttributes().get(1).getDataFaker().setTo("2");
        PreCondetion pre = new PreCondetion(sqlSchema);
        if (pre.checkSqlSchema()) {
            sqlSchema.startToGenerateInstances();
            sqlSchema.getTables().forEach(t -> t.show());
        } else {
            System.out.println(pre.getMsgError());
        }
    }

}
