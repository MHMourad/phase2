/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;
import java.util.Set;
/**
 *
 * @author h7ashadpc
 */
public class Crawler {

        String[] baseUrls; 
        Thread[] cr;
        CrawlerController crawler;
        DBManager db;
        Connection conn;

        Statement st;
        PreparedStatement statement;
        ResultSet results;
        Scanner reader = new Scanner(System.in);
        int BaseUrlsNo;
                
        
        public Crawler()throws SQLException, IOException, InterruptedException{
           db=new DBManager();
           conn=db.connect();
           if(conn != null)
           {st = conn.createStatement();
           statement = conn.prepareStatement("SELECT * FROM Base_URLs");
           results = statement.executeQuery();}
           else System.out.println("No Connection established");
           //System.out.println("Enter Number Of Base URLs: ");
           
           //BaseUrlsNo = reader.nextInt();
           //reader.nextLine();
           //cr = new Thread[BaseUrlsNo];
           //baseUrls=new String[BaseUrlsNo];
           System.out.println("Enter Number Of Base URLs: ");
                int BaseUrlsNo=reader.nextInt();
                cr = new Thread[BaseUrlsNo];
                baseUrls=new String[BaseUrlsNo];
                reader.nextLine();
           int m = 0 ;
                        while(results.next()) { 
                    
                   
                    System.out.println(results.getString("url"));
                    crawler = new CrawlerController(results.getString("url"));
                    
//                    crawler.search(results.getString("url"));
                      cr[m]= new Thread(crawler);
                      cr[m].start();
                      if(m < BaseUrlsNo)
                      m++;
                      else break;

                }
                
                
                Scanner reader = new Scanner(System.in);  // Reading from System.in
              
                 
                for(int i=0;i<BaseUrlsNo;i++){
                System.out.println("Enter Base URL: ");
                String url = reader.nextLine(); 
                url="https://"+url+"";
                baseUrls[i]=url;
                
                }
               for(int i=0;i<BaseUrlsNo;i++){

                crawler = new CrawlerController(baseUrls[i]);
                cr[i]= new Thread(crawler);

                cr[i].start();
                System.out.println("Hello");

               }
               for(int i=0;i<BaseUrlsNo;i++){cr[i].join();}
        }
        
        
       
         
}
