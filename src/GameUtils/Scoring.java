package GameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.List;


public class Scoring {
    private final static String FILE_NAME = "Scores.txt";

    
    
    public static Integer readLastNumberFromFile()  {
    	int n = 1;
    	
        LinkedList<String> lines = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                if (lines.size() > n) {
                    lines.removeFirst();
                }
            }
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return Integer.parseInt(lines.getLast());
    }
    
    
    
    // Get the last number in the file (returns 0 if file missing or empty)
//    public int readLastNumberFromFile() {
//        try {
//            if (Files.notExists(FILE_PATH)) {
//                Files.createFile(FILE_PATH);
//                Files.writeString(FILE_PATH, "0\n");
//                return 0;
//            }
//            List<String> lines = Files.readAllLines(FILE_PATH);
//            for (int i = lines.size() - 1; i >= 0; i--) { // iterate backward
//                String line = lines.get(i).trim();
//                if (!line.isEmpty()) {
//                    return Integer.parseInt(line);
//                }
//            }
//            return 0;
//        } catch (IOException | NumberFormatException e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }

    public void checkHighScore(int newScore) {
    	int oldScore = readLastNumberFromFile();
    	System.out.println(oldScore + " Is the Old Score");
    	System.out.println(newScore + " Is the New Score");
    	
    	if (newScore > oldScore) {
    		try (FileWriter writer = new FileWriter(FILE_NAME)) {
                writer.write("\n" + newScore);
            } catch (IOException e) {
  
            }
    	}
        
    }
}
