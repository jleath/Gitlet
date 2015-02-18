import java.util.Arrays;
public class CountStars {
    public static String[] countStars(String[] args) {
        boolean evenNumStars = true;
        String[] result = new String[args.length];
        int totalNonStars = 0;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("star")) {
                evenNumStars = !evenNumStars;
            } else {
                result[totalNonStars] = args[i];
                totalNonStars += 1;
            }
        }
        if (evenNumStars) {
            return args;
        } else {
            return Arrays.copyOf(result, totalNonStars);
        }
    }

    public static void printArray(String[] args) {
        for (String s : args) {
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        printArray(countStars(args));
    }
}
