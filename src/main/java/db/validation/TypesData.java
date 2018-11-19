/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.validation;

/**
 *
 * @author amirouche
 */public enum TypesData  {
  INT("INT"),
  DATE("DATE");
  
    private String name = "";

  //Constructeur
    TypesData(String name){
        this.name=name;
    } 

    private String getName() {
        return name;
    }
    
    
}