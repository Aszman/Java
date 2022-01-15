package cv;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Document {
    String title;
    Photo photo;
    List<Section> sections = new ArrayList<>();

    Document(){
        this.title = "New CV";
    }

    Document(String title){
        this.title = title;
    }

    Document setTitle(String title){
        this.title = title;
        return this;
    }

    Document setPhoto(String photoURL){
        this.photo = new Photo(photoURL);
        return this;
    }

    Section addSection(String sectionTitle){
        Section newSection = new Section(sectionTitle);
        this.sections.add(newSection);
        return newSection;
    }

    Document addSection(Section s){
        this.sections.add(s);
        return this;
    }


    void writeHTML(PrintStream out){
        out.printf("""
                <!DOCTYPE html>
                <html>
                    <head>
                        <meta charset="UTF-8">
                        <title>%s</title>
                    </head>
                <body>
                <h1>%s</h1>
                """,this.title,this.title);

        this.photo.writeHTML(out);
        for (Section s: sections){
            s.writeHTML(out);
        }

        out.print("""
                </body>
                </html>
                """);
    }

    public static void main(String[] args){
        Document cv = new Document("CV - Zbigniew Bormiński");
        cv.setPhoto("src/image.png");
        cv.addSection("Kontakt")
                .addParagraph("E-mail: zbigniew_borminski@gmail.com")
                .addParagraph("Telefon: 5467042");
        cv.addSection("Wykształcenie")
                .addParagraph("2020-obecnie: Akademia Górniczo-Hutnicza im. Stanisława Staszica w Krakowie")
                .addParagraph("Kierunek: Informatyka")
                .addParagraph("2017-2020: IV Liceum Ogólnokształcące w Ogrójcu");
        cv.addSection("Znajomość języków")
                        .addParagraph(
                                new ParagraphWithList("")
                                        .addItemToList("angielski: zaawansowany")
                                        .addItemToList("hiszpański: komunikatywny"));
        cv.addSection("Umiejętności")
                .addParagraph("skrupulaność")
                .addParagraph("zdolność pracy w zespole")
                .addParagraph(
                        new ParagraphWithList().setContent("znajomość języków programowania")
                                .addItemToList("C")
                                .addItemToList("C++")
                                .addItemToList("Java")
                                .addItemToList("Python"));

        try {
            cv.writeHTML(new PrintStream("cv.html","UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
