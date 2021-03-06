package adventofcode21;

import java.io.*;
import java.util.ArrayList;

public class aoc5 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		ArrayList<Integer[]> lines = ArrayListFromTextFile();
		int[][] coordinatefield = initialiseField();
		insertlines(coordinatefield, lines, false);
		int star1 = overlappingLines(coordinatefield);
		System.out.println(star1);
		
		//creates new field for star2
		coordinatefield = initialiseField();
		insertlines(coordinatefield, lines, true);
		int star2 = overlappingLines(coordinatefield);
		System.out.println(star2);
		
	}

	//Initializes coordinate field with 0s so that points can increase when a line crosses points
	private static int[][] initialiseField() {
		int[][] field = new int[1000][1000];
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field.length; j++) {
				field[i][j] = 0;
			}
		}
		return field;
	}

	//if line crosses point in the coordinate field, value of point increases by 1
	//goes through every case how line is drawn (from up to down, down to up, etc.)
	private static void insertlines(int[][] coordinatefield, ArrayList<Integer[]> lines, boolean findStar2) {
		for (int i = 0; i < lines.size(); i++) {
			int x1 = lines.get(i)[0];
			int y1 = lines.get(i)[1];
			int x2 = lines.get(i)[2];
			int y2 = lines.get(i)[3];
			if (x1 != x2 && y1 == y2) {
				if (x1 < x2) {
					for (int j = x1; j <= x2; j++)
						coordinatefield[y1][j]++;
				} else {
					for (int j = x2; j <= x1; j++)
						coordinatefield[y1][j]++;
				}
			} else if (y1 != y2 && x1 == x2) {
				if (y1 < y2) {
					for (int j = y1; j <= y2; j++)
						coordinatefield[j][x1]++;
				} else {
					for (int j = y2; j <= y1; j++)
						coordinatefield[j][x1]++;
				}
			} else if ((y1 != y2 && x1 != x2) && findStar2) {//only star2 inserts diagonal lines
				if (y1 < y2 && x1 < x2) {
					for (int j = y1; j <= y2; j++) {
						coordinatefield[j][x1]++;
						x1++;
					}
				} else if (y1 < y2 && x1 > x2){
					for (int j = y1; j <= y2; j++) {
							coordinatefield[j][x1]++;
							x1--;
					}
				}else if (y1 > y2 && x1 > x2){
					for (int j = y2; j <= y1; j++) {
						coordinatefield[j][x2]++;
						x2++;
					}
				} else if (y1 > y2 && x1 < x2){
					for (int j = y2; j <= y1; j++) {
						coordinatefield[j][x2]++;
						x2--;
					}
				}
			}
		}
	}
	
	//counts overlapping lines
	private static int overlappingLines(int[][] coordinatefield) {
		int star1 = 0;
		for (int i = 0; i < coordinatefield.length; i++) {
			for (int j = 0; j < coordinatefield.length; j++) {
				if (coordinatefield[i][j] > 1)
					star1++;
			}
		}
		return star1;
	}

	//reads coordinates from files
	private static ArrayList<Integer[]> ArrayListFromTextFile() throws NumberFormatException, IOException {
		ArrayList<Integer[]> ventlines = new ArrayList<Integer[]>();
		File file = new File("PATH_TO_FILE");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";

		while ((line = br.readLine()) != null) {
			Integer[] lineValues = new Integer[] { 0, 0, 0, 0 }; // {x1, y1, x2, y2}
			String[] parts = line.split("\\D+"); // splits at every non-digit
			for (int i = 0; i < parts.length; i++) {
				lineValues[i] = Integer.parseInt(parts[i]);
			}
			ventlines.add(lineValues);
		}
		br.close();
		return ventlines;
	}

}
