/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton Database Connection Manager.
 * Maintains a single, shared connection across the entire application
 * instead of reconnecting on every query.
 *
 * @author mmt
 */
public class DB_Connection {
   
    private static final String URL =
            "jdbc:mysql://localhost:3306/carcare_db";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "";

    // Singleton instance and shared connection
    private static DB_Connection instance;
    private static Connection sharedConnection = null;
    private static Connection proxyConnection = null;

    public DB_Connection() {
        // Public constructor kept for backward compatibility with existing DAOs
    }

    /**
     * Singleton accessor method.
     */
    public static synchronized DB_Connection getInstance() {
        if (instance == null) {
            instance = new DB_Connection();
        }
        return instance;
    }

    /**
     * Returns the single shared database connection.
     * Connects only once, reusing the connection for all subsequent operations.
     */
    public synchronized Connection getConnection() {
        try {
            if (sharedConnection == null || sharedConnection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                sharedConnection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully! (Single connection established)");

                // Proxy wraps the shared connection so DAO try-with-resources does NOT terminate it
                proxyConnection = (Connection) Proxy.newProxyInstance(
                    Connection.class.getClassLoader(),
                    new Class<?>[]{Connection.class},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (method.getName().equals("close")) {
                                // Do not close the shared connection; keep it alive
                                return null;
                            }
                            if (method.getName().equals("isClosed")) {
                                return sharedConnection == null || sharedConnection.isClosed();
                            }
                            return method.invoke(sharedConnection, args);
                        }
                    }
                );
            }
            return proxyConnection;

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Explicitly close the shared connection if the application shuts down.
     */
    public static synchronized void closeConnection() {
        try {
            if (sharedConnection != null && !sharedConnection.isClosed()) {
                sharedConnection.close();
                sharedConnection = null;
                proxyConnection = null;
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
