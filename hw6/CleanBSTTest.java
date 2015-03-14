public class CleanBSTTest {
    public static void main(String[] args) {
        CleanBST<Integer, Integer> test = new CleanBST<Integer, Integer>();
        test.put(1, 1);
        test.put(50, 50);
        test.put(25, 25);
        test.put(20, 20);
        test.put(150, 150);
        test.put(-5, -5);
        test.put(75, 75);
        System.out.println("This should be 7: " + test.size());
        System.out.println("This should be 50: " + test.get(50));
        System.out.println("This should be -5: " + test.get(-5));
        System.out.println("This should be 75: " + test.get(75));
        test.printInOrder();
        CleanBST<String, Integer> other = new CleanBST<String, Integer>();
        other.put("Josh", 25);
        other.put("Andrew", 25);
        other.put("Sarah", 18);
        other.put("Wendy", 45);
        other.put("Robert", 55);
        other.put("Anthony", 23);
        other.printInOrder();
    }
}
