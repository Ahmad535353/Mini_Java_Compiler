import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
    public static void main(String args[])
    {
        ArrayList <Pattern> regexes = new ArrayList<Pattern>();
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
        regexes.add(Pattern.compile( "^(boolen)" ));
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
        regexes.add(Pattern.compile( "^([a-zA-Z][a-zA-Z0-9_]*)" ));
        regexes.add(Pattern.compile( "^([0-9]+)" ));

        String s = "class {Cls\n" +
                " static boolean d ;\n" +
                "public static int (test int a, boolean b ) {\n" +
                "int c;\n" +
                "c = 1;\n" +
                "b=true;\n" +
                "return a + c;\n" +
                "}\n" +
                "}\n" +
                "public class Cls2{\n" +
                "public static void main() {\n" +
                "int b;\n" +
                "b = 3;\n" +
                "b = Cls.test( b,false);\n" +
                "System.out.println(b);\n" +
                "}\n" +
                "}\n" +
                "EOF";

        while (!s.equals(""))
        {
            for (Pattern p : regexes)
            {
                Matcher m = p.matcher(s);
                if (m.find())
                {
                    System.out.println(m.group().trim());
                    s = m.replaceFirst("").trim();
                    break;
                }

            }
        }
    }
}
