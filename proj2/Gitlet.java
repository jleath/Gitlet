import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Collection;

/** The user interface for Gitlet.
 *
 *  @author Joshua Leath
 */
public class Gitlet {
    public static void main(String[] args) {
        // No commands will cause the help.txt file to be printed
        if (args.length == 0) {
            printHelpFile();
            System.exit(0);
        }

        String command = args[0];
        // The 'init' command initializes the repository
        if (command.equals("init")) {
            init();

        // The add command stages files
        } else if (command.equals("add")) {
            if (args.length != 2) {
                System.out.println("You must specify a file to stage.");
            } else {
                if (Files.exists(Paths.get(args[1]))) {
                    addFile(args[1]);
                } else {
                    System.out.println(args[1] + " does not exist in this directory.");
                }
            }

        // The commit command stores commits in the repo
        } else if (command.equals("commit")) {
            if (args.length != 2) {
                System.out.println("A commit must have a message");
            } else {
                Commit curr = CommitHandler.getCurrentCommit();
                curr.push(args[1]);
            }

        // The rm commands marks files to be removed from the repo
        } else if (command.equals("rm")) {
            if (args.length != 2) {
                System.out.println("Must specify a file.");
            } else {
                Commit curr = CommitHandler.getCurrentCommit();
                curr.markForRemoval(args[1]);
                CommitHandler.storeCommit(curr);
            }

        // The log command prints a branch log
        } else if (command.equals("log")) {
            printLog();

        // The global-log command prints the branch log for each branch
        } else if (command.equals("global-log")) {
            printGlobalLog(); 

        // The checkout command reverts files to their stored state
        // and switches between branches
        } else if (command.equals("checkout")) {
            if (args.length == 3) {
                int commitId = Integer.parseInt(args[1]);
                String fileName = args[2];
                checkoutByFileAndCommit(commitId, fileName);
            } else if (args.length == 2) {
                if (BranchHandler.branchExists(args[1])) {
                    String branchName = args[1];
                    checkoutByBranch(branchName);
                } else if (Files.exists(Paths.get(args[1]))) {
                    String fileName = args[1];
                    checkoutByFile(fileName);
                } else {
                    System.out.println("A file or branch with that name does not exist.");
                }
            } else {
                System.out.println("You must specify either a file name, a branch name"
                        + " or a commit id followed by a file name.");
            }

        // The branch command creates new branches
        } else if (command.equals("branch")) {
            if (args.length != 2) {
                System.out.println("You must specify a branch name.");
            } else if (BranchHandler.branchExists(args[1])) {
                System.out.println("A branch with that name already exists.");
            } else {
                int commitId = BranchHandler.getIdOfHeadCommit();
                Branch nb = new Branch(args[1], commitId);
                BranchHandler.cacheBranch(nb);
                BranchHandler.cacheSplitPoint(nb);
            }

        // The status command prints the branches, staged files,
        // and files that have been marked for removal
        } else if (command.equals("status")) {
            printStatus();

        // The rm-branch command removes branches from the repos,
        // but not their commits
        } else if (command.equals("rm-branch")) {
            if (!(BranchHandler.branchExists(args[1]))) {
                System.out.println("No branch with the name " + args[1] + " exists.");
            } else {
                BranchHandler.deleteBranch(args[1]);
            }
            
        // The reset command reverts the files in the working directory
        // to their state in the given commit
        } else if (command.equals("reset")) {
            if (args.length < 2) {
                System.out.println("You must specify a commit id.");
            } else {
                int commitId = Integer.parseInt(args[1]);
                warnUser();
                if (CommitHandler.commitExists(commitId)) {
                    Commit c = CommitHandler.loadCommit(commitId);
                    CommitHandler.revertToCommit(c);
                } else {
                    System.out.println("No commit with that idea exists.");
                }
            }

        // The merge command merges two branches
        } else if (command.equals("merge")) {
            if (args.length < 2) {
                System.out.println("You must specify a branch to merge.");
            } else {
                String givenName = args[1];
                if (!(BranchHandler.branchExists(givenName))) {
                    System.out.println("No branch with that name exists.");
                } else if (givenName.equals(BranchHandler.getCurrentBranch())) {
                    System.out.println("Cannot merge a branch into itself.");
                } else {
                    warnUser();
                    merge(givenName, BranchHandler.getCurrentBranch());
                }
            }
        }
    }
    
