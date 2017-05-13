/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexer;

/**
 *
 * @author h7ashadpc
 */


import java.sql.*;
public class DBManager {
    
    String hostname;
    String password;
    String userNAme;
    public DBManager(String hostname,String password,String userNAme){
        
    
        this.hostname=hostname;
        this.userNAme=userNAme;
        this.password=password;
        
    }
        public DBManager(){
    }

    public Connection connect(){
        
        StringBuilder Link=new StringBuilder();
        Link.append("jdbc:mysql://localhost:3306/engine");
        String dbUrl=Link.toString();
        String user = "root";
        String password = "";
        Connection conn = null;
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            conn = DriverManager.getConnection(dbUrl, user, password);
            

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        catch (ClassNotFoundException e){
            System.out.println("SQL Class not found");
        }
        if(conn != null)
                return conn;

        else { return null;}
        
    }
    

    
    
}
