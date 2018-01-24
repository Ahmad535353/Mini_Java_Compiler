import java.util.LinkedList;

public class Parser {
    private static LinkedList<String> parseStack;
    public void something(){
        String top;
        String inputPointer;
        inputPointer = Scanner.firstInput();
        top = parseStack.getLast();
        while (top != "$") {
            if (isAction(top)){
                //do action
            }
            else if (isTerminal(top)){
                if (top == inputPointer) {
                    inputPointer = Scanner.nextToken();
                }
                else {
                    //error
                }
            }
            else {
                LinkedList<String> rule = parseTablePeek(top, inputPointer);
                while (!rule.isEmpty()) {
                    parseStack.add(rule.poll());
                }
            }
            top = parseStack.poll();
        }
    }

    private static boolean isAction(String top) {
        return (top.charAt(0) == '#');
    }

    public static boolean isTerminal(String token) {
        return true;
    }

    public static LinkedList<String> parseTablePeek(String nonTerminal, String terminal) {
        return null;
    }

    Parser(){
        parseStack = new LinkedList<String>();
        parseStack.add("$");
        parseStack.add("Goal");
    }
}
