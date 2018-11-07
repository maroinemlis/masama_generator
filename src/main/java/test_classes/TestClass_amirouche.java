/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_classes;


import db.bean.SQLSchema;
import db.bean.Table;
import db.connection.SQLConnection;
import javafx.scene.control.TitledPane;
/**
 *
 * @author amirouche
 */

public class TestClass_amirouche {
    public void main() throws Exception {
        SQLConnection cnx = new SQLConnection("/home/amirouche/NetBeansProjects/MASAMA/mySQL/test.sql");
        //cnx = new SQLConnection("C:\\Users\\tamac\\OneDrive\\Desktop\\test.sql");
        SQLSchema schema = new SQLSchema();
        
        for (Table table : schema.getTables()) {            
            table.show();
        }
        
        //schema.show();
        
        
    }
}