import org.junit.Test;
import static org.junit.Assert.*;
import ngordnet.YearlyRecord;
import java.util.HashSet;

/** Tests the Synset class.
 * @author Joshua Leath
 */
public class YearlyRecordTest {

    @Test
    public void testPutAndCount() {
        System.out.println("Testing put()...");
        YearlyRecord yr = new YearlyRecord();
        yr.put("josh", 10);
        yr.put("andrew", 100);
        yr.put("wendy", 42);
        yr.put("Anthony", 152);
        yr.put("Sarah", 42);
        yr.put("Ian", 0);
        System.out.println("Testing count()...");
        assertTrue(yr.count("josh") == 10);
        assertTrue(yr.count("andrew") == 100);
        assertTrue(yr.count("wendy") == 42);
        assertTrue(yr.count("Anthony") == 152);
        assertTrue(yr.count("Sarah") == 42);
        assertTrue(yr.count("Ian") == 0);
    }
    @Test
    public void testWords() {
        System.out.println("Testing words()...");
        YearlyRecord yr = new YearlyRecord();
        yr.put("josh", 10);
        yr.put("andrew", 100);
        yr.put("wendy", 42);
        yr.put("Anthony", 152);
        yr.put("Sarah", 42);
        yr.put("Ian", 0);
        System.out.println("This should print the following:");
        System.out.println("Ian");
        System.out.println("josh");
        System.out.println("wendy");
        System.out.println("Sarah");
        System.out.println("andrew");
        System.out.println("Anthony");
        System.out.println();
        for (String n : yr.words()) {
            System.out.println(n);
        }
    }

    @Test
    public void testCounts() {
        YearlyRecord yr = new YearlyRecord();
        yr.put("josh", 10);
        yr.put("andrew", 100);
        yr.put("wendy", 42);
        yr.put("Anthony", 152);
        yr.put("Sarah", 42);
        yr.put("Ian", 0);
        System.out.println("This should print the following:");
        System.out.println("0");
        System.out.println("10");
        System.out.println("42");
        System.out.println("43");
        System.out.println("100");
        System.out.println("152");
        System.out.println();
        for (Number n : yr.counts()) {
            System.out.println(n);
        }
    }

    @Test
    public void testRank() {
        System.out.println("Testing rank()...");
        YearlyRecord yr = new YearlyRecord();
        yr.put("josh", 10);
        yr.put("andrew", 100);
        yr.put("wendy", 42);
        yr.put("Anthony", 152);
        yr.put("Sarah", 42);
        yr.put("Ian", 0);
        assertTrue(yr.rank("Anthony") == 1);
        assertTrue(yr.rank("andrew") == 2);
        assertTrue(yr.rank("wendy") == 4);
        assertTrue(yr.rank("Sarah") == 3);
        assertTrue(yr.rank("josh") == 5);
        assertTrue(yr.rank("Ian") == 6);
    }

    /** Runs tests. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(YearlyRecordTest.class);
    }
} 
