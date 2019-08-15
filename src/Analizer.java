import java.awt.font.GlyphMetrics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analizer {
    public String request = "";

    public Analizer(String filePath) {
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = (BufferedReader) Files.newBufferedReader(Paths.get(filePath), charset)) {
            String line;
            while ((line = reader.readLine()) != null) {
                    request += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        /*
        String term = "[\\w\\-\\_]+";
        Pattern pattern = Pattern.compile("^" + term + "$");
        String [] word = new String[]{"Planets"};
        Matcher matcher = pattern.matcher(word[0]);

        if(matcher.find()){
            System.out.println("Good");
        } else {
            System.out.println("Bad");
        }

        return;
        */
        Analizer task = new Analizer("requests.txt");
        Grammar grammar = new Grammar("ll(1).txt");

        String [] request = new String[]{task.request};

        if(grammar.checkTerminal("PROGRAM", request)){
            System.out.println("Syntax structure is correct !");
        } else {
            System.out.println("Syntax error "); // + error.type
            System.out.println(grammar.nonTerminalsErrors);
        }

        return;
    }
}
