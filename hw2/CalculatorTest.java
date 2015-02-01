import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    /* Do not change this to be private. For silly testing reasons it is public. */
    public Calculator tester;

    /**
     * setUp() performs setup work for your Calculator.  In short, we 
     * initialize the appropriate Calculator for you to work with.
     * By default, we have set up the Staff Calculator for you to test your 
     * tests.  To use your unit tests for your own implementation, comment 
     * out the StaffCalculator() line and uncomment the Calculator() line.
     **/
    @Before
    public void setUp() {
        // tester = new StaffCalculator(); // Comment me out to test your Calculator
        tester = new Calculator();   // Un-comment me to test your Calculator
    }

    // TASK 1: WRITE JUNIT TESTS
    @Test
    public void testAdd() {
        int test = tester.add(5, 5);
        assertEquals(10, test);
        test = tester.add(5, -5);
        assertEquals(0, test);
        test = tester.add(-5, -5);
        assertEquals(-10, test);
        assertEquals(20, tester.add(10, 10));
        assertEquals(11497, tester.add(1074, 10423));
        assertEquals(-2147483648, tester.add(2147483647, 1));
    }

    @Test
    public void testMultiply() {
        assertEquals(tester.multiply(4, 2), 8);
        assertEquals(tester.multiply(4, 4), 16);
        assertEquals(tester.multiply(10, 16), 160);
        assertEquals(tester.multiply(1, 5), 5);
        assertEquals(tester.multiply(52, 3), 156);
        assertEquals(tester.multiply(0, 51), 0);
        assertEquals(tester.multiply(7, 7), 49);
        assertEquals(tester.multiply(16, 16), 256);
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        jh61b.junit.textui.runClasses(CalculatorTest.class);
    }       
}
