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

public class Indexer implements Runnable {

    private String link;
    private int index;

    public static String removeStopWords(String file) {
        String[] stopWords = {"a", "about", "above", "after", "again", "against", "all", "am", "an",
            "and", "any", "are", "aren't", "as", "at", "be", "because", "been", "before", "being",
            "below", "between", "both", "but", "by", "can't", "cannot", "could", "couldn't", "did",
            "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", "each", "few", "for",
            "from", "further", "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd",
            "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how",
            "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", "its",
            "itself", "let's", "i", "me", "more", "most", "mustn't", "my", "myself", "no", "nor", "not", "of",
            "off", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over",
            "own", "same", "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so", "some",
            "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there",
            "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through",
            "to", "too", "under", "until", "up", "very", "was", "wasn't", "we", "we'd", "we'll", "we're", "we've",
            "were", "weren't", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who",
            "who's", "whom", "why", "why's", "with", "won't", "would", "wouldn't", "you", "you'd", "you'll",
            "you're", "you've", "your", "yours", "yourself"};

        for (String stopWord : stopWords) {
            file = file.replace(" " + stopWord + " ", " ");
        }

        return file;
    }

    public Indexer(String link, int index) throws IOException, SQLException, InterruptedException {
        this.link = link;
        this.index = index;
    }

    public void run() {
        DBManager db = new DBManager();
        java.sql.Connection conn = db.connect();

        URL url;

        InputStream is = null;
        FileWriter file;
        BufferedWriter bw;
        BufferedReader br;

        Stemmer stemmer = new Stemmer();

        char[] w = new char[501];
        String line;
        String fileContents = "";
        String URLaddress;
        URLaddress = link;
        try {
            url = new URL(URLaddress);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));
            PrintWriter writer = new PrintWriter("page" + index + ".txt", "UTF-8");
            PrintWriter writer1 = new PrintWriter("test/indexed" + index + ".txt", "UTF-8");

            while ((line = br.readLine()) != null) {
                //writer.write(line + "\n");
                fileContents += line;
            }

            Document document = Jsoup.parse(fileContents);
            String title = document.title();
            document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
            document.select("br").append("\\n");
            document.select("p").prepend("\\n\\n");
            document.select(":containsOwn(\u00a0)").remove();
            String s = document.html().replaceAll("\\\\n", "\n");

            fileContents = document.body().text();
            fileContents = fileContents.replace("&nbsp;", " ");
            fileContents = fileContents.replaceAll("\\p{Punct}+", " ");
            fileContents = fileContents.toLowerCase();
            writer1.println(URLaddress);
            writer1.println(fileContents);
            writer1.close();
            fileContents = removeStopWords(fileContents);
            writer.println(fileContents);
            writer.close();
            //file = new FileWriter("E:\\testt\\newfile.txt");
            System.out.println("Title is: " + title);

            //bw = new BufferedWriter(file);
            //System.out.print(fileContents);
            

            //System.out.print(fileContents);
            FileInputStream in = new FileInputStream("page" + index + ".txt");
            //int x = in.read();
            //System.out.println(x);
            String final_file = "";
            while (true) {
                int ch = in.read();
                //System.out.print(ch);
                if (Character.isLetter((char) ch)) {
                    int j = 0;
                    while (true) {
                        ch = Character.toLowerCase((char) ch);
                        w[j] = (char) ch;
                        if (j < 500) {
                            j++;
                        }
                        ch = in.read();
                        if (!Character.isLetter((char) ch)) {
                            /* to test add(char ch) */
                            for (int c = 0; c < j; c++) {
                                stemmer.add(w[c]);
                            }

                            /* or, to test add(char[] w, int j) */
 /* s.add(w, j); */
                            stemmer.stem();
                            {
                                String u;

                                /* and now, to test toString() : */
                                u = stemmer.toString();

                                /* to test getResultBuffer(), getResultLength() : */
 /* u = new String(s.getResultBuffer(), 0, s.getResultLength()); */
                                //System.out.print(u);
                                final_file += u;

                            }
                            break;
                        }
                    }
                }
                if (ch < 0) {
                    break;
                }
                //System.out.print((char)ch);
                final_file += (char) ch;
            }

            final_file = final_file.replaceAll("\\s", " ");
            //final_file = final_file.replace("\n","");
            String[] splitWords = final_file.split(" ");

            Set<String> toBeIndexed = new HashSet<String>(Arrays.asList(splitWords));
            /* for ( String ss : toBeIndexed) {

       System.out.println(ss);
  }*/
            int i = 0;
            for (String word : toBeIndexed) {
                if (!(word.equals("") || word.equals(" "))) {
                    int wordIndex = final_file.indexOf(" " + word + " ");

                    while (wordIndex >= 0) {  // indexOf returns -1 if no match found
                        System.out.println(i + " : " + word + ", " + wordIndex);

                        String query = " insert into indices (url, position, word)"
                                + " values (?, ?, ?)";

                        // create the mysql insert preparedstatement
                        PreparedStatement preparedStmt = conn.prepareStatement(query);
                        preparedStmt.setString(1, link);
                        preparedStmt.setInt(2, wordIndex);
                        preparedStmt.setString(3, word);

                        // execute the preparedstatement
                        preparedStmt.execute();
                        preparedStmt.close();
                        wordIndex = final_file.indexOf(" " + word + " ", wordIndex + 1);
                        i++;
                    }
                }
            }

        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ioe) {
            }
        }
    }

}
