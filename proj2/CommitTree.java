import java.util.Date;

public class CommitTree {
    private Commit head;
    private String currBranch;

    private CommitTree(String b) {
        currBranch = b;
        head = ObjectManager.getHeadOfBranch(b);
    }
    
    public CommitTree(Commit c) {
        head = c;
        currBranch = ObjectManager.getCurrentBranch();
    }

    public Commit getHead() {
        return head;
    }

    public void saveState() {
        ObjectManager.storeCommit(head);
        ObjectManager.setCurrentBranch(currBranch);
    }

    public void createNewBranch(String name) {
        Branch newB = new Branch(name, head.getId());
        ObjectManager.cacheBranch(newB);
    }

    public void switchToBranch(String branchName) {
        currBranch = branchName;
        head = ObjectManager.getHeadOfBranch(branchName);
    }

    public void pushCommit(String message) {
        head.setCommitDate(new Date());
        head.setMessage(message);
        for (GitletObject go : head.getStagedFiles()) {
            ObjectManager.commitObject(go);
            head.updateCommitDate(go);
            System.out.println("Commited " + go.getFileName());
        }
        ObjectManager.storeCommit(head);
        head = new Commit(head.getId());
        ObjectManager.cacheBranch(new Branch(currBranch, head.getId()));
    }

    public void stageFile(String fileName) {
        head.stageFile(fileName);
    }

    public String getCurrentBranch() {
        return currBranch;
    }

    public void markForRemoval(String fileName) {
        head.markForRemoval(fileName);
    }

    public void printHeadID() {
        System.out.println(head.getId());
    }

    public void printLog() {
        Commit parent = ObjectManager.loadCommit(head.getParentId());
        System.out.println("Parent loaded succesfully");
        while (!head.getMessage().equals("initial commit")) {
            System.out.print(parent.getLogInfo());
            parent = ObjectManager.loadCommit(parent.getParentId());
        }
    }

}
