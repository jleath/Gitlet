public class BadIntStack {
    private SNode top;

    public boolean isEmpty() {
        return top == null;
    }

    public void push(Integer num) {
        top = new SNode(num, top);
    }

    public Integer pop() {
        if (isEmpty()) {
            throw new RuntimeException("Cannot call pop() on empty stack.");
        }
        Integer ans = top.val;
        top = top.prev;
        return ans;
    }

    public Integer peek() {
        if (isEmpty()) {
            throw new RuntimeException("Cannot call peek() on empty stack.")
        }
        return top.val;
    }
}
