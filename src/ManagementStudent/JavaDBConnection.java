/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagementStudent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hoang
 */
public class JavaDBConnection {

    private static final String URL = "jdbc:mysql:";
    private static final String DATABASE = "quanlynhanvien";
    private static final String HOST_PORT = "//localhost:3306/";
    private static final String USERNAME = "?user=root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        String url = URL + HOST_PORT + DATABASE + USERNAME + PASSWORD;
        Connection connect = DriverManager.getConnection(url);
        return connect;
    }
}
