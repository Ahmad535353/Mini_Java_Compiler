import java.util.LinkedList;

public class Parser {
    private static ParseTable parseTable1;
    private static LinkedList<String> parseStack;
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
        if (actionName.equals("#jump_to_main"))
        {

        }
        else if (actionName.equals("#save"))
        {

        }
        else if (actionName.equals("#class_symbol_table"))
        {

        }
        else if (actionName.equals("#check_existance_inST"))
        {

        }
        else if (actionName.equals("#variable_symbol_table"))
        {

        }
        else if (actionName.equals("#method_symbol_table"))
        {

        }
        else if (actionName.equals("#method_parameter_symbol_table"))
        {

        }
        else if (actionName.equals("#jpf_save"))
        {

        }
        else if (actionName.equals("#jp"))
        {

        }
        else if (actionName.equals("#label"))
        {

        }
        else if (actionName.equals("#save"))
        {

        }
        else if (actionName.equals("#while"))
        {

        }
        else if (actionName.equals("#assign"))
        {

        }
        else if (actionName.equals("#increment"))
        {

        }
        else if (actionName.equals("#print"))
        {

        }
        else if (actionName.equals("#add"))
        {

        }
        else if (actionName.equals("#sub"))
        {

        }
        else if (actionName.equals("#mult"))
        {

        }
        else if (actionName.equals("#push_parameters"))
        {

        }
        else if (actionName.equals("#call_method(jump_i+2_and_fill_caller_slot)"))
        {

        }
        else if (actionName.equals("#equal"))
        {

        }
        else if (actionName.equals("#less"))
        {

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
    }
}
