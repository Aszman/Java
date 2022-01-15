import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snow implements XmasShape{

    int xSize;
    int ySize;

    List<Snowflake> snowflakes = new ArrayList<>();


    Snow(int xSize, int ySize, int amount){
        this.xSize = xSize;
        this.ySize = ySize;

        Random rand = new Random();
        for(int i =0; i < amount; ++i){
            this.snowflakes.add(new Snowflake(rand.nextInt(this.xSize),rand.nextInt(this.ySize),0.05));
        }
    }

    @Override
    public void transform(Graphics2D g2d) {

    }

    @Override
    public void render(Graphics2D g2d) {
        for(Snowflake snowflake: this.snowflakes){
            snowflake.draw(g2d);
        }
    }
}
