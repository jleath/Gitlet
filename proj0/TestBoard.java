import org.junit.Test;
import static org.junit.Assert.*;

/** JUnit tests for the Board class.
 *  @author Joshua Leath
 */
public class TestBoard {
    /** Test the pieceAt method. */
    @Test
    public void testPieceAt() {
        Board b = new Board();
        Piece p = b.pieceAt(0, 0);
        assertTrue(p.isFire());
        assertTrue(p.isPawn());
        p = b.pieceAt(2, 6);
        assertTrue(p.isShield());
        assertFalse(p.isFire());
        p = b.pieceAt(3, 3);
        assertTrue(p == null);
    }

    /** Test the place method. */
    @Test
    public void testPlace() {
        Board b = new Board(true);
        Piece p = new Piece(true, b, 0, 0, "shield");
        b.place(p, 0, 0);
        p = b.pieceAt(0, 0);
        assertTrue(p.isFire());
        assertTrue(p.isShield());
        p = new Piece(false, b, 0, 0, "bomb");
        b.place(p, 0, 0);
        p = b.pieceAt(0, 0);
        assertFalse(p.isFire());
        assertTrue(p.isBomb());
    }

    /** Test the remove method. */
    @Test
    public void testRemove() {
        Board b = new Board();
        assertTrue(b.pieceAt(0, 0) != null);
        b.remove(0, 0);
        assertTrue(b.pieceAt(0, 0) == null);
        assertTrue(b.remove(0, 0) == null);
        assertTrue(b.remove(8, 10) == null);
    }
    
    /** Run the tests for the Board class. */ 
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestBoard.class);
    }
}
