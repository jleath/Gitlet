
public class DoubleChain {
	
	private DNode head;
	
	public DoubleChain(double val) {
            head = new DNode(val);
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
            String result = "[ ";
            DNode ptr = head;
            while (ptr != null) {
                result = result + Double.toString(ptr.val) + " ";
                ptr = ptr.next;
            }
            return result + "]";
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
