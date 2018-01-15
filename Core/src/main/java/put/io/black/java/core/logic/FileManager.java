package put.io.black.java.core.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class FileManager {

    public static final String PATH = "Scenarios/";
    public static final String ERROR_READ_FILE = "File can't be read.";
    public static final String ERROR_WRITE_FILE = "File can't be write.";
    public static final String FILE_ALREADY_EXIST = "File already exist.";
    public static final String FILE_NOT_EXIST = "File not exist.";
    public static final String FILE_WAS_SAVED = "File was saved.";

    public FileManager() {
        if (!new File(PATH).exists()) {
            new File(PATH).mkdir();
        }
    }

    public String saveScenarioText(String title, String scenario) {
        try {
            if (fileExist(title)){
                return FILE_ALREADY_EXIST;
            }

            PrintWriter printWriter = new PrintWriter(PATH + title + ".txt");
            printWriter.write(scenario);
            printWriter.flush();
            return FILE_WAS_SAVED;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ERROR_WRITE_FILE;
    }

    public String readScenario(String scenarioName) {
        try {
            if (!fileExist(scenarioName)){
                return FILE_NOT_EXIST;
            }
            return new String(Files.readAllBytes(Paths.get(PATH+scenarioName+".txt")));
        }catch (IOException e) {
            e.printStackTrace();
        }
        return ERROR_READ_FILE;
    }

    private boolean fileExist(String title){
        return new File(PATH+title+".txt").exists();
    }

    public String listSavedScenario() {
        StringBuilder list = new StringBuilder();
        for (File file : Objects.requireNonNull(new File(PATH).listFiles())){
            String name = file.getName().substring(0, file.getName().length()-4);
            list.append(name).append("\n");
        }
        return list.toString();
    }
}
