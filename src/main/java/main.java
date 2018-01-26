import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class main {

    public static void main(String args[])
    {

        Compiler_Scanner compilerScanner1 = new Compiler_Scanner("public class idf { public static void main ( ) { int idf ; } } EOF");
        Parser parser1 = new Parser();
        parser1.something();




        String s = "class Cls{\n" +
                " static boolean d ;\n" +
                "public static int test (int a, boolean b ) {\n" +
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

//        String S = " ";
//        while (!s.equals(""))
//        {
//
//        }
//        System.out.println(S);
    }
}
