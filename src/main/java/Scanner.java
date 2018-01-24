import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Scanner {
    private static ArrayList<Pattern> regexes = new ArrayList<Pattern>();
    private static String input;

    static String nextToken() {
        for (Pattern p : regexes)
        {
            Matcher m = p.matcher(input);
            if (m.find())
            {
                if (p.pattern() == "^([a-zA-Z][a-zA-Z0-9_]*)")
                {
                    input = m.replaceFirst("").trim();
                    return "idf";
                }
                else if (p.pattern() == "^([0-9]+)")
                {
                    input = m.replaceFirst("").trim();
                    return "intt";
                }
                else
                {
                    String t = m.group().trim();
                    input = m.replaceFirst("").trim();
                    return t;
                }
            }
        }
        return null;
    }

    static String firstInput() {
        for (Pattern p : regexes)
        {
            Matcher m = p.matcher(input);
            if (m.find())
            {
                if (p.pattern() == "^([a-zA-Z][a-zA-Z0-9_]*)")
                {
                    return "idf";
                }
                else if (p.pattern() == "^([0-9]+)")
                {
                    return "intt";
                }
                else
                {
                    return m.group().trim();
                }
            }
        }
        return null;
    }

    Scanner(String input){
        Scanner.input = input + " $";

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
    }
}
