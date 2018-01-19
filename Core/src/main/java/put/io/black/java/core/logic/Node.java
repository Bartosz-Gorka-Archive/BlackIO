package put.io.black.java.core.logic;

/**
 * Abstract class Node to store details about scenario lines
 * @see Visitable
 */
public abstract class Node implements Visitable {
    /**
     * Node nesting level
      */
    private int nestingLevel;
    /**
     * Node string text - single line
     */
    private String line;

    /**
     * Constructor
     * @param nestingLevel Node nesting level
     * @param line Scenario line text
     */
    public Node(int nestingLevel, String line) {
        this.nestingLevel = nestingLevel;
        this.line = line;
    }

    /**
     * Getter - nesting level
     * @return Nesting level of node
     */
    public int getNestingLevel() {
        return nestingLevel;
    }

    /**
     * Getter - scenario line
     * @return Line with text
     */
    public String getLine() {
        return line;
    }

}
