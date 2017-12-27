package put.io.black.java;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScenarioManagerTest {

    private ScenarioManager scenarioManager;
    private String scenarioTextTest = "Develop,Boss\nscenario line 1\nscenario line 2";


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

}