import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Gitlet {
    public static void main(String[] args) {
        boolean repoExists = Files.exists(Paths.get("./.gitlet"));
        if (args[0].equals("init")) {
            if (repoExists) {
                System.out.println("A gitlet repo already exists in this directory.");
            } else {
                try {
                    Files.createDirectory(Paths.get("./.gitlet/"));
                    Files.createDirectory(Paths.get("./.gitlet/obj/"));
                    Files.createDirectory(Paths.get("./.gitlet/commits/"));
                    Files.createDirectory(Paths.get("./.gitlet/branches/"));

                    Commit start = new Commit(0);
                    ObjectManager.cacheCurrentCommit(start);
                    ObjectManager.storeCommit(start);
                    Branch newB = new Branch("Master", start.getId());
                    ObjectManager.cacheBranch(newB);
                    ObjectManager.setCurrentBranch("Master");
                    CommitTree ct = new CommitTree(start);
                    ObjectManager.setCurrentBranch(ct.getCurrentBranch());
                    ct.pushCommit(start, "initial commit");
                    Commit next = new Commit(0);
                    ObjectManager.cacheCurrentCommit(next);
                } catch (IOException e) {
                    System.out.println("Error creating new Gitlet repo.");
                }
            }
            System.exit(0);
        } else if (args[0].equals("add")) {
            if (!repoExists) {
                System.out.println("There is no gitlet repo in this directory.");
                System.out.println("Try 'java Gitlet init' to start a new repo.");
                System.exit(0);
            }
            Commit curr = ObjectManager.loadCurrentCommit();
            curr.stageFile(args[1]);
            ObjectManager.cacheCurrentCommit(curr);
            System.exit(0);
        } else if (args[0].equals("commit")) {
            CommitTree ct = new CommitTree(ObjectManager.getHeadOfBranch(
                        ObjectManager.getCurrentBranch()));
            Commit curr = ObjectManager.loadCurrentCommit();
            ct.pushCommit(curr, args[1]);
            ObjectManager.cacheCurrentCommit(new Commit(curr.getId()));
            ct.saveState();
        } else if (args[0].equals("rm")) {
            Commit curr = ObjectManager.loadCurrentCommit();
            curr.markForRemoval(args[1]);
            System.exit(0);
        } else if (args[0].equals("log")) {
            CommitTree ct = new CommitTree(ObjectManager.getHeadOfBranch(
                        ObjectManager.getCurrentBranch()));
            ct.printLog();
            System.exit(0);
        } else if (args[0].equals("checkout")) {
            if (args.length == 2) {
                if (ObjectManager.branchExists(args[1])) {
                    Commit curr = ObjectManager.getHeadOfBranch(args[1]);
                    CommitTree ct = new CommitTree(curr);
                    ct.switchToBranch(args[1]);
                    for (GitletObject go : curr.getStagedFiles()) {
                        ObjectManager.pullFile(go);
                    }
                    ct.saveState();
                    ObjectManager.cacheCurrentCommit(curr);
                }
            }
        } else if (args[0].equals("branch")) {
            CommitTree ct = new CommitTree(ObjectManager.getHeadOfBranch(
                        ObjectManager.getCurrentBranch()));
            ct.createNewBranch(args[1]);
        }
    }
}
