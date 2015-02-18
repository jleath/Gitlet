public class CNode {
    char head;
    CNode next;
    
    public CNode(char head, CNode next) {
        this.head = head;
        this.next = next;
    }

    public void swapSpace() {
        CNode ptr = this;
        while (ptr != null) {
            if (ptr.head == ' ') {
                ptr.head = '6';
                CNode after = ptr.next;
                ptr.next = new CNode('1', new CNode('B', ptr.next));
                ptr = after;
            } else {
                ptr = ptr.next;
            }
        }
    }
    
    public void insertEnd(char c) {
        CNode ptr = this;
        while (ptr.next != null) {
            ptr = ptr.next;
        }   
        ptr.next = new CNode(c, null);
    }

    public void print() {
        String result = "";
        CNode ptr = this;
        while (ptr != null) {
            result += ptr.head + " ";
            ptr = ptr.next;
        }
        System.out.println(result);
    }

    public static CNode makeHugString(String s) {
        if (s.length() == 0) {
            return new CNode(' ', null);
        }
        CNode result = new CNode(s.charAt(0), null);
        for (int i = 1; i < s.length(); i++) {
            result.insertEnd(s.charAt(i)); 
        }
        return result;
    }

    public static void testMakeHugString() {
        makeHugString("booger").print();
        makeHugString("").print();
        makeHugString("1234").print();
        CNode test1 = makeHugString("booo ger");
        CNode test2 = makeHugString("");
        CNode test3 = makeHugString("12 3 4");
        test1.swapSpace();
        test2.swapSpace();
        test3.swapSpace();
        test1.print();
        test2.print();
        test3.print();
    }

    public static void main(String[] args) {
        testMakeHugString();
    }
}
