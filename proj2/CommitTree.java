import java.util.Date;

/** A CommitTree is a graph of all of this repos past commits.
 *  The root of the tree is the most recent commit of the current
 *  branch.
 *
 *  @author Joshua Leath
 */
public class CommitTree {
    /** The most recent commit. */
    private Commit head;
    /** The name of the current branch. */
    private String currBranch;

    public CommitTree(String b) {
        currBranch = b;
        head = ObjectManager.getHeadOfBranch(b);
    }
    
    public CommitTree(Commit c) {
        head = c;
        currBranch = ObjectManager.getCurrentBranch();
    }

    /** Return the root of this most recent commit of this branch. */
    public Commit getHead() {
        return head;
    }

    /** Serialize and store the head commit of this tree and ensure that
     *  the branch pointer is current. */
    public void saveState() {
        ObjectManager.storeCommit(head);
        ObjectManager.setCurrentBranch(currBranch);
    }

    /** Create a new branch of the repo. */
    public void createNewBranch(String branchName) {
        if (ObjectManager.branchExists(branchName)) {
            System.out.println("A branch with that name already exists.");
            return;
        }
        Branch newB = new Branch(branchName, head.getId());
        ObjectManager.cacheBranch(newB);
    }

    /** Switch to the branch named by BRANCHNAME. */
    public void switchToBranch(String branchName) {
        if (currBranch == branchName) {
            System.out.println("No need to checkout the current branch.");
        } else if (ObjectManager.branchExists(branchName)) {
            currBranch = branchName;
            head = ObjectManager.getHeadOfBranch(branchName);
        } else {
            System.out.println("File does not exist in the most recent " +
                "commit, or no such branch exists.");
        }
    }

    /** Adds the current Commit to the tree by changing the commit that
     *  the current branch points at to be the current Commit. */
    public void pushCommit(Commit toPush, String message) {
        // push the current commit
        toPush.setCommitDate(new Date());
        toPush.setMessage(message);
        // Add staged files to the ./gitlet/obj directory
        for (GitletObject go : toPush.getStagedFiles()) {
            ObjectManager.commitObject(go);
            toPush.updateCommitDate(go);
            System.out.println("Commited " + go.getFileName());
        }
        ObjectManager.storeCommit(toPush);
        ObjectManager.cacheBranch(new Branch(currBranch, toPush.getId()));
    }

    /** Stage the given file FILENAME. */
    public void stageFile(String fileName) {
        head.stageFile(fileName);
    }

    /** Return the name of the current branch. */
    public String getCurrentBranch() {
        return currBranch;
    }

    /** Mark the given file FILENAME for removal. */
    public void markForRemoval(String fileName) {
        head.markForRemoval(fileName);
    }

    /** Print the Id of the head commit of the current branch. */
    public void printHeadID() {
        System.out.println(head.getId());
    }

    /** Print a log of all of the commits in this branch. */
    public void printLog() {
        Commit curr = head;
        while (!(curr.getMessage().equals("initial commit"))) {
            System.out.print(curr.getLogInfo());
            curr = ObjectManager.loadCommit(curr.getParentId());
        }
        System.out.print(curr.getLogInfo());
    }
}
