public class SNode {
    public SNode next;
    public double val;
    public SNode(double v, SNode n) {
        next = n;
        val = v;
    }
}
