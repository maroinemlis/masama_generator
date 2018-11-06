/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.models;

import db.bean.Table;

/**
 *
 * @author tamac
 */
public class TableModel {

    Table table;
    int numberOfLignes;

    public TableModel(int numberOfLignes) {
        this.numberOfLignes = numberOfLignes;
    }
}
