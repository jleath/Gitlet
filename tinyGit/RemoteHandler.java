import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

/** This class is an experiment and is not yet implement in tinyGit.
 *  Prototyping ideas for sharing tinyGit repos across machines. */

public class RemoteHandler {

    /** Pulls a file from the remote R located at FILEPATH and copies it
     *  to DESTPATH on the local machine. */
    public static void getFileFromRemote(Remote r, String filePath, String destPath) {
        String cmd = "scp " + r.getUserName() + "@" + r.getServer() + ":" + r.getPath() 
            + filePath + " " + destPath;
        System.out.println(cmd);
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            System.out.println("Error retrieving " + filePath + " from remote " 
                    + r.getName());
        }
    }

    /** Sends the file located at PATH to DESTPATH on remote machine R. */
    public static void pushFileToRemote(Remote r, String path, String destPath) {
        String cmd = "scp " + path + " " + r.getUserName() + "@" + r.getServer()
            + ":" + r.getPath() + destPath;
        System.out.println(cmd);
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("File " + path + " does not exist on this machine.");
        } else {
            try {
                Runtime.getRuntime().exec(cmd);
            } catch (IOException e) {
                System.out.println("Error pushing " + path + " to remote "
                        + r.getName());
            }
        }
    }

    /** Pull a deserialized commit file from the remote R with the commitId ID. */
    public static Commit getCommitFromRemote(Remote r, int id) {
        try {
            Files.createDirectory(Paths.get("./.gitlet/remotes/TEMP"));
        } catch (IOException e) {
            System.out.println("Error creating TEMP directory for remote files.");
        }
        String destPath = "./.gitlet/remotes/TEMP/" + id + ".tmp";
        String filePath = r.getPath() + "/.gitlet/commits/" + id;
        getFileFromRemote(r, filePath, destPath);
        Commit result = null;
        String fileName = ".gitlet/remotes/TEMP/" + id + ".tmp";
        File src = new File(fileName);
        if (src.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(src);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                result = (Commit) objectIn.readObject();
            } catch (IOException e) {
                System.out.println("Error  reading " + fileName);
            } catch (ClassNotFoundException e) {
                System.out.println("Error loading commit.");
            }
        }
        return result;
    }

    /** Pull a GitletObject from the remote R with the name FILENAME to the
     *  working directory.. */
    public static void pullFileFromRemote(Remote r, int id, String fileName) {
        String destPath = fileName;
        String filePath = r.getPath() + "/.gitlet/obj/" + id + ".go";
        getFileFromRemote(r, filePath, destPath);
    }
}
