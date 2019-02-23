package connection;

import beans.SQLSchema;
import beans.Table;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.*;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

/**
 * An object represent an SQL Connection, it serves to get the meta data
 * informations (bdMetaDate object)
 *
 * @author Martem
 */
public final class SQLConnection {

    public String url = "";
    private String user = "";
    private String password = "";
    private Connection connection;
    private Statement stm;
    private boolean isNewConnection = false;
    private static DatabaseMetaData bdMetaDate;
    private static SQLConnection singlotonSQLConnection = null;
    private Map<String, String[]> driverMapping;

    /**
     * Initialize for each database the name of Driver on name of jdbc
     * corresponding
     */
    public SQLConnection() {
        driverMapping = new HashMap<>();
        driverMapping.put("SQLite", new String[]{"org.sqlite.JDBC", "jdbc:sqlite:"});
        driverMapping.put("Oracle", new String[]{"", ""});
        driverMapping.put("Postgresql", new String[]{"org.postgresql.Driver", "jdbc:postgresql:"});
        driverMapping.put("MySQL", new String[]{"com.mysql.jdbc.Driver", "jdbc:mysql:"});
        driverMapping.put("SQLServer", new String[]{"com.microsoft.sqlserver.jdbc.SQLServerDriver", "jdbc:sqlserver:"});
        driverMapping.put("Derby", new String[]{"org.apache.derby.jdbc.EmbeddedDriver", "jdbc:derby:"});
    }

    /**
     * Singleton allow to having a single instance of SQLConnection.
     *
     * @return instance of SQLConnection
     */
    public static SQLConnection getInstance() {
        if (singlotonSQLConnection == null) {
            singlotonSQLConnection = new SQLConnection();
        }
        return singlotonSQLConnection;
    }

    /**
     * Check if the connection is already established.
     *
     * @return true if connection is established.
     */
    public boolean isNewConnection() {
        return isNewConnection;
    }

    /**
     * Set the state of connection.
     *
     * @param isNewConnection represent the state of connection
     */
    public void isNewConnection(boolean isNewConnection) {
        this.isNewConnection = isNewConnection;
    }

    /**
     * Read file of sql shema
     *
     * @param fileUrl the path of file
     * @param Charset supported characters
     * @return the SQL query inside the file
     */
    private String readFile(String fileUrl, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(fileUrl));
        return new String(encoded, encoding);
    }

    /**
     * Execute the SQL file
     *
     * @param file to execute
     * @throws Exception if a database access error occurs
     */
    public void executeSQLFile(String file) throws Exception {
        String queries = readFile(file, StandardCharsets.UTF_8);
        stm.executeUpdate(queries);
    }

    /**
     * Instance of DatabaseMetaData
     *
     * @return data base meta data instance
     */
    public static DatabaseMetaData getDatabaseMetaData() {
        return bdMetaDate;
    }

    /**
     * Change the state of connection and initialize instance of
     * DatabaseMetaData and stm Statement.
     *
     * @throws SQLException
     */
    private void connect() throws SQLException {
        isNewConnection = true;
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
    /**
     * Constructor for class SQLConnection
     *
     * @param url
     * @param user
     * @param password
     * @param sqlType
     */
    /**
     * Define the url, user, password and the type of SGBD connection. the type
     * of SGBD connection can be: SQLite, Postgresql, mySQL, SQLServer, Derby
     *
     * @param url the complete path do database
     * @param user
     * @param password
     * @param sqlType
     * @throws Exception
     */
    public void connect(String url, String user, String password, String sqlType) throws Exception {
        String[] driver = driverMapping.get(sqlType);
        Class.forName(driver[0]);
        this.url = driver[1] + "//" + url;
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
    public void connect(String fileUrl, String sqlType, boolean isBinaryFile) throws Exception {
        String[] driver = driverMapping.get(sqlType);
        Class.forName(driver[0]);
        url = driver[1];
        if (isBinaryFile) {
            this.url = "//" + fileUrl;
        }
        connect();
        if (!isBinaryFile) {
            executeSQLFile(fileUrl);
        }
    }

    /**
     * Execute the SQL query
     *
     * @param query to execute
     * @throws SQLException if a database access error occurs
     */
    public void execute(String query) throws SQLException {
        stm.executeUpdate(query);
    }
    
    /**
     * Create and execute sql index request
     *
     * @throws SQLException
     */
    public void checkIndex() throws SQLException{
        for(Table table : SQLSchema.getInstance().getTables()){
            if(!table.getIndex().isEmpty()){
                String dropQuery = "DROP INDEX IF EXISTS index_"+table.getTableName();
                String query ="CREATE INDEX index_"+table.getTableName()+" ON "+table.getTableName()+" (";
                int i=0;
                for(i=0; i<table.getIndex().size() - 1; i++){
                    query+=table.getIndex().get(i).getName()+", ";
                }
                query+=table.getIndex().get(i).getName()+")";
                execute(dropQuery);     execute(query);
            }
        }
    }

    /**
     * Delete all data in the Database --unused
     *
     * @throws SQLException
     */
    private static void deleteData() throws SQLException {
        List<Table> tables = SQLSchema.getInstance().getTables();
        for (Table table : tables) {
            String insert = "DELETE FROM " + table.getTableName();
            getInstance().execute(insert);
        }
    }

    /**
     * Insert all data generated to database.
     *
     * @throws SQLException
     */
    public static void writeToDataBase() throws SQLException {
        deleteData();
        List<Table> tables = SQLSchema.getInstance().getTables();
        for (Table table : tables) {
            for (int j = 0; j < table.getHowMuch(); j++) {
                int i = 0;
                StringBuilder insert = new StringBuilder("INSERT INTO " + table.getTableName() + " VALUES(");
                for (i = 0; i < table.getAttributes().size() - 1; i++) {
                    insert.append(table.getAttributes().get(i).getInstances().get(j));
                    insert.append(", ");
                }
                insert.append(table.getAttributes().get(i).getInstances().get(j));
                insert.append(")");
                getInstance().execute(insert.toString());
            }
        }
    }
}
