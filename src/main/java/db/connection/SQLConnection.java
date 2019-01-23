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
import java.util.ArrayList;
import java.util.List;
import views.alertMeg.AlertExeption;

/**
 * An object represent an SQL Connection, it serves to get the meta data infos
 * (bdMetaDate object)
 *
 * @author Maroine
 */
public final class SQLConnection {

    public String url = "";
    private String user = "";
    private String password = "";
    private Connection connection;
    private static DatabaseMetaData bdMetaDate;
    private Statement stm;

    /**
     * read file of sql shema
     *
     * @param fileUrl
     * @param Charset
     * @return String
     */
    private String readFile(String fileUrl, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(fileUrl));
        return new String(encoded, encoding);
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * execute the SQL file
     *
     * @param file
     */
    public void executeSQLFile(String file) {
        try {
            String[] queries = readFile(file, StandardCharsets.UTF_8).split(";");
            for (String query : queries) {
                stm.executeUpdate(query);
            }
        } catch (IOException e) {
        } catch (SQLException ex) {
            System.out.println("db.connection.SQLConnection.executeSQLFile()");
            AlertExeption alertExeption = new AlertExeption(ex.getMessage());
            alertExeption.showStandarAlert();

        }

    }

    /**
     *
     * @return data base meta data instance
     */
    public static DatabaseMetaData getDatabaseMetaData() {
        return bdMetaDate;
    }

    /**
     *
     *
     * @throw exception
     */
    public void connect() throws Exception {
        connection = DriverManager.getConnection(url, user, password);
        bdMetaDate = connection.getMetaData();
        stm = connection.createStatement();
    }

    /**
     * define the type of sgbd connexion, it's can be:
     *
     * SQLite,Oracle,Postgresql,mySQL,SQLServer,Derby
     *
     * @param sqlType
     */
    public void setConnexionType(String sqlType) throws Exception {
        switch (sqlType) {
            case "SQLite":
                Class.forName("org.sqlite.JDBC");
                this.url = "jdbc:sqlite:" + url;
                break;
            case "Oracle":
                Class.forName("org.sqlite.JDBC");
                this.url = "jdbc:sqlite:" + url;
                break;
            case "Postgresql":
                Class.forName("org.postgresql.Driver");
                this.url = "jdbc:postgresql:" + url;
                break;
            case "mySQL":
                Class.forName("com.mysql.cj.jdbc.Driver");
                this.url = "jdbc:mysql:" + url;
                break;
            case "SQLServer":
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                this.url = "jdbc:sqlserver:" + url;
                break;
            case "Derby":
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                this.url = "jdbc:derby:" + url;
                break;
        }
    }

    /**
     * Constructor for class SQLConnection
     *
     * @param url
     * @param user
     * @param password
     * @param sqlType
     */
    public SQLConnection(String url, String user, String password, String sqlType) throws Exception {
        setConnexionType(sqlType);
        this.user = user;
        this.password = password;
        connect();
    }

    /**
     * Constructor for class SQLConnection binary file or a SQL file
     *
     * @param fileUrl
     * @param sqlType
     * @param isBinaryFile
     */
    public SQLConnection(String fileUrl, String sqlType, boolean isBinaryFile) throws Exception {
        if (isBinaryFile) {
            this.url = "//" + fileUrl;
        }
        setConnexionType(sqlType);
        connect();
        if (!isBinaryFile) {
            this.url = "//localhost";
            executeSQLFile(fileUrl);
        }
    }

    public List<String> executeQuerySelecte(String nameTable, String nameAttribute) throws SQLException {
        List<String> result = new ArrayList<>();
        String query = "select " + nameAttribute + " from " + nameTable + ";";
        ResultSet rs = null;
        try {
            Connection conn = this.connection;
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                result.add(rs.getString(nameAttribute));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            rs.close();
        }
        return result;
    }

    public void displaySelect(String query, Integer valPourc) throws ClassNotFoundException, SQLException {
        ResultSet rs = null;
        Connection conn = this.connection;

        conn.setAutoCommit(false);
        for (int i = 0; i < valPourc; i++) {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
        }
        conn.commit();
        //System.out.println(rs);
        //while (rs.next()) {
        //  System.out.println("aaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbb" + rs.getString("title"));
        //}
    }

    public void displayUpdate(String query, Integer valPourc) throws ClassNotFoundException, SQLException {
        Connection conn = this.connection;
        conn.setAutoCommit(false);
        for (int i = 0; i < valPourc; i++) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);

        }
        conn.commit();
    }
}
