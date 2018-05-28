import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class bigram_source {

	static char[] charArray = { 'a', 'b', 'c', 'd', 'e', 'f' };
	// probability multiplied by 100 to avoid floating point errors
	static int[] charProb = { 5, 10, 15, 18, 22, 30 };
	static Random rand;

	public static void main(String[] args) {

		String[] stringArray = { "aa", "ab", "ac", "ad", "ae", "af", "ba", "bb", "bc", "bd", "be", "bf", "ca", "cb",
				"cc", "cd", "ce", "cf", "da", "db", "dc", "dd", "de", "df", "ea", "eb", "ec", "ed", "ee", "ef", "fa",
				"fb", "fc", "fd", "fe", "ff" };

		int[] stringProb = calcProb(charArray, charProb, stringArray);

		int[] probRange = sumElementsInArray(stringProb);
		
		String source = generateSource(probRange, stringArray);
		
		File input = new File("input.txt");
		
		try {
			input.createNewFile();
			
			FileWriter writer = new FileWriter(input);
			
			writer.write(source);
			
			writer.close();
		} catch (IOException e) {
			System.out.println("Could not write to a file");
			e.printStackTrace();
		}

		for (int i = 0; i < stringProb.length; i++) {
			System.out.println(stringProb[i]);
			System.out.println(probRange[i]);
		}
		
		System.out.println(source);

	}

	public static int[] calcProb(char[] charArray, int[] charProb, String[] StringArray) {

		int[] StringProb = new int[StringArray.length];

		for (int i = 0; i < StringArray.length; i++) {
			int first = 0, last = 0;

			for (int j = 0; j < charArray.length; j++) {
				if (StringArray[i].charAt(0) == charArray[j]) {
					first = charProb[j];
				}
				if (StringArray[i].charAt(1) == charArray[j]) {
					last = charProb[j];
				}
			}

			StringProb[i] = first * last;

		}

		return StringProb;
	}

	public static int[] sumElementsInArray(int[] StringProb) {

		int[] probRange = new int[StringProb.length];

		probRange[0] = StringProb[0];

		for (int i = 1; i < StringProb.length; i++) {
			probRange[i] = probRange[i - 1] + StringProb[i];
		}

		return probRange;
	}

	public static String generateSource(int[] probRange, String[] stringArray) {
		
		rand = new Random();
		int nextProb;
		
		StringBuilder source = new StringBuilder();
		
		for (int i = 0; i < 150; i++){
			nextProb = rand.nextInt(10000);
			
			for (int j = 0; j < probRange.length; j++){
				if (nextProb <= probRange[j]){
					source.append(stringArray[j]);
					break;
				}
			}
		}
		
		return source.toString();
	}

}
