package adventofcode21;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/* every method is static because it's used by main, usually those methods would be in another class but are kept in one class here for readability
 * that's also the reason why no private attributes for the class could be used (reference of 'this' not allowed in static classes)
 */
public class aoc3p2 {
	
	public static void main(String[] args) throws IOException {
		List <String> binaries = new ArrayList<String>();
		binaries = readFileValues(); //interpret binaries as string
		int lengthBin = binaries.get(0).length();
		int oxygen = 0;
		int coTwo = 0;
		
		//remove so long binaries till only one is left
		removeValues('1', '0', binaries, lengthBin);
		oxygen = computeValues(binaries, lengthBin);
		
		//do the same as for oxygen, remove so long binaries till only one is left
		binaries = readFileValues();
		removeValues('0', '1', binaries, lengthBin);
		coTwo = computeValues(binaries, lengthBin);
		
		System.out.println(oxygen*coTwo); //star2
	}
	
	//determines, which binaries should be removed if there are more 0s than 1s 
	private static void removeValues(char moreZeros, char moreOnes, List<String> binaries, int lengthBin) {
		int[] amount = new int[2];
		
		for(int i = 0; i < lengthBin; i++) {
			amount = amountOneZero(binaries, i);
			if(binaries.size() > 1) {
				if(amount[0] > amount[1])
					removing(moreZeros, i, binaries);
				else 
					removing(moreOnes, i, binaries);
			}
		}
	}
	
	//calculates the amount of 0s and 1s for each binary on a specific position
	private static int[] amountOneZero (List <String> binaries, int position) {
		int[] test = new int[]{0,0};
		for (int i = 0; i < binaries.size(); i++) {
			if(binaries.get(i).charAt(position) == '0')
				test[0]++;	
			else
				test[1]++;
		}
		return test;
	}
	
	//actual removal of binaries from the list, depending if there were more 0s than 1s and if its oxygen or coTwo
	private static void removing(int value, int i, List<String> binaries) {
		for(int j = 0; j < binaries.size(); j++) {
			if(binaries.get(j).charAt(i) == value) {
				binaries.remove(j);
				j--;
			}
		}
	}	
	
	//turn binary into decimal value
	private static int computeValues(List <String> binaries, int lengthBin) {
		int value = 0;
		String finalBin = binaries.get(0);
		for(int i = 0; i < lengthBin; i++) {
			value += Math.pow(2, lengthBin-1-i) * Character.getNumericValue(finalBin.charAt(i));
		}
		return value;
	}
	
	//reads input of files as a list of strings
	private static List<String> readFileValues() throws IOException { 
		File file = new File("C:\\Users\\Lea\\Downloads\\test.txt");
		List<String> lines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file)); //creates buffering input character stream
	    String values = "";
	    
	    //as long as end of file is not reached, add each line to list
	    while ((values = br.readLine()) != null) {
	    	lines.add(values);
	    }
	    br.close(); //closes stream, prevents possible I/O errors
	    return lines;
	}
}	
