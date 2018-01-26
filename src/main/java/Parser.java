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
        parseStack = new LinkedList<String>();
        parseStack.add("$");
        parseStack.add("Goal");
    }
}
