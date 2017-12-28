package put.io.black.java;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScenarioManagerTest {

    private ScenarioManager scenarioManager;
    private String scenarioTextTest = "Develop,Boss\nscenario line 1\nIF scenario line 2\n\tline if 1\n\tline if 2\nELSE scenario line 3\n\tline else 1\nscenario line 4\nFOR EACH scenario line 5\n\tline for each 1\nscenario line 6";


    @Before
    public void setUp() throws Exception {
        scenarioManager = new ScenarioManager(scenarioTextTest);
    }


    @Test
    public void actorsAreFindingInHeader(){
        assertEquals(2,scenarioManager.actors.length);
    }

    @Test
    public void foundActorsAreCorrect(){
        assertEquals("Develop", scenarioManager.actors[0]);
        assertEquals("Boss", scenarioManager.actors[1]);
    }

    @Test
    public void lineStartFromKeyWordTest(){
        String line = "IF scenario line 2";
        assertTrue(scenarioManager.lineStartFromKeyWord(line));
    }

    @Test
    public void lineStartFromKeyWordWithTabulaturesTest(){
        String line = "\t\t\t\t\tIF scenario line 2";
        assertTrue(scenarioManager.lineStartFromKeyWord(line));
    }

    @Test
    public void lineNoStartFromKeyWordTest(){
        String line = "\tscenario line 2";
        assertFalse(scenarioManager.lineStartFromKeyWord(line));
    }

    @Test
    public void lineContainsTabulature(){
        String line = "\t\t\t\t\tIF scenario line 2";
        assertEquals(5, scenarioManager.countTabSign(line));
    }

    @Test
    public void lineNoContainsTabulature(){
        String line = "IF scenario line 2";
        assertEquals(0, scenarioManager.countTabSign(line));
    }

    @Test
    public void lineIsEmpty(){
        String line = "";
        assertFalse(scenarioManager.lineStartFromKeyWord(line));
        assertEquals(0, scenarioManager.countTabSign(line));
    }

    @Test
    public void scenarioContainSixNodes(){
        assertEquals(6, scenarioManager.nodes.size());
    }

    @Test
    public void scenarioContainInSecondNodeTwoChildren(){
        assertEquals(2,scenarioManager.nodes.get(1).getChildrenCount());
    }

    @Test
    public void secondChildOfSecondNodeEquals(){
        String string = "line if 2";
        assertEquals(string, scenarioManager.nodes.get(1).getChildren().get(1).getLine());
    }

}