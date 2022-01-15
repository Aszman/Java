package cv;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class DocumentTest {

    @Test
    public void writeHTML() {
        String docTitle = "Example Title";
        String imageURL = "image-url.png";
        String sectionTitle ="Title of the section";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);

        Document doc = new Document(docTitle);
        doc.setPhoto(imageURL).addSection(sectionTitle);
        doc.writeHTML(ps);

        String result = null;

        try {
            result = os.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        assertTrue(result.contains(imageURL));
        assertTrue(result.contains("<html>"));
        assertTrue(result.contains("<head>"));
        assertTrue(result.contains("</head>"));
        assertTrue(result.contains("<body>"));
        assertTrue(result.contains(docTitle));
        assertTrue(result.contains(imageURL));
        assertTrue(result.contains("</body>"));
        assertTrue(result.contains(sectionTitle));
    }
}