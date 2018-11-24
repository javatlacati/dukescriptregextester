package org.javapro.regextester;

import net.java.html.junit.BrowserRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.junit.Test;

/**
 * Tests for behavior of your application in isolation. Verify behavior of your
 * MVVC code in a unit test.
 */
@RunWith(BrowserRunner.class)
public class DataModelTest {

    @Test
    public void testCapturingGroup() {
        RegexTesting model = new RegexTesting("f.o,bar,hello,world", "f\\.o,(bar)", "f\\\\.o,(bar)", "$1", false, false);
        assertFalse(model.isMatches());
        assertEquals("bar,hello,world", model.getReplaced());
        assertEquals("f\\\\.o,(bar)", model.getEscapeRegexText());
    }

    @Test
    public void testGreedyOption() {
        RegexTesting model = new RegexTesting("humbapumpa jim", ".*(jim|joe).*", ".*(jim|joe).*", "", false, false);
        assertTrue(model.isMatches());
    }

    @Test
    public void testUnicode() {
        RegexTesting model = new RegexTesting("abcdefghijkTYYtyyQ", "(?=\\p{Lu})", "(?=\\\\p{Lu})", "_", true, false);
        assertFalse(model.isMatches());
        assertEquals("(?=\\\\p{Lu})", model.getEscapeRegexText());
        assertEquals("abcdefghijk_T_Y_Ytyy_Q", model.getReplaced());
    }
}
