class SNode {
    Integer val;
    SNode prev;

    public SNode(Integer v, SNode p) {
        val = v;
        prev = p;
    }
    public SNode(Integer v) {
        this(v, null);
    }
}
