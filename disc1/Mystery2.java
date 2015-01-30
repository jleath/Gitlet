public class Mystery2 {
    public static void mystery2(int[] inputArray) {
        int index = 0;
        while (index < inputArray.length) {
            int targetIndex = Mystery.mystery(inputArray, index);
            int temp = inputArray[targetIndex];
            inputArray[targetIndex] = inputArray[index];
            inputArray[index] = temp;
            index = index + 1;
        }
    }

    public static void main(String[] args) {
        int[] test = {3, 0, 1, 6, 3};
        mystery2(test);
        for (int i : test)
            System.out.print(i + " ");
        System.out.println();
    }
}
