/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.connection;

import static db.connection.Execute_query.st;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author user
 */
public class Estimation_Time {

    Connect_MySql cnu = new Connect_MySql();
    public static Statement st;
    public static ResultSet rs;

    public Estimation_Time() throws SQLException {
        st = cnu.connexdb().createStatement();
        long startTime = System.currentTimeMillis();
        st.execute("select * from movie");
        st.execute("select * from reviewer");
        st.execute("select * from rating");

        long endTime = System.currentTimeMillis();
        System.out.println("Total elapsed time  is :" + (endTime - startTime) + "ms");
    }

    public static void main(String[] args) throws SQLException {
        Estimation_Time est = new Estimation_Time();
    }
}
