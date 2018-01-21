package put.io.black.java.core.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MockedScenarioManagerTest {

    @Mock
    private FileManager fileManager;
    private ScenarioManager scenarioManager;

    @Before
    public void setUp() {
        ScenarioManager scenarioManager = new ScenarioManager();
        scenarioManager.setFileManager(this.fileManager);
        this.scenarioManager = scenarioManager;
    }

    @Test
    public void correctReadScenarioTest() {
        String result = "Correct read scenario";
        when(fileManager.readScenario(anyString())).thenReturn(result);

        String title = "MyScenario";
        assertEquals(result, scenarioManager.readScenario(title));
    }

    @Test
    public void scenarioDoesNotExistTest() {
        String fileNotExists = "File not exist.";
        when(fileManager.readScenario(anyString())).thenReturn(fileNotExists);

        String title = "MyScenario";
        assertEquals(fileNotExists, scenarioManager.readScenario(title));
    }

    @Test
    public void canNotReadScenarioTest() {
        String fileNotExists = "File can't be read.";
        when(fileManager.readScenario(anyString())).thenReturn(fileNotExists);

        String title = "MyScenario";
        assertEquals(fileNotExists, scenarioManager.readScenario(title));
    }

    @Test
    public void listSavedScenarioTest() {
        String fileList = "my_scenario\nsecond_scenario_example";
        when(fileManager.listSavedScenario()).thenReturn(fileList);

        String[] expectedList = {"my_scenario", "second_scenario_example"};
        assertArrayEquals(expectedList, scenarioManager.getListScenarioSaved());
    }

    @Test
    public void NoOneScenarioInListTest() {
        String fileList = "\n";
        when(fileManager.listSavedScenario()).thenReturn(fileList);

        String[] expectedList = {};
        assertArrayEquals(expectedList, scenarioManager.getListScenarioSaved());
    }

    @Test
    public void saveScenarioToFileCorrectTest() {
        String fileManagerResponse = "File was saved.";
        ScenarioManager scenarioManager = mock(ScenarioManager.class);
        when(scenarioManager.saveScenarioToFile(anyString())).thenReturn(fileManagerResponse);

        String title = "MyScenario";
        assertEquals(fileManagerResponse, scenarioManager.saveScenarioToFile(title));
    }

    @Test
    public void saveScenarioToFileAlreadyExistTest() {
        String fileManagerResponse = "File already exist.";
        ScenarioManager scenarioManager = mock(ScenarioManager.class);
        when(scenarioManager.saveScenarioToFile(anyString())).thenReturn(fileManagerResponse);

        String title = "MyScenario";
        assertEquals(fileManagerResponse, scenarioManager.saveScenarioToFile(title));
    }

    @Test
    public void saveScenarioToFileCantWriteTest() {
        String fileManagerResponse = "File can't be write.";
        ScenarioManager scenarioManager = mock(ScenarioManager.class);
        when(scenarioManager.saveScenarioToFile(anyString())).thenReturn(fileManagerResponse);

        String title = "MyScenario";
        assertEquals(fileManagerResponse, scenarioManager.saveScenarioToFile(title));
    }

    @Test
    public void countTabSignTest() {
        ScenarioManager scenarioManager = mock(ScenarioManager.class);
        when(scenarioManager.countTabSign(anyString())).thenReturn(2);

        String textToCheck = "\t\tLibrarian prepare list of books.";
        assertEquals(2, scenarioManager.countTabSign(textToCheck));
    }

    @Test
    public void countKeyWordsInScenarioTest() {
        assertEquals(0, scenarioManager.countKeyWordsInScenario());
    }

}
