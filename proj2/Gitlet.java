import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Gitlet {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.exit(0);
        }
        if (args[0].equals("init")) {
            init();
        } else if (args[0].equals("add")) {
            if (args.length != 2) {
                System.out.println("You must specify a file to stage.");
            } else {
                addFile(args[1]);
            }
        } else if (args[0].equals("commit")) {
            if (args.length != 2) {
                System.out.println("A commit must have a message");
            } else {
                Commit curr = ObjectManager.loadCommit(ObjectManager.getIdOfCurrentCommit());
                System.out.println("Loaded commit with id:" + curr.getId());
                curr.push(args[1]);
            }
        } else if (args[0].equals("rm")) {
            if (args.length != 2) {
                System.out.println("Must specify a file.");
            } else {
                Commit curr = ObjectManager.loadCommit(ObjectManager.getIdOfCurrentCommit());
                curr.markForRemoval(args[1]);
            }
        } else if (args[0].equals("log")) {
            printLog();
        }
    }

    private static void printLog() {
        Commit head = ObjectManager.getHeadOfBranch(ObjectManager.getCurrentBranch());
        while (!(head.getMessage().equals("initial commit"))) {
            System.out.print(head.getLogInfo());
            head = ObjectManager.loadCommit(head.getParentId());
        }
        System.out.print(head.getLogInfo());
    }

    private static void addFile(String name) {
        Commit curr = ObjectManager.loadCommit(ObjectManager.getIdOfCurrentCommit());
        curr.stageFile(name);
        ObjectManager.storeCommit(curr);
    }

    private static void init() {
        createDirectories();
        Commit defaultCom = new Commit(0);
        Branch master = new Branch("master", defaultCom.getId());
        ObjectManager.cacheBranch(master);
        ObjectManager.setCurrentBranch("master");
        defaultCom.push("initial commit");
    }

    private static void createDirectories() {
        try {
            Files.createDirectory(Paths.get("./.gitlet/"));
            Files.createDirectory(Paths.get("./.gitlet/obj"));
            Files.createDirectory(Paths.get("./.gitlet/branches"));
            Files.createDirectory(Paths.get("./.gitlet/commits"));
        } catch (IOException e) {
            System.out.println("Error creating gitlet repo directories.");
        }
    }
}
