package cv;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class PhotoTest {

    @org.junit.Test
    public void writeHTML() throws Exception {
        String imageUrl = "jan-kowalski.png";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);

        new Photo(imageUrl).writeHTML(ps);
        String result = null;

        try {
            result = os.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        assertTrue(result.contains("<img"));
        assertTrue(result.contains("/>"));
        assertTrue(result.contains("src="));
        assertTrue(result.contains(imageUrl));

    }
}