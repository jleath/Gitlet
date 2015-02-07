import org.junit.Test;
import static org.junit.Assert.*;

/** JUnit tests for the Piece class.
 *  @author Joshua Leath
 */
public class TestPiece {
    
    /** Test the Piece class' isFire method. */
    @Test
    public void testIsFire() {
        Piece p = new Piece(true, null, 0, 0, "bomb");
        assertTrue(p.isFire());
        p = new Piece(false, null, 0, 0, "bomb");
        assertFalse(p.isFire());
    }

    /** Test the Piece class' side method. */
    @Test
    public void testSide() {
        Piece p = new Piece(true, null, 0, 0, "bomb");
        assertEquals(0, p.side());
        p = new Piece(false, null, 0, 0, "bomb");
        assertEquals(1, p.side());
    }
    
    /** Test the Piece class' isKing method. */
    @Test
    public void testIsKing() {
        Piece p = new Piece(true, null, 0, 0, "king");
        assertTrue(p.isKing());
        p = new Piece(true, null, 0, 0, "bomb");
        assertFalse(p.isKing());
    }

    /** Test the Piece class' isBomb method. */
    @Test
    public void testIsBomb() {
        Piece p = new Piece(true, null, 0, 0, "king");
        assertFalse(p.isBomb());
        p = new Piece(true, null, 0, 0, "bomb");
        assertTrue(p.isBomb());
    }

    /** Test the Piece class' isShield method. */
    @Test
    public void testIsShield() {
        Piece p = new Piece(true, null, 0, 0, "king");
        assertFalse(p.isShield());
        p = new Piece(true, null, 0, 0, "shield");
        assertTrue(p.isShield());
    }



    /** Run the tests for the Piece class. */ 
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestPiece.class);
    }
}
