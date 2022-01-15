package cv;

import java.io.PrintStream;

public class Paragraph {
        String content;

        Paragraph(){
                this.content = "";
        }

        Paragraph(String content){
                this.content = content;
        }

        Paragraph setContent(String content){
                this.content = content;
                return this;
        }

        void writeHTML(PrintStream out){
                out.printf("""
                        <p>%s</p>
                        """, this.content);
        }
}
