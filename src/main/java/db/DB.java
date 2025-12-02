package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static DB instance;

    private Connection connection;

    private DB() throws SQLException {
        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/pharmacy", "root", "1234");
    }

    public static DB getInstance() throws SQLException {
        if(instance == null){
            instance=new DB();
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }

}
