package put.io.black.java.core.rest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ScenarioControllerTest {

    private ScenarioController scenarioController;
    private String messageToAPI =
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
    public void setUp() {
        scenarioController = new ScenarioController();
    }

    @Test
    public void getScenarioTest(){
        assertEquals(messageToAPI, scenarioController.getScenario(messageToAPI));
    }

    @Test
    public void getScenarioWithNumericTest(){
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
        assertEquals(scenarioWithCorrectNumeration, scenarioController.getScenarioWithNumeric(messageToAPI));
    }

    @Test
    public void getScenarioWithoutActorsTest(){
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
        assertEquals(scenarioTextWithoutActorsTest, scenarioController.getScenarioWithoutActors(messageToAPI));
    }

    @Test
    public void getScenarioNumberKeyWordsTest(){
        assertEquals(4, Integer.parseInt(scenarioController.getScenarioNumberKeyWords(messageToAPI)));
    }

    @Test
    public void getScenarioStepsTest(){
        assertEquals(14, Integer.parseInt(scenarioController.getScenarioSteps(messageToAPI)));
    }

    @Test
    public void getScenarioNestingTest(){
        assertEquals(3, Integer.parseInt(scenarioController.getScenarioNesting(messageToAPI)));
    }

    @Test
    public void getScenarioToLevel2Test(){
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
        String toLevel = "2";
        assertEquals(scenarioToLevel2, scenarioController.getScenarioToLevel(messageToAPI, toLevel));
    }

    @Test
    public void getScenarioToLevelIsNotNumberTest(){
        String toLevel = "I am not number!";
        String errorMessage = "Nesting level is not a integer!";
        assertEquals(errorMessage, scenarioController.getScenarioToLevel(messageToAPI, toLevel));
    }

}