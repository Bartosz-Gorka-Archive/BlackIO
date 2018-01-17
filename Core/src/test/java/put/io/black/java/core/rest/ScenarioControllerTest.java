package put.io.black.java.core.rest;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import put.io.black.java.core.logic.FileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.*;

public class ScenarioControllerTest {

    private ScenarioController scenarioController;
    private String messageToAPI =
            "{\"level\": 2, \"title\": \"my scenario\", " + "\"scenario\": \"Develop,Boss\n" +
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
                    "Boss scenario line 7\"}";

    @Before
    public void setUp() {
        scenarioController = new ScenarioController();
    }

    @Test
    public void getScenarioTest() {
        String expectedResult = "{\"status\":\"success\",\"result\":\"Develop,Boss\\n" +
                "scenario line 1\\n" +
                "IF scenario line 2\\n" +
                "\\tline if 1\\n" +
                "\\tline if 2\\n" +
                "\\tDevelop line if 3\\n" +
                "ELSE scenario line 3\\n" +
                "\\tline else 1\\n" +
                "\\tIF line else 2\\n" +
                "\\t\\tline else if 1\\n" +
                "scenario line 4\\n" +
                "FOR EACH scenario line 5\\n" +
                "\\tline for each 1\\n" +
                "scenario line 6\\n" +
                "Boss scenario line 7\"}";
        assertEquals(expectedResult, scenarioController.getScenario(messageToAPI));
    }

    @Test
    public void getScenarioWithNumericTest() {
        String expectedResult = "{\"status\":\"success\",\"result\":\"Develop,Boss\\n" +
                "1.scenario line 1\\n" +
                "2.IF scenario line 2\\n" +
                "\\t2.1.line if 1\\n" +
                "\\t2.2.line if 2\\n" +
                "\\t2.3.Develop line if 3\\n" +
                "3.ELSE scenario line 3\\n" +
                "\\t3.1.line else 1\\n" +
                "\\t3.2.IF line else 2\\n" +
                "\\t\\t3.2.1.line else if 1\\n" +
                "4.scenario line 4\\n" +
                "5.FOR EACH scenario line 5\\n" +
                "\\t5.1.line for each 1\\n" +
                "6.scenario line 6\\n" +
                "7.Boss scenario line 7\"}";
        assertEquals(expectedResult, scenarioController.getScenarioWithNumeric(messageToAPI));
    }

    @Test
    public void getScenarioWithoutActorsTest() {
        String expectedResult = "{\"status\":\"success\",\"result\":\"Develop,Boss\\n" +
                "scenario line 1\\n" +
                "IF scenario line 2\\n" +
                "\\tline if 1\\n" +
                "\\tline if 2\\n" +
                "ELSE scenario line 3\\n" +
                "\\tline else 1\\n" +
                "\\tIF line else 2\\n" +
                "\\t\\tline else if 1\\n" +
                "scenario line 4\\n" +
                "FOR EACH scenario line 5\\n" +
                "\\tline for each 1\\n" +
                "scenario line 6\"}";
        assertEquals(expectedResult, scenarioController.getScenarioWithoutActors(messageToAPI));
    }

    @Test
    public void getScenarioNumberKeyWordsTest() {
        String expectedResult = "{\"status\":\"success\",\"result\":4}";
        assertEquals(expectedResult, scenarioController.getScenarioNumberKeyWords(messageToAPI));
    }

    @Test
    public void getScenarioStepsTest() {
        String expectedResult = "{\"status\":\"success\",\"result\":14}";
        assertEquals(expectedResult, scenarioController.getScenarioSteps(messageToAPI));
    }

    @Test
    public void getScenarioNestingTest() {
        String expectedResult = "{\"status\":\"success\",\"result\":3}";
        assertEquals(expectedResult, scenarioController.getScenarioNesting(messageToAPI));
    }

    @Test
    public void getScenarioToLevel2Test() {
        String expectedResult = "{\"status\":\"success\",\"result\":\"Develop,Boss\\n" +
                "scenario line 1\\n" +
                "IF scenario line 2\\n" +
                "\\tline if 1\\n" +
                "\\tline if 2\\n" +
                "\\tDevelop line if 3\\n" +
                "ELSE scenario line 3\\n" +
                "\\tline else 1\\n" +
                "\\tIF line else 2\\n" +
                "scenario line 4\\n" +
                "FOR EACH scenario line 5\\n" +
                "\\tline for each 1\\n" +
                "scenario line 6\\n" +
                "Boss scenario line 7\"}";
        assertEquals(expectedResult, scenarioController.getScenarioToLevel(messageToAPI));
    }

    @Test
    public void missingBody() {
        String request = "{\"level\": 2, \"title\": \"my scenario\", " + "\"NOT_SCENARIO\": \"Develop,Boss\n" +
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
                "Boss scenario line 7\"}";
        String expectedResult = "{\"status\":\"error\",\"message\":\"Missing scenario field in body.\"}";
        assertEquals(expectedResult, scenarioController.getScenarioToLevel(request));
    }

    @Test
    public void saveScenarioTest() throws IOException {
        String expectedResult = "{\"status\":\"success\",\"result\":\"File was saved.\"}";
        assertEquals(expectedResult, scenarioController.saveScenario(messageToAPI));

        // Clear
        for (File file : Objects.requireNonNull(new File(FileManager.PATH).listFiles())) {
            Files.delete(file.toPath());
        }
    }

    @Test
    public void saveScenarioExistsFileTest() throws IOException {
        String expectedResult = "{\"status\":\"success\",\"result\":\"File was saved.\"}";
        assertEquals(expectedResult, scenarioController.saveScenario(messageToAPI));

        expectedResult = "{\"status\":\"error\",\"message\":\"File already exist.\"}";
        assertEquals(expectedResult, scenarioController.saveScenario(messageToAPI));

        // Clear
        for (File file : Objects.requireNonNull(new File(FileManager.PATH).listFiles())) {
            Files.delete(file.toPath());
        }
    }
}