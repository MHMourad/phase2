/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.util.*;

/**
 *
 * @author h7ashadpc
 */
public class PhraseProcessor {

    public ArrayList<String> phraseProcess(String quer) throws FileNotFoundException {
        File dir = new File("\\Indexer\\test");
        File[] directoryListing = dir.listFiles();
        ArrayList<String> rankList = new ArrayList<String>();
        String Url = "";
        if (directoryListing != null) {
            for (File child : directoryListing) {
                Scanner lineScan = new Scanner(child);
                if (lineScan.hasNextLine()) {
                    Url = lineScan.nextLine();
                }
                while (lineScan.hasNextLine()) {
                    if (lineScan.nextLine().contains(quer)) {
                        rankList.add(Url);
                    }
                }
            }
        }
        return rankList;
    }
}
