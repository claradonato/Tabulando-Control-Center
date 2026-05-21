package com.example.sistemagerenciamentotabulando.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection connection;


    public static Connection getConnection(){
        if(connection==null){
            try {
                Properties props = new Properties();

                InputStream input = DB.class.getClassLoader().getResourceAsStream("database.properties");
                if (input == null) {
                    throw new RuntimeException("Arquivo database.properties não encontrado");
                }
                props.load(input);
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");
                connection = DriverManager.getConnection(url,user,password);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static void closeConnection(){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeResultSet(ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void closeStatement(Statement st){
        if(st!=null){
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

