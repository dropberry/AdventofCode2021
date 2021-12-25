package adventofcode21;

import java.io.*;
import java.util.ArrayList;

//this version works good for only 80 days, for 256 days I had to write a different code because this needed really long and could finish because it ran out of heap space
//here, the amount of fishes is represented in the length of the list
public class aoc6 {

	public static void main(String[] args) throws IOException {
		ArrayList<Integer> fish = readFileValues(); //content of file
		
		for(int i = 0; i < 80; i++) {
			long size = fish.size();
			for(long j = 0; j < size; j++) {
				switch(fish.get((int)j)) { 
					case 0: //for each fish with timer 0 a new fish is added to list with timer 8
						fish.set((int)j, 6);
						fish.add(8);
						break;
					default:
						fish.set((int)j, fish.get((int)j)-1);
						break;
				}
			}
		}
		
		long star1 = fish.size();
		System.out.println(star1);
	}

	//reads initial list from file
	private static ArrayList<Integer> readFileValues() throws IOException {
		File file = new File("C:\\Users\\Lea\\Downloads\\test.txt");
		ArrayList<Integer> lines = new ArrayList<Integer>();
		BufferedReader br = new BufferedReader(new FileReader(file)); //creates buffering input character stream
	    String values = "";
	    
	    while ((values = br.readLine()) != null) {
	    	String[] parts = values.split(",");
	    	for(String a : parts)
	    		lines.add(Integer.valueOf(a));
	    }
	    br.close(); //closes stream, prevents possible I/O errors
	    return lines;
	}

}
