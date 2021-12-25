package adventofcode21;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class aoc8 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		ArrayList<String[]> segment = readFileValues();
		int star1 = computeStar1(segment);
		int star2 = computeStar2(segment);
		System.out.println(star1);
		System.out.println(star2);
	}

	//counts how often in the final values the numbers occur which can be uniquely identified by their length
	private static int computeStar1(ArrayList<String[]> segment) {
		int counter = 0;
		for (int j = 0; j < segment.size(); j++) {
			for (int i = 10; i < 14; i++) {
				int length = segment.get(j)[i].length();
				if (length == 2 || length == 4 || length == 3 || length == 7)
					counter++;
			}
		}
		return counter;
	}

	//determines the right number for the final output value for each segment, values are added in variable counter
	private static int computeStar2(ArrayList<String[]> segment) {
		int counter = 0;
		for (int j = 0; j < segment.size(); j++) {
			String[] sortNumber = sortNumber(segment.get(j)); //sorts the mixed numbers of each segment line
			String rightOutput = "";
			for (int i = 10; i < 14; i++) {
				//for the first four cases, the right number is detected by the length of each segment number (because they are unique)
				switch (segment.get(j)[i].length()) {
				case 2:
					rightOutput += "1";
					break;
				case 4:
					rightOutput += "4";
					break;
				case 3:
					rightOutput += "7";
					break;
				case 7:
					rightOutput += "8";
					break;
				//for length 5 are three possibilities which number should be displayed by the segment number
				//it checks all possibilities with the checkNumber function; to do so it's important the numbers are sorted in sortNumber[]
				case 5:
					if(checkNumbers(segment.get(j)[i], sortNumber[5])) {
						rightOutput += "5";
					} else if(checkNumbers(segment.get(j)[i], sortNumber[2])) {
						rightOutput += "2";
					} else if(checkNumbers(segment.get(j)[i], sortNumber[3])) {
						rightOutput += "3";
					}
					break;
				//the same goes for length 6 as for length 5
				case 6:
					if(checkNumbers(segment.get(j)[i], sortNumber[0])) {
						rightOutput += "0";
					} else if(checkNumbers(segment.get(j)[i], sortNumber[9])) {
						rightOutput += "9";
					} else if(checkNumbers(segment.get(j)[i], sortNumber[6])) {
						rightOutput += "6";
					}
					break;
				default:
					break;
				}
			}
			counter += Integer.valueOf(rightOutput);
		}
		return counter;
	}

	//sorts the numbers in the given line of false segment numbers and returns the sorted array
	private static String[] sortNumber(String[] segment) {
		String[] sortedNumbers = new String[10];
		List<String> mixedNumbers = new ArrayList<String>(Arrays.asList(segment)); //to remove the already detected numbers, convertion to ArrayList is neccessary

		//removes last four output values
		for (int m = 0; m < 4; m++) {
			mixedNumbers.remove(10);
		}
		
		//this first iteration through the line of segments removes the four numbers which can be uniquely identified by their length
		//it also writes them on the right position in sortedNumbers
		for (int j = 0; j < mixedNumbers.size(); j++) {
			switch (mixedNumbers.get(j).length()) {
			case 2:
				sortedNumbers[1] = mixedNumbers.get(j);
				mixedNumbers.remove(j);
				j--;
				break;
			case 4:
				sortedNumbers[4] = mixedNumbers.get(j);
				mixedNumbers.remove(j);
				j--;
				break;
			case 3:
				sortedNumbers[7] = mixedNumbers.get(j);
				mixedNumbers.remove(j);
				j--;
				break;
			case 7:
				sortedNumbers[8] = mixedNumbers.get(j);
				mixedNumbers.remove(j);
				j--;
				break;
			default:
				break;
			}
		}

		int k = 0;
		// >2 is necessary because for length 6 and 5 is one number each which can't be 100% identified through overlapping with other numbers
		//so, those numbers (2 and 6) are allocated in the end after the loop
		while (mixedNumbers.size() > 2) {
			//each unknown number is identified with overlapping of other numbers: 9 is the only number where 4 completely fits in, for 0 it's 1 and so on.
			if (mixedNumbers.get(k).length() == 6 && checkNumbers(mixedNumbers.get(k), sortedNumbers[4])) {
				sortedNumbers[9] = mixedNumbers.get(k);
				mixedNumbers.remove(k);
			} else if (mixedNumbers.get(k).length() == 6 && checkNumbers(mixedNumbers.get(k), sortedNumbers[1])) {
				sortedNumbers[0] = mixedNumbers.get(k);
				mixedNumbers.remove(k);
			} else if (mixedNumbers.get(k).length() == 5 && checkNumbers(mixedNumbers.get(k), sortedNumbers[1])) {
				sortedNumbers[3] = mixedNumbers.get(k);
				mixedNumbers.remove(k);
			} else if (mixedNumbers.get(k).length() == 5 && sortedNumbers[9] != null
					&& checkNumbers(mixedNumbers.get(k), sortedNumbers[9])) {
				sortedNumbers[5] = mixedNumbers.get(k);
				mixedNumbers.remove(k);
			}
			k++;
			if (k >= mixedNumbers.size()) //necessary so that k can never be bigger than the size of mixedNumbers and the loop keeps going
				k = 0;
		}
		
		//allocated last two remaining values, which can be identified through their length because the other values have been removed
		if (mixedNumbers.get(0).length() == 6) {
			sortedNumbers[6] = mixedNumbers.get(0);
			sortedNumbers[2] = mixedNumbers.get(1);
		} else if (mixedNumbers.get(1).length() == 6) {
			sortedNumbers[2] = mixedNumbers.get(0);
			sortedNumbers[6] = mixedNumbers.get(1);
		}
	
		return sortedNumbers;
	}

	//checks if the unknownNumber can be detected by an already known one
	//this happens for checking if each char in the shorter String is part of the longer one
	private static boolean checkNumbers(String unknownNum, String knownNum) {
		int counter = 0; //counts how often a char in one string appears in the other string
		boolean matches = false;
		String shorterNum = "";
		String largerNum = "";
		//the allocation to shorterNum and largerNum is needed
		//usually the knownNum is smaller than the unknownNum, but when checking if 5 fits in 9 its the opposite and the counter won't work
		if (knownNum.length() < unknownNum.length()) {
			shorterNum = knownNum;
			largerNum = unknownNum;
		} else {
			shorterNum = unknownNum;
			largerNum = knownNum;
		}
		
		//creates a regex for each char in shortNum which checks if the char occurs in largerNum
		//if so, the counter is incremented by 1
		for (int i = 0; i < shorterNum.length(); i++) {
			String regex = ".*" + shorterNum.charAt(i) + ".*";
			if (largerNum.matches(regex)) {
				counter++;
			}
		}

		if (counter == shorterNum.length()) { //if true, every char in shorterNum appears in largerNum
			matches = true;
		}
		
		return matches;
	}

	// reads initial segments from file in List of Arrays
	private static ArrayList<String[]> readFileValues() throws NumberFormatException, IOException {
		ArrayList<String[]> segment = new ArrayList<String[]>();
		File file = new File("PATH_TO_FILE");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] parts = line.trim().split("\\W+"); // removes leading zeros and splits at every whitespace }
			segment.add(parts);
		}
		br.close();
		return segment;
	}
}
