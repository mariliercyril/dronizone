package com.scp.dronizone.warehouse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by garni on 11/12/2016.
 */
public class BddConnection {
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://192.168.99.100:3306/dronizone?&useSSL=true";
    private static String user = "root";
    private static String pwd = "scp1920";

    private static Connection connect;

    private static void printSQLErrors(SQLException e) {
        while(e != null) {
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("Message:  " + e.getMessage());
            System.err.println("Vendor:   " + e.getErrorCode());
            e = e.getNextException();
        }
    }

    private BddConnection(){
        try {
            Class.forName(driver);
            connect = DriverManager.getConnection(url, user, pwd);
        }
        catch(SQLException e) {
            printSQLErrors(e);
            new SQLException(e);
        }
        catch(Exception e) {
            new Exception(e);
        }
    }

    public static Connection getConnection()  {
        if(connect == null){
            new BddConnection();
        }
        return connect;
    }
}
