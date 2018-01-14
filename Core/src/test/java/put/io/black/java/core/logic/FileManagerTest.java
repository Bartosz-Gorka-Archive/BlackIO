package put.io.black.java.core.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    public void fileManagerIsReadyTest() {
        assertTrue(fileManager.isReady());
    }

    @Test
    public void fileManagerSaveTest() {
        String title = "title" + getFileIterator();
        String text = "test";
        assertTrue(fileManager.isReady());
        fileManager.saveScenarioText(title, text);
        assertTrue(new File(fileManager.PATH + title + ".txt").exists());
    }

    @Test
    public void fileExistThenNotOverwrite() {
        String text = "text";
        String title = "title" + getFileIterator();
        String title2 = "title" + (getFileIterator() - 1);
        assertTrue(fileManager.isReady());
        assertTrue(fileManager.saveScenarioText(title, text));
        assertFalse(fileManager.saveScenarioText(title2, text));
    }

    @Test
    public void readExistFile(){
        String title = "title"+getFileIterator();
        String text = "text";
        fileManager.saveScenarioText(title,text);
        assertEquals(text, fileManager.readScenario(title));
    }

    @Test
    public void readNotExistFile(){
        String title = "title"+getFileIterator();
        assertEquals(FileManager.FILE_NOT_EXIST, fileManager.readScenario(title));
    }

    @Test
    public void listHasContainsSaveFiles(){
        String title = "title"+getFileIterator();
        String title2 = "title"+getFileIterator();
        fileManager.saveScenarioText(title,title);
        fileManager.saveScenarioText(title2,title2);
        assertTrue(fileManager.listSavedScenario().contains(title));
        assertTrue(fileManager.listSavedScenario().contains(title2));
    }

    @Test
    public void listIsEmpty(){
        assertEquals("",fileManager.listSavedScenario());
    }

    @After
    public void tearDown() throws Exception {
        for (File file : new File(FileManager.PATH).listFiles()) {
            Files.delete(file.toPath());
        }
        Files.delete(Paths.get(FileManager.PATH));
    }
}