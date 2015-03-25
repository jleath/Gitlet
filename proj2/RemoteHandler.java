import java.io.File;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class RemoteHandler {

    /** Pulls a file from the remote R located at 'r.getPath()/filePath'
     *  and copies it to '.gitlet/destPath' on the local machine. */
    private static void getFileFromRemote(Remote r, String filePath, String destPath) {
        try {
            Runtime.getRuntime().exec("scp " + r.getUserName() + "@"
                    + r.getServer() + ":" + filePath + " " + destPath);
        } catch (IOException e) {
            System.out.println("Error retrieving " + filePath + " from remote" 
                    + r.getName());
        }
        // Check to make sure the file was retrieved.
        File file = new File("./.gitlet/" + filePath);
        if (!file.exists()) {
            System.out.println("Error retrieving " + filePath + " from remote "
                    + r.getName());
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
