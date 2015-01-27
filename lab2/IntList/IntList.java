import java.util.Formatter;

/** Scheme-like pairs that can be used to form a list of integers.
 *  @author P. N. Hilfinger, with some modifications by Josh Hug and melaniecebula
 *  [Do not modify this file.]
 */
public class IntList {
  /** First element of list. */
  public int head;
  /** Remaining elements of list. */
  public IntList tail;

  /** A List with head HEAD0 and tail TAIL0. */
  public IntList(int head0, IntList tail0) {
    head = head0;
    tail = tail0;
  }

  /** A List with null tail, and head = 0. */
  public IntList() {
    /* NOTE: public IntList () { }  would also work. */
    this (0, null);
  }

  /* YOU DO NOT NEED TO LOOK AT ANY CODE BELOW THIS LINE UNTIL
     YOU GET TO THE PROBLEMS YOU NEED TO SOLVE. Search for 'FILL IN'
     and you'll be where you need to go. */


  /** Returns a new IntList containing the ints in ARGS. */
  public static IntList list(Integer ... args) {
    IntList result, p;

    if (args.length > 0) {
      result = new IntList(args[0], null);
    } else {
      return null;
    }

    int k;
    for (k = 1, p = result; k < args.length; k += 1, p = p.tail) {
      p.tail = new IntList(args[k], null);
    }
    return result;
  }

  /** Returns true iff X is an IntList containing the same sequence of ints
   *  as THIS. Cannot handle IntLists with cycles. */
  public boolean equals(Object x) {
    if (!(x instanceof IntList)) {
      return false;
    }
    IntList L = (IntList) x;
    IntList p;

    for (p = this; p != null && L != null; p = p.tail, L = L.tail) {
      if (p.head != L.head) {
        return false;
      }
    }
    if (p != null || L != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return head;
  }


  /** If a cycle exists in the IntList, this method
   *  returns an integer equal to the item number of the
   *  location where the cycle is detected.
   *
   *  If there is no cycle, the number 0 is returned instead.
   */

  private int detectCycles(IntList A) {
    IntList tortoise = A;
    IntList hare = A;

    if (A == null)
      return 0;

    int cnt = 0;


    while (true) {
      cnt++;
      if (hare.tail != null)
        hare = hare.tail.tail;
      else
        return 0;

      tortoise = tortoise.tail;

      if (tortoise == null || hare == null)
        return 0;

      if (hare == tortoise)
        return cnt;
    }
  }

  @Override
  public String toString() {
    Formatter out = new Formatter();
    String sep;
    sep = "(";
    int cycleLocation = detectCycles(this);
    int cnt = 0;

    for (IntList p = this; p != null; p = p.tail) {
      out.format("%s%d", sep, p.head);
      sep = ", ";

      cnt++;
      if ((cnt > cycleLocation) && (cycleLocation > 0)) {
        out.format("... (cycle exists) ...");
        break;
      }
    }
    out.format(")");
    return out.toString();
  }

  static void dSquareList(IntList L) {

    while (L != null) {
      L.head = L.head * L.head;
      L = L.tail;
    }
  }

  static IntList squareListIterative(IntList L) {
    if (L == null) {
      return null;
    }
    IntList res = new IntList(L.head * L.head, null);
    IntList ptr = res;
    L = L.tail;
    while (L != null) {
      ptr.tail = new IntList(L.head * L.head, null);
      L = L.tail;
      ptr = ptr.tail;
    }
    return res;
  }

  static IntList squareListRecursive(IntList L) {
    if (L == null) {
      return null;
    }
    return new IntList(L.head * L.head, squareListRecursive(L.tail));
  }

  /** DO NOT MODIFY ANYTHING ABOVE THIS LINE! */
  //FILL IN

  /** Returns a list consisting of the elements of A followed by the
   **  elements of B.  May modify items of A. Don't use 'new'. */

  static IntList dcatenate(IntList A, IntList B) {
    //TODO:  fill in method
    return null;
  }

  /** Returns a list consisting of the elements of A followed by the
   ** elements of B.  May NOT modify items of A.  Use 'new'. */
  static IntList catenate(IntList A, IntList B) {
    //TODO:  fill in method
    return null;
  }
}

