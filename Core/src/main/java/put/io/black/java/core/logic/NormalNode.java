package put.io.black.java.core.logic;

public class NormalNode extends Node {

    public NormalNode(int nestingLevel, String line) {
        super(nestingLevel, line);
    }

    @Override
    public String toString() {
        return "NormalNode{" + "nestingLevel=" + getNestingLevel() +
                ", line='" + getLine() + '\'' + '}';
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
