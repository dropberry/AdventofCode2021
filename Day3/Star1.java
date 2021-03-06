package adventofcode21;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class aoc3 {

	public static void main(String[] args) throws IOException {
		String[] binaries = readFileValues(); //interpret binaries as string
		int lengthBin = binaries[0].length();
		int gamma = 0;
		int epsilon = 0;
		int[][] amount = amountZeroOne(binaries);
			
		//computes decimal value of each "binary"
		//depending on the position and value, a power of 2 is added to epsilon or gamma
		//value of power gets smaller when i increases
		for(int i = 0; i < lengthBin; i++) {
			if(amount[0][i] > amount [1][i]) {
				epsilon += Math.pow(2, lengthBin-1-i);
			}
			else {
				gamma += Math.pow(2, lengthBin-1-i);
			}
		}
		System.out.println(gamma*epsilon); //star1
	}
	
	//goes through each position of each binary and counts the =0s and 1s
	private static int[][] amountZeroOne(String[] binaries) {
		int lengthBin = binaries[0].length();
		int[][] amount = new int[2][lengthBin]; //stores amount of 0s in [0] for each position, same for 1s in [1]
		
		for (int i = 0; i < binaries.length; i++) {
			for (int j = 0; j < lengthBin; j++) {
				if(binaries[i].charAt(j) == '0')
					amount[0][j] += 1;
				else
					amount[1][j] += 1;
			}
		}
		return amount;
	}
	
	private static String[] readFileValues() throws IOException {
		File file = new File("PATH_TO_FILE");
		List<String> lines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file)); //creates buffering input character stream
	    String values = "";
	    
	    //as long as end of file is not reached, add each line to list
	    while ((values = br.readLine()) != null) {
	    	lines.add(values);
	    }
	    br.close(); //closes stream, prevents possible I/O errors
	    
	    String[] dataStr = lines.toArray(new String[lines.size()]);
	    return dataStr;
	}
}
