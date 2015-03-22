import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

/** This is a utility class with static methods for working with
 *  files and GitletObjects.
 *
 *  This class is responsible for the actual mechanical work of
 *  caching and writing the files that GitletObjects point to.
 *  This class also has static methods for serializing and 
 *  deserializing objects.
 *
 *  Several of the methods in this class assume that the working directory
 *  contains a directory called .gitlet and that the .gitlet directory
 *  contains another directory called obj.  Therefore, none of this code
 *  should be used before a gitlet repository is initialized.  Failure to
 *  do this will result in IOExceptions.
 *
 *  @author Joshua Leath
 */
public final class ObjectManager {

    /** Returns the number of files in the given directory DIR. */
    public static int numFilesInDir(String dirPath) {
        return new File(dirPath).list().length;
    }

    /** Commit a GitletObject to the repo. */
    public static void commitObject(GitletObject obj) {
        ObjectManager.cacheNewFile(obj.getFileName(), obj.getId());
    }

    /** Overwrite the GitletObject OBJ's associated file with the content
     *  that the file had at the time OBJ was commited. */
    public static void pullFile(GitletObject obj) {
        ObjectManager.copyToWorkingDirectory(obj.getId(), obj.getFileName());
    }

    /** Stores a backup of the file with the name FILENAME,
     *  saves it in the 'objects' directory of the gitlet repo
     *  with ID as the filename. */
    public static void cacheNewFile(String fileName, int id) {
        File toCache = new File(fileName);
        File dest = new File("./.gitlet/obj/" + id + ".go");
        if (!toCache.exists()) {
            System.out.println("File " + fileName + " does not exist.");
        } else if (dest.exists()) {
            System.out.println("Bad object filename " + id);
        } else {
            try {
                Files.copy(Paths.get(fileName), 
                        Paths.get("./.gitlet/obj/" + id + ".go"));
            } catch (IOException e) {
                System.out.println("Error making backup of " + fileName);
            }
        }
    }

    /** Copies the file with the given ID in the 'objects'
     *  directory to the path given by FILENAME. */
    public static void copyToWorkingDirectory(int id, String fileName) {
        File toCopy = new File("./.gitlet/obj/" + id + ".go");
        File dest = new File(fileName);
        if (!toCopy.exists()) {
            System.out.println("File " + fileName + " does not exist.");
        } else {
            try {
                Files.copy(Paths.get("./.gitlet/obj/" + id + ".go"), 
                        Paths.get(fileName), REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Error pulling object " + id
                        + " from repo.");
            }
        }
    }

    /** Returns a Date object representing the date that a file
     *  was last modified. */
    public static Date getLastModifiedDate(String fileName) {
        File src = new File(fileName);
        return new Date(src.lastModified());
    }

    /** A utility method for Serializing a Commit OBJ and saving
     *  it in a file named DESTNAME. */
    public static void storeCommit(Commit obj) {
        if (obj == null) {
            return;
        }
        try {
            String destName = ".gitlet/commits/" + obj.getId();
            File destFile = new File(destName);
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
        String fileName = ".gitlet/commits/" + id;
        File src = new File(fileName);
        if (src.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(src);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                result = (Commit) objectIn.readObject();
            } catch (IOException e) {
                System.out.println("Error reading " + fileName);
            } catch (ClassNotFoundException e) {
                System.out.println("Error loading Commit.");
            }
        }
        return result;
    }

    /** Gets the current branch. */
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

    /** Sets the name of the current branch.  This name is located in the file
     *  found at './gitlet/HEAD'. */
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
    }

    public static void cacheCurrentCommit(int id) {
        try {
            File file = new File("./.gitlet/CURR");
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.print(id);
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing current commit");
        }
    }

    public static int getIdOfCurrentCommit() {
        int commitId = -1;
        try {
            File file = new File("./.gitlet/CURR");
            Scanner in = new Scanner(file);
            commitId = in.nextInt();
        } catch (IOException e) {
            System.out.println("Error reading CURR file.");
        }
        return commitId;
    }

    public static Commit getHeadOfBranch(String name) {
        Commit result = null;
        File file = new File("./.gitlet/branches/" + name); 
        try {
            Scanner scanner = new Scanner(file);
            int commitId = scanner.nextInt();
            result =  ObjectManager.loadCommit(commitId);
        } catch (FileNotFoundException e) {
            System.out.println("Branch " + name + " does not exist");
        }
        return result;
    }
}
