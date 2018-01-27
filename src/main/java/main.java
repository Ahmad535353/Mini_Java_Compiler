import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class main {

    public static void main(String args[])
    {
        ArrayList <String> PB;

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

        Compiler_Scanner compilerScanner1 = new Compiler_Scanner(s);
//                "class cls2 { static boolean cls2_a;" +
//                "public static boolean meth2(int c , int d){ int e; return e + c ;} }" +
//                "public class clss { public static void main ( ) " +
//                "{ int a ;" +
//                "int b ;" +
//                "a = 5;" +
//                "while ( a < 10 ) {a = a + 1;}" +
//                "if (a < 10){ a = a + 2;}else{ a = 5;}" +
//                " for (b = 0 ; b < 5 ; b += 1){ a = b ;}" +
//                "System.out.println ( a + b ) ;" +
//                "} } EOF");
        Parser parser1 = new Parser();
        PB = parser1.something();
        for (int i = 0; i < PB.size(); i++) {
            System.out.println(i + "\t" + PB.get(i));
        }






//        String S = " ";
//        while (!s.equals(""))
//        {
//
//        }
//        System.out.println(S);
    }
}
