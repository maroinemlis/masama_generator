/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.connection;

import java.sql.*;
import javax.swing.JOptionPane;

public class Connect_MySql {

    Connection connex;

    public Connect_MySql() {
        try {
            //connexion vers le serveur
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erreur de connexion vers le serveur" + e.getMessage());
        }

        try {
            //connexion vers la base de données
            connex = DriverManager.getConnection("jdbc:mysql://localhost:3306/masama?useTimezone=true&serverTimezone=UTC", "root", "");
            //JOptionPane.showMessageDialog(null,"connexion vers la base de données reussie");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "erreur de connexion vers la base de données" + e.getMessage());
        }

    }

    public Connection connexdb() {
        return connex;
    }

}
