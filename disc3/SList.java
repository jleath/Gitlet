
public class SList {
    public SNode head;

    public SList() {
        head = null;
    }

    public void insert(double val, int position) {
        if (head == null) {
            head = new SNode(val, null);
            return;
        }
        SNode p = head;
        while (position > 1) {
            if (p.next == null) {
                p.next = new SNode(val, null);
                return;
            }
            p = p.next;
            position = position - 1;
        }
        p.next = new SNode(val, p.next);
    }

    public String toString() {
        String result = "[ ";
        SNode p = head;
        while (p != null) {
            result = result + Double.toString(p.val) + " ";
            p = p.next;
        }
        return result + "]";
    }
}
