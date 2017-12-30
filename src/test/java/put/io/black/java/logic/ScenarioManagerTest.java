package put.io.black.java.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
    private String scenarioTextWithoutActorsTest =
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
            "scenario line 6\n";


    @Before
    public void setUp() throws Exception {
        scenarioManager = new ScenarioManager(scenarioTextTest);
    }


    @Test
    public void actorsAreFindingInHeader() {
        assertEquals(2, scenarioManager.actors.length);
    }

    @Test
    public void foundActorsAreCorrect() {
        assertEquals("Develop", scenarioManager.actors[0]);
        assertEquals("Boss", scenarioManager.actors[1]);
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
        assertEquals(7, scenarioManager.firstLevelNodes.size());
    }

    @Test
    public void scenarioContainInSecondNodeTwoChildren() {
        assertEquals(3, scenarioManager.firstLevelNodes.get(1).getChildrenCount());
    }

    @Test
    public void secondChildOfSecondNodeEquals() {
        String string = "line if 2";
        assertEquals(string, scenarioManager.firstLevelNodes.get(1).getChildren().get(1).getLine());
    }

    @Test
    public void secondChildOfSecondNodeHas2NestingLevel() {
        assertEquals(2, scenarioManager.firstLevelNodes.get(1).getChildren().get(1).getNestingLevel());
    }

    @Test
    public void scenarioNestingEqualsThree(){
        assertEquals(3, scenarioManager.countScenarioNesting());
    }

    @Test
    public void scenarioDoesHasNotAnyLinesNestingEqualsZero(){
        ScenarioManager scenarioManager = new ScenarioManager("");
        assertEquals(0, scenarioManager.countScenarioNesting());
    }
    @Test
    public void scenarioHasTwelveSteps(){
        assertEquals(14, scenarioManager.countNumberOfScenarioSteps());
    }

    @Test
    public void scenarioHasZeroSteps(){
        ScenarioManager scenarioManager = new ScenarioManager("");
        assertEquals(0, scenarioManager.countNumberOfScenarioSteps());
    }

    @Test
    public void scenarioHasFourKeyWords(){
        assertEquals(4, scenarioManager.countKeyWordsInScenario());
    }

    @Test
    public void scenarioDoesNotHasKeyWords(){
        ScenarioManager scenarioManager = new ScenarioManager("");
        assertEquals(0, scenarioManager.countKeyWordsInScenario());
    }

    @Test
    public void testCutActorLinesFromScenario(){
        assertEquals(scenarioTextWithoutActorsTest, scenarioManager.cutActorsFromScenario());
    }

    @Test
    public void cutActorsReturnEmptyStringIfScenarioContainsOnlyActorLines(){
        String scenario = "Boss\n" +
                "Boss line 1\n" +
                "Boss line 2\n";
        ScenarioManager scenarioManager = new ScenarioManager(scenario);
        assertEquals("Boss\n", scenarioManager.cutActorsFromScenario());
    }


}