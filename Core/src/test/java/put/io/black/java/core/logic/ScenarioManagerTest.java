package put.io.black.java.core.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ScenarioManagerTest {

    private ScenarioManager scenarioManager;
    private String scenarioTextTest =
            "Develop,Boss\n" +
                    "scenario line 1\n" +
                    "IF scenario line 2\n" +
                    "\tline if 1\n" +
                    "\tline if 2\n" +
                    "\tDevelop line if 3\n" +
                    "ELSE scenario line 3\n" +
                    "\tline else 1\n" +
                    "\tIF line else 2\n" +
                    "\t\tline else if 1\n" +
                    "scenario line 4\n" +
                    "FOR EACH scenario line 5\n" +
                    "\tline for each 1\n" +
                    "scenario line 6\n" +
                    "Boss scenario line 7";

    @Before
    public void setUp() throws Exception {
        scenarioManager = new ScenarioManager(scenarioTextTest);
    }

    @Test
    public void actorsAreFindingInHeader() {
        assertEquals(2, scenarioManager.getActors().length);
    }

    @Test
    public void foundActorsAreCorrect() {
        assertEquals("Develop", scenarioManager.getActors()[0]);
        assertEquals("Boss", scenarioManager.getActors()[1]);
    }

    @Test
    public void lineStartFromKeyWordTest() {
        String line = "IF scenario line 2";
        assertTrue(scenarioManager.lineStartFromKeyWord(line));
    }

    @Test
    public void lineStartFromKeyWordWithTabulaturesTest() {
        String line = "\t\t\t\t\tIF scenario line 2";
        assertTrue(scenarioManager.lineStartFromKeyWord(line));
    }

    @Test
    public void lineNoStartFromKeyWordTest() {
        String line = "\tscenario line 2";
        assertFalse(scenarioManager.lineStartFromKeyWord(line));
    }

    @Test
    public void lineContainsTabulature() {
        String line = "\t\t\t\t\tIF scenario line 2";
        assertEquals(5, scenarioManager.countTabSign(line));
    }

    @Test
    public void lineNoContainsTabulature() {
        String line = "IF scenario line 2";
        assertEquals(0, scenarioManager.countTabSign(line));
    }

    @Test
    public void lineIsEmpty() {
        String line = "";
        assertFalse(scenarioManager.lineStartFromKeyWord(line));
        assertEquals(0, scenarioManager.countTabSign(line));
    }

    @Test
    public void scenarioContainSixNodes() {
        assertEquals(7, scenarioManager.getFirstLevelNodes().size());
    }

    @Test
    public void scenarioContainInSecondNodeTwoChildren() {
        assertEquals(3, ((KeyNode) scenarioManager.getFirstLevelNodes().get(1)).getChildrenCount());
    }

    @Test
    public void secondChildOfSecondNodeEquals() {
        String string = "line if 2";
        assertEquals(string, ((KeyNode) scenarioManager.getFirstLevelNodes().get(1)).getChildren().get(1).getLine());
    }

    @Test
    public void secondChildOfSecondNodeHas2NestingLevel() {
        assertEquals(2, ((KeyNode) scenarioManager.getFirstLevelNodes().get(1)).getChildren().get(1).getNestingLevel());
    }

    @Test
    public void scenarioNestingEqualsThree() {
        assertEquals(3, scenarioManager.countScenarioNesting());
    }

    @Test
    public void scenarioDoesHasNotAnyLinesNestingEqualsZero() {
        ScenarioManager scenarioManager = new ScenarioManager("");
        assertEquals(0, scenarioManager.countScenarioNesting());
    }

    @Test
    public void scenarioHasMoreThenOneSteps() {
        assertEquals(14, scenarioManager.countNumberOfScenarioSteps());
    }

    @Test
    public void scenarioHasZeroSteps() {
        ScenarioManager scenarioManager = new ScenarioManager("");
        assertEquals(0, scenarioManager.countNumberOfScenarioSteps());
    }

    @Test
    public void scenarioHasFourKeyWords() {
        assertEquals(4, scenarioManager.countKeyWordsInScenario());
    }

    @Test
    public void scenarioDoesNotHasKeyWords() {
        ScenarioManager scenarioManager = new ScenarioManager("");
        assertEquals(0, scenarioManager.countKeyWordsInScenario());
    }

    @Test
    public void testCutActorLinesFromScenario() {
        String scenarioTextWithoutActorsTest =
                "Develop,Boss\n" +
                        "scenario line 1\n" +
                        "IF scenario line 2\n" +
                        "\tline if 1\n" +
                        "\tline if 2\n" +
                        "ELSE scenario line 3\n" +
                        "\tline else 1\n" +
                        "\tIF line else 2\n" +
                        "\t\tline else if 1\n" +
                        "scenario line 4\n" +
                        "FOR EACH scenario line 5\n" +
                        "\tline for each 1\n" +
                        "scenario line 6";
        assertEquals(scenarioTextWithoutActorsTest, scenarioManager.cutActorsFromScenario());
    }

    @Test
    public void cutActorsReturnEmptyStringIfScenarioContainsOnlyActorLines() {
        String scenario = "Boss\n" +
                "Boss line 1\n" +
                "Boss line 2";
        ScenarioManager scenarioManager = new ScenarioManager(scenario);
        assertEquals("Boss", scenarioManager.cutActorsFromScenario());
    }

    @Test
    public void scenarioHasCorrectNumeration() {
        String scenarioWithCorrectNumeration = "Develop,Boss\n" +
                "1.scenario line 1\n" +
                "2.IF scenario line 2\n" +
                "\t2.1.line if 1\n" +
                "\t2.2.line if 2\n" +
                "\t2.3.Develop line if 3\n" +
                "3.ELSE scenario line 3\n" +
                "\t3.1.line else 1\n" +
                "\t3.2.IF line else 2\n" +
                "\t\t3.2.1.line else if 1\n" +
                "4.scenario line 4\n" +
                "5.FOR EACH scenario line 5\n" +
                "\t5.1.line for each 1\n" +
                "6.scenario line 6\n" +
                "7.Boss scenario line 7";
        assertEquals(scenarioWithCorrectNumeration, scenarioManager.getScenarioWithNumeration());
    }

    @Test
    public void getScenarioEqualsInputStringScenario() {
        assertEquals(scenarioTextTest, scenarioManager.getScenario());
    }

    @Test
    public void pullScenarioOnlyToTwoNestingLevel() {
        String scenarioToLevel2 = "Develop,Boss\n" +
                "scenario line 1\n" +
                "IF scenario line 2\n" +
                "\tline if 1\n" +
                "\tline if 2\n" +
                "\tDevelop line if 3\n" +
                "ELSE scenario line 3\n" +
                "\tline else 1\n" +
                "\tIF line else 2\n" +
                "scenario line 4\n" +
                "FOR EACH scenario line 5\n" +
                "\tline for each 1\n" +
                "scenario line 6\n" +
                "Boss scenario line 7";
        assertEquals(scenarioToLevel2, scenarioManager.getScenario(2));
    }

    @Test
    public void nodesListIsEmpty() {
        scenarioManager = new ScenarioManager("");
        assertEquals(0, scenarioManager.getNodes().size());
    }

    @Test
    public void nodesListIsNotEmpty() {
        assertEquals(scenarioTextTest.split("\n").length - 1, scenarioManager.getNodes().size());
    }

    @Test
    public void visitorVisitNodes() {
        Visitor visitor = new NodeViewer();
        scenarioManager.visit(visitor);
    }

    @Test
    public void mockFileManagerSaveIsSucceedThenSuccess() {
        String text = "test";
        FileManager mock = mock(FileManager.class);
        when(mock.saveScenarioText(text, text)).thenReturn("True");

        assertEquals("True", mock.saveScenarioText(text, text));
        verify(mock, times(1)).saveScenarioText(text, text);
    }

    @Test
    public void mockFileManagerReadIsSucceedThenReturnScenario() {
        String text = "test";
        FileManager mock = mock(FileManager.class);
        when(mock.readScenario(text)).thenReturn(text);

        assertEquals(text, mock.readScenario(text));
        verify(mock, times(1)).readScenario(text);
    }

    @Test
    public void getListSavedScenarioTest() {
        String list = "scenario1.txt\nscenario2.txt\n";
        FileManager mock = mock(FileManager.class);
        when(mock.listSavedScenario()).thenReturn(list);

        String mockReturn = mock.listSavedScenario();

        assertEquals(list, mockReturn);
        verify(mock, times(1)).listSavedScenario();
    }

    @Test
    public void saveFileWithTitle() {
        String title = "title";
        assertEquals(FileManager.FILE_WAS_SAVED, scenarioManager.saveScenarioToFile(title));
        File file = new File(FileManager.PATH + title + ".txt");
        assertTrue(file.exists());

        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void saveFileAlreadyExist() {
        String title = "title2";
        assertEquals(FileManager.FILE_WAS_SAVED, scenarioManager.saveScenarioToFile(title));
        assertEquals(FileManager.FILE_ALREADY_EXIST, scenarioManager.saveScenarioToFile(title));
        File file = new File(FileManager.PATH + title + ".txt");
        assertTrue(file.exists());

        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listIsEmpty() {
        StringBuilder result = new StringBuilder();
        String[] names = scenarioManager.getListScenarioSaved();
        for (String name : names) {
            result.append(name);
        }
        assertEquals("", result.toString());
    }

    @Test
    public void listContainsFiles() {
        String title = "title3";
        scenarioManager.saveScenarioToFile(title);
        StringBuilder result = new StringBuilder();
        String[] names = scenarioManager.getListScenarioSaved();
        for (String name : names) {
            result.append(name);
        }
        File file = new File(FileManager.PATH + title + ".txt");
        assertTrue(file.exists());
        assertTrue(result.toString().contains(title));

        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void scenarioReadTryReadExistFile() {
        String title = "title4";
        scenarioManager.saveScenarioToFile(title);
        File file = new File(FileManager.PATH + title + ".txt");
        assertTrue(file.exists());
        assertEquals(scenarioTextTest, scenarioManager.readScenario(title));

        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void scenarioReadTryReadNotExistFile() {
        String title = "title5";
        scenarioManager.saveScenarioToFile(title);
        File file = new File(FileManager.PATH + title + ".txt");
        assertTrue(file.exists());
        assertEquals(FileManager.FILE_NOT_EXIST, scenarioManager.readScenario(title + "1"));

        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        for (File file : Objects.requireNonNull(new File(FileManager.PATH).listFiles())) {
            System.out.println("Del" + file.toPath().toString());
            Files.delete(file.toPath());
        }
        Files.delete(Paths.get(FileManager.PATH));
    }
}