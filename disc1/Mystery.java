public class Mystery {
    public static int mystery(int[] inputArray, int k) {
        int x = inputArray[k];
        int answer = k;
        int index = k + 1;
        while (index < inputArray.length) {
            if (inputArray[index] < x) {
                x = inputArray[index];
                answer = index;
            }
            index = index + 1;
        }
        return answer;
    }

    public static void main(String[] args) {
        int[] test = {3, 0, 1, 6, 3};
        int k = 2;
        System.out.println(mystery(test, k));
        int[] test2 = {3, 0, 3, 6, 1};
        int k2 = 2;
        System.out.println(mystery(test2, k2));
    }
}
