public class Reverse {
    public static IntList reverseNonDestructive(IntList lst) {
        IntList result = new IntList(lst.element, null);
        IntList p = lst.tail;
        while (p != null) {
            result = new IntList(p.element, result);
            p = p.tail;
        }
        return result;
    }

    public static void reverseDestructive(IntList L) {
        IntList prev = null;
        IntList current = L;
        IntList next;
        while (current != null) {
            next = current.tail;
            current.tail = prev;
            current = next;
        }
        L = prev;
        
    }

    public static void main(String[] args) {
        IntList test = new IntList(1, new IntList(2, new IntList(3, null)));
        IntList result = reverseNonDestructive(test);
        System.out.println(test);
        System.out.println(result);
        reverseDestructive(test);
        System.out.println(test);
    }
}
