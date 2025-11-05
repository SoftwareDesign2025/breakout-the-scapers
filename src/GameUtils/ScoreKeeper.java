package GameUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;




// This class is in charge of maintaining and updating the high scores from the player when the game ends
public class ScoreKeeper {
    private final static String FILE_NAME = "Scores.txt";

    private List<String> gameList = List.of("breakout", "galaga");
    
    
    
    
    
    // reads the last high score saved inside of the Scores.txt file 
    public int readLastNumberFromFile(String gamename)  {

    	
    	int nScores = 1;
    	
        LinkedList<String> lines = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(gamename+FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                if (lines.size() > nScores) {
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
    
    

    // takes in the players end game score after winning/losing and compares it to the high score saved in Scores.txt and updates accordingly
    public void checkHighScore(int newScore, String gamename) {
    	int oldScore = readLastNumberFromFile(gamename);
//    	System.out.println(oldScore + " Is the Old Score");
//    	System.out.println(newScore + " Is the New Score");
    	
    	if (newScore > oldScore) {
    		try (FileWriter writer = new FileWriter(gamename+FILE_NAME)) {
                writer.write("\n" + newScore);
            } catch (IOException e) {
  
            }
    	}
        
    }
}
