package com.learning.wiremock.wiremock;

import java.sql.*;

public class DbManager {
    private final String csvPath;
    private Connection dbConnection;

    public DbManager(String csvPath) {
        this.csvPath = csvPath;
    }

    public void dbConnect() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("org.hsqldb.jdbcDriver").newInstance();
        this.dbConnection = DriverManager.getConnection("jdbc:hsqldb:file:" + this.csvPath, "CSV", "");
    }

    public void dbDisconnect() throws SQLException {
        final Statement statement = this.dbConnection.createStatement();
        statement.execute("SHUTDOWN");
        statement.close();
        this.dbConnection.close();
    }

    public QueryResults execute(String querySQL) throws SQLException {
        final Statement stmt = this.dbConnection.createStatement();
        final ResultSet results = stmt.executeQuery(querySQL);
            return new QueryResults(results);
    }
}
