import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Compiler_Scanner {
    private static ArrayList<Pattern> regexes = new ArrayList<Pattern>();
    private static String input;
    private static String currentToken;
    private static String currentTokenAbsVal;
    public static HashMap<String , Integer> symbolTable = new HashMap<>();
    static Matcher m;

    static String getCurrentToken(){
        return currentToken;
    }
    static String getCurrentTokenAbsVal(){
        return currentTokenAbsVal;
    }
    static void next_token(){
        input = m.replaceFirst("").trim();
        for (Pattern p : regexes)
        {
            m = p.matcher(input);
            if (m.find())
            {
                if (p.pattern() == "^([a-zA-Z][a-zA-Z0-9_]*)")
                {
                    symbolTable.put(m.group(),m.group().hashCode());
                    currentToken = "idf";
                }
                else if (p.pattern() == "^([0-9]+)")
                {
                    currentToken = "intt";
                }
                else
                {
                    currentToken = m.group().trim();
                }
                currentTokenAbsVal = m.group();
                break;
            }
        }
    }

//    static String trim_current_input() {
//        for (Pattern p : regexes)
//        {
//            m = p.matcher(input);
//            if (m.find())
//            {
//                if (p.pattern() == "^([a-zA-Z][a-zA-Z0-9_]*)")
//                {
//                    symbolTable.put(m.group(),m.group().hashCode());
//                    input = m.replaceFirst("").trim();
//                    return "idf";
//                }
//                else if (p.pattern() == "^([0-9]+)")
//                {
//                    input = m.replaceFirst("").trim();
//                    return "intt";
//                }
//                else
//                {
//                    String t = m.group().trim();
//                    input = m.replaceFirst("").trim();
//                    return t;
//                }
//            }
//        }
//        return null;
//    }
//    static String peek_next_token(){
//        for (Pattern p : regexes)
//        {
//            Matcher m = p.matcher(input);
//            if (m.find())
//            {
//                return m.group();
//            }
//        }
//        return null;
//    }

    Compiler_Scanner(String input){
        Compiler_Scanner.input = input + " $";

        regexes.add(Pattern.compile( "^(EOF)" ));
        regexes.add(Pattern.compile( "^(public)" ));
        regexes.add(Pattern.compile( "^(class)" ));
        regexes.add(Pattern.compile( "^(\\{)" ));
        regexes.add(Pattern.compile( "^(})" ));
        regexes.add(Pattern.compile( "^(static)" ));
        regexes.add(Pattern.compile( "^(void)" ));
        regexes.add(Pattern.compile( "^(main)" ));
        regexes.add(Pattern.compile( "^(extends)" ));
        regexes.add(Pattern.compile( "^(\\()" ));
        regexes.add(Pattern.compile( "^(\\))" ));
        regexes.add(Pattern.compile( "^(return)" ));
        regexes.add(Pattern.compile( "^(;)" ));
        regexes.add(Pattern.compile( "^(,)" ));
        regexes.add(Pattern.compile( "^(boolean)" ));
        regexes.add(Pattern.compile( "^(\\+=)" ));
        regexes.add(Pattern.compile( "^(int)" ));
        regexes.add(Pattern.compile( "^(if)" ));
        regexes.add(Pattern.compile( "^(else)" ));
        regexes.add(Pattern.compile( "^(while)" ));
        regexes.add(Pattern.compile( "^(for)" ));
        regexes.add(Pattern.compile( "^(=)" ));
        regexes.add(Pattern.compile( "^(\\+)" ));
        regexes.add(Pattern.compile( "^(-)" ));
        regexes.add(Pattern.compile( "^(\\*)" ));
        regexes.add(Pattern.compile( "^(System.out.println)" ));
        regexes.add(Pattern.compile( "^(&&)" ));
        regexes.add(Pattern.compile( "^(true)" ));
        regexes.add(Pattern.compile( "^(false)" ));
        regexes.add(Pattern.compile( "^(<)" ));
        regexes.add(Pattern.compile( "^(\\.)" ));
        regexes.add(Pattern.compile( "^(\\$)" ));
        regexes.add(Pattern.compile( "^([a-zA-Z][a-zA-Z0-9_]*)" ));
        regexes.add(Pattern.compile( "^([0-9]+)" ));

        for (Pattern p : regexes)
        {
            m = p.matcher(input);
            if (m.find())
            {
                if (p.pattern() == "^([a-zA-Z][a-zA-Z0-9_]*)")
                {
                    symbolTable.put(m.group(),m.group().hashCode());
                    currentToken = "idf";
                }
                else if (p.pattern() == "^([0-9]+)")
                {
                    currentToken = "intt";
                }
                else
                {
                    currentToken = m.group().trim();
                }
                currentTokenAbsVal = m.group();
                break;
            }
        }
    }
}
