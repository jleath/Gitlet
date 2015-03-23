import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Collection;
import java.util.HashSet;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;

/** A class of static utility methods for dealing with branches in a gitlet repo.
 *
 *  @author Joshua Leath
 */
public final class BranchHandler {
    
    /** Sets the name of the current branch. This name is located in the file
     *  found at '.gitlet/HEAD'. */
    public static void setCurrentBranch(String b) {
        try {
            File file = new File("./.gitlet/HEAD");
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.print(b);
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing to HEAD file.");
        }
    }

    /** Removes the branch with the given name from the repo. */
    public static void deleteBranch(String b) {
        if (b.equals(getCurrentBranch())) {
            System.out.println("Cannot delete the current branch.");
            return;
        }
        try {
            Files.delete(Paths.get("./.gitlet/branches/" + b));
        } catch (NoSuchFileException e) {
            System.out.println("The branch " + b + " does not exist.");
        } catch (IOException e) {
            System.out.println("Error deleting branch " + b);
        }
    }
    
    /** Returns a collection of the names of all existing branches. */
    public static Collection<String> getBranchNames() {
        HashSet<String> results = new HashSet<String>();
        File file = new File("./.gitlet/branches/");
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                results.add(f.getName());
            }	
        }
        return results;
    }

    /** Returns the id of the current branches head commit. */
    public static int getIdOfHeadCommit() {
        String currBranch = getCurrentBranch();
        Commit head = getHeadOfBranch(currBranch);
        return head.getId();
    }

    /** Returns the name of the current branch. */
    public static String getCurrentBranch() {
        String name = null;
        try {
            File file = new File("./.gitlet/HEAD");
            Scanner in = new Scanner(file);
            name = in.next();
            in.close();
        } catch (IllegalStateException e) {
            System.out.println("Error reading HEAD file.");
        } catch (FileNotFoundException e) {
            System.out.println("HEAD file does not exist.");
        }
        return name;
    }

    /** Returns true a branch named NAME exists, else false. */
    public static boolean branchExists(String name) {
        return Files.exists(Paths.get("./.gitlet/branches/" + name)); 
    }

    /** Caches a branch, saving it as a file in the 'branches' directory
     *  named after the branch containing only the id of the branches head
     *  commit. */
    public static void cacheBranch(Branch b) {
        try {
            File file = new File("./.gitlet/branches/" + b.getName());
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.print(b.getCommitId());
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing branch file for " + b.getName());
        }
        cacheSplitPoint(b);
    }

    /** Caches the splitPoint of the given branch in a file named
     *  './.gitlet/splits/[branchName]'. */
    public static void cacheSplitPoint(Branch b) {
        try {
            File file = new File("./.gitlet/splits/" + b.getName());
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.print(b.getSplitPointId());
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing split file for " + b.getName());
        }
    }

    /** Returns the id of the splitPoint commit for the given branchName. */
    public static int getSplitPointId(String branchName) {
        int result = -1;
        File file = new File("./.gitlet/splits/" + branchName);
        try {
            Scanner scanner = new Scanner(file);
            result = scanner.nextInt();
        } catch (FileNotFoundException e) {
            System.out.println("Split point for branch " + branchName + " does not exist.");
        }
        return result;
    }

    /** Returns the commit at the head of the branch named NAME,
     *  returns null if the branch does not exist. */
    public static Commit getHeadOfBranch(String name) {
        Commit result = null;
        File file = new File("./.gitlet/branches/" + name);
        try {
            Scanner scanner = new Scanner(file);
            int commitId = scanner.nextInt();
            result = CommitHandler.loadCommit(commitId);
        } catch (FileNotFoundException e) {
            System.out.println("Branch " + name + " does not exist");
        }
        return result;
    }	
}
