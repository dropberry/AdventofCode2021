package adventofcode21;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class aoc9 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		ArrayList<String> field = readFileValues();
		int star1 = 0;
		int star2 = 0;
		ArrayList<Character> basin = new ArrayList();
		ArrayList<Integer> basinSizes = new ArrayList();
		for (int i = 0; i < field.size(); i++) {
			for (int j = 0; j < field.get(i).length(); j++) {
				boolean lowpoint = checkForLowpoints(field, i, j); //if true, there is a lowpoint
				if (lowpoint) {
					star1 += field.get(i).charAt(j) + 1 - 48; //-48 converts ascii number in decimal number
					computeStar2(field, i, j, basin);
					basinSizes.add(basin.size()); //stores size of current basin
					basin.clear(); //removes all elements so it's empty for the next basin
				}
			}
		}
		System.out.println(star1);
		basinSizes.sort(Comparator.naturalOrder());
		star2 = basinSizes.get(basinSizes.size() - 1) * basinSizes.get(basinSizes.size() - 2)
				* basinSizes.get(basinSizes.size() - 3);
		System.out.println(star2);
	}

	//counts the size of a basin if lowpoint is detected
	//if neighbour is not a 9 and not out of the field, the function is called recursive for the neighbors
	//already counted fields are marked with '*' and are excluded from counting again
	private static void computeStar2(ArrayList<String> field, int i, int j, ArrayList<Character> basin) {
		ArrayList<String> fieldWithBasins = field; //the field where later '*' mark already counted points
		if (fieldWithBasins.get(i).charAt(j) != '9' && fieldWithBasins.get(i).charAt(j) != '*') {
			basin.add(field.get(i).charAt(j));
			String replace = fieldWithBasins.get(i).substring(0, j) + '*' + fieldWithBasins.get(i).substring(j + 1);
			fieldWithBasins.set(i, replace);
			if ((i == 0 || i == field.size() - 1) && (j == 0 || j == field.get(i).length() - 1)) {
				if (i == 0) {
					computeStar2(fieldWithBasins, i + 1, j, basin);
				} else {
					computeStar2(fieldWithBasins, i - 1, j, basin);
				}

				if (j == 0) {
					computeStar2(fieldWithBasins, i, j + 1, basin);
				} else {
					computeStar2(fieldWithBasins, i, j - 1, basin);
				}
			} else if (i == 0 || j == 0) {
				computeStar2(fieldWithBasins, i + 1, j, basin);
				computeStar2(fieldWithBasins, i, j + 1, basin);
				if (i == 0)
					computeStar2(fieldWithBasins, i, j - 1, basin);
				else if (j == 0)
					computeStar2(fieldWithBasins, i - 1, j, basin);
			} else if (i == field.size() - 1 || j == field.get(i).length() - 1) {
				computeStar2(fieldWithBasins, i - 1, j, basin);
				computeStar2(fieldWithBasins, i, j - 1, basin);
				if (i == field.size() - 1)
					computeStar2(fieldWithBasins, i, j + 1, basin);
				else if (j == field.get(i).length() - 1)
					computeStar2(fieldWithBasins, i + 1, j, basin);
			} else {
				computeStar2(fieldWithBasins, i - 1, j, basin);
				computeStar2(fieldWithBasins, i, j - 1, basin);
				computeStar2(fieldWithBasins, i + 1, j, basin);
				computeStar2(fieldWithBasins, i, j + 1, basin);
			}
		}
	}

	//returns true if the current number is a lowpoint
	private static boolean checkForLowpoints(ArrayList<String> field, int i, int j) {
		char current = field.get(i).charAt(j);
		boolean lowpoint = false;
		if (i == 0 && j == 0) { //left upper corner
			lowpoint = corners(current, field.get(i + 1).charAt(j), field.get(i).charAt(j + 1));
		} else if (i == 0 && j == field.get(i).length() - 1) { //right upper corner
			lowpoint = corners(current, field.get(i + 1).charAt(j), field.get(i).charAt(j - 1));
		} else if (i == field.size() - 1 && j == 0) { //left bottom corner
			lowpoint = corners(current, field.get(i - 1).charAt(j), field.get(i).charAt(j + 1));
		} else if (i == field.size() - 1 && j == field.get(i).length() - 1) { //right bottom corner
			lowpoint = corners(current, field.get(i - 1).charAt(j), field.get(i).charAt(j - 1));
		} else if (i == 0) { //upper rim
			lowpoint = rim(current, field.get(i + 1).charAt(j), field.get(i).charAt(j - 1), field.get(i).charAt(j + 1));
		} else if (i == field.size() - 1) { //bottom rim
			lowpoint = rim(current, field.get(i - 1).charAt(j), field.get(i).charAt(j - 1), field.get(i).charAt(j + 1));
		} else if (j == 0) { //left rim
			lowpoint = rim(current, field.get(i + 1).charAt(j), field.get(i - 1).charAt(j), field.get(i).charAt(j + 1));
		} else if (j == field.get(i).length() - 1) { //right rim
			lowpoint = rim(current, field.get(i + 1).charAt(j), field.get(i).charAt(j - 1), field.get(i - 1).charAt(j));
		} else { //middle area
			if (current < field.get(i).charAt(j - 1) && current < field.get(i).charAt(j + 1)
					&& current < field.get(i - 1).charAt(j) && current < field.get(i + 1).charAt(j))
				lowpoint = true;
		}
		return lowpoint;
	}

	//checks if the neighbors of corner values are higher
	private static boolean corners(char current, char firstNeighbor, char secondNeighbor) {
		if (current < firstNeighbor && current < secondNeighbor)
			return true;
		else
			return false;
	}

	//checks if the neighbors of rim values are higher
	private static boolean rim(char current, char firstNeighbor, char secondNeighbor, char thirdNeighbor) {
		if (current < firstNeighbor && current < secondNeighbor && current < thirdNeighbor)
			return true;
		else
			return false;
	}

	// reads initial list from file
	private static ArrayList<String> readFileValues() throws NumberFormatException, IOException {
		ArrayList<String> field = new ArrayList<String>();
		File file = new File("PATH_TO_FILE");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		while ((line = br.readLine()) != null) {
			field.add(line);
		}
		br.close();
		return field;
	}
}
