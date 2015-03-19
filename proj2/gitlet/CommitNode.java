package gitlet;
import java.util.Date;

/** This class represents a single node in gitlet's CommitTree.
 *  A CommitNode tracks the commit that precedes it in the history
 *  of commits and stores the following information:
 *      - a message describing the commit
 *      - the date and time of the commit
 *      - a unique ID number
 *      - the files that are tracked by the commit
 *      - files that have been staged for a commit
 *      - a label (for branching)
 *
 *  @author Joshua Leath
 */
public class CommitNode {
    private CommitNode parent, nextCommit;
    private String message;
    private Date commitDate;
    private int ID;
    boolean filesStaged;
    private String label;

}
