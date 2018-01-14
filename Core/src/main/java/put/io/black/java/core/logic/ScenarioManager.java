package put.io.black.java.core.logic;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Class used to analyze the scenario
 */
public class ScenarioManager {
    /**
     * Special keywords starting new nesting level
     */
    private String[] keyWords = {"IF", "ELSE", "FOR EACH"};
    /**
     * Actors in scenario
     */
    private String[] actors;
    /**
     * First level nodes - main nodes
     */
    private LinkedList<Node> firstLevelNodes = new LinkedList<>();
    /**
     * All nodes in scenario
     */
    private LinkedList<Visitable> nodes = new LinkedList<>();

    /**
     * Method which splits whole text to steps, extracts the actors in the scenario and builds the structure of the tree
     *
     * @param scenario Long scenario text lines separated \n
     */
    public ScenarioManager(String scenario) {
        String[] scenarioLines = scenario.split("\\r\\n|\\n|\\r");

        pullOutActors(scenarioLines[0]);
        buildTreeStructure(scenarioLines);
        saveScenario(scenarioLines[0],scenario);
    }

    private boolean saveScenario(String titlePart, String scenario){
        FileManager fileManager = new FileManager();
        if (fileManager.isReady()){
            fileManager.saveScenarioText("Scenario_with_"+titlePart.replace(",","_").replace(" ",""),scenario);
            return true;
        }else {
            return false;
        }
    }

    /**
     * Defines the actors given in the script header
     *
     * @param header Scenario header to fetch actors
     */
    private void pullOutActors(String header) {
        actors = header.split("\\s*,\\s*");
    }

