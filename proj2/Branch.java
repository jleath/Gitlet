/** A small class representing a branch of a gitlet repo.
 *
 *  A branch merely stores the id of the commit at the head
 *  of this branch and has a name.  Branches are saved in the
 *  gitlet repo in the 'branches' directory as files.  These
 *  files are named after the branch and contain the id of the
 *  head commit.
 *
 *  @author Joshua Leath
 */
public class Branch {
    private int commitId;
    private String branchName;

    public Branch(String name, int commitId) {
        this.commitId = commitId;
        branchName = name;
    }
    
    public Branch(String name) {
        this(name, 0);
    }

    public void setCommitId(int id) {
        commitId = id;
    }

    public int getCommitId() {
        return commitId;
    }

    public String getName() {
        return branchName;
    }
}
