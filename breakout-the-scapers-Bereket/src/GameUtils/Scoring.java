package GameUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class Scoring {
    private final Path FILE_PATH = Paths.get("Scores.txt");

    // Get the last number in the file (returns 0 if file missing or empty)
    public int readLastNumberFromFile() {
        try {
            if (Files.notExists(FILE_PATH)) {
                Files.createFile(FILE_PATH);
                Files.writeString(FILE_PATH, "0\n");
                return 0;
            }
            List<String> lines = Files.readAllLines(FILE_PATH);
            for (int i = lines.size() - 1; i >= 0; i--) { // iterate backward
                String line = lines.get(i).trim();
                if (!line.isEmpty()) {
                    return Integer.parseInt(line);
                }
            }
            return 0;
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Append new score to the end of the file
    public void checkHighScore(int newScore) {
        try {
            if (Files.notExists(FILE_PATH)) {
                Files.createFile(FILE_PATH);
                Files.writeString(FILE_PATH, "0\n", StandardOpenOption.APPEND);
            }
            Files.writeString(FILE_PATH, newScore + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
