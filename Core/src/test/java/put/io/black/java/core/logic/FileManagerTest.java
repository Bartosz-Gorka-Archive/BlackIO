package put.io.black.java.core.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import static org.junit.Assert.*;

public class FileManagerTest {

    private FileManager fileManager;
    private int fileIterator = 0;

    private int getFileIterator() {
        fileIterator++;
        return fileIterator;
    }

    @Before
    public void setUp() throws Exception {
        fileManager = new FileManager();
    }

    @Test
    public void fileManagerSaveTest() {
        String title = "title" + getFileIterator();
        String text = "test";
        fileManager.saveScenarioText(title, text);
        assertTrue(new File(fileManager.dirPath + title + ".txt").exists());
    }

    @Test
    public void fileExistThenNotOverwrite() {
        String text = "text";
        String title = "title" + getFileIterator();
        String title2 = "title" + (getFileIterator() - 1);
        assertEquals("File was saved.", fileManager.saveScenarioText(title, text));
        assertEquals("File already exist.", fileManager.saveScenarioText(title2, text));
    }

    @Test
    public void readExistFile() {
        String title = "title" + getFileIterator();
        String text = "text";
        fileManager.saveScenarioText(title, text);
        assertEquals(text, fileManager.readScenario(title));
    }

    @Test
    public void readNotExistFile() {
        String title = "title" + getFileIterator();
        assertEquals("File not exist.", fileManager.readScenario(title));
    }

    @Test
    public void listHasContainsSaveFiles() {
        String title = "title" + getFileIterator();
        String title2 = "title" + getFileIterator();
        fileManager.saveScenarioText(title, title);
        fileManager.saveScenarioText(title2, title2);
        assertTrue(fileManager.listSavedScenario().contains(title));
        assertTrue(fileManager.listSavedScenario().contains(title2));
        assertFalse(fileManager.listSavedScenario().contains(".txt"));
    }

    @Test
    public void listIsEmpty() {
        assertEquals("", fileManager.listSavedScenario());
    }

    @After
    public void tearDown() throws Exception {
        for (File file : Objects.requireNonNull(new File(FileManager.dirPath).listFiles())) {
            try {
                Files.delete(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}