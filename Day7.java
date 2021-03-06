package adventofcode21;

import java.io.*;
import java.util.ArrayList;

public class aoc7 {

	public static void main(String[] args) throws IOException {
		ArrayList<Integer> crab = readFileValues(); // content of file
		int star1 = minimalFuel(crab, false);
		int star2 = minimalFuel(crab, true);
		System.out.println(star1);
		System.out.println(star2);
	}

	// computes minimal value for fuel, boolean necessary to select algorithm for star1 or star2
	private static int minimalFuel(ArrayList<Integer> crab, boolean isStar2) {
		int highestNum = 0;
		int lowestNum = 0;
		int totalFuel = 0;
		for (int i = 0; i < crab.size(); i++) { // determines range in which optimal position must be in
			if (crab.get(i) > highestNum)
				highestNum = crab.get(i);
			else if (crab.get(i) < lowestNum)
				lowestNum = crab.get(i);
		}

		// determines optimal fuel by comparing use of fuel for each position to totalFuel, which contains least use of fuel from previous positions
		//for star1: subtracting each grab position with position in range or vice versa
		//for star2: adding each number between 1 and grab position - position in range
		for (int i = lowestNum; i <= highestNum; i++) {
			int fuel = 0;
			for (int j = 0; j < crab.size(); j++) {
				if (crab.get(j) > i) {
					if (!isStar2) {
						fuel += crab.get(j) - i; //star1
					} else {
						for (int k = 1; k <= crab.get(j) - i; k++) //star2
							fuel += k;
					}
				} else if (crab.get(j) < i) {
					if (!isStar2) {
						fuel += i - crab.get(j); //star1
					} else {
						for (int k = 1; k <= i - crab.get(j); k++) //star2
							fuel += k;
					}
				}
			}
			if (i == lowestNum) // necessary, because totalFuel is initialized with 0 and would so always be lower as fuel
				totalFuel = fuel;
			else if (totalFuel > fuel)
				totalFuel = fuel;
		}
		return totalFuel;
	}

	// reads initial list from file
	private static ArrayList<Integer> readFileValues() throws IOException {
		File file = new File("PATH_TO_FILE");
		ArrayList<Integer> lines = new ArrayList<Integer>();
		BufferedReader br = new BufferedReader(new FileReader(file)); // creates buffering input character stream
		String values = "";

		while ((values = br.readLine()) != null) {
			String[] parts = values.split(",");
			for (String a : parts)
				lines.add(Integer.valueOf(a));
		}
		br.close(); // closes stream, prevents possible I/O errors
		return lines;
	}
}
