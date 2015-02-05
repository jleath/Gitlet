public class Disc3 {
    /** Returns a new array of integers with VAL at index POSITION.
     *  Shifts all values greater than POSITION one space to the right.
     */
    public static int[] insert(int[] x, int val, int position) {
        int[] newX = new int[x.length + 1];
        int i = 0;
        // Get values before the new position
        while (i < position) {
            newX[i] = x[i];
            i = i + 1;
        }
        // place the new value into the array
        newX[i] = val;
        i = i + 1;
        // continue putting the rest of the elements in the new array
        int j = i - 1;
        while (j < x.length) {
            newX[i] = x[j];
            i = i + 1;
            j = j + 1;
        }
        return newX;
    }

    public static int[] xify(int[] x) {
        int newLength = calculateSize(x);
        int[] result = new int[newLength];
        int j = 0;
        for (int i = 0; i < x.length; i++) {
            int start = j;
            while (j < start + x[i]) {
                result[j] = x[i];
                j = j + 1;
            }
        }
        return result;
    }

    private static int calculateSize(int[] x) {
        int i = 0;
        for (int j : x)
            i = i + j;
        return i;
    }
}
