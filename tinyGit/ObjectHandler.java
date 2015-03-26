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
 *  files and TinyGitObjects.
 *
 *  This class is responsible for the actual mechanical work of
 *  caching and writing the files that TinyGitObjects point to.
 *  This class also has static methods for serializing and 
 *  deserializing objects.
 *
 *  Several of the methods in this class assume that the working directory
 *  contains a directory called .tinyGit and that the .tinyGit directory
 *  contains another directory called obj.  Therefore, none of this code
 *  should be used before a tinyGit repository is initialized.  Failure to
 *  do this will result in IOExceptions.
 *
 *  @author Joshua Leath
 */
public final class ObjectHandler {

    /** Returns the number of files in the given directory DIR. */
    public static int numFilesInDir(String dirPath) {
        return new File(dirPath).list().length;
    }

    /** Overwrite the TinyGitObject OBJ's associated file with the content
     *  that the file had at the time OBJ was commited. */
    public static void pullFile(TinyGitObject obj) {
        ObjectHandler.copyToWorkingDirectory(obj.getId(), obj.getFileName());
    }

    /** Stores a backup of the file with the name FILENAME,
     *  saves it in the 'objects' directory of the tinyGit repo
     *  with ID as the filename. */
    public static void cacheNewFile(String fileName, int id) {
        File toCache = new File(fileName);
        File dest = new File("./.tinyGit/obj/" + id + ".go");
        if (!toCache.exists()) {
            System.out.println("File " + fileName + " does not exist.");
        } else if (dest.exists()) {
            System.out.println("Bad object filename " + id);
        } else {
            try {
                Files.copy(Paths.get(fileName), 
                        Paths.get("./.tinyGit/obj/" + id + ".go"));
            } catch (IOException e) {
                System.out.println("Error making backup of " + fileName);
            }
        }
    }

    /** Stores a backup of a conflicted file during a branch merge in the
     *  '.tinyGit/obj/' directory as [fileName].conflicted. */
    public static void cacheConflictedFile(String fileName, int id) {
        File toCache = new File(fileName);
        File dest = new File("./.tinyGit/obj/" + fileName + ".conflicted");
        if (!toCache.exists()) {
            System.out.println("File " + fileName + " does not exist.");
        } else {
            try {
                Files.copy(Paths.get("./.tinyGit/obj/" + id + ".go"),
                        Paths.get("./.tinyGit/obj/" + fileName + ".conflicted"));
            } catch (IOException e) {
                System.out.println("Error making backup of " + fileName);
            }
        }
    }

    /** Copies the file with the given ID in the 'objects'
     *  directory to the path given by FILENAME. */
    public static void copyToWorkingDirectory(int id, String fileName) {
        File toCopy = new File("./.tinyGit/obj/" + id + ".go");
        File dest = new File(fileName);
        if (!toCopy.exists()) {
            System.out.println("File " + fileName + " does not exist.");
        } else {
            try {
                Files.copy(Paths.get("./.tinyGit/obj/" + id + ".go"), 
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
}
