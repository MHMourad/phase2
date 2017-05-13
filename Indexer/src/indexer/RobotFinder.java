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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

/**
 *
 * @author mohammedh.mourad
 */

///// REFERENCES///////
//tutorials point
//stack overflow


public class RobotFinder {
    
         public static HashSet<String> disallowed(String url) throws MalformedURLException, IOException
    {
        
        String base_urlstr = url+"/robots.txt";
        System.out.println(base_urlstr);
        HashSet<String> dis = new HashSet();
        
        URL  robot_url = new URL(base_urlstr);
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(robot_url.openStream()));
        String line;
//        System.out.println(reader.readLine());


        while((line = reader.readLine()) != null)
        {
            if(line.equalsIgnoreCase("User-agent: *"))
            {
                while((line = reader.readLine())!= null)
                {
                    if(line.contains("Disallow: "))
                    {
                        dis.add(url.toString()+line.substring(11,line.length()));
                    }
                    else if(!line.contains("Allow: ")){  break;}
                }
            }
            
        }
        reader.close();

       return dis;
    }

    
}
