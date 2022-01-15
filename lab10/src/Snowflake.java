import java.awt.*;

public class Snowflake implements XmasShape{

    int x = 0;
    int y = 0;
    double scale = 1;

    Snowflake(int x, int y,double scale){
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(new Color(255,255,255));
        for(int i=0;i<12;i++){
            g2d.drawLine(0,0,100,100);
            g2d.rotate(2*Math.PI/12);
        }
    }
}
