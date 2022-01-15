package cv;

import java.io.PrintStream;


public class ParagraphWithList extends Paragraph{

    UnorderedList ul = new UnorderedList();

    ParagraphWithList(){
        super("");
    }

    ParagraphWithList(String content){
        super(content);
    }

    ParagraphWithList setContent(String content){
        super.setContent(content);
        return this;
    }

    ParagraphWithList addItemToList(ListItem item){
        this.ul.addItem(item);
        return this;
    }

    ParagraphWithList addItemToList(String itemContent){
        this.ul.addItem(itemContent);
        return this;
    }

    void writeHTML(PrintStream out){
        super.writeHTML(out);
        this.ul.writeHTML(out);
    }
}
