package adventofcode21;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//every method is static because it's used by main, usually those methods would be in another class but are kept in one class here for readability
public class aoc1 {

	public static void main(String[] args) throws IOException {
		int[] data = readFileValues();
		int star1 = star1(data);
		int star2 = star2(data);
	    
	    System.out.println(star1);
	    System.out.println(star2);
	}
	
	private static int star1(int[] data) {
		int counter = 0;
		//compares every value in the array with its successor, if values increase counter increases by 1
	    for (int i = 0; i < data.length-1; i++) {
	    	if(data[i] < data[i+1])
    			counter++;
	    }
	    return counter;
	}
	
	private static int star2(int[] data) {
    	int comp1 = 0;
    	int comp2 = 0;
    	int counter = 0;
    	//-3 necessary to not get error message, not needed numbers at the end are not counted automatically
	    for (int i = 0; i < data.length - 3; i++) {
	    	comp1 = data[i] + data[i+1] + data[i+2];
	    	comp2 = data[i+1] + data[i+2] + data[i+3];
	    	if(comp1 < comp2)
    			counter++;
	    }
	    return counter;
	}
	
	private static int[] readFileValues() throws IOException {
		File file = new File("PATH_TO_FILE");
		List<String> lines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(file)); //creates buffering input character stream
	    String values = "";
	    
	    while ((values = br.readLine()) != null) {
	    	lines.add(values);
	    }
	    br.close(); //closes stream, prevents possible I/O errors
	    
	    //parses List<String> to int[] with help of lambda expression
	    //Source: https://stackoverflow.com/questions/6881458/converting-a-string-array-into-an-int-array-in-java
	    int[] data = lines.stream().mapToInt(Integer::valueOf).toArray();
	    return data;
	}
}
