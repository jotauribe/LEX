package co.lex.domain.model.DataLoader;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Danie on 13/11/2016.
 */
public class ArchiveReader {


    public static List<String> FileReader(String route) throws IOException {
        List<String> lista  = new ArrayList<>();
        String string = "";

            BufferedReader br = new BufferedReader( new InputStreamReader(
                    new FileInputStream(route),"UTF-8"));
            try {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                string += line;
                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                    if(line != null)
                    string += line;
                }
                String everything = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                br.close();
            }


            StringTokenizer as = new StringTokenizer(string,"#");

            while(as.hasMoreTokens()){
                lista.add(as.nextToken());
            }




        return lista;

    }
}
