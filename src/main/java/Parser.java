import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Parser {
    private ArrayList<String > programBlock ;
    private int i;
    private ArrayList <Integer> semanticStack ;
    private static ParseTable parseTable1;
    private static LinkedList<String> parseStack;
    private static HashMap<String,Class_ST> symbolTable;
    private Class_ST currentClass;
    private Method_ST currentMethod;


    public void something(){
        String top;
        String inputPointer;
        inputPointer = Compiler_Scanner.nextToken();
        top = parseStack.getLast();
        while (!top.equals("$")) {
            if (isAction(top)){
                //do action
                action(parseStack.pollLast());
            }
            else if (isTerminal(top)){
                if (top.equals(inputPointer)) {
                    inputPointer = Compiler_Scanner.nextToken();
                    parseStack.pollLast();
                }
                else {
                    //error
                }
            }
            else {
                LinkedList<String> rule = parseTablePeek(top, inputPointer);
                parseStack.pollLast();
                while (!rule.isEmpty()) {
                    parseStack.add(rule.pollLast());
                }
            }
            System.out.println(parseStack);
            top = parseStack.peekLast();
        }
    }

    private void action(String actionName) {
        if (actionName.equals("#save"))
        {
            semanticStack.add(i);
            i++;
            // push i
            // i++
        }
        else if (actionName.equals("#main"))
        {
            programBlock.set(semanticStack.get(semanticStack.size()-1),"(JP,"+i+",,)");
            semanticStack.remove(semanticStack.size()-1);
            // PB[topOfStack] = jump to i
            // pop
        }
        else if (actionName.equals("#check_and_add_to_class_symbol_table"))
        {
            String nameOfClass = Compiler_Scanner.peek_next_token();
            if (!symbolTable.containsKey(nameOfClass))
            {
                Class_ST t = new Class_ST(nameOfClass);
                symbolTable.put(nameOfClass,t);
                currentClass = t;
            }
            else
            {
                System.out.println("duplicate class " + nameOfClass);
            }
            // id to symbol table class
        }
        else if (actionName.equals("#check_existence_in_class_symbol_table"))
        {
            String nameOfClass = Compiler_Scanner.peek_next_token();
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
            String nameOfField = Compiler_Scanner.peek_next_token();
            if (!currentClass.fields.containsKey(nameOfField))
            {
                Variable_ST t = new Variable_ST(nameOfField);
                currentClass.fields.put(nameOfField, t);
            }
            else
            {
                System.out.println("duplicate field " + nameOfField);
            }
        }
        else if (actionName.equals("#check_and_add_to_var_symbol_table"))
        {
            String nameOfVar = Compiler_Scanner.peek_next_token();
            if (!currentMethod.variables.containsKey(nameOfVar))
            {
                Variable_ST t = new Variable_ST(nameOfVar);
                currentMethod.variables.put(nameOfVar, t);
            }
            else
            {
                System.out.println("duplicate class " + nameOfVar);
            }
        }
        else if (actionName.equals("#check_and_add_to_method_symbol_table"))
        {
            String nameOfMethod = Compiler_Scanner.peek_next_token();
            if (!currentClass.methods.containsKey(nameOfMethod))
            {
                Method_ST t = new Method_ST(nameOfMethod);
                currentClass.methods.put(nameOfMethod, t);
            }
            else
            {
                System.out.println("duplicate method " + nameOfMethod);
            }
        }
        else if (actionName.equals("#check_and_add_to_param_symbol_table"))
        {
            String nameOfParam = Compiler_Scanner.peek_next_token();
            if (!currentMethod.inputs.containsKey(nameOfParam))
            {
                Variable_ST t = new Variable_ST(nameOfParam);
                currentMethod.inputs.put(nameOfParam, t);
            }
            else
            {
                System.out.println("duplicate parameter " + nameOfParam + "in function " + currentMethod.name);
            }
        }
        else if (actionName.equals("#jpf_save"))
        {
            int topIndex = semanticStack.size() - 1;
            programBlock.set(semanticStack.get(topIndex), "(JPF,"+semanticStack.get(topIndex)+","+(i+1)+",)");
            semanticStack.remove(topIndex);
            semanticStack.remove(topIndex-1);
            semanticStack.add(i);
            i++;
            // PB[ss(top) <- (jpf,ss(top-1),i+1,)
            // pop(2)
            // push(i)
            // i++
        }
        else if (actionName.equals("#jp"))
        {
            int topIndex = semanticStack.size() - 1;
            programBlock.set(topIndex, "(JP,"+i+",,)");
            i++;
            //PB[ss(top)] <- (jp,i,,)
            //i++
        }
        else if (actionName.equals("#label"))
        {
            semanticStack.add(i);
            //push(i)
        }
        else if (actionName.equals("#while"))
        {
            int topIndex = semanticStack.size() - 1;
            programBlock.set(topIndex, "(JPF, "+ semanticStack.get(topIndex)+", " + (i+1) + ",)");
            programBlock.set(i,"(JP, " + semanticStack.get(topIndex - 2) + ", " + (i+1) + ",)");
            i++;
            semanticStack.remove(topIndex);
            semanticStack.remove(topIndex-1);
            semanticStack.remove(topIndex-2);
//            PB[ss(top)]  (jpf, ss(top-1), i+1, )
//            PB[i]  (jp, ss(top-2), ,);
//            i  i + 1;
//            pop(3)
        }
        else if (actionName.equals("#variable_symbol_table_check_for"))
        {
            // check existence and push value
        }
        else if (actionName.equals("#assign"))
        {
//            PB[i] <- (:=, ss(top), ss(top-1),);
//            i ++;
//            pop(2)
        }
        else if (actionName.equals("#for"))
        {

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
//            PB[i] <- (print,ss(top))
//            i++
//            pop

        }
        else if (actionName.equals("#add"))
        {
//            t <- gettemp
//            PB[i] <- (+ , ss(top), ss(top-1), t);
//            i++ ; pop(2); push(t)
        }
        else if (actionName.equals("#sub"))
        {
            //            t <- gettemp
//            PB[i] <- (- , ss(top), ss(top-1), t);
//            i++ ; pop(2); push(t)
        }
        else if (actionName.equals("#mult"))
        {
//            t <- gettemp
//            PB[i] <- (* , ss(top), ss(top-1), t);
//            i++ ; pop(2); push(t)
        }
        else if (actionName.equals("#push_unfirst_parameters_address_twice"))
        {
//            t = gettemp();
//            t = ss(top)
//            pop
//            push(t+4)
//            push(t+4)
        }
        else if (actionName.equals("#push_parameter_address_twice"))
        {
//            push methods first parameter in stack twice
        }
        else if (actionName.equals("#return_to_caller"))
        {
//            PB[i] <- (jmp,@method_return_slot)
        }
        else if (actionName.equals("#call_method(jump_i+2_and_fill_caller_slot)"))
        {

        }
        else if (actionName.equals("#equal"))
        {
//            t <- gettemp
//            PB[i] <- (EQ , ss(top), ss(top-1), t);
//            i++ ; pop(2); push(t)
        }
        else if (actionName.equals("#less"))
        {
//            t <- gettemp
//            PB[i] <- (LT , ss(top), ss(top-1), t);
//            i++ ; pop(2); push(t)
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

    Parser(){
        parseTable1 = new ParseTable();
        parseStack = new LinkedList<>();
        parseStack.add("$");
        parseStack.add("Goal");
        semanticStack = new ArrayList<>();
        programBlock = new ArrayList<>(1000);
        i = 0;
        symbolTable = new HashMap<>();
    }
}
