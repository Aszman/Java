import java.awt.*;

public class Gift implements XmasShape{
    //center of the gift is in center of box
    int x = 0;
    int y = 0;
    double scaleX = 1;
    double scaleY = 1;

    Color boxColor = new Color(255,0,0);;
    Color ribbonColor = new Color(255,255,0);

    Gift(){}

    Gift(int x,int y, double scaleX, double scaleY){
        this.x = x;
        this.y = y;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public Gift setBoxColor(Color boxColor) {
        this.boxColor = boxColor;
        return this;
    }

    public Gift setRibbonColor(Color ribbonColor) {
        this.ribbonColor = ribbonColor;
        return this;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(this.x,this.y);
        g2d.scale(this.scaleX,scaleY);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(this.boxColor);
        g2d.fillRect(-50,-50, 100, 100);

        g2d.setColor(this.ribbonColor);
        g2d.fillRect(-50, -10, 100, 20);
        g2d.fillRect(-10, -50, 20, 100);
    }
}
