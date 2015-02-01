public class CalculatorUI {
    private static Calculator calc = new Calculator();
    
    /** Handles the history, dump, quit, clear, and undo commands.
     *  Returns a string containing the first operand, if there is one.
     *  Otherwise, returns null;
     */
    private static String handleCommands() {
        String first = StdIn.readString();
        // check for 'quit'
        if (first.equals("quit"))
            System.exit(0);
        else if (first.equals("dump")) {
            calc.printAllHistory();
            return null;
        } else if (first.equals("history")) {
            int n = StdIn.readInt();
            calc.printHistory(n);
            return null;
        } else if (first.equals("clear")) {
            calc.clearHistory();
            return null;
        } else if (first.equals("undo")) {
            calc.undoEquation();
            return null;
        } else if (first.equals("sum")) {
            System.out.println(calc.cumulativeSum());
            return null;
        } else if (first.equals("product")) {
            System.out.println(calc.cumulativeProduct());
            return null;
        }
        return first;
    }

    /** Returns the result of the given expression where A is an integer,
     *  op is an arithmetic operator, and B is an integer.
     */
    private static int calcResult(int a, char op, int b) {
        int result;
        switch(op) {
            case '+':
                result = calc.add(a, b);
                break;
            case '*':
                result = calc.multiply(a, b);
                break;
            default:
                result = 0;
                System.out.println("Invalid Operator");
                System.exit(0);
                break;
        }
        return result;
    }

    /** Prompts the user for mathematical expressions and then outputs
     *  the expression's result.
     *  Expressions must match the format, a op b where a and b are 
     *  positive or negative integers and op is either + or *.
     *  Does not handle malformed expressions, the program will crash.
     *  The user can enter 'quit' to terminate the program.
     */
    public static void main(String[] args) {
        while (true) {
            System.out.print("> ");
            String first = handleCommands();
            if (first == null)
                continue;
            // get operands and operator
            int a = Integer.parseInt(first);
            StdIn.readChar();
            char op = StdIn.readChar();
            int b = StdIn.readInt();
            // calculate the result
            int result = calcResult(a, op, b);
            // Save the equation
            calc.saveEquation(a + " " + op + " " + b, result);
            // print the result
            System.out.println(result);
        }
    }
}
