public class ObjectManagerTest {
    public static void main(String[] args) {
        if (args[0].equals("commitobj")) {
            // Test commitObject method
        } else if (args[0].equals("pullfile")) {
            // Test pullfile method
        } else if (args[0].equals("cachenew")) {
            // Test cacheNewFile method
            String path = args[1];
            int id = Integer.parseInt(args[2]);
            ObjectManager.cacheNewFile(path, id);
        } else if (args[0].equals("copy")) {
            // test copyToWorkingDirectory method
            int id = Integer.parseInt(args[1]);
            String path = args[2];
            ObjectManager.copyToWorkingDirectory(id, path);
        } else if (args[0].equals("lastmod")) {
            // test getLastModifiedDate method
            String path = args[1];
            System.out.println(ObjectManager.getLastModifiedDate(path));
        } else if (args[0].equals("numFiles")) {
            // test numFilesInDirPath method
            System.out.println(ObjectManager.numFilesInDir(args[1]));
        }
    }
}
