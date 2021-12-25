package adventofcode21;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//every method is static because it's used by main, usually those methods would be in another class but are kept in one class here for readability
public class aoc2 {

	public static void main(String[] args) throws NumberFormatException, IOException {
        Map<String, Integer> course = HashMapFromTextFile();
        ArrayList<String[]> newCourse = ArrayListFromTextFile();
        int star1 = star1(course);
        int star2 = star2(newCourse);

        System.out.println(star1);
        System.out.println(star2);
	}
	
	//computes horizontal and depth as written in task
	private static int star2(ArrayList<String[]> newCourse) {
		String direction = "";
		int value = 0;
		int horizontal = 0;
		int aim = 0;
		int depth = 0;
		for(int i = 0; i < newCourse.size(); i++) {
			direction = newCourse.get(i)[0];
			value = Integer.valueOf(newCourse.get(i)[1]);
			if (direction.equals("forward")) {
				horizontal += value;
				depth += aim*value;
			} else if(direction.equals("down")) {
				aim += value;
			} else if(direction.equals("up")) {
				aim -= value;
			}
		}
		return depth*horizontal;
	}
	
	//multiplies final star1 out of horizontal (stored in forward) and depth (which has to be positive)
	private static int star1(Map<String, Integer> course) {
		int result = 0;
		if(course.get("up") > course.get("down")) {
        	result = course.get("forward")*(course.get("up") - course.get("down"));
        }
        else {
        	result = course.get("forward")*(course.get("down") - course.get("up"));
        }
		return result;
	}
	
	//reads values as ArrayList, each entry contains array with direction as first and value as second argument
	private static ArrayList<String[]> ArrayListFromTextFile() throws NumberFormatException, IOException {
		ArrayList<String[]> newCourse = new ArrayList<String[]>();
    	File file = new File("C:\\Users\\Lea\\Downloads\\test.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
  
        while ((line = br.readLine()) != null) {
        	String[] parts = line.split(" ");
        	newCourse.add(parts);
        }
        br.close();  
        return newCourse;
    }

	
	//reads values of each line in HashMap with direction as key and change of position as value
	//source: https://www.geeksforgeeks.org/reading-text-file-into-java-hashmap/
	private static Map<String, Integer> HashMapFromTextFile() throws NumberFormatException, IOException {
		Map<String, Integer> course = new HashMap<String, Integer>();
		course.put("forward", 0);
		course.put("up", 0);
		course.put("down", 0);
    	File file = new File("PATH_TO_FILE");
		BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
  
        while ((line = br.readLine()) != null) {
        	String[] parts = line.split(" ");
        	//trim() removes leading and ending whitespace
            String direction = parts[0].trim();
            Integer value = Integer.valueOf(parts[1].trim());
            course.put(direction, course.get(direction) + value);
        }
        br.close();  
        return course;
    }
}


