import java.awt.*;

public class Star implements XmasShape{

    int x = 0;
    int y = 0;
    double scale = 1;

    static int[] starX = new int[]{0,15,50,32,50,15,0,-15,-50,-32,-50,-15};
    static int[] starY = new int[]{-50,-25,-25,0,25,25,50,25,25,0,-25,-25};
    static Color starColor = new Color(255, 255, 0);

    Star(){}

    Star(int x,int y, double scale){
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
        g2d.setColor(starColor);
        g2d.fillPolygon(starX, starY, starX.length);
    }
}
