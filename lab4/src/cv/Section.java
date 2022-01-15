package cv;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Section {
    String title;
    List<Paragraph> paragraphs = new ArrayList<>();

    Section(String title) {
        this.title = title;
    }

    Section setTitle(String title){
        this.title = title;
        return this;
    }

    Section addParagraph(Paragraph paragraph){
        paragraphs.add(paragraph);
        return this;
    }

    Section addParagraph(String paragraphContent){
        paragraphs.add(new Paragraph(paragraphContent));
        return this;
    }

    void writeHTML(PrintStream out){
        out.printf("""
                <h2>%s</h2>
                """, this.title);
        for (Paragraph p: this.paragraphs){
            p.writeHTML(out);
        }

    }

}
