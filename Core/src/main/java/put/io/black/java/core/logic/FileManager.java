package put.io.black.java.core.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {

    public static final String PATH = "Scenarios/";
    public static final String FILE_NOT_EXIST = "File not exist";

    private boolean ready;

    public FileManager() {
        new File(PATH).mkdir();
        File file = new File(PATH);
        ready = file.exists();
    }

    public boolean saveScenarioText(String title, String scenario) {
        try {
            if (fileExist(title)){
                return false;
            }

            PrintWriter printWriter = new PrintWriter(PATH + title + ".txt");
            printWriter.write(scenario);
            printWriter.flush();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String readScenario(String scenarioName) {
        String scenario = "";
        try {
            if (!fileExist(scenarioName)){
                return FILE_NOT_EXIST;
            }
            scenario = new String(Files.readAllBytes(Paths.get(PATH+scenarioName+".txt")));
        }catch (IOException e) {
            e.printStackTrace();
        }
        return scenario;
    }

    private boolean fileExist(String title){
        for (File file : new File(PATH).listFiles()) {
            if (file.getName().equals(title + ".txt")) {
                return true;
            }
        }
        return false;
    }

    public String listSavedScenario() {
        StringBuilder list = new StringBuilder();
        for (File file : new File(PATH).listFiles()){
            list.append(file.getName()).append("\n");
        }
        return list.toString();
    }

    public boolean isReady() {
        return ready;
    }

}
