package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

/* An implementation of the Clorus, a barbaric creature that enjoys
 * eating the peaceful Plips.
 * @author Joshua Leath
 */

public class Clorus extends Creature {
    /** red color. */
    private int r = 34;
    /** green color. */
    private int g = 0;
    /** blue color. */
    private int b = 231;

    /* The amount of energy lost when moving. */
    private static final double ENERGY_LOSS = .03;
    /* The amount of energy gained when not moving. */
    private static final double ENERGY_GAIN = .01;
    
    /** Creates a clorus with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    /** Creates a clorus with energy equal to 1. */
    public Clorus() {
        this(1);
    }

    /** Returns the color of a clorus where red = 34, green = 0, and blue = 231. */
    public Color color() {
        return color(r, g, b);
    }

    /** Attack the creature C and gain that creatures energy. */
    public void attack(Creature c) {
        energy = energy + c.energy();  
    }

    public void move() {
        energy = energy - ENERGY_LOSS;
    }

    public void stay() {
        energy = energy - ENERGY_GAIN;
    }

    /** Cloruses confer half of their energy to their offspring when
     *  they replicate */
    public Clorus replicate() {
        double halved = energy / 2.0;
        Clorus offspring = new Clorus(halved);
        energy = halved;
        return offspring;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty"); 
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        // If there are no empty spaces around the clorus, the clorus will stay.
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plips.size() > 0) {
            Direction attackDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, attackDir);
        } else if (energy >= 1) {
            Direction repDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, repDir);
        }
        Direction moveDir = HugLifeUtils.randomEntry(empties);
        return new Action(Action.ActionType.MOVE, moveDir);
    }
}
