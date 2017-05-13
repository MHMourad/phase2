/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author mohammedh.mourad
 */
class MyComparator implements Comparator<rankedUrl> {

    @Override
    public int compare(rankedUrl o1, rankedUrl o2) {
        if (o1.rank > o2.rank) {
            return -1;
        } else if (o1.rank < o2.rank) {
            return 1;
        }
        return 0;
    }
}

public class NewServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param req
     * @param res
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        ArrayList<String> links = new ArrayList();
        ArrayList<String> titles = new ArrayList();
        ArrayList<String> texts = new ArrayList();
        String mainQuery = req.getParameter("q");
        boolean flag = false;
        if(mainQuery.charAt(0) == '"' && mainQuery.charAt(mainQuery.length() - 1) == '"') {
            flag = true;
            StringBuilder sb = new StringBuilder(mainQuery);
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length() - 1);
            mainQuery = sb.toString();
        }
        queryProcess process = new queryProcess();
        mainQuery = process.process(mainQuery);

        try {
            DBManager db = new DBManager();
            java.sql.Connection conn = db.connect();
            String query = " SELECT DISTINCT url FROM indices where word = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            Ranker ranker = new Ranker();
            ArrayList<rankedUrl> rankList = new ArrayList<rankedUrl>();
            
            if(!flag){
            String[] splitWords = mainQuery.split(" ");
            Set<String> toBeIndexed = new HashSet<String>(Arrays.asList(splitWords));
            
            for (String quer : toBeIndexed) {

                // create the mysql insert preparedstatement
                preparedStmt.setString(1, quer);
                // execute the preparedstatement
                ArrayList<String> urlList = new ArrayList<String>();
                ResultSet rs = preparedStmt.executeQuery();
                while (rs.next()) {
                    urlList.add(rs.getString(1));
                }
                String[] urls = new String[urlList.size()];
                urls = urlList.toArray(urls);
                rankList.addAll(ranker.rank(urls, conn, quer));
            }
            Collections.sort(rankList, new MyComparator());

            for (rankedUrl RankedUrl : rankList) {
                links.add(RankedUrl.url);
                System.out.println(RankedUrl.url + " : " + RankedUrl.rank);
            }

            preparedStmt.close();
            }
            else{
                PhraseProcessor phrase = new PhraseProcessor();
                links.addAll(phrase.phraseProcess(mainQuery));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < links.size(); i++) {

//        Document DT = (Document) Jsoup.parse(links.get(i));
//         String title = DT.title();  
            Document doc = Jsoup.connect(links.get(i)).get();
            String tit = doc.title();
            String temp = doc.body().text();
            StringBuilder T = new StringBuilder(temp.substring(0,Math.min(temp.length(),200)));
            T.append("...");
            texts.add(T.toString());
            titles.add(tit);

        }

        //String searchString=req.getParameter("q");
        req.setAttribute("SS", mainQuery);
        req.setAttribute("links", links);
        req.setAttribute("titles", titles);
        req.setAttribute("texts", texts);
                
//res.setContentType("text/html;charset=UTF-8");
        res.getWriter().append("Served at: ").append(req.getContextPath());
        RequestDispatcher rd = req.getRequestDispatcher("page2.jsp");
        rd.forward(req, res);

//PrintWriter wt=res.getWriter();
//
//String searchString=req.getParameter("q");
//System.out.println("");
//
//wt.println("<h2>"+searchString+"</h2>");
//
//System.out.println(searchString);
    }

    /**
     *
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String id = req.getParameter("realname");
        String password = req.getParameter("mypassword");
    }

}
