package put.io.black.java.core.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * File manager in API
 */
public class FileManager {
    /**
     * Scenarios directory path
     */
    public static final String dirPath = "scenarios/";

    /**
     * FileManager constructor.
     * Check exists directory for scenarios (create if not exists).
     */
    public FileManager() {
        if (!new File(dirPath).exists()) {
            new File(dirPath).mkdir();
        }
    }

    /**
     * Save scenario to file
     * @param title Scenario title
     * @param scenario Scenario text
     * @return Message about action
     */
    public String saveScenarioText(String title, String scenario) {
        try {
            if (fileExist(title)){
                return "File already exist.";
            }

            PrintWriter printWriter = new PrintWriter(dirPath + title + ".txt");
            printWriter.write(scenario);
            printWriter.flush();
            return "File was saved.";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "File can't be write.";
    }

    /**
     * Read scenario from file
     * @param scenarioName Scenario title to read
     * @return Scenario text or message about error
     */
    public String readScenario(String scenarioName) {
        try {
            if (!fileExist(scenarioName)){
                return "File not exist.";
            }
            return new String(Files.readAllBytes(Paths.get(dirPath + scenarioName + ".txt")));
        }catch (IOException e) {
            e.printStackTrace();
        }
        return "File can't be read.";
    }

    /**
     * Check file exists in directory
     * @param title Scenario title to check file
     * @return Exists status
     */
    private boolean fileExist(String title){
        return new File(dirPath + title + ".txt").exists();
    }

    /**
     * Listing saved scenario in API
     * @return List with titles separated new line character
     */
    public String listSavedScenario() {
        StringBuilder list = new StringBuilder();
        for (File file : Objects.requireNonNull(new File(dirPath).listFiles())){
            String name = file.getName().substring(0, file.getName().length() - 4);
            list.append(name).append("\n");
        }
        return list.toString();
    }
}
