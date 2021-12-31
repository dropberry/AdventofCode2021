package adventofcode21;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Day10 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		ArrayList<String> lines = readFileValues();
		ArrayList<Long> scoresCompletion = new ArrayList<Long>(); //stores completion score of each incomplete line
		int star1 = checksCorruptionAndCalculatesBothScores(lines, scoresCompletion);

		scoresCompletion.sort(Comparator.naturalOrder());
		long star2 = scoresCompletion.get(scoresCompletion.size() / 2);
		
		System.out.println(star1);
		System.out.println(star2);
	}

	private static int checksCorruptionAndCalculatesBothScores(ArrayList<String> lines, ArrayList<Long> scoresCompletion) {
		//points for each illegal chunk
		HashMap<Character, Integer> pointsStarOne = new HashMap<Character, Integer>(); 
		pointsStarOne.put(')', 3);
		pointsStarOne.put(']', 57);
		pointsStarOne.put('}', 1197);
		pointsStarOne.put('>', 25137);
		
		//points for the completion sequence, uses opening chunks because all other correct chunks will be replaced with '*' later
		HashMap<Character, Integer> pointsStarTwo = new HashMap<Character, Integer>();
		pointsStarTwo.put('(', 1);
		pointsStarTwo.put('[', 2);
		pointsStarTwo.put('{', 3);
		pointsStarTwo.put('<', 4);
		
		int scoreCorruption = 0;
		for (int i = 0; i < lines.size(); i++) {
			boolean corrupted = false;
			String currentLine = lines.get(i); //current line stored in own variable because later, correct chunks will be replaced with '*'
			for (int j = 0; j < lines.get(i).length(); j++) {
				//if corruption is detected, line is not examined to the end
				if (corrupted) {
					break;
				}
				char currentChar = lines.get(i).charAt(j);
				//if current char is closening chunk, the line will be search backwards for inherent opening chunk
				if (currentChar == '}' || currentChar == ']' || currentChar == '>' || currentChar == ')') {
					int k = j;
					char opening = ' ';
					if(currentChar == ')') { //only for '()', the chars are not 2 numbers apart in the ascii table
						opening = '(';
					} else {
						opening = (char) (currentChar - 2);
					}
					
					//correct chunks are replaced by with '*'
					//if the char on the left of the current char is not its opening and not a '*', current char is illegal
					while(!corrupted && currentLine.charAt(k) != opening) {
						if(currentLine.charAt(k-1) != '*' && currentLine.charAt(k-1) != opening) {
							corrupted = true;
							scoreCorruption += pointsStarOne.get(currentChar);
						} else if(currentLine.charAt(k-1) == opening) { //replaces chars with '*'
							currentLine = currentLine.substring(0, k-1) + "*" + currentLine.substring(k,j) + "*" + currentLine.substring(j+1);
							break;
						} else if(currentLine.charAt(k-1) == '*') { //as long as left neighbour is '*', the current line is checked for the left neighour of '*'
							k--;
						}
					}
				}
				
				//if line incomplete, its score is computed here and added to scoresCompletion
				if(j == currentLine.length() - 1 && !corrupted) {
					long scoreComplete = 0;
					for(int m = j; m >= 0; m--) {
						if(currentLine.charAt(m) == '(') 
							scoreComplete = scoreComplete * 5 + pointsStarTwo.get(currentLine.charAt(m));
						else if(currentLine.charAt(m) == '[' || currentLine.charAt(m) == '{' || currentLine.charAt(m) == '<') 
							scoreComplete = scoreComplete * 5 + pointsStarTwo.get(currentLine.charAt(m));
					}
					scoresCompletion.add(scoreComplete);
				}
			}
		}
		return scoreCorruption;
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
