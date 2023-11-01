package com.google;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class SQLDownload {

    public static String DBURL = "";
    public static String DBUSER = "";
    public static String DBPASSWORD = "";

    public static Integer DBFunction = 0;

    public static Scanner scanner = new Scanner(System.in);

    public static Connection connection = null;

    public static void main(String[] args) throws SQLException {

        readConfigFile();
        System.out.print("If you want to exit during configuration, please type (exit)\n");
        if (DBURL.equals("")){
            System.out.print("Please enter your MySQL connection URL:\n");
            System.out.print("-: ");

            DBURL = isNotExitString();
        }
        if (DBUSER.equals("")){
            System.out.println("Please enter your MySQL username:");
            System.out.print("-: ");
            DBUSER = isNotExitString();
        }
        if (DBPASSWORD.equals("")){
            System.out.println("Please enter your MySQL password:");
            System.out.print("-: ");
            DBPASSWORD = isNotExitString();
        }
        if (DBFunction == 0){
            System.out.println("Please confirm the serial number of the function you want to select:");
            System.out.println("1. Retrieve the names of all database repositories under the connection.");
            System.out.println("2. Read all tables and fields of the specified database.");
            System.out.println("3. Drag all the contents of the specified library.");
            System.out.println("4. Terminal prints the first 50 records of SQL execution results for debugging purposes.");
            System.out.println("5. Save SQL execution results with unlimited queries.");
            System.out.print("-: ");
            DBFunction = Integer.parseInt(isNotExitString());
        }



        try {
            if (SQLDownload.connection == null && !DBURL.equals("") && !DBUSER.equals("") ){
                SQLDownload.connection = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
            }

            if (DBFunction == 1){
                chooseOne(SQLDownload.connection);
            }else if (DBFunction == 2){
                chooseTwo(SQLDownload.connection);
            }else if (DBFunction == 3) {
                assert SQLDownload.connection != null;
                chooseThree(SQLDownload.connection);
            } else if (DBFunction ==4 || DBFunction == 5) {
                WaitForSQLImport();
            }

            scanner.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void WaitForSQLImport() throws SQLException {
        while (true) {
            System.out.println("Please enter your SQL, if you want to out, please enter \"exit\":");
            System.out.print("> ");
            String sql = scanner.nextLine();
            if (sql.equalsIgnoreCase("exit")) {
                break;
            }

            if (DBFunction == 4){
                assert SQLDownload.connection != null;
                executeSQL(SQLDownload.connection, sql,true);
            }else if (DBFunction == 5){
                assert SQLDownload.connection != null;
                executeSQL(SQLDownload.connection, sql,false);
            }
        }
    }

    public static String isNotExitString(){
        String str = scanner.nextLine();
        if (str.equals("exit")){
            System.exit(0);
        }
        return str;
    }

    public static void chooseOne(Connection connection) throws SQLException {
        listAccessibleDatabases(connection);
    }

    public static void chooseTwo(Connection connection) throws SQLException{
        getDatabaseMetadata(connection);
    }

    public static void chooseThree(Connection connection) throws SQLException{
        Statement statement = connection.createStatement();

        ResultSet tables = connection.getMetaData().getTables( connection.getCatalog(), null, null, new String[] {"TABLE"});


        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            String fileName = tableName + ".csv";

            // 执行查询以获取表数据
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            ResultSetMetaData metaData = resultSet.getMetaData();

            try (FileWriter writer = new FileWriter(fileName)) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    writer.append(metaData.getColumnName(i));
                    if (i < metaData.getColumnCount()) {
                        writer.append(",");
                    }
                }
                writer.append("\n");

                while (resultSet.next()) {
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        writer.append(resultSet.getString(i));
                        if (i < metaData.getColumnCount()) {
                            writer.append(",");
                        }
                    }
                    writer.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void executeSQL(Connection connection, String sql, boolean isLimit) throws SQLException {
        Statement statement = connection.createStatement();
        if (!sql.endsWith(";")) {
            sql += ";";
        }

        if (isLimit) {
            sql = sql.substring(0, sql.length() - 1) + " LIMIT 50;";
        }

        ResultSet resultSet = statement.executeQuery(sql);

        if (isLimit) {
            printResultSetToConsole(resultSet);
        } else {
            saveResultSetToCSV(resultSet);
        }

        resultSet.close();
        statement.close();
    }

    private static void printResultSetToConsole(ResultSet resultSet) throws SQLException {
        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
            System.out.print(resultSet.getMetaData().getColumnName(i));
            if (i < resultSet.getMetaData().getColumnCount()) {
                System.out.print(", ");
            }
        }
        System.out.println("\n");
        while (resultSet.next()) {
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                System.out.print(resultSet.getString(i));
                if (i < resultSet.getMetaData().getColumnCount()) {
                    System.out.print(", ");
                }
            }
            System.out.println("\n");
        }
    }

    private static void saveResultSetToCSV(ResultSet resultSet) throws SQLException {
        String fileName = generateRandomFileName() + ".csv";

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                fileWriter.append(resultSet.getMetaData().getColumnName(i));
                if (i < resultSet.getMetaData().getColumnCount()) {
                    fileWriter.append(",");
                }
            }
            fileWriter.append("\n");

            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    fileWriter.append(resultSet.getString(i));
                    if (i < resultSet.getMetaData().getColumnCount()) {
                        fileWriter.append(",");
                    }
                }
                fileWriter.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomFileName() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomFileName = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * chars.length());
            randomFileName.append(chars.charAt(index));
        }
        return randomFileName.toString();
    }



    public static void readConfigFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("info.txt"))) {
            Properties properties = new Properties();
            properties.load(br);
            DBURL = properties.getProperty("db_url");
            DBUSER = properties.getProperty("db_user");
            DBPASSWORD = properties.getProperty("db_passwd");
            DBFunction = Integer.parseInt(properties.getProperty("db_function"));

        } catch (IOException e) {
            System.out.println("IO Error:" + e.getMessage());
        }
    }
    public static void listAccessibleDatabases(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SHOW DATABASES";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String databaseName = resultSet.getString(1);
            if (!(databaseName.equals("information_schema") || databaseName.equals("performance_schema") || databaseName.equals("mysql"))){
                System.out.println("Database: " + databaseName);
            }
        }

        resultSet.close();
        statement.close();
    }

    public static void getDatabaseMetadata(Connection connection) throws SQLException {
        System.out.println("Current database name: " + connection.getCatalog());
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, null, new String[] {"TABLE"});

        while (tables.next()) {
            String tableName = tables.getString("TABLE_NAME");
            System.out.println("Table: " + tableName);

            ResultSet columns = metaData.getColumns(null, null, tableName, null);

            List<String> columnNames = new ArrayList<>();
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                columnNames.add(columnName);
            }

            System.out.println("Columns: " + String.join(", ", columnNames));

        }
    }

}


