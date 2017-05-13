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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.sql.*;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author mohammedh.mourad
 */
public class CrawlerController implements Runnable {

    private Set<String> VisitedUrls = new HashSet<String>();
    private final static Set<String> allVisitedUrls = new HashSet<String>();
    private static int index = 0;
    private Set<String> robots = new HashSet<String>();
    private List<String> UrlsToVisit = new LinkedList<String>();
    private final static int Max_Pages = 5000;
    private final static CountDownLatch latch = new CountDownLatch(Max_Pages);
    private String baseUrl;
    private DBManager db;
    private Connection conn;
    private String SQL;
    private Thread indexers[] = new Thread[Max_Pages];

    /**
     *
     * @param baseUrl
     */
    public CrawlerController(String baseUrl) {

        db = new DBManager();
        conn = db.connect();
        this.baseUrl = baseUrl;

    }

    public static synchronized Set<String> getVisitedUrls() throws InterruptedException {

        try {

            latch.await();

        } catch (InterruptedException e) {
        }

        return allVisitedUrls;
    }

    private synchronized String nextUrl() throws SQLException {

        String nextUrl;

        do {
            nextUrl = this.UrlsToVisit.remove(0);
            SQL = "Delete From PagesToVisit WHERE urlToVisit= (?) and base= (?)";
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, nextUrl);
            pstmt.setString(2, baseUrl);
            pstmt.executeUpdate();
            pstmt.close();

        } while (allVisitedUrls.contains(nextUrl));
        this.VisitedUrls.add(nextUrl);
        allVisitedUrls.add(nextUrl);
        latch.countDown();
        return nextUrl;

    }

    public synchronized void search() throws SQLException, IOException, InterruptedException {

        RobotFinder robot = new RobotFinder();

        robots = robot.disallowed(baseUrl);
        Iterator iterator = robots.iterator();

        // check values
        while (iterator.hasNext()) {
            System.out.println("Value: " + iterator.next() + " ");
        }

        Statement st = conn.createStatement();
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM Base_URLs where url = ?");
        statement.setString(1, baseUrl);
        ResultSet results = statement.executeQuery();

        if (results.next()) {

            System.out.println("heeeeeeh");
            System.out.println(results.getString("url"));
            statement = conn.prepareStatement("SELECT * FROM PagesToVisit where base = ?");
            statement.setString(1, baseUrl);
            results = statement.executeQuery();

            while (results.next()) {

                System.out.println("heeeeeeh");
                System.out.println(results.getString("urlToVisit"));
                this.UrlsToVisit.add(results.getString("urlToVisit"));

            }

            st = conn.createStatement();
            statement = conn.prepareStatement("SELECT * FROM Visited_URLs where base = ?");
            statement.setString(1, baseUrl);
            results = statement.executeQuery();

            while (results.next()) {

                System.out.println("heeeeeeh");
                System.out.println(results.getString("visitedUrl"));
                this.VisitedUrls.add(results.getString("visitedUrl"));
                allVisitedUrls.add(results.getString("visitedUrl"));
                latch.countDown();
            }

        } else {

            String SQL = "INSERT INTO Base_URLs VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, baseUrl);
            pstmt.executeUpdate();
            pstmt.close();

        }

        
        while (this.allVisitedUrls.size() < Max_Pages) {

            String currentUrl;
            CrawlerHelper crawlerHelper = new CrawlerHelper();
            if (this.UrlsToVisit.isEmpty()) {
                currentUrl = baseUrl;
                this.VisitedUrls.add(baseUrl);
                allVisitedUrls.add(baseUrl);
                latch.countDown();
            } else {
                currentUrl = this.nextUrl();
            }
            indexers[index] = new Thread(new Indexer(currentUrl, index));
            indexers[index].start();
            index++;

            if (notEmpty(currentUrl)) {

                if (!(this.robots.contains(currentUrl))) {
                    crawlerHelper.crawl(currentUrl, baseUrl);
                    this.UrlsToVisit.addAll(crawlerHelper.getURLs());
                } else {
                    System.out.println("ROBOT");
                }
            }
        }
        for (int i = 0; i < this.VisitedUrls.size(); i++) {
            indexers[i].join();
        }
        synchronized (this.latch) {
            latch.notify();
        }

        System.out.println(String.format("**Done** Visited %s web page(s) for base : " + baseUrl, this.VisitedUrls.size()));
        SQL = "Delete From Base_URLs WHERE url= (?)";
        PreparedStatement pstmt = conn.prepareStatement(SQL);
        pstmt.setString(1, baseUrl);
        pstmt.executeUpdate();
        pstmt.close();
        SQL = "Delete From Visited_URLs WHERE base=(?)";
        pstmt = conn.prepareStatement(SQL);
        pstmt.setString(1, baseUrl);
        pstmt.executeUpdate();
        pstmt.close();
        SQL = "Delete From PagesToVisit WHERE base=(?)";
        pstmt = conn.prepareStatement(SQL);
        pstmt.setString(1, baseUrl);
        pstmt.executeUpdate();
        pstmt.close();

    }

    public static boolean notEmpty(String string) {
        if (string == null || string.length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void run() {

        try {
            search();
        } catch (SQLException ex) {
            Logger.getLogger(CrawlerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrawlerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(CrawlerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
