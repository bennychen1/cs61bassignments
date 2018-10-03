import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *  @author
 */
public class TrReader extends Reader {
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     *  in STR unchanged.  FROM and TO must have the same length. */
    Reader str;
    String from;
    String to;
    int pos;
    int index;
    public TrReader(Reader str, String from, String to) throws IOException {
        this.str = str;
        this.from = from;
        this.to = to;
        pos = -2;
        // FILL IN
    }
    public int read(char[] cbuf, int off, int len) throws IOException{
        if (pos == -2) {
            pos = cbuf.length + 1;
        }
        for (int index = off, count = 1; count <= len; count += 1, index += 1) {
            cbuf[index] = (char) str.read();
            int charIndex = from.indexOf(cbuf[index]);
            if (charIndex >= 0) {
                cbuf[index] = to.charAt(charIndex);
            }
        }
        pos = pos - 1;
        return pos;
    }

    public void close() {}

    // FILL IN
    // NOTE: Until you fill in the right methods, the compiler will
    //       reject this file, saying that you must declare TrReader
    //     abstract.  Don't do that; define the right methods instead!
}


