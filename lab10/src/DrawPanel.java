import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel{

    List<XmasShape> shapes = new ArrayList<>();

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(XmasShape shape: this.shapes ){
            shape.draw((Graphics2D)g);
        }
    }

    public DrawPanel addShape(XmasShape shape){
        this.shapes.add(shape);
        return this;
    }
}
