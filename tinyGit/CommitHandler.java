import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

/** A class of static utility methods for dealing with commits in a 
 *  tinyGit repo.
 *
 *  @author Joshua Leath
 */
public final class CommitHandler {
    
    /** Commit a TinyGitObject to the repo. */
    public static void commitObject(TinyGitObject obj) {
        ObjectHandler.cacheNewFile(obj.getFileName(), obj.getId());
    }

    /** Restores all the files in the given commit C to the working
     *  directory. */
    public static void revertToCommit(Commit c) {
        for (TinyGitObject go : c.getAllButRemoved()) {
            ObjectHandler.pullFile(go);
        }
    }

    /** Return a collection of the ids of all commits that have been made
     *  on all branches. */
    public static Collection<Integer> getCommitIds() {
        HashSet<Integer> result = new HashSet<Integer>();
        File file = new File("./.tinyGit/commits/");
        File[] files = file.listFiles();
        for (File f : files) {
            result.add(Integer.parseInt(f.getName()));
        }
        return result;
    }

    /** Returns true if there is a commit with the given ID in the 
     *  repo's commits directory, else false. */
    public static boolean commitExists(int commitId) {
        return Files.exists(Paths.get("./.tinyGit/commits/" + commitId));
    }

    /** Returns the current commit. */
    public static Commit getCurrentCommit() {
        return loadCommit(getIdOfCurrentCommit());
    }

    /** A utility method for Serializing a Commit OBJ. */
    public static void storeCommit(Commit obj) {
        if (obj == null) {
            return;
        }
        try {
            File destFile = new File(".tinyGit/commits/" + obj.getId());
            FileOutputStream fileOut = new FileOutputStream(destFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Error serializing object.");
        }
    }

    /** A utility method for reading in a serialized object,
     *  returns null if there is no file with the name FILENAME. */
    public static Commit loadCommit(int id) {
        Commit result = null;
        String fileName = ".tinyGit/commits/" + id;
        File src = new File(fileName);
        if (src.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(src);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                result = (Commit) objectIn.readObject();
            } catch (IOException e) {
                System.out.println("Error reading " + fileName);
            } catch (ClassNotFoundException e) {
                System.out.println("Error loading commit.");
            }
        }
        return result;
    }

    /** Store the current commit (the commit that files are currently
     *  being added to and removed from but has not yet been pushed, this
     *  is saved in ".tinyGit/CURR". */
    public static void cacheCurrentCommit(int id) {
        try {
            File file = new File("./.tinyGit/CURR");
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.print(id);
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing current commit");
        }
    }

    /** Store the current commit (the commit that files are currently being
     *  added to and removed from but has not yet been pushed, this is saved
     *  in ".tinyGit/CURR". */
    public static void cacheCurrentCommit() {
        cacheCurrentCommit(getIdOfCurrentCommit());
    }

    /** Returns the integer id of the current commit (the commit that files are
     *  currently being added to and removed from but has not yet been pushed,
     *  this is saved in ".tinyGit/CURR".
     *  Returns -1 if there is no file at this location. */
    public static int getIdOfCurrentCommit() {
        int commitId = -1;
        try {
            File file = new File("./.tinyGit/CURR");
            Scanner in = new Scanner(file);
            commitId = in.nextInt();
        } catch (IOException e) {
            System.out.println("Error reading CURR file.");
        }
        return commitId;
    }
}
