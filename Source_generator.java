import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Source_generator {
	public static void main(String[] args) {
		
		//character that can appear in source with there probability
		char[] charArray = {'a', 'b', 'c', 'd', 'e', 'f'};
		//probability multiplied by 100 to avoid floating point errors
		int[] charProb = {5, 10, 15, 18, 22, 30};
		
		//how much character should source produce
		int numOfCharInSource = 300;
		
		//checks if sum of probabilities is equal to 1;
		SumElementsInArray(charProb);
		
		//returns source string
		String source = Generator(charArray, charProb, numOfCharInSource);
		
		
		File input = new File("input.txt");
		try {
			input.createNewFile();
			
			FileWriter writer = new FileWriter(input);
			//write source string to a file called input.txt
			writer.write(source);
			
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Source generated!");
		
		
	}

	private static void SumElementsInArray(int[] charProb) {
		int sum = 0;
		
		//sums all the elements
		for (int i = 0; i < charProb.length; i++){
			sum += charProb[i];
		}
		
		//is sum is not 1 then print message and exit
		if (sum != 100){
			System.out.println("Sum of probability is not equal to 1");
			System.exit(1);
		} else {
//			System.out.println("sum is OK!");
		}
		
	}
	
	private static String Generator(char[] charArray, int[] charProb, int numOfCharInSource){
		int[] probRange = new int[charProb.length];
		
		StringBuilder source = new StringBuilder();
		Random rd = new Random();
		
		if (charArray.length != charProb.length){
			System.out.println("Array of characters and probabilities are not the same length.");
			System.exit(3);
		}
		
		//if there is no probability values, print message and exit program
		if (charProb.length < 1){
			System.out.println("Probability array does not contain any elements");
			System.exit(2);
		}
		
		probRange[0] = charProb[0];
		
		//maps probabilities from 0 to 1
		for (int i = 1; i < probRange.length; i++){
			//sum previous value, add current and write it to current spot in array
			probRange[i] = probRange[i-1] + charProb[i];
		}
		
		for (int i = 0; i < numOfCharInSource; i++){
			//create random number
			int nextProb = rd.nextInt(100)+1;
			
			//check for charater
			for (int j = 0; j < probRange.length; j++) {
				if (nextProb <= probRange[j]){
					//append charater at the end of the string
					source.append(charArray[j]);
					break;
				}
			}
		}
		
		//return source string
		return source.toString();
		
	}
	
}
