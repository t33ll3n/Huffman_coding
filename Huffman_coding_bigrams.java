import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

class HuffmanNode_bigrams {

	int data;
	String bigram;

	HuffmanNode_bigrams left;
	HuffmanNode_bigrams right;
}

// comparator class compares nodes to keep priority-queue organized
class MyComparator_bigrams implements Comparator<HuffmanNode_bigrams> {
	public int compare(HuffmanNode_bigrams x, HuffmanNode_bigrams y) {

		return x.data - y.data;
	}
}

public class Huffman_coding_bigrams {

	// collection of bigrams(String) and there codes
	static HashMap<String, String> codes = new HashMap<String, String>();

	public static void main(String[] args) {

		FileReader fr = null;
		try {
			// Load input text file
			fr = new FileReader("input.txt");
		} catch (FileNotFoundException e) {
			System.out.println(
					"Can't find file called input.txt. Make sure it is in the same folder as Huffman_coding.class");
			e.printStackTrace();
		}

		BufferedReader br = new BufferedReader(fr);
		// number of characters in input file
		long charCounter = 0;
		String line;
		// holds values for how many times a character appears in input file
		HashMap<String, Integer> bigrams = new HashMap<String, Integer>();

		try {
			// read first line
			while ((line = br.readLine()) != null) {
				// check each character
				for (int i = 0; i < line.length(); i+=2) {
//					char znak = line.charAt(i);
					
					String bigram = line.substring(i, i+2);
					System.out.println(bigram + " curr bigram " + i + " i");
					
					// if character have been seen before
					if (bigrams.containsKey(bigram)) {
						// increase value by 1
						bigrams.put(bigram, bigrams.get(bigram) + 1);
					} else {
						// add character to he collection
						bigrams.put(bigram, 1);
					}
					// count character
					charCounter += 2;
				}
			}
			// Print how many characters and bits have been read. Standard input
			// file encoding is ANSI. Which uses 8 bits per character
			System.out.println(charCounter + " characters encoded. Total number of bits: " + (charCounter * 8));

			printCharFreq(bigrams);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// number of char in source
		int n = bigrams.size();

		// priority Queue. Elements are sorted by frequency in ascending order.
		PriorityQueue<HuffmanNode_bigrams> q = new PriorityQueue<HuffmanNode_bigrams>(n, new MyComparator_bigrams());

		// create a node for each character
		for (String znak : bigrams.keySet()) {

			HuffmanNode_bigrams hn = new HuffmanNode_bigrams();

			hn.bigram = znak;
			hn.data = bigrams.get(znak);

			hn.left = null;
			hn.right = null;

			// add to queue
			q.add(hn);
		}

		// create a root node
		HuffmanNode_bigrams root = null;

		// build Huffman tree
		// extract two nodes until the queue is empty
		while (q.size() > 1) {

			// first min node
			HuffmanNode_bigrams x = q.peek();
			q.poll();

			// second min node.
			HuffmanNode_bigrams y = q.peek();
			q.poll();

			// create new node
			HuffmanNode_bigrams f = new HuffmanNode_bigrams();

			// sum frequency and asign it to new node
			f.data = x.data + y.data;
			f.bigram = "-";

			// add children
			// first min goes left
			// second min goes right
			f.left = x;
			f.right = y;

			// make node f as root
			root = f;

			// add this node to the queue.
			q.add(f);
		}

		// build codes
		buildCode(root, "");

		// number of bits written using Huffman codes.
		long bitsCoded = 0;

		// create output file
		File file = new File("Compressed.txt");
		try {
			fr = new FileReader("input.txt");

			br = new BufferedReader(fr);

			file.createNewFile();
			FileWriter writer = new FileWriter(file);

			// at the start of the file write character and Huffman's codes.
			// Separates them with '_'
			for (String znak : codes.keySet()) {
				writer.write(znak + codes.get(znak) + "_");

			}
			writer.write("\n");

			while ((line = br.readLine()) != null) {
				for (int i = 0; i < line.length(); i+=2) {
//					char znak = line.charAt(i);
					String znak = line.substring(i, i+2);

					// write Huffman's code for current character into output
					// file
					writer.write(codes.get(znak));
					// count bits written
					bitsCoded += codes.get(znak).length();
					writer.flush();
				}
			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// calculate number of written bits, and compression percentage.
		// number of characters in input file must me multiplied by 8 because
		// ANSI encoding.
		float procent = (float) bitsCoded / (charCounter * 8) * 100;
		// Prints results
		System.out.println(bitsCoded + " number of bits written. Bits reduced from " + charCounter * 8 + " to "
				+ bitsCoded + ". Which is " + procent + "% compression");

	}

	private static void printCharFreq(HashMap<String, Integer> bigrams) {

		for (String znak : bigrams.keySet()) {
			System.out.println(znak + ": " + bigrams.get(znak));
		}

	}

	// recursive function to build Huffman codes from tree
	// String s represents the code.
	public static void buildCode(HuffmanNode_bigrams root, String s) {

		// If the node is leaf
		if (root.left == null && root.right == null) {

			// c is character, s is code in bits for it
			// saves codes to hashmap
			codes.put(root.bigram, s);

			return;
		}

		// if we go to left then add "0" to the code.
		// if we go to the right add"1" to the code.
		buildCode(root.left, s + "0");
		buildCode(root.right, s + "1");
	}

}