    /**
     * Verify if the line starts with the keyword
     *
     * @param line Single step from scenario
     * @return True if line starts with keyword or false if not
     */
    public boolean lineStartFromKeyWord(String line) {
        String lineWithoutTabs = line.replace("\t", "");
        for (String keyWord : keyWords) {
            if (lineWithoutTabs.matches(keyWord + "(.*)")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine the nesting of a scenario step
     *
     * @param line Single step from scenario
     * @return The number of tab characters in a given step
     */
    public int countTabSign(String line) {
        String[] words = line.split("\t");
        return words.length - 1;
    }

    /**
     * Build a tree structure of scenario
     *
     * @param scenarioLines String array, step in the scenario
     */
    private void buildTreeStructure(String[] scenarioLines) {
        if (scenarioLines.length > 1) {
            Stack<KeyNode> stackNestingNormalNode = new Stack<>();
            Node actualFatherNormalNode = new NormalNode(1, scenarioLines[1]);
            nodes.addLast(actualFatherNormalNode);
            getFirstLevelNodes().addLast(actualFatherNormalNode);
            for (int lineNumber = 2; lineNumber < scenarioLines.length; lineNumber++) {
                while (stackNestingNormalNode.size() > countTabSign(scenarioLines[lineNumber])) {
                    stackNestingNormalNode.pop();
                    if (stackNestingNormalNode.size() != 0) {
                        actualFatherNormalNode = stackNestingNormalNode.peek();
                    }
                }
                Node node;
                if (lineStartFromKeyWord(scenarioLines[lineNumber])) {
                    node = new KeyNode(stackNestingNormalNode.size() + 1, scenarioLines[lineNumber].replace("\t", ""));
                } else {
                    node = new NormalNode(stackNestingNormalNode.size() + 1, scenarioLines[lineNumber].replace("\t", ""));
                }

                nodes.addLast(node);
                if (node.getNestingLevel() == 1) {
                    getFirstLevelNodes().addLast(node);
                } else {
                    ((KeyNode) actualFatherNormalNode).addChild(node);
                }

                if (lineStartFromKeyWord(scenarioLines[lineNumber])) {
                    stackNestingNormalNode.push((KeyNode) node);
                    actualFatherNormalNode = node;
                }
            }
        } else {
            System.out.println("Too short scenario");
        }
    }

    /**
     * Count scenario nesting level
     *
     * @return Level of scenario nesting
     */
    public int countScenarioNesting() {
        int maxNesting = 1;
        if (getFirstLevelNodes().size() != 0) {
            for (Node firstLevelNode : getFirstLevelNodes()) {
                if (firstLevelNode instanceof KeyNode) {
                    if (((KeyNode) firstLevelNode).hasChildren()) {
                        int level = searchTheNestingNode(firstLevelNode, maxNesting);
                        if (level > maxNesting) {
                            maxNesting = level;
                        }
                    }
                }
            }
            return maxNesting;
        } else {
            return 0;
        }
    }

    /**
     * Search maximum nesting level from nodes
     *
     * @param node            NormalNode to check
     * @param maxNestingLevel Actual max nesting level
     * @return Nesting level for node
     */
    private int searchTheNestingNode(Node node, int maxNestingLevel) {
        if (node instanceof KeyNode) {
            if (((KeyNode) node).hasChildren()) {
                for (Node child : ((KeyNode) node).getChildren()) {
                    int level = searchTheNestingNode(child, maxNestingLevel);
                    if (level > maxNestingLevel) {
                        maxNestingLevel = level;
                    }
                }
                return maxNestingLevel;
            }
        }
        return node.getNestingLevel();
    }

    /**
     * Count steps in scenario
     *
     * @return Steps in scenario
     */
    public int countNumberOfScenarioSteps() {
        int scenarioSteps = 0;
        if (getFirstLevelNodes().size() != 0) {
            for (Node firstLevelNode : getFirstLevelNodes()) {
                scenarioSteps++;
                if (firstLevelNode instanceof KeyNode) {
                    if (((KeyNode) firstLevelNode).hasChildren()) {
                        scenarioSteps = searchTheNode(firstLevelNode, scenarioSteps);
                    }
                }
            }
            return scenarioSteps;
        } else {
            return 0;
        }
    }

    /**
     * Search steps in node
     *
     * @param node          NormalNode to check
     * @param scenarioSteps Scenario steps
     * @return Scenario steps to find a node
     */
    private int searchTheNode(Node node, int scenarioSteps) {
        if (node instanceof KeyNode) {
            if (((KeyNode) node).hasChildren()) {
                for (Node child : ((KeyNode) node).getChildren()) {
                    scenarioSteps = searchTheNode(child, ++scenarioSteps);
                }
            }
        }
        return scenarioSteps;
    }

    /**
     * Count keywords in scenario
     *
     * @return Amount of keywords in scenario
     */
    public int countKeyWordsInScenario() {
        int keyWordsInScenario = 0;
        if (getFirstLevelNodes().size() != 0) {
            for (Node firstLevelNode : getFirstLevelNodes()) {
                if (firstLevelNode instanceof KeyNode) {
                    if (((KeyNode) firstLevelNode).hasChildren()) {
                        keyWordsInScenario = searchTheKeyWordsNode(firstLevelNode, keyWordsInScenario);
                    }
                }
            }
        }
        return keyWordsInScenario;
    }

    /**
     * Count nodes with keywords
     *
     * @param node               NormalNode to check
     * @param keyWordsInScenario Keywords counter
     * @return Amount of keywords in node
     */
    private int searchTheKeyWordsNode(Node node, int keyWordsInScenario) {
        if (node instanceof KeyNode) {
            if (((KeyNode) node).hasChildren()) {
                for (Node child : ((KeyNode) node).getChildren()) {
                    keyWordsInScenario = searchTheNode(child, keyWordsInScenario);
                }
            }
        }
        if (lineStartFromKeyWord(node.getLine())) {
            keyWordsInScenario++;
        }
        return keyWordsInScenario;
    }

    /**
     * Cut actors from scenario text
     *
     * @return Scenario without actors
     */
    public String cutActorsFromScenario() {
        LinkedList<String> scenarioWithoutActors = new LinkedList<>();
        scenarioWithoutActors.addLast("");
        for (Node firstLevelNode : getFirstLevelNodes()) {
            if (firstLevelNode instanceof KeyNode) {
                if (((KeyNode) firstLevelNode).hasChildren()) {
                    searchLineWithoutActors(firstLevelNode, scenarioWithoutActors);
                }
            } else if (lineNotStartFromActor(firstLevelNode.getLine())) {
                String line = makeTabulaturePrefix(firstLevelNode.getNestingLevel()) + firstLevelNode.getLine() + "\n";
                scenarioWithoutActors.addLast(line);
            }
        }
        return changeLinkedListToString(scenarioWithoutActors);
    }

    /**
     * Validate lines in scenario. Require lines with actors
     *
     * @param node                  node to check
     * @param scenarioWithoutActors Scenario lines without actors
     */
    private void searchLineWithoutActors(Node node, LinkedList<String> scenarioWithoutActors) {
        if (lineNotStartFromActor(node.getLine())) {
            String line = makeTabulaturePrefix(node.getNestingLevel()) + node.getLine() + "\n";
            scenarioWithoutActors.addLast(line);
        }
        if (node instanceof KeyNode) {
            if (((KeyNode) node).hasChildren()) {
                for (Node child : ((KeyNode) node).getChildren()) {
                    searchLineWithoutActors(child, scenarioWithoutActors);
                }
            }
        }
    }

    /**
     * Check line not start from actor
     *
     * @param line Line to check
     * @return False if line start from actor and true if not
     */
    private boolean lineNotStartFromActor(String line) {
        String[] words = line.split(" ");
        for (String actor : getActors()) {
            if (words[0].equals(actor)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Add tabulations nestingLevel-1 times
     *
     * @param nestingLevel Nesting level
     * @return String with tabulation
     */
    private String makeTabulaturePrefix(int nestingLevel) {
        StringBuilder prefix = new StringBuilder();
        for (int i = 1; i < nestingLevel; i++) {
            prefix.append("\t");
        }
        return prefix.toString();
    }

    /**
     * Method which changes linked list to string
     *
     * @param scenario List with scenario lines
     * @return Single scenario line
     */
    private String changeLinkedListToString(LinkedList<String> scenario) {
        String header = addActorsHeader();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(header);
        for (String line : scenario) {
            stringBuilder.append(line);
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

    /**
     * Add actors header
     *
     * @return String line with actors
     */
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

    /**
     * Generate scenario with numerations. New nesting level use parent number and create block 1. 1.1. 1.2 ...
     *
     * @return Scenario with numerations
     */
    public String getScenarioWithNumeration() {
        LinkedList<String> scenarioWithNumeration = new LinkedList<>();
        for (int i = 0; i < getFirstLevelNodes().size(); i++) {
            if (getFirstLevelNodes().get(i) instanceof KeyNode) {
                if (((KeyNode) getFirstLevelNodes().get(i)).hasChildren()) {
                    addLineWithNumeration(getFirstLevelNodes().get(i), scenarioWithNumeration, (i + 1) + ".");
                }
            } else {
                String line = makeTabulaturePrefix(getFirstLevelNodes().get(i).getNestingLevel()) + (i + 1) + "." + getFirstLevelNodes().get(i).getLine() + "\n";
                scenarioWithNumeration.addLast(line);
            }

        }
        return changeLinkedListToString(scenarioWithNumeration);
    }

    /**
     * Add line with numeration to scenario
     *
     * @param node                   NormalNode
     * @param scenarioWithNumeration Scenario with numeration list
     * @param prefix                 Prefix to prepend (number block)
     */
    private void addLineWithNumeration(Node node, LinkedList<String> scenarioWithNumeration, String prefix) {
        String line = makeTabulaturePrefix(node.getNestingLevel()) + prefix + node.getLine() + "\n";
        scenarioWithNumeration.addLast(line);
        if (node instanceof KeyNode) {
            if (((KeyNode) node).hasChildren()) {
                for (int i = 0; i < ((KeyNode) node).getChildrenCount(); i++) {
                    addLineWithNumeration(((KeyNode) node).getChildren().get(i), scenarioWithNumeration, prefix + (i + 1) + ".");
                }
            }
        }
    }

    /**
     * Return a scenario in base form
     *
     * @return Scenario as text
     */
    public String getScenario() {
        LinkedList<String> scenario = new LinkedList<>();
        for (Node firstLevelNode : getFirstLevelNodes()) {
            if (firstLevelNode instanceof KeyNode) {
                if (((KeyNode) firstLevelNode).hasChildren()) {
                    addLine(firstLevelNode, scenario);
                }
            } else {
                String line = makeTabulaturePrefix(firstLevelNode.getNestingLevel()) + firstLevelNode.getLine() + "\n";
                scenario.addLast(line);
            }

        }
        return changeLinkedListToString(scenario);
    }

    /**
     * Add line to specific node
     *
     * @param node                   NormalNode
     * @param scenarioWithNumeration Scenario list
     */
    private void addLine(Node node, LinkedList<String> scenarioWithNumeration) {
        String line = makeTabulaturePrefix(node.getNestingLevel()) + node.getLine() + "\n";
        scenarioWithNumeration.addLast(line);
        if (node instanceof KeyNode) {
            if (((KeyNode) node).hasChildren()) {
                for (Node child : ((KeyNode) node).getChildren()) {
                    addLine(child, scenarioWithNumeration);
                }
            }
        }
    }

    /**
     * Get scenario to selected nesting level
     *
     * @param maxNestingLevel Limit - nesting level
     * @return Scenario to selected level
     */
    public String getScenario(int maxNestingLevel) {
        LinkedList<String> scenario = new LinkedList<>();
        for (Node firstLevelNode : getFirstLevelNodes()) {
            if (firstLevelNode.getNestingLevel() <= maxNestingLevel) {
                if (firstLevelNode instanceof KeyNode) {
                    if (((KeyNode) firstLevelNode).hasChildren()) {
                        addLine(firstLevelNode, scenario, maxNestingLevel);
                    }
                } else {
                    String line = makeTabulaturePrefix(firstLevelNode.getNestingLevel()) + firstLevelNode.getLine() + "\n";
                    scenario.addLast(line);
                }
            }
        }
        return changeLinkedListToString(scenario);
    }

    /**
     * Add line
     *
     * @param node                   NormalNode
     * @param scenarioWithNumeration Scenario with numeration
     * @param maxNestingLevel        Max nesting level
     */
    private void addLine(Node node, LinkedList<String> scenarioWithNumeration, int maxNestingLevel) {
        if (node.getNestingLevel() <= maxNestingLevel) {
            String line = makeTabulaturePrefix(node.getNestingLevel()) + node.getLine() + "\n";
            scenarioWithNumeration.addLast(line);
            if (node instanceof KeyNode) {
                if (((KeyNode) node).hasChildren()) {
                    for (Node child : ((KeyNode) node).getChildren()) {
                        addLine(child, scenarioWithNumeration, maxNestingLevel);
                    }
                }
            }
        }
    }

    /**
     * Get actors from scenario
     *
     * @return Actors array
     */
    public String[] getActors() {
        return actors;
    }

    /**
     * Get first level nodes
     *
     * @return First level nodes
     */
    public LinkedList<Node> getFirstLevelNodes() {
        return firstLevelNodes;
    }

    /**
     * Get nodes from scenario
     *
     * @return Nodes in scenario
     */
    public LinkedList<Visitable> getNodes() {
        return nodes;
    }

    /**
     * Visit
     *
     * @param visitor Visitor
     * @see Visitor
     */
    public void visit(Visitor visitor) {
        for (Visitable node : nodes) {
            node.accept(visitor);
        }
    }

}
