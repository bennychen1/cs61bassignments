import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class ECHashTest {
    @Test
    public void testECHashTest() {
        ECHashStringSet ech = new ECHashStringSet();

        ech.put("random strings");
        ech.put("abc");

        List<String> s = new ArrayList<String>();
        s.add("abc");
        s.add("random strings");

        ECHashStringSet ech2 = new ECHashStringSet();

        for (int i = 0; i < 100; i++) {
            String t = StringUtils.randomString(10);
            ech2.put(t);
        }

        assertEquals(s, ech.asList());
        assertEquals(100, ech2.asList().size());
    }
}
