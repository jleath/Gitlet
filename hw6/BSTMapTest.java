public class BSTMapTest {
    public static void main(String[] args) {
        BSTMap<Integer, Integer> test = new BSTMap<Integer, Integer>();
        test.put(1, 1);
        test.put(2, 2);
        test.put(3, 3);
        test.put(6, 6);
        test.put(12, 12);
        test.put(-2, -2);
        test.put(-5, -5);
        test.put(1000, 1000);
        test.printInOrder();
        System.out.println("Removing -2");
        test.remove(-2);
        test.printInOrder();
        test.remove(12);
        System.out.println("Removing 12");
        test.printInOrder();
        test.remove(3);
        System.out.println("Removing 3");
        test.printInOrder();
        test.put(55, 55);
        test.put(152, 152);
        test.put(32, 32);
        test.remove(32);
        System.out.println("Removing 32");
        test.printInOrder();
        for (int i : test.keySet()) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
