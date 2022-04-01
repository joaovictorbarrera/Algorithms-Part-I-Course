import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
	public static void main(String[] args) {
		String current, survivor = null;
		int i = 0;
		while(!StdIn.isEmpty()) {
			i++;
			current = StdIn.readString();
			if(StdRandom.bernoulli(1/(double)i)) {
				survivor = current;
			}
		}
		StdOut.println(survivor);
	}
}
