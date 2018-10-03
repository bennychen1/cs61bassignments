import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

import ucb.junit.textui;

public class TranslateTest {

    @Test
    public void testTranslate() {
        String S = "hello there";
        String from = "ht";
        String to = "xr";
        String result = "xello rxere";
        assertEquals(result, Translate.translate(S, from, to));

    }
    public static void main(String[] args) {
        System.exit(textui.runClasses(TranslateTest.class));
    }
}
