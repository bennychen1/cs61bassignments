package testing.error;
import java.util.Arrays;

public class X {
    public static void main(String[] args) {
                String c = " *   B Beta";
                c = c.replaceAll("\\s+\\*", "* ");
                String[] cArr = c.split("\\s+");
                System.out.print(Arrays.toString(cArr));
    }
}
