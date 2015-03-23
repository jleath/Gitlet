import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Gitlet {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.exit(0);
        }
        if (args[0].equals("init")) {
            init();
        } else if (args[0].equals("add")) {
            if (args.length != 2) {
                System.out.println("You must specify a file to stage.");
            } else {
                if (Files.exists(Paths.get(args[1]))) {
                    addFile(args[1]);
                } else {
                    System.out.println(args[1] + " does not exist in this directory.");
                }
            }
        } else if (args[0].equals("commit")) {
            if (args.length != 2) {
                System.out.println("A commit must have a message");
            } else {
                Commit curr = CommitHandler.getCurrentCommit();
                curr.push(args[1]);
            }
        } else if (args[0].equals("rm")) {
            if (args.length != 2) {
                System.out.println("Must specify a file.");
            } else {
                Commit curr = CommitHandler.getCurrentCommit();
                curr.markForRemoval(args[1]);
                CommitHandler.storeCommit(curr);
            }
        } else if (args[0].equals("log")) {
            printLog();
        } else if (args[0].equals("checkout")) {
            if (args.length == 3) {
                // Deal with command checkout [commit id] [file name]
            } else {
                if (BranchHandler.branchExists(args[1])) {
                    CommitHandler.revertToCommit(BranchHandler.getHeadOfBranch(args[1]));
                    BranchHandler.setCurrentBranch(args[1]);
                    Commit curr = CommitHandler.getCurrentCommit();
                    curr.setParentId(BranchHandler.getIdOfHeadCommit());
                    CommitHandler.storeCommit(curr);
                } else if (Files.exists(Paths.get(args[1]))) {
                    Commit head = CommitHandler.loadCommit(BranchHandler.getIdOfHeadCommit());
                    ObjectHandler.pullFile(head.getObject(args[1]));
                } else {
                    System.out.println("A file or branch with that name does not exist.");
                }
            }
        } else if (args[0].equals("branch")) {
            if (args.length != 2) {
                System.out.println("You must specify a branch name.");
            } else if (BranchHandler.branchExists(args[1])) {
                System.out.println("A branch with that name already exists.");
            } else {
                int commitId = BranchHandler.getIdOfHeadCommit();
                BranchHandler.cacheBranch(new Branch(args[1], commitId));
            }
        } else if (args[0].equals("status")) {
            printStatus();
        }
    }

    /** Prints the status of the git repo. */
    private static void printStatus() {
        System.out.println("=== Branches ===");
        String currBranchName = BranchHandler.getCurrentBranch();
        for (String name : BranchHandler.getBranchNames()) {
            if (name.equals(currBranchName)) {
                System.out.println("*" + name);
            } else {
                System.out.println(name);
            }
        }
        System.out.println();
        System.out.println("=== Staged Files ===");
        Commit currCommit = CommitHandler.getCurrentCommit();
        for (GitletObject go : currCommit.getStagedFiles()) {
            System.out.println(go.getFileName());
        }
        System.out.println();
        System.out.println("=== Files Marked for Removal ===");
        for (GitletObject go : currCommit.getRemovedFiles()) {
            System.out.println(go.getFileName());
        }
        System.out.println();
    }

    /** Print a log of the current branch. */
    private static void printLog() {
        Commit head = BranchHandler.getHeadOfBranch(BranchHandler.getCurrentBranch());
        while (!(head.getMessage().equals("initial commit"))) {
            System.out.print(head.getLogInfo());
            head = CommitHandler.loadCommit(head.getParentId());
        }
        System.out.print(head.getLogInfo());
    }

    /** Stage a file named NAME to the current commit. */
    private static void addFile(String name) {
        Commit curr = CommitHandler.getCurrentCommit();
        curr.stageFile(name);
        CommitHandler.storeCommit(curr);
    }

    /** Initialize the gitlet repo. */
    private static void init() {
        createDirectories();
        Commit defaultCom = new Commit(0);
        Branch master = new Branch("master", defaultCom.getId());
        BranchHandler.cacheBranch(master);
        BranchHandler.setCurrentBranch("master");
        defaultCom.push("initial commit");
        System.out.println("Gitlet repo initialized!");
    }

    /** Create the gitlet repo directories. */
    private static void createDirectories() {
        try {
            Files.createDirectory(Paths.get("./.gitlet/"));
            Files.createDirectory(Paths.get("./.gitlet/obj"));
            Files.createDirectory(Paths.get("./.gitlet/branches"));
            Files.createDirectory(Paths.get("./.gitlet/commits"));
        } catch (IOException e) {
            System.out.println("Error creating gitlet repo directories.");
        }
    }
}
