package put.io.black.java.logic;

import java.util.LinkedList;
import java.util.Stack;

public class ScenarioManager {
    private String[] keyWords = {"IF", "ELSE", "FOR EACH"};
    private String[] actors;
    private LinkedList<Node> firstLevelNodes = new LinkedList<>();
    private LinkedList<Visitable> nodes = new LinkedList<>();

    public ScenarioManager(String scenario) {
        String[] scenarioLines = scenario.split("\n");

        pullOutActors(scenarioLines[0]);
        buildTreeStructure(scenarioLines);
    }

    private void pullOutActors(String header) {
        actors = header.split(",");
    }

    public boolean lineStartFromKeyWord(String line) {
        String lineWithoutTabs = line.replace("\t", "");
        for (String keyWord : keyWords) {
            if (lineWithoutTabs.matches(keyWord + "(.*)")) {
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
            nodes.addLast(actualFatherNode);
            getFirstLevelNodes().addLast(actualFatherNode);
            for (int lineNumber = 2; lineNumber < scenarioLines.length; lineNumber++) {
                while (stackNestingNode.size() > countTabSign(scenarioLines[lineNumber])) {
                    stackNestingNode.pop();
                    if (stackNestingNode.size() != 0) {
                        actualFatherNode = stackNestingNode.peek();
                    }
                }
                Node node = new Node(scenarioLines[lineNumber].replace("\t", ""), stackNestingNode.size() + 1);
                nodes.addLast(node);
                if (node.getNestingLevel() == 1) {
                    getFirstLevelNodes().addLast(node);
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
        if (getFirstLevelNodes().size() != 0) {
            for (Node firstLevelNode : getFirstLevelNodes()) {
                if (firstLevelNode.hasChildren()) {
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
        if (node.hasChildren()) {
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
        if (getFirstLevelNodes().size() != 0) {
            for (Node firstLevelNode : getFirstLevelNodes()) {
                scenarioSteps++;
                if (firstLevelNode.hasChildren()) {
                    scenarioSteps = searchTheNode(firstLevelNode, scenarioSteps);
                }
            }
            return scenarioSteps;
        } else {
            return 0;
        }
    }

    private int searchTheNode(Node node, int scenarioSteps) {
        if (node.hasChildren()) {
            for (Node child : node.getChildren()) {
                scenarioSteps = searchTheNode(child, ++scenarioSteps);
            }
        }
        return scenarioSteps;
    }

    public int countKeyWordsInScenario() {
        int keyWordsInScenario = 0;
        if (getFirstLevelNodes().size() != 0) {
            for (Node firstLevelNode : getFirstLevelNodes()) {
                if (firstLevelNode.hasChildren()) {
                    keyWordsInScenario = searchTheKeyWordsNode(firstLevelNode, keyWordsInScenario);
                }
            }
        }
        return keyWordsInScenario;
    }

    private int searchTheKeyWordsNode(Node node, int keyWordsInScenario) {
        if (node.hasChildren()) {
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
        for (Node firstLevelNode : getFirstLevelNodes()) {
            if (firstLevelNode.hasChildren()) {
                searchLineWithoutActors(firstLevelNode, scenarioWithoutActors);
            } else if (lineNotStartFromActor(firstLevelNode.getLine())) {
                String line = makeTabulaturePrefix(firstLevelNode.getNestingLevel()) + firstLevelNode.getLine() + "\n";
                scenarioWithoutActors.addLast(line);
            }
        }
        return changeLinkedListToString(scenarioWithoutActors);
    }

    private void searchLineWithoutActors(Node node, LinkedList<String> scenarioWithoutActors) {
        if (lineNotStartFromActor(node.getLine())) {
            String line = makeTabulaturePrefix(node.getNestingLevel()) + node.getLine() + "\n";
            scenarioWithoutActors.addLast(line);
        }
        if (node.hasChildren()) {
            for (Node child : node.getChildren()) {
                searchLineWithoutActors(child, scenarioWithoutActors);
            }
        }
    }

    private boolean lineNotStartFromActor(String line) {
        String[] words = line.split(" ");
        for (String actor : getActors()) {
            if (words[0].equals(actor)) {
                return false;
            }
        }
        return true;
    }

    private String makeTabulaturePrefix(int nestingLevel) {
        StringBuilder prefix = new StringBuilder();
        for (int i = 1; i < nestingLevel; i++) {
            prefix.append("\t");
        }
        return prefix.toString();
    }

    private String changeLinkedListToString(LinkedList<String> scenario) {
        String header = addActorsHeader();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(header);
        for (String line : scenario) {
            stringBuilder.append(line);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    private String addActorsHeader() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String actor : getActors()) {
            stringBuilder.append(actor);
            if (!actor.equals(getActors()[getActors().length - 1])) {
                stringBuilder.append(",");
            }
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public String getScenarioWithNumeration() {
        LinkedList<String> scenarioWithNumeration = new LinkedList<>();
        for (int i = 0; i < getFirstLevelNodes().size(); i++) {
            if (getFirstLevelNodes().get(i).hasChildren()) {
                addLineWithNumeration(getFirstLevelNodes().get(i), scenarioWithNumeration, (i + 1) + ".");
            } else {
                String line = makeTabulaturePrefix(getFirstLevelNodes().get(i).getNestingLevel()) + (i + 1) + "." + getFirstLevelNodes().get(i).getLine() + "\n";
                scenarioWithNumeration.addLast(line);
            }

        }
        return changeLinkedListToString(scenarioWithNumeration);
    }

    private void addLineWithNumeration(Node node, LinkedList<String> scenarioWithNumeration, String prefix) {
        String line = makeTabulaturePrefix(node.getNestingLevel()) + prefix + node.getLine() + "\n";
        scenarioWithNumeration.addLast(line);
        if (node.hasChildren()) {
            for (int i = 0; i < node.getChildrenCount(); i++) {
                addLineWithNumeration(node.getChildren().get(i), scenarioWithNumeration, prefix + (i + 1) + ".");
            }
        }
    }

    public String getScenario() {
        LinkedList<String> scenario = new LinkedList<>();
        for (Node firstLevelNode : getFirstLevelNodes()) {
            if (firstLevelNode.hasChildren()) {
                addLine(firstLevelNode, scenario);
            } else {
                String line = makeTabulaturePrefix(firstLevelNode.getNestingLevel()) + firstLevelNode.getLine() + "\n";
                scenario.addLast(line);
            }

        }
        return changeLinkedListToString(scenario);
    }

    private void addLine(Node node, LinkedList<String> scenarioWithNumeration) {
        String line = makeTabulaturePrefix(node.getNestingLevel()) + node.getLine() + "\n";
        scenarioWithNumeration.addLast(line);
        if (node.hasChildren()) {
            for (Node child : node.getChildren()) {
                addLine(child, scenarioWithNumeration);
            }
        }
    }

    public String getScenario(int maxNestingLevel) {
        LinkedList<String> scenario = new LinkedList<>();
        for (Node firstLevelNode : getFirstLevelNodes()) {
            if (firstLevelNode.getNestingLevel() <= maxNestingLevel) {
                if (firstLevelNode.hasChildren()) {
                    addLine(firstLevelNode, scenario, maxNestingLevel);
                } else {
                    String line = makeTabulaturePrefix(firstLevelNode.getNestingLevel()) + firstLevelNode.getLine() + "\n";
                    scenario.addLast(line);
                }
            }
        }
        return changeLinkedListToString(scenario);
    }

    private void addLine(Node node, LinkedList<String> scenarioWithNumeration, int maxNestingLevel) {
        if (node.getNestingLevel() <= maxNestingLevel) {
            String line = makeTabulaturePrefix(node.getNestingLevel()) + node.getLine() + "\n";
            scenarioWithNumeration.addLast(line);
            if (node.hasChildren()) {
                for (Node child : node.getChildren()) {
                    addLine(child, scenarioWithNumeration, maxNestingLevel);
                }
            }
        }
    }

    public String[] getActors() {
        return actors;
    }

    public LinkedList<Node> getFirstLevelNodes() {
        return firstLevelNodes;
    }

    public LinkedList<Visitable> getNodes() {
        return nodes;
    }

    public void visit(Visitor visitor) {
        for (Visitable node : nodes) {
            node.accept(visitor);
        }
    }

}
