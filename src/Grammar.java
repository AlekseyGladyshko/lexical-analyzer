import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Grammar {
    List<String> rules = new ArrayList<>();
    List<String> nonTerminalsErrors = new ArrayList<>();

    public Grammar(String grammarFilePath){
        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = (BufferedReader) Files.newBufferedReader(Paths.get(grammarFilePath), charset)) {

            String line = new String();
            while ((line = reader.readLine()) != null) {
                rules.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLastToken(String subrequest){
        Pattern pattern = Pattern.compile("\\s?[\\S]+$");
        Matcher match = pattern.matcher(subrequest);
        if(match.find()) {
            return match.group().toString();
        } else {
            return "";
        }
    }

    // request[0] = request[0].replaceAll("UPDATE", "");

    public boolean checkTerminal(String terminal, String [] request){
        String [] rules = new String[]{};
        String [] terminals = new String[]{};
        boolean checker = false;

        for (String rule:this.rules) {
            if (rule.contains(terminal + " ::= ")) {
                terminals = rule.replaceAll(terminal + " ::= ", "").split(" ");

                for (String term:terminals) {

                    if(term.contains("<") && term.contains(">")) {

                        if (term.equals("<vars>")) {
                            System.out.println("+");
                        }

                        if(!(checkTerminal(term, request))){
                            //
                            nonTerminalsErrors.add(term);
                            //
                            //return false;
                            checker = false;
                            break;
                        } else {
                            checker = true;
                        }
                    } else {

                        Pattern pattern = Pattern.compile("^" + term + "$");
                        Matcher matcher;
                        String subrequest = request[0];

                        while (subrequest.length() > 0){
                            matcher = pattern.matcher(subrequest);

                            if(matcher.find()){
                                request[0] = request[0].replaceFirst(matcher.group().toString(), "");
                                request[0] = request[0].replaceFirst("^\\s+","");
                                checker = true;
                                break;
                            } else {
                                String lastToken = getLastToken(subrequest);
                                subrequest = subrequest.replaceFirst(lastToken, "");
                                subrequest = subrequest.replaceFirst("^\\s+","");
                                checker = false;
                            }
                        }

                        if(subrequest.length() == 0) {
                            if (term.equals("~")) {
                                checker = true;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
            if(checker){
                break;
            }
        }
        return checker;
    }
}