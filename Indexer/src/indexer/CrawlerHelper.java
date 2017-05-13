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
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.*;


/**
 *
 * @author mohammedh.mourad
 */


public class CrawlerHelper
{
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> URLs = new LinkedList<String>();
    private Document htmlDocument;


    public boolean crawl(String url,String baseUrl) throws SQLException
    {
        
               DBManager db=new DBManager();
              java.sql.Connection conn=db.connect();

        try
        {
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() == 200) // 200 is the HTTP OK status code
                                                          // indicating that everything is great.
            {
                System.out.println("\n**Visiting** Received web page at " + url);
                
              Statement st = conn.createStatement();
             Statement ss = conn.createStatement();
             String SQL = "INSERT INTO Visited_URLs VALUES (?,?)";
             PreparedStatement pstmt = conn.prepareStatement(SQL);
             pstmt.setString(1, baseUrl);
              pstmt.setString(2, url);
             pstmt.executeUpdate();
             pstmt.close();

                
            }
            if(!connection.response().contentType().contains("text/html"))
            {
                System.out.println("Not HTML");
                return false;
            }
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Found (" + linksOnPage.size() + ") links");
            for(Element link : linksOnPage)
            {
                this.URLs.add(link.absUrl("href"));
                String SQL = "INSERT INTO PagesToVisit VALUES (?,?)";
                 PreparedStatement pstmt = conn.prepareStatement(SQL);
                pstmt.setString(1, baseUrl);
                pstmt.setString(2, link.absUrl("href"));
                pstmt.executeUpdate();
                pstmt.close();

            }
            return true;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            return false;
        }
    }


    
    public List<String> getURLs()
    {
        return this.URLs;
    }

}
