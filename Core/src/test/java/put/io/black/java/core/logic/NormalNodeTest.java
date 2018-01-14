package put.io.black.java.core.logic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NormalNodeTest {

    private String nodeTestText = "normalNode text";
    private NormalNode normalNode;

    @Before
    public void setUp() throws Exception {
        normalNode = new NormalNode( 1,nodeTestText);
    }


}