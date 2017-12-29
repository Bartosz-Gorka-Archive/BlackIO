package put.io.black.java;

import java.util.LinkedList;
import java.util.Stack;

public class ScenarioManager {
    String[] keyWords = {"IF", "ELSE", "FOR EACH"};
    String[] actors;
    LinkedList<Node> firstLevelNodes = new LinkedList<>();

    //TODO search in the tree

    ScenarioManager(String scenario) {
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
            Stack<Node> stackNestingNode = new Stack<>();
            Node actualFatherNode = new Node(scenarioLines[1], 1);
            firstLevelNodes.addLast(actualFatherNode);
            for (int lineNumber = 2; lineNumber < scenarioLines.length; lineNumber++) {
                while (stackNestingNode.size() > countTabSign(scenarioLines[lineNumber])) {
                    stackNestingNode.pop();
                    if (stackNestingNode.size() != 0) {
                        actualFatherNode = stackNestingNode.peek();
                    }
                }
                Node node = new Node(scenarioLines[lineNumber].replace("\t", ""), stackNestingNode.size() + 1);

                if (node.getNestingLevel() == 1) {
                    firstLevelNodes.addLast(node);
                } else {
                    actualFatherNode.addChild(node);
                }
                if (lineStartFromKeyWord(scenarioLines[lineNumber])) {
                    stackNestingNode.push(node);
                    actualFatherNode = node;
                }
            }
        } else {
            System.out.println("Too short scenario");
        }
    }

    private int searchTheNode(Node node, int maxNestingLevel){
        if (node.getChildrenCount() != 0) {
            for (Node child: node.getChildren()) {
                int level = searchTheNode(child, maxNestingLevel);
                if (level > maxNestingLevel) {
                    maxNestingLevel = level;
                }
            }
            return maxNestingLevel;
        }
        return node.getNestingLevel();
    }

    public int countScenarioNesting() {
        int maxNesting = 1;
        if (firstLevelNodes.size() != 0) {
            for (Node firstLevelNode : firstLevelNodes) {
                if (firstLevelNode.getChildrenCount() != 0) {
                    int level = searchTheNode(firstLevelNode, maxNesting);
                    if (level > maxNesting) {
                        maxNesting = level;
                    }
                }
            }
            return maxNesting;
        }else {
            return -1;
        }
    }

}
