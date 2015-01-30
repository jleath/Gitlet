public class IntList {
    public int element;
    public IntList tail;

    public IntList(int i, IntList n) {
        element = i;
        tail = n;
    }

    public String toString() {
        String result = "";
        IntList p = this;
        while (p != null) {
            result = result + Integer.toString(p.element) + " ";
            p = p.tail;
        }
        return result;
    }
}

