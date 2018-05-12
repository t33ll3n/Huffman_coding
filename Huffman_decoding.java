import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Huffman_decoding {
	public static void main(String[] args) {
		//Hashmap for Huffman codes
		HashMap<String, Character> codes = new HashMap<String, Character>();
		
		FileReader fr = null;
		BufferedReader br = null;
		try {
			//read compressed text file
			fr = new FileReader("Compressed.txt");
			br = new BufferedReader(fr);
			
			//first line contains Huffman codes
			String line = br.readLine();
			//separated with '_'
			String[] linespl = line.split("_");
			
			//read codes
			for (String kode : linespl){
				//first character is a character follow by its code
				char znak = kode.charAt(0);
				String code = kode.substring(1);
				
				//save code in hashmap
				codes.put(code, znak);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String line;
		FileWriter writer = null;
		try {
			//create decompress file
			File file = new File("Decompressed.txt");
			file.createNewFile();
			writer = new FileWriter(file);
			
			//read compressed file
			while((line = br.readLine()) != null){
				//start position of potential Huffman code
				int start = 0;
				for (int i = 1; i < line.length(); i++){
					//create substring of potential code
					String curr = line.substring(start, i+1);
					
					//check if it is a valid code
					if (curr.length() != 0 && codes.containsKey(curr)){
						//get corresponded character 
						char znak = codes.get(curr);
						//write it to decompressed file
						writer.write(znak + "");
						writer.flush();
						//increment i for 1, because i-th code character was allready used 
						start = i+1;
						
					}
				}
				writer.close();
			}
		} catch (IOException e) {
			System.out.println("Can't find file called Compressed.txt. Make sure it is in the same folder as Huffman_decoding.class.");
			e.printStackTrace();
		}
		
		System.out.println("Decompression completed.");
		
	}
}
