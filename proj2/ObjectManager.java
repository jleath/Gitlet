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
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

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
    private static final String sep = File.pathSeparator;
    private static final String repoRoot = "./.gitlet/";
    private static final String objDir = repoRoot + "obj/";
    private static final String objectSuffix = ".go";
    private static final String commitDir = repoRoot + "commits/";
    private static final String commitSuffix = ".co";
    private static final String branchDir = repoRoot + "branches/";
    private static final String branchSuffix = ".br";
    private static final String headFile = repoRoot + "HEAD";
    

    /** Returns true if the branch named BRANCHNAME exists. */
    public static boolean branchExists(String branchName) {
        File branchFile = new File(branchDir + branchName + branchSuffix);
        return branchFile.exists();
    }
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
        File dest = new File(objDir + id + objectSuffix);
        if (!toCache.exists()) {
            System.out.println("File " + fileName + " does not exist.");
        } else if (dest.exists()) {
            System.out.println("Bad object filename " + id);
        } else {
            try {
                Files.copy(Paths.get(fileName), 
                        Paths.get(objDir + id + objectSuffix));
            } catch (IOException e) {
                System.out.println("Error making backup of " + fileName);
            }
        }
    }

    /** Copies the file with the given ID in the 'objects'
     *  directory to the path given by FILENAME. */
    public static void copyToWorkingDirectory(int id, String fileName) {
        File toCopy = new File(objDir + id + objectSuffix);
        File dest = new File(fileName);
        if (!toCopy.exists()) {
            System.out.println("File " + fileName + " does not exist.");
        } else {
            try {
                Files.copy(Paths.get(objDir+ id + objectSuffix), 
                        Paths.get(fileName), REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Error pulling object " + id
                        + " from repo.");
            }
        }
    }

    /** Returns true if there is a commit with the given ID in the
     *  commits directory, else false. */
    public static boolean commitExists(int id) {
        File commit = new File(objDir + id + objectSuffix);
        return commit.exists();
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
            String destName = commitDir + obj.getId() + commitSuffix;
            File destFile = new File(destName);
            FileOutputStream fileOut = new FileOutputStream(destFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Error serializing object.");
        }
    }

    /** Caches a special commit that is not yet a part of the commit tree.
     *  It is the commit that the user is currently working with to stage
     *  files and/or mark them for removal. */
    public static void cacheCurrentCommit(Commit curr) {
        if (curr == null) {
            System.out.println("There is no current commit.");
            return;
        }
        try {
            String destName = repoRoot + "CURR";
            File destFile = new File(destName);
            FileOutputStream fileOut = new FileOutputStream(destFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(curr);
        } catch (IOException e) {
            System.out.println("Error serializing object.");
        }
    }

    /** Loads the current commit. */
    public static Commit loadCurrentCommit() {
        Commit result = null;
        String fileName = repoRoot + "CURR";
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
    
    /** A utility method for reading in a serialized object,
     *  returns null if there is no file with the name FILENAME. */
    public static Commit loadCommit(int id) {
        Commit result = null;
        String fileName = commitDir + id + commitSuffix;
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
    
    /** Caches a branch, saving it as a file in the 'branches' directory
     *  named after the branch containing only the id of the branches head
     *  commit. */
    public static void cacheBranch(Branch b) {
        try {
            File file = new File(branchDir + b.getName() + branchSuffix);
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.print(b.getCommitId());
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing branch file for " + b.getName());
        }
    }

    /** Creates a file that stores the repo's current branch.  This file will
    *  always be named 'HEAD' and will contain the name of the current branch. */
    public static void setCurrentBranch(String b) {
        if (!Files.exists(Paths.get(headFile))) {
            try {
                Files.createFile(Paths.get(headFile));
            } catch (IOException e) {
                System.out.println("Error creating new HEAD file.");
            }
        }
        try {
            File file = new File(headFile);
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(b);
            out.close();
        } catch (IOException e) {
            System.out.println("Error setting the current branch to " + b);
        }
    }

    /** Returns the name of the current branch. */
    public static String getCurrentBranch() {
        File file = new File(headFile);
        String branchName = null;
        try {
            Scanner scanner = new Scanner(file);
            branchName = scanner.next();
        } catch (FileNotFoundException e) {
            System.out.println("Error getting the name of the current branch.");
        }
        return branchName;
    }

    public static Commit getHeadOfBranch(String name) {
        Commit result = null;
        File file = new File(branchDir + name + branchSuffix); 
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
