import java.util.Scanner;
import java.io.File;
import java.nio.file.Paths;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

public class RemoteTest {
    public static void main(String[] args) {
        String name = args[0];
        String userName = args[1];
        String server = args[2];
        String filePath = args[3];
        System.out.println("name = " + name);
        System.out.println("userName = " + userName);
        System.out.println("filePath = " + filePath);
        System.out.println("server = " + server);
        Remote r = new Remote(name, userName, server, filePath);
        // Test sending a file to the remote server
        RemoteHandler.pushFileToRemote(r, "test.txt", "/.gitlet/obj/test.txt");
        RemoteHandler.getFileFromRemote(r, "/.gitlet/obj/test.txt", "test.copy");
    }
}
