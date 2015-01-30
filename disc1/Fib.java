public class Fib {
    public static int fib(int n, int k, int f0, int f1) {
        if (n == k)
    }

    public static void printFibs(int i) {
        for (int j = 1; j <= i; j++)
            System.out.print(fib(0, j) + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        printFibs(10);
    }
}
