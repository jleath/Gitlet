import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

public class GitletObjectTest {
    @Test
    public void testAll() {
        GitletObject test = new GitletObject("test", 0);
        assertFalse(test.hasBeenCommitted());
        test.setCommitDate(new Date());
        assertTrue(test.hasBeenCommitted());
        assertFalse(test.isStaged());
        assertFalse(test.isMarkedForRemoval());
        test.stage();
        assertTrue(test.isStaged());
        test.markForRemoval();
        assertTrue(test.isMarkedForRemoval());
        test.unstage();
        assertFalse(test.isStaged());
        test.unmarkForRemoval();
        assertFalse(test.isMarkedForRemoval());
        Date curr = new Date();
        test.setCommitDate(curr);
        assertTrue(curr.equals(test.lastCommitDate()));
        assertTrue(test.getFileName().equals("test"));
    }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(GitletObjectTest.class);
    }

}
