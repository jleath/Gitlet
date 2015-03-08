package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;
import java.util.InputMismatchException;
import java.util.Set;
import java.util.ArrayList;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author Joshua Leath
 */
public class NgordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");
        In helpFile = new In("./ngordnet/help.txt");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        NGramMap ngm = new NGramMap(wordFile, countFile);

        int startYear = 0;
        int endYear = 2010;

        System.out.println("\nBased on ngordnetui.config, using the following: "
                           + wordFile + ", " + countFile + ", " + synsetFile +
                           ", and " + hyponymFile + ".");

        System.out.println("\nFor tips on implementing NgordnetUI, see ExampleUI.java.");
        while (true) {
            System.out.print("> ");
            String cmd = "";
            cmd = StdIn.readString();
            if (cmd.equals("")) {
                continue;
            } else if (cmd.equals("quit")) {
                System.exit(0);
            } else if (cmd.equals("help")) {
                while (helpFile.hasNextLine()) {
                    System.out.println(helpFile.readLine());
                }
            } else if (cmd.equals("range")) {
                try {
                    startYear = StdIn.readInt(); 
                } catch (InputMismatchException e) {
                    System.out.println("Invalid start date");
                    continue;
                }
                try {
                    endYear = StdIn.readInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid end date");
                    continue;
                }
            } else if (cmd.equals("hyponyms")) {
                String word = StdIn.readString();
                Set<String> hypos;
                try {
                    hypos = wn.hyponyms(word);
                } catch (IllegalArgumentException e) {
                    System.out.println("Cannot find the word " + word);
                    continue;
                }
                System.out.println(hypos);
            } else if (cmd.equals("count")) {
                String word = StdIn.readString();
                int year = 0;
                try {
                    year = StdIn.readInt(); 
                } catch (InputMismatchException e) {
                    System.out.println("Invalid year");
                    continue;
                }
                System.out.println(ngm.countInYear(word, year));
            } else if (cmd.equals("history")) {
                ArrayList<String> words = new ArrayList<String>();
                String[] ex = new String[1];
                String line = StdIn.readLine();
                StringBuilder sb = new StringBuilder();
                int curr = 0;
                while (curr < line.length() && line.charAt(curr) == ' ')
                    curr = curr + 1;
                while (curr < line.length()) {
                    while (curr < line.length() && line.charAt(curr) != ' ') {
                        sb.append(line.charAt(curr));
                        curr = curr + 1;
                    }
                    if (wn.isNoun(sb.toString())) {
                        words.add(sb.toString());
                    } else {
                        System.out.println("Cannot find " + sb.toString() + ". Ignoring.");
                    }
                    sb = new StringBuilder();
                    curr = curr + 1;
                }
                Plotter.plotAllWords(ngm, words.toArray(ex), startYear, endYear);
            } else if (cmd.equals("hypohist")) {
                ArrayList<String> words = new ArrayList<String>();
                String[] ex = new String[1];
                String line = StdIn.readLine();
                StringBuilder sb = new StringBuilder();
                int curr = 0;
                while (curr < line.length() && line.charAt(curr) == ' ')
                    curr = curr + 1;
                while (curr < line.length()) {
                    while (curr < line.length() && line.charAt(curr) != ' ') {
                        sb.append(line.charAt(curr));
                        curr = curr + 1;
                    }
                    if (wn.isNoun(sb.toString())) {
                        words.add(sb.toString());
                    }
                    sb = new StringBuilder();
                    curr = curr + 1;
                }
                if (words.size() > 0) {
                    Plotter.plotCategoryWeights(ngm, wn, words.toArray(ex), startYear, endYear);
                } else {
                    System.out.println("No valid words entered.");
                }
            } else if (cmd.equals("wordlength")) {
                WordLengthProcessor wlp = new WordLengthProcessor();
                Plotter.plotProcessedHistory(ngm, startYear, endYear, wlp);
            }
        }
    }
} 
