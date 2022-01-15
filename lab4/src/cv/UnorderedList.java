package cv;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class UnorderedList {
    List<ListItem> listItems = new ArrayList<>();

    UnorderedList addItem(ListItem item){
        listItems.add(item);
        return this;
    }

    UnorderedList addItem(String itemContent){
        listItems.add(new ListItem(itemContent));
        return this;
    }

    void writeHTML(PrintStream out){
        out.println("<ul>");

        for (ListItem i: this.listItems) {
            i.writeHTML(out);
        }

        out.println("</ul>");
    }
}
