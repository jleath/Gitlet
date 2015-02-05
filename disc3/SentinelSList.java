public class SentinelSList {
    private SNode front;
    private SNode back;
    public SentinelSList() {
        back = new SNode(0, null);
        front = new SNode(0, back);
    }

    public String toString() {
        String result = "[ ";
        SNode p = front.next;
        while (p != back) {
            result = result + Double.toString(p.val) + " ";
            p = p.next;
        }
        return result + "]";
    }

    public void insert(double val, int position) {
        SNode p = front;
        while (position > 0) {
            if (p.next == back) {
                p.next = new SNode(val, back);
                return;
            }
            p = p.next;
            position = position - 1;
        }
        p.next = new SNode(val, p.next);
    }
}
