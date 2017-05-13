/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author h7ashadpc
 */
public class queryProcess {

    public String process(String query) throws IOException {
        String final_file = "";
        Stemmer stemmer = new Stemmer();
        InputStream in = new ByteArrayInputStream(query.getBytes(StandardCharsets.UTF_8));
        char[] w = new char[501];

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
        return final_file;
    }
}
