import java.util.AbstractList;

/* An implementation of an Array-based list that is capable of resizing
 */
public class ArrayList61B<T> extends AbstractList<T> {
    protected T[] contents;
    protected int count;
    
    public ArrayList61B(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException();
        }
        contents = (T[]) new Object[initialCapacity];
        count = 0;
    }

    public ArrayList61B() {
        this(1);
    }

    @Override
    public T get(int i) {
        if (i < 0 || i >= count) {
            throw new IllegalArgumentException();
        }
        return contents[i];
    }

    @Override
    public boolean add(T item) {
        if (count == contents.length) {
            int newLength = contents.length * 2;
            T[] newContents = (T[]) new Object[newLength];
            System.arraycopy(contents, 0, newContents, 0, count);
            newContents[count] = item;
            contents = newContents;
        } else {
            contents[count] = item;
        }
        count = count + 1;
        return true;
    }

    @Override
    public int size() {
        return count;
    }
}
