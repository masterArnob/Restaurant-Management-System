/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.management.system;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ASUS
 */
public class database {
    
    public static Connection connectDB()
    {
        Connection con;
        String userName="root";
        String pass="";
        String dbMechine="localhost";
        String dbName="restaurant";
        String url="jdbc:mysql://"+dbMechine+"/"+dbName;
        
        
        try{
            con=DriverManager.getConnection(url, userName, pass);
            System.out.println("Database is connected");
            return con;
        }catch(Exception e)
        {
            System.out.println("" +e);
        }
        return null;
    }
}
