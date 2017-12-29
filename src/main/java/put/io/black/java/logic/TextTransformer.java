package put.io.black.java.logic;

public class TextTransformer {

    private final String[] transforms;

    public TextTransformer(String[] transforms) {
        this.transforms = transforms;
    }

    public String transform(String text) {
        return text.toUpperCase();
    }
}
