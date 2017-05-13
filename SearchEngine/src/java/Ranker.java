/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author h7ashadpc
 */
import java.sql.SQLException;
import java.sql.*;
import java.util.*;



public class Ranker {

    public ArrayList<rankedUrl> rank(String[] urls, java.sql.Connection conn, String quer) throws SQLException {
        int docCount, wordDocCount, DocsPerWord, queryDocCount, pointingDocs, maxDocCount;
        ArrayList<rankedUrl> rankList = new ArrayList<rankedUrl>();
        
        String query1 = "SELECT COUNT(DISTINCT url) FROM indices";
        String query2 = "SELECT COUNT(*) FROM indices where url = ?";
        String query3 = "SELECT COUNT(*) FROM indices where word = ? AND url = ?";
        String query4 = "SELECT COUNT(DISTINCT url) FROM indices where word = ?";
        String query5 = "SELECT COUNT(*) FROM pagestovisit where urlToVisit = ?";
        String query6 = "SELECT COUNT(*) from pagestovisit group by urlToVisit ORDER BY COUNT(*) DESC";
        PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
        PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
        PreparedStatement preparedStmt3 = conn.prepareStatement(query3);
        PreparedStatement preparedStmt4 = conn.prepareStatement(query4);
        PreparedStatement preparedStmt5 = conn.prepareStatement(query5);
        PreparedStatement preparedStmt6 = conn.prepareStatement(query6);

        ResultSet rs1 = preparedStmt1.executeQuery();
        rs1.next();
        docCount = rs1.getInt(1);
        preparedStmt1.close();

        ResultSet rs6 = preparedStmt6.executeQuery();
        rs6.next();
        maxDocCount = rs6.getInt(1);
        preparedStmt6.close();

        for (int i = 0; i < urls.length; i++) {
            preparedStmt2.setString(1, urls[i]);
            ResultSet rs2 = preparedStmt2.executeQuery();
            rs2.next();
            wordDocCount = rs2.getInt(1);
            
            preparedStmt3.setString(1, quer);
            preparedStmt3.setString(2, urls[i]);
            ResultSet rs3 = preparedStmt3.executeQuery();
            rs3.next();
            queryDocCount = rs3.getInt(1);

            preparedStmt4.setString(1, quer);
            ResultSet rs4 = preparedStmt4.executeQuery();
            rs4.next();
            DocsPerWord = rs4.getInt(1);

            preparedStmt5.setString(1, urls[i]);
            ResultSet rs5 = preparedStmt5.executeQuery();
            rs5.next();
            pointingDocs = rs5.getInt(1);

            rankList.add(new rankedUrl((Double.valueOf(((double) queryDocCount / (double) wordDocCount) * ((double) docCount / (double) DocsPerWord) * ((double) pointingDocs))), urls[i]));

        }

        preparedStmt2.close();
        preparedStmt3.close();
        preparedStmt4.close();
        preparedStmt5.close();
        

        return rankList;
    }
}
