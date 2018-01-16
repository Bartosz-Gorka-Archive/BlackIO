package put.io.black.java.core.logic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeyNodeTest {

    private String nodeTestText = "keyNode text";
    private KeyNode keyNode;

    @Before
    public void setUp() throws Exception {
        keyNode = new KeyNode( 1,nodeTestText);
    }

    @Test
    public void nodeDoesNotHaveChild(){
        assertEquals(0, keyNode.getChildrenCount());
    }

    @Test
    public void nodeHasChild(){
        NormalNode nodechild1 = new NormalNode( 1,nodeTestText);
        KeyNode nodechild2 = new KeyNode( 1,nodeTestText);
        keyNode.addChild(nodechild1);
        keyNode.addChild(nodechild2);
        assertEquals(2, keyNode.getChildrenCount());
        assertTrue(keyNode.hasChildren());
    }

    @Test
    public void nodeNestLevelTest(){
        assertEquals(1, keyNode.getNestingLevel());
    }

}