package testing.error;
import java.util.Arrays;

public class X {
    public static void main(String[] args) {
                String c = " *    ";
                c = c.replaceAll("\\s+\\*", "* ");
                String[] cArr = c.split("\\s+");
                System.out.println(cArr[2]);
                System.out.print(Arrays.toString(cArr));
    }
}
