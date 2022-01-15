package cv;

import java.io.PrintStream;

public class Photo {
    String url;

    Photo(String url){
        this.url = url;
    }
    void writeHTML(PrintStream out){
        out.printf("<img src=\"%s\" alt=\"Smiley face\" height=\"60\" width=\"60\"/>\n",url);
    }
}
