package test_classes;

import beans.SQLSchema;
import beans.Table;
import connection.SQLConnection;
import controllers.report.SQLExecutionSimulation;

/**
 *
 * @author amirouche
 */
public class TestClass_amirouche {

    SQLSchema sqlSchema;
    private SQLExecutionSimulation executionSimulation;

    public void main() throws Exception {
        //todo: quand on fait une nouvelle connection (dans la interface) il prand tj l'ancien connection
        //todo: replace the code in beans.SQLSchema.resetGeneration() withe the commanted code
        //is unique ne march pas
        SQLConnection.getInstance().connect("/home/amirouche/NetBeansProjects/masama_generator/SQL/"
                + "4_tables.sql", "SQLite", false);
        sqlSchema = SQLSchema.getInstance();
        sqlSchema.constructSchema();
        for (Table table : sqlSchema.getTables()) {
            table.setHowMuch(100);
        }
        sqlSchema.startToGenerateInstances();
        sqlSchema.getTables().forEach(t -> t.show());
        System.out.println("--------------------- ");

        try {
            executionSimulation.setTotalBlocExecution(20);
            executionSimulation.simulate();
            System.out.println("time execution" + executionSimulation.getTotalTime());
        } catch (Exception e) {
            alert.Alerts.error("err");
            System.err.println("err :" + e.getMessage());
        }

        //sqlSchema.getTables().get(0).getAttributes().get(1).getDataFaker().setFrom("1");
        //sqlSchema.getTables().get(0).getAttributes().get(1).getDataFaker().setTo("2");
        /*if (PreCondition.getInstance().checkSQLSchema()) {
            sqlSchema.startToGenerateInstances();
            sqlSchema.getTables().forEach(t -> t.show());
        } else {
            System.out.println(PreCondition.getInstance().getErrorMessage());
        }*/
    }

}
