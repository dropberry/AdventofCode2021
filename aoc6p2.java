package adventofcode21;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//here, the amount of fishes is represented in an array, each entry contains the number of fishes for each timer sate
public class aoc6p2 {

	public static void main(String[] args) throws IOException {
		ArrayList<Integer> fish = readFileValues(); //content of file
		long[] population = new long[] {0,0,0,0,0,0,0,0,0}; //each entry represents the amount of fishes for each timer state
		
		//allocating initial values from file aka day 0
		for(int i = 0; i < fish.size(); i++) { 
			population[fish.get(i)] += 1;
		}
		
		for(int i = 0; i < 256; i++) {
			long timerZero = population[0]; //saved for later allocation because every fish with timer 0 creates new fish with timer 8
			
			//changes values between each entry, simulating the decrease of the timer each day
			//runs only till 5 for easier allocation of fishes with timer 6 as the former population of 0s and 7s
			for(int j = 0; j < 6; j++) {
				long current = population[j];
				population[j] = population[j+1];
				population[j+1] = current;
				
				if(j == 5) //allocating to fishes with timer 6 (who already have the number of fishes with 0s) additionally the amount of fishes with timer 7
					population[j+1] += population[j+2];
			}
			
			population[7] = population[8];
			population[8] = timerZero; //creation of new fishes
		}
		
		long star2 = 0;
		for(long num : population) //adds number of fishes together
			star2 += num;
		System.out.println(star2);
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
