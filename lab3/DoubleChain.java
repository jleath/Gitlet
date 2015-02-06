
public class DoubleChain {
	
    private DNode head;
    
    public DoubleChain(double val) {
        head = new DNode(val);
    }

    public static DoubleChain buildChain(double ... vals) {
        DoubleChain result = new DoubleChain(vals[0]);
        int currentVal = 1;
        while (currentVal < vals.length) {
            result.insertBack(vals[currentVal]);
            currentVal += 1;
        }
        return result;
    }

    /** Returns the index of the first DNode with a value equal to VALUE
     *  if such a node exists, otherwise returns -1.
     */
    public int indexOf(double value) {
        DNode ptr = head;
        int index = 0;
        while (ptr != null) {
            if (Math.abs(ptr.val - value) < 1e-11)
                return index;
            ptr = ptr.next;
            index += 1;
        }
        return -1;
    }

    /** Removes all DNodes from the DoubleChain that have a val
     *  equal to VALUE.
     */
    public void deleteByValue(double value) {
        int indexOfValue = this.indexOf(value);
        while (indexOfValue >= 0) {
            this.deleteByIndex(indexOfValue);
            indexOfValue = this.indexOf(value);
        }
    }

    /** Returns the value of the node located at position I
     *  of the DoubleChain. Exits the program if an invalid
     *  index is passed in. */
    public double get(int i) {
        if (i >= this.size() || i < 0)
            System.exit(0);
        DNode ptr = head;
        int currentPos = 0;
        while (currentPos < i) {
            ptr = ptr.next;
            currentPos += 1;
        }
        return ptr.val;
    }

    public DNode getFront() {
            return head;
    }

    /** Returns the last item in the DoubleChain. */		
    public DNode getBack() {
        DNode ptr = head;
        while (ptr.next != null)
            ptr = ptr.next;
        return ptr;
    }
    
    /** Adds D to the front of the DoubleChain. */	
    public void insertFront(double d) {
        head = new DNode(null, d, head);
    }
    
    /** Adds D to the back of the DoubleChain. */	
    public void insertBack(double d) {
        DNode ptr = head;
        while (ptr.next != null) {
            ptr = ptr.next;
        }
        DNode newNode = new DNode(ptr, d, null);
        ptr.next = newNode;
    }

    /** Removes the first item in the DoubleChain and returns it.
     */
    public DNode deleteFront() {
        DNode result = head;
        head = head.next;
        head.prev = null;
        return result;
    }
    
    /** Removes the last item in the DoubleChain and returns it. 
      * This is an extra challenge problem. */
    public DNode deleteBack() {
        DNode ptr = head;
        while (ptr.next != null) {
            ptr = ptr.next;
        }
        DNode result = ptr;
        ptr = ptr.prev;
        ptr.next = null;
        return result;
    }
    
    /** Returns a string representation of the DoubleChain. 
      * This is an extra challenge problem. */
    public String toString() {
        String result = "<[";
        DNode ptr = head;
        while (ptr != null) {
            if (ptr.next == null) {
                result = result + Double.toString(ptr.val); 
            } else {
                result = result + Double.toString(ptr.val) + ", ";
            }
            ptr = ptr.next;
        }
        return result + "]>";
    }

    public int size() {
        int result = 0;
        DNode ptr = head;
        while (ptr != null) {
            result = result + 1;
            ptr = ptr.next;
        }
        return result;
    }

    /** Deletes the DNode at index I of the DoubleChain.
     *  Does nothing if I is larger than this.size() */
    public void deleteByIndex(int i) {
        // Deal with invalid indices (kinda).
        if (i >= this.size())
            return;
        // Deal with deleting the last node.
        if (i == this.size() - 1) {
            this.deleteBack();
            return;
        }
        // Deal with deleting the first node.
        if (i == 0) {
            this.deleteFront();
            return;
        }
        // move to the Ith node.
        DNode ptr = head;
        int currentPos = 0;
        while (currentPos < i) {
            ptr = ptr.next;
            currentPos += 1;
        }
        // delete the Ith node.
        ptr.prev.next = ptr.next;
        ptr.next.prev = ptr.prev;
        ptr.next = null;
        ptr.prev = null;
    }

    public static class DNode {
            public DNode prev;
            public DNode next;
            public double val;
            
            private DNode(double val) {
                    this(null, val, null);
            }
            
            private DNode(DNode prev, double val, DNode next) {
                    this.prev = prev;
                    this.val = val;
                    this.next =next;
            }
    }
	
}
