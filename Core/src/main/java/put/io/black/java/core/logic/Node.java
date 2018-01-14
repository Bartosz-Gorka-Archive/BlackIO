package put.io.black.java.core.logic;

public abstract class Node implements Visitable{

    private int nestingLevel;
    private String line;

    public Node(int nestingLevel, String line) {
        this.nestingLevel = nestingLevel;
        this.line = line;
    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    public String getLine() {
        return line;
    }


}
