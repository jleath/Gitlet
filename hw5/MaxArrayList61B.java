public class MaxArrayList61B<T extends Comparable<T>> extends ArrayList61B<T> {
    private T largest = null;

    @Override
    public boolean add(T item) {
        if (largest == null) {
            largest = item;
        }
        if (item.compareTo(largest) <= 0) {
            return false;
        }
        return super.add(item);
    }
}
