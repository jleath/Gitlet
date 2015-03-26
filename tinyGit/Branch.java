/** A small class representing a branch of a tinyGit repo.
 *
 *  A branch merely stores the id of the commit at the head
 *  of this branch and has a name.  Branches are saved in the
 *  tinyGit repo in the 'branches' directory as files.  These
 *  files are named after the branch and contain the id of the
 *  head commit.
 *
 *  @author Joshua Leath
 */
public class Branch {
    /** The id of the head commit of this branch. */
    private int commitId;
    /** The name of this branch. */
    private String branchName;
    /** The id of the commit that represents the split point of this
     *  branch and other branches. */
    private int splitPointId;

    public Branch(String name, int commitId) {
        this.commitId = commitId;
        branchName = name;
        splitPointId = commitId;
    }
    
    public Branch(String name) {
        this(name, 0);
    }

    /** Return the id of the commit that represents the split point
     *  of this branch and other branches. */
    public int getSplitPointId() {
        return splitPointId;
    }

    /** Sets the head commit of this branch to be the commit with
     *  id number ID. */
    public void setCommitId(int id) {
        commitId = id;
    }

    /** Returns the commit id of the head of this branch. */
    public int getCommitId() {
        return commitId;
    }

    /** Return the name of this branch. */
    public String getName() {
        return branchName;
    }
}
