/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import javafx.stage.FileChooser;
import static views.main.LuncherApp.primaryStage;
import views.main.TableView;

public class Execute_query {

    Connect_MySql cnu = new Connect_MySql();
    public static Statement st;
    public static ResultSet rs;

    public Execute_query() throws Exception {
        //lecture du fichier texte
        st = cnu.connexdb().createStatement();
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\user\\Pictures\\masama_generator\\bin.txt"));
        List<String> requete = new ArrayList<>();

        //ajouter les requetes dans une liste
        String st2;
        while ((st2 = br.readLine()) != null) {
            requete.add(st2);
        }
        for (int i = 0; i < requete.size(); i++) {
            if (requete.get(i).length() == 0) {

            } else {
                System.out.println(requete.get(i));
            }

        }
        //executer les requete dans la liste
        for (int i = 0; i < requete.size(); i++) {
            if (requete.get(i).length() == 0) {
                //ne rien faire ..
            } else {
                st.execute(requete.get(i));
            }

        }

    }

    public static void main(String[] args) throws Exception {
        Execute_query ex = new Execute_query();

    }
}
