package put.io.black.java;

import java.util.LinkedList;
import java.util.Stack;

public class ScenarioManager {
    String[] keyWords = {"IF", "ELSE", "FOR EACH"};
    String[] actors;
    LinkedList<Node> nodes = new LinkedList<>();
    //structure

    public ScenarioManager(String scenario) {
        //todo build structure and get key words
        String[] scenarioLines = scenario.split("\n");

        pullOutActors(scenarioLines[0]);
        buildTreeStructure(scenarioLines);

    }

    private void pullOutActors(String header) {
        actors = header.split(",");
    }

    //to test public
    public boolean lineStartFromKeyWord(String line) {
        String lineWithoutTabs = line.replace("\t", "");
        String[] words = lineWithoutTabs.split(" ");
        if (!words[0].equals("")) {
            if (keyWords[0].contains(words[0])) {
                return true;
            } else if (keyWords[1].contains(words[0])) {
                return true;
            } else if (keyWords[2].contains(words[0] + " " + words[1])) {
                return true;
            }
        }
        return false;
    }

    public int countTabSign(String line) {
        String[] words = line.split("\t");
        return words.length - 1;
    }


    private void buildTreeStructure(String[] scenarioLines) {
        if (scenarioLines.length > 1) {
            Stack<Node> stack = new Stack<>();
            Node actualNode = new Node(scenarioLines[1], 1);
            nodes.addLast(actualNode);
            for (int i = 2; i < scenarioLines.length; i++) {
                while (stack.size() > countTabSign(scenarioLines[i])){
                    stack.pop();
                    if (stack.size() != 0) {
                        actualNode = stack.peek();
                    }
                }
                Node node = new Node(scenarioLines[i].replace("\t",""), stack.size() + 1);
                if (lineStartFromKeyWord(scenarioLines[i])) {
                    stack.push(node);
                    actualNode = node;
                }

                if (node.getNestingLevel() == 1){
                    nodes.addLast(node);
                }else {
                    actualNode.addChild(node);
                }
            }
        } else {
            System.out.println("Too short scenario");
        }
    }

}
