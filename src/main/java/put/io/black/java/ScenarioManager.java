package put.io.black.java;

import java.util.LinkedList;

public class ScenarioManager {
    private String[] keyWords = {"IF","ELSE","FOR EACH"};
    private String[] actors;
    private LinkedList<Node> nodes = new LinkedList<>();
    //structure

    public ScenarioManager(String scenario){
        //todo build structure and get key words
        String[] scenarioLines = scenario.split("\n");
        pullOutActors(scenarioLines[0]);
    }

    private void pullOutActors(String header){
        actors = header.split(",");
    }

}
