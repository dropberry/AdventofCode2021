package adventofcode21;

import java.io.*;
import java.util.ArrayList;

public class aoc4p2 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		ArrayList<String[][]> fields = readFileValues();
		int[] numbers = new int[]{15,62,2,39,49,25,65,28,84,59,75,24,20,76,60,55,17,7,93,69,32,23,44,81,8,67,41,56,43,89,95,97,61,77,64,37,29,10,79,26,51,48,5,86,71,58,78,90,57,82,45,70,11,14,13,50,68,94,99,22,47,12,1,74,18,46,4,6,88,54,83,96,63,66,35,27,36,72,42,98,0,52,40,91,33,21,34,85,3,38,31,92,9,87,19,73,30,16,53,80};
		int number = 0;  //represents number when bingo occurs
		int sum = 0;
		boolean bingo = false;
		String[][] loser = new String[5][5]; //represents bingo which is the last one left
		
		for(int i = 0; i < numbers.length; i++) {
			number = numbers[i];
			for(int j = 0; j < fields.size(); j++) {
				for(int k = 0; k < 5; k++) {
					String row = "";
					String column = "";
					for(int m = 0; m < 5; m++) {
						//marks every field with '*' if it matches the current number
						if(!fields.get(j)[k][m].equals("*") && Integer.valueOf(fields.get(j)[k][m]) == number) {
							fields.get(j)[k][m] = "*";
						}
						//count stars in each row and column
						row += fields.get(j)[k][m];
						column += fields.get(j)[m][k];
					}
					//checks for bingos; if they occcur fields are removed unless it's the last one
					if(row.equals("*****") || column.equals("*****")) {
						if(fields.size() == 1) {
							bingo = true;
							break;
						}
						fields.remove(j);
						j--;
						break;
					}
				}
			}
			//only true if bingo is found in last field
			if(bingo)
				break;
		}
		
		//counts unmarked fields
		loser = fields.get(0);
		for(int k = 0; k < 5; k++) {
			for(int m = 0; m < 5; m++) {
				if(!loser[k][m].equals("*"))
					sum += Integer.valueOf(loser[k][m]);
			}
		}
		int star2 = sum*number;
		System.out.println(star2);
	}

	//read bingo fields in 5x5 arrays, each bingo field is added to ArrayList
		private static ArrayList<String[][]> readFileValues() throws NumberFormatException, IOException {
			ArrayList<String[][]> fields = new ArrayList<String[][]>();
	    	File file = new File("C:\\Users\\Lea\\Downloads\\test.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
	        String line = "";
	        String[][] singleField = new String[5][5]; //represents single bingo fields
	        int k = 0;
	        //loop automatically skips lines between bingo fields because of if-else statement
	        while ((line = br.readLine()) != null) {
	        	if(k > 4) { //when one field is read (aka after 5 lines)
	        		fields.add(singleField);
	        		k=0; //sets counter to 0
	        		singleField = new String[5][5];
	        	} else {
	        		String[] parts = line.trim().split("\\s+"); //removes leading zeros and splits at every whitespace
	        		singleField[k] = parts; //adds new lines to bingo field
	        		k++;
	        	}
	        }
	        fields.add(singleField); //adds last field (with loop above, fields are only added if lines are still read from file)
	        br.close();  
	        return fields;
	    }
}
