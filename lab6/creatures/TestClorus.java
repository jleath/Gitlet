package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Test the Clorus class.
 * @author Joshua Leath
 */

public class TestClorus {
    @Test
    public void testChoose() {
        // Test that a clorus will replicate properly.
        Clorus c = new Clorus(1.5);
        HashMap<Direction, Occupant> empty = new HashMap<Direction, Occupant>();
        empty.put(Direction.TOP, new Impassible());
        empty.put(Direction.BOTTOM, new Impassible());
        empty.put(Direction.LEFT, new Impassible());
        empty.put(Direction.RIGHT, new Empty());

        Action actual = c.chooseAction(empty);
        Action expected = new Action(Action.ActionType.REPLICATE, Direction.RIGHT);

        assertEquals(expected, actual);

        // Test that a clorus will stay if surrounded by plips.

        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        empty.put(Direction.TOP, new Plip());
        empty.put(Direction.BOTTOM, new Plip());
        empty.put(Direction.LEFT, new Plip());
        empty.put(Direction.RIGHT, new Plip());

        actual = c.chooseAction(surrounded);
        expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        // Test that a clorus will attack a nearby plip if there are empties.
        empty.put(Direction.TOP, new Empty());
        empty.put(Direction.LEFT, new Impassible());
        empty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(empty);
        expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

        assertEquals(expected, actual);

        c = new Clorus(.2);
        empty.put(Direction.BOTTOM, new Impassible());
        empty.put(Direction.TOP, new Impassible());
        empty.put(Direction.RIGHT, new Impassible());
        empty.put(Direction.LEFT, new Empty());

        actual = c.chooseAction(empty);
        expected = new Action(Action.ActionType.MOVE, Direction.LEFT);

        assertEquals(expected, actual);
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }
}
