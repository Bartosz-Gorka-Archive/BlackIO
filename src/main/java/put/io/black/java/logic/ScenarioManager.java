package put.io.black.java.logic;

import java.util.LinkedList;
import java.util.Stack;

public class ScenarioManager {
    String[] keyWords = {"IF", "ELSE", "FOR EACH"};
    String[] actors;
    LinkedList<Node> firstLevelNodes = new LinkedList<>();


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

    public int countScenarioNesting() {
        int maxNesting = 1;
        if (firstLevelNodes.size() != 0) {
            for (Node firstLevelNode : firstLevelNodes) {
                if (firstLevelNode.getChildrenCount() != 0) {
                    int level = searchTheNestingNode(firstLevelNode, maxNesting);
                    if (level > maxNesting) {
                        maxNesting = level;
                    }
                }
            }
            return maxNesting;
        } else {
            return 0;
        }
    }

    private int searchTheNestingNode(Node node, int maxNestingLevel) {
        if (node.getChildrenCount() != 0) {
            for (Node child : node.getChildren()) {
                int level = searchTheNestingNode(child, maxNestingLevel);
                if (level > maxNestingLevel) {
                    maxNestingLevel = level;
                }
            }
            return maxNestingLevel;
        }
        return node.getNestingLevel();
    }

    public int countNumberOfScenarioSteps() {
        int scenarioSteps = 0;
        if (firstLevelNodes.size() != 0) {
            for (Node firstLevelNode : firstLevelNodes) {
                scenarioSteps++;
                if (firstLevelNode.getChildrenCount() != 0) {
                    scenarioSteps = searchTheNode(firstLevelNode, scenarioSteps);
                }
            }
            return scenarioSteps;
        } else {
            return 0;
        }
    }

    private int searchTheNode(Node node, int scenarioSteps) {
        if (node.getChildrenCount() != 0) {
            for (Node child : node.getChildren()) {
                scenarioSteps = searchTheNode(child, ++scenarioSteps);
            }
        }
        return scenarioSteps;
    }

    public int countKeyWordsInScenario() {
        int keyWordsInScenario = 0;
        if (firstLevelNodes.size() != 0) {
            for (Node firstLevelNode : firstLevelNodes) {
                if (firstLevelNode.getChildrenCount() != 0) {
                    keyWordsInScenario = searchTheKeyWordsNode(firstLevelNode, keyWordsInScenario);
                }
            }
            return keyWordsInScenario;
        } else {
            return 0;
        }
    }

    private int searchTheKeyWordsNode(Node node, int keyWordsInScenario) {
        if (node.getChildrenCount() != 0) {
            for (Node child : node.getChildren()) {
                keyWordsInScenario = searchTheNode(child, keyWordsInScenario);
            }
        }
        if (lineStartFromKeyWord(node.getLine())) {
            keyWordsInScenario++;
        }
        return keyWordsInScenario;
    }

    public String cutActorsFromScenario() {
        LinkedList<String> scenarioWithoutActors = new LinkedList<>();
        scenarioWithoutActors.addLast("");
        for (Node firstLevelNode : firstLevelNodes) {
            if (firstLevelNode.getChildrenCount() != 0) {
                searchLineWithoutActors(firstLevelNode, scenarioWithoutActors);
            } else if (!lineStartFromActor(firstLevelNode.getLine())){
                String line = makeTabulaturePrefix(firstLevelNode.getNestingLevel())+firstLevelNode.getLine()+"\n";
                scenarioWithoutActors.addLast(line);
            }

        }
        return changeLinkedListToString(scenarioWithoutActors);
    }

    private void searchLineWithoutActors(Node node, LinkedList<String> scenarioWithoutActors) {
        if (!lineStartFromActor(node.getLine())) {
            String line = makeTabulaturePrefix(node.getNestingLevel())+node.getLine()+"\n";
            scenarioWithoutActors.addLast(line);
        }
        if (node.getChildrenCount() != 0) {
            for (Node child : node.getChildren()) {
                searchLineWithoutActors(child, scenarioWithoutActors);
            }
        }
    }

    private boolean lineStartFromActor(String line){
        String[] words = line.split(" ");
        for (String actor: actors) {
            if (words[0].equals(actor)){
                return true;
            }
        }
        return false;
    }

    private String makeTabulaturePrefix(int nestingLevel){
        String prefix = "";
        for (int i = 1; i<nestingLevel; i++){
            prefix+="\t";
        }
        return prefix;
    }

    private String changeLinkedListToString(LinkedList<String> scenario){
        String header = addActorsHeader();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(header);
        for (String line : scenario){
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    private String addActorsHeader(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String actor : actors){
            stringBuilder.append(actor);
            if (!actor.equals(actors[actors.length-1])){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public String getScenarioWithNumeration() {
        LinkedList<String> scenarioWithNumeration = new LinkedList<>();
        scenarioWithNumeration.addLast("");
        for (int i = 0; i< firstLevelNodes.size(); i++) {
            if (firstLevelNodes.get(i).getChildrenCount() != 0) {
                addLineWithNumeration(firstLevelNodes.get(i),scenarioWithNumeration, (i+1)+".");
            }else {
                String line = makeTabulaturePrefix(firstLevelNodes.get(i).getNestingLevel())+(i+1)+"."+firstLevelNodes.get(i).getLine()+"\n";
                scenarioWithNumeration.addLast(line);
            }

        }
        return changeLinkedListToString(scenarioWithNumeration);
    }

    private void addLineWithNumeration(Node node, LinkedList<String> scenarioWithNumeration, String prefix) {
        String line = makeTabulaturePrefix(node.getNestingLevel())+prefix+node.getLine()+"\n";
        scenarioWithNumeration.addLast(line);
        if (node.getChildrenCount() != 0) {
            for (int i=0; i<node.getChildrenCount(); i++){
                addLineWithNumeration(node.getChildren().get(i),scenarioWithNumeration, prefix+(i+1)+".");
            }
        }
    }


    //TODO Pobranie scenariusz do określonego poziomu (wszystko do określonego poziomu, z tym poziomem)

}
