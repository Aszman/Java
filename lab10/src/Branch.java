import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Branch implements XmasShape{
    //center of the branch is on center of bottom edge
    int x = 0;
    int y = 0;
    double scale = 1;

    static int[] branchX = new int[]{-100,0,100};
    static int[] branchY = new int[]{0,-100,0};


    Garland garland;
    List<Bubble> bubbles = new ArrayList<>();

    Color branchColor = new Color(7, 38, 3);

    Branch(){}

    Branch(int x,int y, double scale){
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public Branch setBranchColor(Color branchColor) {
        this.branchColor = branchColor;
        return this;
    }

    public Branch setGarland(Garland garland){
        this.garland = garland;
        return this;
    }

    public Branch addBubble(Bubble bubble){
        this.bubbles.add(bubble);
        return this;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(branchColor);
        g2d.fillPolygon(branchX, branchY, 3);

        if(this.garland != null){
            this.garland.draw(g2d);
        }

        for(Bubble bubble: this.bubbles){
            bubble.draw(g2d);
        }
    }
}
