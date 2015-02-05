public class Disc3Test {
    public static void printArray(int[] a) {
        for (int i : a) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void testXify() {
        int[] x = {3, 2, 1};
        printArray(x);
        int[] result = Disc3.xify(x);
        printArray(result);
        int[] y = {1, 2, 3, 4};
        printArray(y);
        result = Disc3.xify(y);
        printArray(result);
    }

    public static void testSentinel() {
        SentinelSList test = new SentinelSList();
        System.out.println(test);
        test.insert(1.0, 5);
        System.out.println(test);
        test.insert(2.0, 8);
        System.out.println(test);
        test.insert(3.0, 2);
        System.out.println(test);
        test.insert(5.0, 3);
        System.out.println(test);
        test.insert(4.0, 3);
        System.out.println(test);
    }

    public static void testInsert() {
        int[] test = {1, 2, 3, 5, 6};
        int val = 4;
        int pos = 3;
        int[] result = Disc3.insert(test, val, pos);
        printArray(test);
        printArray(result);
    }

    public static void testListInsert() {
        SList test = new SList();
        test.head = new SNode(1, new SNode(2, new SNode(4, null)));
        System.out.println(test);
        test.insert(3.0, 2);
        System.out.println(test);
        test.insert(5.0, 10);
        System.out.println(test);
        SList test2 = new SList();
        test2.insert(3.14159, 100);
        System.out.println(test2);
    }

    public static void main(String[] args) {
        testXify();
    }
}
