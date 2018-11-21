/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.main;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author sa3d
 */
public class Data  extends RecursiveTreeObject<Data>{
    
    ArrayList<StringProperty> lines = new ArrayList();
    String table_name;
    
    public Data (String table, ArrayList line){
        table_name = table;
        for(int i = 0; i<line.size(); i++){
            lines.add(new SimpleStringProperty((String) line.get(i)));
        }
    }
    
    public String getTableName(){
        return table_name;
    }
    public ArrayList<StringProperty> getLines(){
        return lines;
    }
    
}
