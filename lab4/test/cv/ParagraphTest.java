package cv;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class ParagraphTest {

    @Test
    public void writeHTML() {
        String paragraphContent = "someText to check if exist";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);

        new Paragraph(paragraphContent).writeHTML(ps);
        String result = null;

        try {
            result = os.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        assertTrue(result.contains("<p>"));
        assertTrue(result.contains("</p>"));
        assertTrue(result.contains(paragraphContent));

    }
}