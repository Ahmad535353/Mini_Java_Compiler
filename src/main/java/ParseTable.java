import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class ParseTable {

    static HashSet<String> nonTerminals = new HashSet<>();
    static HashSet <String> terminals = new HashSet<>();
    static ArrayList<LinkedList<String>> productionRules = new ArrayList<>();

    static HashMap <String , Integer> parsingTable = new HashMap<>();
    ParseTable(){
        parsingTable.put("Goal_EOF",1);
        parsingTable.put("Goal_public",1);
        parsingTable.put("Goal_class",1);
        parsingTable.put("Goal_$",1);
        parsingTable.put("Source_EOF",2);
        parsingTable.put("Source_public",2);
        parsingTable.put("Source_class",2);
        parsingTable.put("MainClass_public",3);
        parsingTable.put("ClassDeclarations_class",4);
        parsingTable.put("ClassDeclarations_public",5);
        parsingTable.put("ClassDeclaration_class",6);
        parsingTable.put("Extension_extends",7);
        parsingTable.put("Extension_{",8);
        parsingTable.put("FieldDeclarations_static",9);
        parsingTable.put("FieldDeclarations_public",10);
        parsingTable.put("FieldDeclarations_class",10);
        parsingTable.put("FieldDeclaration_static",11);
        parsingTable.put("VarDeclarations_boolean",12);
        parsingTable.put("VarDeclarations_int",12);
        parsingTable.put("VarDeclarations_EOF",13);
        parsingTable.put("VarDeclarations_{",13);
        parsingTable.put("VarDeclarations_if",13);
        parsingTable.put("VarDeclarations_while",13);
        parsingTable.put("VarDeclarations_for",13);
        parsingTable.put("VarDeclarations_idf",13);
        parsingTable.put("VarDeclarations_System.out.println",13);
        parsingTable.put("VarDeclarations_}",13);
        parsingTable.put("VarDeclarations_public",13);
        parsingTable.put("VarDeclarations_return",13);
        parsingTable.put("VarDeclaration_boolean",14);
        parsingTable.put("VarDeclaration_int",14);
        parsingTable.put("MethodDeclarations_public",15);
        parsingTable.put("MethodDeclarations_}",16);
        parsingTable.put("MethodDeclaration_public",17);
        parsingTable.put("Parameters_boolean",18);
        parsingTable.put("Parameters_int",18);
        parsingTable.put("Parameters_)",19);
        parsingTable.put("Parameter_,",20);
        parsingTable.put("Parameter_)",21);
        parsingTable.put("Type_boolean",22);
        parsingTable.put("Type_int",23);
        parsingTable.put("Statements_{",24);
        parsingTable.put("Statements_if",24);
        parsingTable.put("Statements_while",24);
        parsingTable.put("Statements_for",24);
        parsingTable.put("Statements_idf",24);
        parsingTable.put("Statements_System.out.println",24);
        parsingTable.put("Statements_}",25);
        parsingTable.put("Statements_return",25);
        parsingTable.put("Statement_{",26);
        parsingTable.put("Statement_if",27);
        parsingTable.put("Statement_while",28);
        parsingTable.put("Statement_for",29);
        parsingTable.put("Statement_idf",30);
        parsingTable.put("Statement_System.out.println",31);
        parsingTable.put("GenExpression_(",32);
        parsingTable.put("GenExpression_idf",32);
        parsingTable.put("GenExpression_true",32);
        parsingTable.put("GenExpression_false",32);
        parsingTable.put("GenExpression_intt",32);
        parsingTable.put("ExpressionTT_==",33);
        parsingTable.put("ExpressionTT_<",33);
        parsingTable.put("ExpressionTT_;",34);
        parsingTable.put("ExpressionTT_)",34);
        parsingTable.put("ExpressionTT_,",34);
        parsingTable.put("Expression_(",35);
        parsingTable.put("Expression_idf",35);
        parsingTable.put("Expression_true",35);
        parsingTable.put("Expression_false",35);
        parsingTable.put("Expression_intt",35);
        parsingTable.put("Exp_+",36);
        parsingTable.put("Exp_-",37);
        parsingTable.put("Exp_;",38);
        parsingTable.put("Exp_)",38);
        parsingTable.put("Exp_==",38);
        parsingTable.put("Exp_<",38);
        parsingTable.put("Exp_&&",38);
        parsingTable.put("Exp_,",38);
        parsingTable.put("Term_(",39);
        parsingTable.put("Term_idf",39);
        parsingTable.put("Term_true",39);
        parsingTable.put("Term_false",39);
        parsingTable.put("Term_intt",39);
        parsingTable.put("TermP_*",40);
        parsingTable.put("TermP_+",41);
        parsingTable.put("TermP_-",41);
        parsingTable.put("TermP_;",41);
        parsingTable.put("TermP_)",41);
        parsingTable.put("TermP_==",41);
        parsingTable.put("TermP_<",41);
        parsingTable.put("TermP_&&",41);
        parsingTable.put("TermP_,",41);
        parsingTable.put("Factor_(",42);
        parsingTable.put("Factor_idf",43);
        parsingTable.put("Factor_true",46);
        parsingTable.put("Factor_false",47);
        parsingTable.put("Factor_intt",48);
        parsingTable.put("FactorF_.",44);
        parsingTable.put("FactorF_*",45);
        parsingTable.put("FactorF_+",45);
        parsingTable.put("FactorF_-",45);
        parsingTable.put("FactorF_;",45);
        parsingTable.put("FactorF_)",45);
        parsingTable.put("FactorF_==",45);
        parsingTable.put("FactorF_<",45);
        parsingTable.put("FactorF_&&",45);
        parsingTable.put("FactorF_,",45);
        parsingTable.put("FactorFF_(",49);
        parsingTable.put("FactorFF_*",50);
        parsingTable.put("FactorFF_+",50);
        parsingTable.put("FactorFF_-",50);
        parsingTable.put("FactorFF_;",50);
        parsingTable.put("FactorFF_)",50);
        parsingTable.put("FactorFF_==",50);
        parsingTable.put("FactorFF_<",50);
        parsingTable.put("FactorFF_&&",50);
        parsingTable.put("FactorFF_,",50);
        parsingTable.put("RelExp_&&",51);
        parsingTable.put("RelExp_;",52);
        parsingTable.put("RelExp_)",52);
        parsingTable.put("RelExp_,",52);
        parsingTable.put("RelTerm_(",53);
        parsingTable.put("RelTerm_idf",53);
        parsingTable.put("RelTerm_true",53);
        parsingTable.put("RelTerm_false",53);
        parsingTable.put("RelTerm_intt",53);
        parsingTable.put("ExpressionF_==",54);
        parsingTable.put("ExpressionF_<",55);
        parsingTable.put("Arguments_(",56);
        parsingTable.put("Arguments_idf",56);
        parsingTable.put("Arguments_true",56);
        parsingTable.put("Arguments_false",56);
        parsingTable.put("Arguments_intt",56);
        parsingTable.put("Arguments_)",57);
        parsingTable.put("Argument_,",58);
        parsingTable.put("Argument_)",59);

        File grammFile = new File("Gramm.txt");
        Scanner scn = null;
        try {
            scn = new Scanner(grammFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String pastWord = null;
        String currentWord = null;
        LinkedList<String> currentRule = new LinkedList<>();
        while (scn.hasNext()){
            pastWord = currentWord;
            currentWord = scn.next();
            if (currentWord.equals("->"))
            {
                productionRules.add((LinkedList<String>) currentRule.clone());
                currentRule.clear();
                nonTerminals.add(pastWord);
            }
            else
            {
                if (pastWord != null && !pastWord.equals("->") && !pastWord.equals("\'\'")){
                    if (!pastWord.startsWith("#"))
                    {
                        terminals.add(pastWord);
                    }
                    currentRule.add(pastWord);
                }
            }
        }
        //IMPORTANT
        if (!currentWord.equals("\'\'")){
            terminals.add(currentWord);
            currentRule.add(currentWord);
        }
        productionRules.add((LinkedList<String>) currentRule.clone());

        productionRules.remove(0);
        for (String s : nonTerminals){
            terminals.remove(s);
        }
        terminals.add("$");
        //IMPORTANT

    }
    public static LinkedList<String> getRule(String nonTerm, String term)
    {
        return productionRules.get(parsingTable.get(nonTerm + "_" + term) - 1);
    }
}
