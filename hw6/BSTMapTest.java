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
    }
}
