package gitlet;
import java.nio.file.Files;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.File;

/** Gitlet is a version control system similar to git, but with less
 *  functionality.  
 *
 *  Gitlet works by saving a CommitTree object named HEAD along with File
 *  objects and their respective file backups into a .gitlet directory.  
 *  Files are never deleted, only overwritten with newer versions.  The
 *  .gitlet directory will contain copies of each version of each file.
 *  This is not very memory-efficient, and at some point I would like to
 *  find a way to avoid the memory cost.
 *
 *  The existing commands for Gitlet are as follows:
 *
 *  init - Initializes a new gitlet repository, this will fail if a repo
 *         already exists in the current directory.
 *  add [file-name] - Stages a file for the next commit, this will fail if
 *                    there is no file named [file-name].  If the given file
 *                    has not been modified since the last commit, nothing will
 *                    change.  If the file has previously been marked for removal,
 *                    it will be unmarked.
 *  commit [message] - Adds a new CommitNode to HEAD, this will fail if there
 *                     is no message given with the command or if there are
 *                     no staged files.
 *  @author Joshua Leath
 */
public class Gitlet {
    /** Entry point for gitlet, gets the command entered by the user and
     *  performs the requested operation. */
    public static void main(String[] args) {
        // check that user entered command line flags 
        if (args.length == 0) {
            System.exit(0);
        }
        String command = args[0];
        if (command.equals("init")) {
            try {
                Files.createDirectory(Paths.get(".gitlet"));
            } catch (FileAlreadyExistsException e) {
                System.out.println("A gitlet version control system already " +
                        "exists in the current directory.");
                System.exit(0);
            } catch (IOException e) {
                System.exit(0);
            }
            // Create new CommitTree with empty head
            // TODO
            // Serialize and save new CommitTree
            // TODO
        } else if (command.equals("add")) {
            // check that a file name was passed in
            if (args.length != 2) {
                System.exit(0);
            }
            // check that the file exists
            File toAdd = new File(args[1]);
            if (!toAdd.exists()) {
                System.out.println("File does not exist.");
                System.exit(0);
            }

            // check that the file has been modified since the last commit
            // TODO
            // Get the current head
            // TODO
            // stage the file
            // TODO
            // serialize and save head
        } else if (command.equals("commit")) {
            // check that a message was passed in
            if (args.length != 2) {
                System.out.println("Please enter a commit message.");
                System.exit(0);
            }
            // Get the current head
            // TODO
            // check that files have been staged
            // TODO
            // add commit to commit tree
            // TODO
        }
    }
}
