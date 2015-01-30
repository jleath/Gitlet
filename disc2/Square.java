public class Square {
    public static IntList squareDestructive(IntList L) {
        IntList p = L;
        while (p != null) {
            p.element = p.element * p.element;
            p = p.tail;
        }
        return L;
    }

    public static IntList squareNonDestructive(IntList L) {
        if (L == null)
            return L;
        else {
            return new IntList(L.element * L.element, squareNonDestructive(L.tail));
        }
    }

    public static void main(String[] args) {
        IntList test = new IntList(1, new IntList(2, new IntList(3, null)));
        IntList result = squareNonDestructive(test);
        System.out.println(result);
        squareDestructive(test);
        System.out.println(test);
    }
}
