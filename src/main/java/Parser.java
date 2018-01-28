import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Parser {
    public static HashMap <Integer, Variable_ST> allVars = new HashMap<>();
    int numOfClassesSoFar = 0 ;
    private ArrayList<String > programBlock ;
    private int i;
    private ArrayList <String> semanticStack ;
    private static ParseTable parseTable1;
    private static LinkedList<String> parseStack;
    private static HashMap<String,Class_ST> symbolTable;
    private Class_ST currentClass;
    private Method_ST currentMethod;
    private String lastTypeRead;
    HashMap<Integer, Integer> temps;
    int tempAddress;
    String save_id_to_know_if_class_or_var;
    String save_id_to_know_if_method_or_var;


    public ArrayList<String> something(){
        String top;
        String inputPointer;
        String absInputPointer;
        String absInputPointer1;

        top = parseStack.getLast();
        while (!top.equals("$")) {
            inputPointer = Compiler_Scanner.getCurrentToken();
            if (isAction(top)){
                //do action
                action(parseStack.pollLast());
            }
            else if (isTerminal(top)){
                if (top.equals(inputPointer)) {
                    Compiler_Scanner.next_token();
                    parseStack.pollLast();
                }
                else {
                    //error
                }
            }
            else {
                LinkedList<String> rule = (LinkedList<String>) parseTablePeek(top, inputPointer).clone();
                parseStack.pollLast();
                while (!rule.isEmpty()) {
                    parseStack.add(rule.pollLast());
                }
            }
//            System.out.println(parseStack);
            top = parseStack.peekLast();
        }
//        System.out.println(programBlock);
        return programBlock;
    }

    private void action(String actionName) {
        if (actionName.equals("#save"))
        {
            semanticStack.add(Integer.toString(i));
            i++;
            // push i
            // i++
        }
        else if (actionName.equals("#main"))
        {
            String s = "(JP,"+ i +",,)";
            programBlock.set(Integer.valueOf(semanticStack.get(semanticStack.size()-1)),s);
            semanticStack.remove(semanticStack.size()-1);
            // PB[topOfStack] = jump to i
            // pop
        }
        else if (actionName.equals("#save_type"))
        {
            lastTypeRead = Compiler_Scanner.getCurrentTokenAbsVal();
        }
        else if (actionName.equals("#check_and_add_to_class_symbol_table"))
        {
            String nameOfClass = Compiler_Scanner.getCurrentTokenAbsVal();
            if (!symbolTable.containsKey(nameOfClass))
            {
                Class_ST t = new Class_ST(nameOfClass,numOfClassesSoFar);
                symbolTable.put(nameOfClass,t);
                currentClass = t;
                numOfClassesSoFar++;
            }
            else
            {
                System.out.println("duplicate class with name: " + nameOfClass);
            }
            // id to symbol table class
        }
        else if (actionName.equals("#check_existence_in_class_symbol_table"))
        {
            String nameOfClass = Compiler_Scanner.getCurrentTokenAbsVal();
            if (!symbolTable.containsKey(nameOfClass))
            {
                System.out.println("class " + nameOfClass + "doesn't exists.");
            }
            else
            {
//                inheritance
            }
            // id to symbol table class
        }
        else if (actionName.equals("#check_and_add_to_field_symbol_table"))
        {
            String nameOfField = Compiler_Scanner.getCurrentTokenAbsVal();
            if (!currentClass.checkFieldExists(nameOfField))
            {
                currentClass.add_field(nameOfField, lastTypeRead);
            }
            else
            {
                System.out.println("duplicate field " + nameOfField);
            }
        }
        else if (actionName.equals("#check_and_add_to_var_symbol_table"))
        {
            String nameOfVar = Compiler_Scanner.getCurrentTokenAbsVal();
            if (!currentMethod.variables.containsKey(nameOfVar))
            {
                currentClass.add_var_to_method(nameOfVar, lastTypeRead, currentMethod.name);
//                Variable_ST t = new Variable_ST(nameOfVar);
//                currentMethod.variables.put(nameOfVar, t);
            }
            else
            {
                System.out.println("duplicate class " + nameOfVar);
            }
        }
        else if (actionName.equals("#check_existence_in_var_symbol_table_and_push"))
        {
            String nameOfVar = Compiler_Scanner.getCurrentTokenAbsVal();
            if (currentMethod.variables.containsKey(nameOfVar))
            {
                semanticStack.add(Integer.toString(currentMethod.variables.get(nameOfVar).getAddress()));
            }
            else if (currentMethod.inputs.containsKey(nameOfVar))
            {
                semanticStack.add(Integer.toString(currentMethod.inputs.get(nameOfVar).getAddress()));
            }
            else
            {
                System.out.println("variable "+nameOfVar+" doesn't exist. ");
            }
            // check existence and push address
        }
        else if (actionName.equals("#check_and_add_to_method_symbol_table"))
        {
            String nameOfMethod = Compiler_Scanner.getCurrentTokenAbsVal();
            if (!currentClass.checkMethodExists(nameOfMethod))
            {
                currentMethod = currentClass.add_method(nameOfMethod, lastTypeRead);
                currentMethod.firstLineAddress = i;
//                Method_ST t = new Method_ST(nameOfMethod);
//                currentClass.methods.put(nameOfMethod, t);
//                currentMethod = t;
            }
            else
            {
                System.out.println("duplicate method " + nameOfMethod);
            }
        }
        else if (actionName.equals("#check_and_add_to_param_symbol_table"))
        {
            String nameOfParam = Compiler_Scanner.getCurrentTokenAbsVal();
            if (!currentMethod.inputs.containsKey(nameOfParam))
            {
                currentClass.add_parameter_to_method(nameOfParam, lastTypeRead, currentMethod.name);
//                Variable_ST t = new Variable_ST(nameOfParam);
//                currentMethod.inputs.put(nameOfParam, t);
            }
            else
            {
                System.out.println("duplicate parameter " + nameOfParam + "in function " + currentMethod.name);
            }
        }
        else if (actionName.equals("#jpf_save"))
        {
            int topIndex = semanticStack.size() - 1;
            programBlock.set(Integer.valueOf(semanticStack.get(topIndex)), "(JPF,"+semanticStack.get(topIndex - 1)+","+Integer.toString((i+1))+",)");
            semanticStack.remove(topIndex);
            semanticStack.remove(topIndex-1);
            semanticStack.add(Integer.toString(i));
            i++;
            // PB[ss(top) <- (jpf,ss(top-1),i+1,)
            // pop(2)
            // push(i)
            // i++
        }
        else if (actionName.equals("#jp"))
        {
            int topIndex = semanticStack.size() - 1;
            programBlock.set(Integer.valueOf(semanticStack.get(topIndex)), "(JP,"+Integer.toString(i)+",,)");
            i++;
            //PB[ss(top)] <- (jp,i,,)
            //i++
        }
        else if (actionName.equals("#label"))
        {
            semanticStack.add(Integer.toString(i));
            //push(i)
        }
        else if (actionName.equals("#while"))
        {
            int topIndex = semanticStack.size() - 1;
            programBlock.set(Integer.valueOf(semanticStack.get(topIndex)), "(JPF, "+ semanticStack.get(topIndex)+", " + Integer.toString((i+1)) + ",)");
            programBlock.set(i,"(JP, " + semanticStack.get(topIndex - 2) + ", " + Integer.toString((i+1)) + ",)");
            i++;
            semanticStack.remove(topIndex);
            semanticStack.remove(topIndex-1);
            semanticStack.remove(topIndex-2);
//            PB[ss(top)]  (jpf, ss(top-1), i+1, )
//            PB[i]  (jp, ss(top-2), ,);
//            i  i + 1;
//            pop(3)
        }
        else if (actionName.equals("#push_imm"))
        {
//            int t = getTemp(Integer.valueOf(Compiler_Scanner.getCurrentTokenAbsVal()));
//            semanticStack.add(t);
            semanticStack.add("#" + Compiler_Scanner.getCurrentTokenAbsVal());
        }
        else if (actionName.equals("#push_one"))
        {
//            int t = getTemp(1);
//            semanticStack.add(Integer.toString(t));
            semanticStack.add("true");
        }
        else if (actionName.equals("#push_zero"))
        {
//            int t = getTemp(0);
//            semanticStack.add(t);
            semanticStack.add("false");
        }
        else if (actionName.equals("#assign"))
        {
            int topIndex = semanticStack.size() - 1;
            String first = semanticStack.get(topIndex);
            String second = semanticStack.get(topIndex-1);
            check_type(first,second);
            programBlock.set(i,"(ASSIGN, "+first+ ", "+second+",)");
            i++;
            semanticStack.remove(topIndex);
            semanticStack.remove(topIndex-1);
//            PB[i] <- (:=, ss(top), ss(top-1),);
//            i ++;
//            pop(2)
        }
        else if (actionName.equals("#for"))
        {
            int topIndex = semanticStack.size()-1;
            programBlock.set(i, "(ADD, "+semanticStack.get(topIndex) + " , " + semanticStack.get(topIndex-1) + " ,"
            + semanticStack.get(topIndex-1) + ")");
            i++;
            programBlock.set(i, "(JP, " + Integer.toString((Integer.valueOf(semanticStack.get(topIndex-2))-1)) + ",,) ");
            i++;
            programBlock.set(Integer.valueOf(semanticStack.get(topIndex - 2)) , "(JPF, " + semanticStack.get(topIndex - 3)+ ", " + i);
            semanticStack.remove(topIndex);
            semanticStack.remove(topIndex-1);
            semanticStack.remove(topIndex-2);
            semanticStack.remove(topIndex-3);

//                          jump(destination address, condition)
//                          add(first,second,destination)
//            PB[i] <- add(ss(top),ss(top-1),ss(top-1))
//            i++
//            PB[i] <- jp(ss(top-2)-1)
//            i++
//            PB[top-2] <- jpf(ss(top-3),i)
//            pop(4)
        }
        else if (actionName.equals("#print"))
        {
            int topIndex = semanticStack.size()-1;
            programBlock.set(i, "(PRINT, " + semanticStack.get(topIndex) + ",,)");
            i++;
            semanticStack.remove(topIndex);
//            PB[i] <- (print,ss(top))
//            i++
//            pop

        }
        else if (actionName.equals("#add"))
        {
            int topIndex = semanticStack.size()-1;
            String first = semanticStack.get(topIndex);
            String second = semanticStack.get(topIndex-1);
            int t = getTemp(0);
            check_type(first,second);
            programBlock.set(i, "(ADD, " + first + ", " + second + ", " + t + ")");
            i++;
            semanticStack.remove(topIndex);
            semanticStack.remove(topIndex-1);
            semanticStack.add(Integer.toString(t));
//            t <- gettemp
//            PB[i] <- (+ , ss(top), ss(top-1), t);
//            i++ ; pop(2); push(t)
        }
        else if (actionName.equals("#sub"))
        {
            int topIndex = semanticStack.size()-1;
            String first = semanticStack.get(topIndex);
            String second = semanticStack.get(topIndex-1);
            int t = getTemp(0);
            check_type(first,second);
            programBlock.set(i, "(SUB, " + semanticStack.get(topIndex) + ", " + semanticStack.get(topIndex-1) + ", " + t);
            i++;
            semanticStack.remove(topIndex);
            semanticStack.remove(topIndex-1);
            semanticStack.add(Integer.toString(t));
            //            t <- gettemp
//            PB[i] <- (- , ss(top), ss(top-1), t);
//            i++ ; pop(2); push(t)
        }
        else if (actionName.equals("#mult"))
        {
            int topIndex = semanticStack.size()-1;
            String first = semanticStack.get(topIndex);
            String second = semanticStack.get(topIndex-1);
            int t = getTemp(0);
            check_type(first,second);
            programBlock.set(i, "(MULT, " + semanticStack.get(topIndex) + ", " + semanticStack.get(topIndex-1) + ", " + t);
            i++;
            semanticStack.remove(topIndex);
            semanticStack.remove(topIndex-1);
            semanticStack.add(Integer.toString(t));
//            t <- gettemp
//            PB[i] <- (* , ss(top), ss(top-1), t);
//            i++ ; pop(2); push(t)
        }
        else if (actionName.equals("#push_unfirst_parameters_address_twice"))
        {
            int topIndex = semanticStack.size()-1;
            int t = getTemp(0);
            t = Integer.valueOf(semanticStack.get(topIndex));
            semanticStack.remove(semanticStack.get(topIndex));
            semanticStack.add(Integer.toString(t+4));
            semanticStack.add(Integer.toString(t+4));
//            t = gettemp();
//            t = ss(top)
//            pop
//            push(t+4)
//            push(t+4)
        }
        else if (actionName.equals("#push_parameter_address_twice"))
        {
            int topIndex = semanticStack.size()-1;
            if (!currentMethod.inputs.isEmpty())
            {
                semanticStack.add(Integer.toString(currentMethod.outputAddress + 4));
                semanticStack.add(Integer.toString(currentMethod.outputAddress + 4));
            }
            else
            {
                System.out.println("Semantic error: method needs no parameter.");
            }
//            push methods first parameter in stack twice
        }
        else if (actionName.equals("#return_to_caller"))
        {
            programBlock.set(i, "(JMP,@"+currentMethod.getCallerAddressSlot()+",,)");
            currentMethod.outputAddress = Integer.valueOf(semanticStack.get(semanticStack.size()-1));
            semanticStack.remove(semanticStack.size()-1);
            i++;
//            PB[i] <- (jmp,@method_return_slot)
        }
        else if (actionName.equals("#pop"))
        {
            semanticStack.remove(semanticStack.size()-1);
        }
        else if (actionName.equals("#call_method"))
        {
            int topIndex = semanticStack.size()-1;
            programBlock.set(i,"(ASSIGN, #"+ (i+2) + ", "+currentMethod.getCallerAddressSlot()+",)");
            i++;
            programBlock.set(i,"(JP, "+ currentMethod.firstLineAddress + ", ,)");
            i++;
            semanticStack.add(Integer.toString(currentMethod.outputAddress));
        }
        else if (actionName.equals("#equal"))
        {
            int topIndex = semanticStack.size()-1;
            int t = getTemp(0);
            programBlock.set(i, "(EQ, " + semanticStack.get(topIndex) + ", " + semanticStack.get(topIndex-1) + ", " + t);
            i++;
            semanticStack.remove(topIndex);
            semanticStack.remove(topIndex-1);
            semanticStack.add(Integer.toString(t));
//            t <- gettemp
//            PB[i] <- (EQ , ss(top), ss(top-1), t);
//            i++ ; pop(2); push(t)
        }
        else if (actionName.equals("#less"))
        {
            int topIndex = semanticStack.size()-1;
            int t = getTemp(0);
            programBlock.set(i, "(LT, " + semanticStack.get(topIndex) + ", " + semanticStack.get(topIndex-1) + ", " + t + ")");
            i++;
            semanticStack.remove(topIndex);
            semanticStack.remove(topIndex-1);
            semanticStack.add(Integer.toString(t));
//            t <- gettemp
//            PB[i] <- (LT , ss(top), ss(top-1), t);
//            i++ ; pop(2); push(t)
        }
        else if (actionName.equals("#and"))
        {
            int t = getTemp(0);
            int topIndex = semanticStack.size() - 1;
            programBlock.set(i, "(AND, "+semanticStack.get(topIndex)+", " + semanticStack.get(topIndex-1)+ ",)");
            i++;
            semanticStack.remove(topIndex);
            semanticStack.remove(topIndex-1);
            semanticStack.add(Integer.toString(t));
        }
        else if (actionName.equals("#save_id_to_know_if_class_or_var"))
        {
            save_id_to_know_if_class_or_var = Compiler_Scanner.getCurrentTokenAbsVal();
        }
        else if (actionName.equals("#save_id_to_know_if_method_or_var"))
        {
            save_id_to_know_if_method_or_var = Compiler_Scanner.getCurrentTokenAbsVal();
        }
        else if (actionName.equals("#so_it_was_variable"))
        {
            String nameOfVar = save_id_to_know_if_class_or_var;
            if (currentMethod.variables.containsKey(nameOfVar))
            {
                semanticStack.add(Integer.toString(currentMethod.variables.get(nameOfVar).getAddress()));
//                semanticStack.add(Integer.toString(currentMethod.variables.get(nameOfVar).address));
            }
            else if (currentMethod.inputs.containsKey(nameOfVar))
            {
                semanticStack.add(Integer.toString(currentMethod.inputs.get(nameOfVar).getAddress()));
//                semanticStack.add(Integer.toString(currentMethod.variables.get(nameOfVar).address));
            }
        }
        else if (actionName.equals("#so_it_was_method"))
        {
            currentClass = symbolTable.get(save_id_to_know_if_class_or_var);
            currentMethod = currentClass.get_method(save_id_to_know_if_method_or_var);
            symbolTable.get(save_id_to_know_if_class_or_var).get_method(save_id_to_know_if_method_or_var);
        }
        else if (actionName.equals("#so_it_was_field"))
        {
            Variable_ST c = symbolTable.get(save_id_to_know_if_class_or_var).get_field(save_id_to_know_if_method_or_var);
            semanticStack.add(Integer.toString(c.getAddress()));
        }
    }

    private void check_type(String first, String second) {
        if (first.startsWith("#"))
        {
            if (allVars.containsKey(Integer.valueOf(second)) && !allVars.get(Integer.valueOf(second)).getType().equals("int"))
            {
                System.out.println("Semantic error. type mismatch : "+ first +" , "+
                        allVars.get(Integer.valueOf(second)).name);
            }
        }
        else if (second.startsWith("#"))
        {
            if (allVars.containsKey(Integer.valueOf(first)) && !allVars.get(Integer.valueOf(first)).getType().equals("int"))
            {
                System.out.println("Semantic error. type mismatch : "+ second +" , "+
                        allVars.get(Integer.valueOf(first)).name);
            }
        }
        else if (first.equals("true") || first.equals("false") )
        {
            if (allVars.containsKey(Integer.valueOf(second)) && !allVars.get(Integer.valueOf(second)).getType().equals("boolean"))
            {
                System.out.println("Semantic error. type mismatch : "+ first +" , "+
                        allVars.get(Integer.valueOf(second)).name);
            }
        }
        else if (second.equals("true") || second.equals("false"))
        {
            if (allVars.containsKey(Integer.valueOf(first)) && !allVars.get(Integer.valueOf(first)).getType().equals("boolean"))
            {
                System.out.println("Semantic error. type mismatch : "+ second +" , "+
                        allVars.get(Integer.valueOf(first)).name);
            }
        }
        else
        {
            if (allVars.containsKey(Integer.valueOf(first)) && allVars.containsKey(Integer.valueOf(second)))
            {
                if (!allVars.get(Integer.valueOf(first)).getType().equals(allVars.get(Integer.valueOf(second)).getType()))
                {
                    System.out.println("Semantic error. type mismatch : "+ allVars.get(Integer.valueOf(second)).name +" , "+
                            allVars.get(Integer.valueOf(first)).name);
                }
            }
        }
    }

    private static boolean isAction(String top) {
        return (top.charAt(0) == '#');
    }

    public static boolean isTerminal(String token) {
        return ParseTable.terminals.contains(token);
    }

    public static LinkedList<String> parseTablePeek(String nonTerminal, String terminal) {
        return ParseTable.getRule(nonTerminal, terminal);
    }
    public int getTemp(int val){
        tempAddress += 4;
        temps.put(tempAddress,val);
        return tempAddress;
    }

    Parser(){
        parseTable1 = new ParseTable();
        parseStack = new LinkedList<>();
        parseStack.add("$");
        parseStack.add("Goal");
        semanticStack = new ArrayList<>();
        programBlock = new ArrayList<>(1000);
        for (int j = 0; j < 30; j++) {
            programBlock.add(String.valueOf(j));
        }
        i = 0;
        symbolTable = new HashMap<>();
        tempAddress = 996;
        temps = new HashMap<>();
    }
}
