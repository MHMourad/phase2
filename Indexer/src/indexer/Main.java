/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.*;
import java.util.*;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 *
 * @author h7ashadpc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SQLException, InterruptedException{
        // TODO code application logic here
        Crawler crawler = new Crawler();
       Set<String> VisitedUrls = CrawlerController.getVisitedUrls();
       Thread indexers[] = new Thread[VisitedUrls.size()];

    }
    
}
