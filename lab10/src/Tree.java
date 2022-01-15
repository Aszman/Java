import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tree implements XmasShape{
    //center of tree is on the top of trunk
    int x = 0;
    int y = 0;
    double scale = 1;

    double branchScale = 1;
    double branchScaleStep = 0.12;
    int branchX = 0;
    int branchY = 0;

    List<Branch> branches = new ArrayList<>();
    Color trunkColor = new Color(84, 36, 4);
    Color branchColor = new Color(7, 38, 3);

    Tree(){}

    Tree(int x, int y, double scale){
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public Tree setTrunkColor(Color trunkColor) {
        this.trunkColor = trunkColor;
        return this;
    }

    public Tree setBranchColor(Color branchColor) {
        this.branchColor = branchColor;
        return this;
    }


    public Tree addDefaultBranches(int amount){

        try{
            if(amount < 0){
                throw new RuntimeException("Branches' amount can't be negative!");
            }

            for(int i = 0; i < amount; ++i){
                this.branches.add(new Branch(this.branchX, this.branchY, this.branchScale)
                        .setBranchColor(this.branchColor));

                this.branchY -= 60*this.branchScale;
                this.branchScale -= branchScaleStep;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }

    public Tree addBranch(Branch branch){
        branch.x = this.branchX;
        branch.y = this.branchY;
        branch.scale = branchScale;

        this.branchY -= 60*this.branchScale;
        this.branchScale -=  this.branchScaleStep;

        this.branches.add(branch);
        return this;
    }

    @Override
    public void transform(Graphics2D g2d) {
        g2d.translate(x,y);
        g2d.scale(scale,scale);
    }

    @Override
    public void render(Graphics2D g2d){
        g2d.setColor(this.trunkColor);
        g2d.fillRect(-20, 0,40,40);

        for(Branch branch: this.branches){
            branch.draw(g2d);
        }

        new Star(branchX, branchY- (int)(60*this.branchScale),0.8*this.branchScale).draw(g2d);
    }
}
