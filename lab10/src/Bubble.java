import java.awt.*;

public class Bubble implements XmasShape {
    //center of the bubble is on top of oval
    int x = 0;
    int y = 0;
    double scale = 1;

    Color lineColor = new Color(0,0,0);
    Color fillColor = new Color(34,160,69);

    Bubble(){}

    Bubble(int x,int y, double scale){
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public Bubble setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        return this;
    }

    public Bubble setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        return this;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        // ustaw kolor wypełnienia
        g2d.setColor(fillColor);
        g2d.fillOval(-50,0,100,100);

        // ustaw kolor obramowania
        g2d.setColor(lineColor);
        g2d.drawOval(-50,0,100,100);
    }
}