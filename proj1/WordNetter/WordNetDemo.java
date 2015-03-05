import ngordnet.WordNet;

public class WordNetDemo {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new RuntimeException("No argument.");
        }
        WordNet wn = new WordNet("./wordnet/synsets.txt", "./wordnet/hyponyms.txt");
        int cnt = 0;
        for (String noun : wn.hyponyms(args[0])) {
            System.out.print(noun + " ");
            cnt = cnt + 1;
            if (cnt == 5) {
                System.out.println();
                cnt = 0;
            }
        }
        System.out.println();
    }
}
