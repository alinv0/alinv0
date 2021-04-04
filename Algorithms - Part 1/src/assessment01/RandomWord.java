import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

    public static void main(String[] args) {
        double probability = 0.0002;
        String champion = null;

        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            if (champion == null || StdRandom.bernoulli(probability)) {
                champion = input;
            }
        }
        StdOut.println(champion);
    }
}
