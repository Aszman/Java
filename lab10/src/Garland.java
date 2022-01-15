import java.awt.*;

public class Garland implements XmasShape {

    //garland has not a center
    Color garlandColor = new Color(86, 65, 125);

    Garland(){}

    Garland(Color garlandColor){
        this.garlandColor = garlandColor;
    }

    @Override
    public void transform(Graphics2D g2d) {}

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(this.garlandColor);
        g2d.setStroke(new BasicStroke(3) {
        });
        g2d.drawLine(-70,-30, 40, -60);
    }
}
