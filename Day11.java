package adventofcode21;

import java.io.*;
import java.util.ArrayList;

public class Day11 {

	public static void main(String[] args) throws IOException {
		int[][] octopus = readFileValues();
		int star1 = 0;
		boolean firstTime = true; //helps to determine time when all octopus flash

		//1000 is a random number to see when all octopus flash
		for (int k = 0; k < 1000; k++) {
			ArrayList<Integer[]> positionOfNines = new ArrayList<Integer[]>(); //contains the position of all flashing octopus
			Integer[] pos = new Integer[2]; //uses to store position of octopus
			int star2 = 0;
			//loops through the field, increases all numbers except 9s(those positions are added to the list)
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if(octopus[i][j] == 0) { //counts the amount of 0s
						star2++;
					}
					switch (octopus[i][j]) {
					case 9:
						pos = new Integer[] { i, j };
						positionOfNines.add(pos);
						break;
					default:
						octopus[i][j]++;
						break;
					}
				}
			}
			
			if(k == 100) {
				System.out.println(star1);
			}
			
			if(star2 == 100 && firstTime) { //prints out only the first time when all octopus flash
				System.out.println(k);
				firstTime = false;
			}
			
			//goes through each element in list (aka positions of 9s) and increases their neighbours if they exist and are not 0 (because then its a former 9)
			//if all neighbours are increased, the position is removed from the list and set to 0 in the field
			while (!positionOfNines.isEmpty()) {
				int i = positionOfNines.get(0)[0];
				int j = positionOfNines.get(0)[1];
				if (i + 1 < 10) {
					if(octopus[i+1][j] != 0)
						octopus[i+1][j]++;
					if (j + 1 < 10 && octopus[i+1][j+1] != 0)
						octopus[i+1][j+1]++;
					if (j - 1 >= 0 && octopus[i+1][j-1] != 0)
						octopus[i+1][j-1]++;
				}
				if (i - 1 >= 0) {
					if(octopus[i-1][j] != 0)
						octopus[i-1][j]++;
					if (j + 1 < 10 && octopus[i-1][j+1] != 0)
						octopus[i-1][j+1]++;
					if (j - 1 >= 0 && octopus[i-1][j-1] != 0)
						octopus[i-1][j-1]++;
				}
				if (j + 1 < 10 && octopus[i][j+1] != 0) {
					octopus[i][j+1]++;
				}
				if (j - 1 >= 0 && octopus[i][j-1] != 0) {
					octopus[i][j-1]++;
				}
				star1++; //counts amount of flashes
				octopus[i][j] = 0;
				positionOfNines.remove(0);
				
				//if all 9s of one round are 'used', the loop goes again through the field and adds all positions to the list where number is greater than 9
				if(positionOfNines.isEmpty()) {
					for (int m = 0; m < 10; m++) {
						for (int n = 0; n < 10; n++) {
							if(octopus[m][n] > 9) {
								pos = new Integer[] {m, n};
								positionOfNines.add(pos);
							}
						}
					}
				}
			}
		}
	}

	//reads values out of file
	private static int[][] readFileValues() throws IOException {
		int[][] octopus = new int[10][10];
		File file = new File("PATH_TO_FILE");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		int i = 0;
		while ((line = br.readLine()) != null) {
			String[] split = line.split("");
			for (int j = 0; j < 10; j++) {
				octopus[i][j] = Integer.valueOf(split[j]);
			}
			i++;
		}
		br.close();
		return octopus;
	}
}
