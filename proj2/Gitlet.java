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
                    ObjectManager.storeCommit(start);
                    Branch newB = new Branch("Master", start.getId());
                    ObjectManager.cacheBranch(newB);
                    ObjectManager.setCurrentBranch("Master");
                    CommitTree ct = new CommitTree(start);
                    ObjectManager.setCurrentBranch(ct.getCurrentBranch());
                    ct.pushCommit("initial commit");
                } catch (IOException e) {
                    System.out.println("Error creating new Gitlet repo.");
                }
            }
            System.exit(0);
        } else if (args[0].equals("commitId")) {
            CommitTree ct = new CommitTree(ObjectManager.getHeadOfBranch(
                        ObjectManager.getCurrentBranch()));
            ct.printHeadID();
        } else if (args[0].equals("add")) {
            if (!repoExists) {
                System.out.println("There is no gitlet repo in this directory.");
                System.out.println("Try 'java Gitlet init' to start a new repo.");
                System.exit(0);
            }
            CommitTree ct = new CommitTree(ObjectManager.getHeadOfBranch(
                        ObjectManager.getCurrentBranch()));
            ct.stageFile(args[1]);
            ct.saveState();
            System.exit(0);
        } else if (args[0].equals("commit")) {
            CommitTree ct = new CommitTree(ObjectManager.getHeadOfBranch(
                        ObjectManager.getCurrentBranch()));
            ct.pushCommit(args[1]);
            ct.saveState();
        } else if (args[0].equals("rm")) {
            CommitTree ct = new CommitTree(ObjectManager.getHeadOfBranch(
                        ObjectManager.getCurrentBranch()));
            ct.markForRemoval(args[1]);
            ct.saveState();
            System.exit(0);
        } else if (args[0].equals("log")) {
            CommitTree ct = new CommitTree(ObjectManager.getHeadOfBranch(
                        ObjectManager.getCurrentBranch()));
            ct.printLog();
            System.exit(0);
        }
    }
}
