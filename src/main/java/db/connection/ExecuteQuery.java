/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author amirouche
 */
public class ExecuteQuery {

    private Connection connection;

    public ExecuteQuery(Connection connection) {
        this.connection = connection;
    }

    public boolean executeUpdateOrInsert(String query) {
        boolean result = true;
        try {
            Connection conn = this.connection;
            Statement stmt = conn.createStatement();
            int rs = stmt.executeUpdate(query);
            if (rs < 0) {
                result = false;
            }
            stmt.close();
        } catch (SQLException e) {
            result = false;
            System.err.println("db.connection.ExecuteQuery.execute()");
            System.err.println(e.getMessage());
        }
        return result;
    }

}
