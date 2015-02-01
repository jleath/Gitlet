public class CalculatorUI {
    /** Prompts the user for mathematical expressions and then outputs
     *  the expression's result.
     *  Expressions must match the format, a op b where a and b are 
     *  positive or negative integers and op is either + or *.
     *  Does not handle malformed expressions, the program will crash.
     *  The user can enter 'quit' to terminate the program.
     */
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        while (true) {
            System.out.print("> ");
            // check for 'quit'
            String first = StdIn.readString();
            if (first.equals("quit"))
                System.exit(0);
            // check for history
            if (first.equals("dump")) {
                calc.printAllHistory();
                continue;
            }
            if (first.equals("history")) {
                int n = StdIn.readInt();
                calc.printHistory(n);
                continue;
            }
            // get operands and operator
            int a = Integer.parseInt(first);
            StdIn.readChar();
            char op = StdIn.readChar();
            int b = StdIn.readInt();
            int result;
            // calculate the result
            if (op == '+')
                result = calc.add(a, b);
            else if (op == '*')
                result = calc.multiply(a, b);
            else {
                result = 0;
                System.out.println("Invalid operator.");
                System.exit(0);
            }
            // Save the equation
            calc.saveEquation(a + " " + op + " " + b, result);
            // print the result
            System.out.println(result);
        }
    }
}