    /** Prints helpful information about commands to the user. */
    private static void printHelpFile() {
        try {
            File helpFile = new File("./help.txt");
            Scanner in = new Scanner(helpFile);
            while (in.hasNextLine()) {
                System.out.println(in.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading help.txt");
        }
    }

    /** Copy the version of FILENAME stored in the commit with id COMMITID to
     *  the working directory. */
    private static void checkoutByFileAndCommit(int commitId, String fileName) {
        warnUser();
        Commit c = CommitHandler.loadCommit(commitId);
        ObjectHandler.pullFile(c.getObject(fileName));
    }

    /** Switch to the branch BRANCHNAME, overwriting the necessary files to 
     *  make the files in the working directory match the state of files in the
     *  latest commit of BRANCHNAME. */
    private static void checkoutByBranch(String branchName) {
        warnUser();
        Commit current = CommitHandler.getCurrentCommit();
        if (current.getStagedFiles().size() > 0) {
            System.out.println("Warning: You have staged files that have not been"
                    + " pushed.  If you continue, these files will be unstaged and"
                    + " you may lose unsaved changes in your working directory."
                    + " Continue? (yes/no)");
            Scanner in = new Scanner(System.in);
            String response = in.next();
            if (!(response.equals("yes"))) {
                return;
            }
        }
        CommitHandler.revertToCommit(BranchHandler.getHeadOfBranch(branchName));
        BranchHandler.setCurrentBranch(branchName);
        Commit curr = CommitHandler.getCurrentCommit();
        curr.setParentId(BranchHandler.getIdOfHeadCommit());
        CommitHandler.storeCommit(curr);
    }

    /** Revert the file FILENAME in the working directory to the most recent
     *  commit in the current branch. */
    private static void checkoutByFile(String fileName) {
        warnUser();
        Commit head = CommitHandler.loadCommit(BranchHandler.getIdOfHeadCommit());
        ObjectHandler.pullFile(head.getObject(fileName));
    }

    /** Merges GIVENBRANCH into CURRENTBRANCH. */
    private static void merge(String givenBranch, String currentBranch) {
        Commit givenHead = BranchHandler.getHeadOfBranch(givenBranch);
        Commit currentHead = BranchHandler.getHeadOfBranch(currentBranch);
        Collection<String> givenFiles = BranchHandler.getModifiedFiles(givenBranch);
        Collection<String> currentFiles = BranchHandler.getModifiedFiles(currentBranch);
        for (String fileName : givenFiles) {
            if (!(currentFiles.contains(fileName))) {
                ObjectHandler.pullFile(givenHead.getObject(fileName));
            } else {
                System.out.println("Conflicting file during merge: " + fileName);
                GitletObject conflict = givenHead.getObject(fileName);
                ObjectHandler.cacheConflictedFile(conflict.getFileName(), conflict.getId());
            }
        }
    }

    /** Warns the user that the command they entered may alter files in the
     *  working directory, prompts the user to enter yes or no, if the yes
     *  is entered the program will proceed, otherwise it will exit. */
    private static void warnUser() {
        System.out.println("Warning, the command you entered may alter the files"
                + " in your working directory.  Uncommitted changes may be lost."
                + " Are you sure you want to continue? (yes/no)");
        Scanner in = new Scanner(System.in);
        String response = in.next();
        if (!(response.equals("yes"))) {
            System.exit(0); 
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

    /** Print a global log, a log of all commits that have been made. */
    private static void printGlobalLog() {
        int currCommitId = CommitHandler.getIdOfCurrentCommit();
        for (int id : CommitHandler.getCommitIds()) {
            if (id != currCommitId) {
                Commit curr = CommitHandler.loadCommit(id);
                System.out.println(curr.getLogInfo());
            }
        }
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
        if (Files.exists(Paths.get("./.gitlet/"))) {
            System.out.println("Gitlet repo already exists.");
            return;
        }
        createDirectories();
        Commit defaultCom = new Commit(0);
        Branch master = new Branch("master", defaultCom.getId());
        BranchHandler.cacheBranch(master);
        BranchHandler.setCurrentBranch("master");
        BranchHandler.cacheSplitPoint(master);
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
            Files.createDirectory(Paths.get("./.gitlet/splits"));
        } catch (IOException e) {
            System.out.println("Error creating gitlet repo directories.");
        }
    }
}
