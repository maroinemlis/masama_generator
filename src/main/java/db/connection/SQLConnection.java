/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.connection;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.*;

/**
 * An object represente an SQL Connection, it serves to get the meta data infos
 * (bdMetaDate object)
 *
 * @author Maroine
 */
public final class SQLConnection {

    private String url;
    private String user;
    private String password;
    private Connection connection;
    private static DatabaseMetaData bdMetaDate;
    private Statement stm;

    private void connect() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(url, user, password);
        bdMetaDate = connection.getMetaData();
        stm = connection.createStatement();
    }

    public SQLConnection(String fileUrl) throws Exception {
        this.url = "jdbc:sqlite:";
        this.user = "";
        this.password = "";
        connect();
        executeSQLFile(fileUrl);
    }

    public SQLConnection(String url, String user, String password) throws Exception {
        this.url = url;
        this.user = user;
        this.password = password;
        connect();
    }

    private String readFile(String fileUrl, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(fileUrl));
        return new String(encoded, encoding);
    }

    public void executeSQLFile(String file) throws IOException, SQLException {
        String[] queries = readFile(file, StandardCharsets.UTF_8).split(";");
        for (String query : queries) {
            stm.executeUpdate(query);
        }
    }

    /**
     *
     * @return data base meta data instance
     */
    public static DatabaseMetaData getDatabaseMetaData() {
        return bdMetaDate;
    }

}
