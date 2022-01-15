import javax.swing.*;
import java.awt.*;

public class ChristmasTree {
    public static void main(String[] args) {
        // write your code here
        JFrame frame = new JFrame("Christmas Tree");
        DrawPanel choinka = new DrawPanel();
        choinka.setBackground(new Color(3, 3, 100));

        Tree tree = new Tree(500, 450,1.6);
        tree.addBranch(new Branch().setGarland(new Garland())
                .addBubble(new Bubble(-45,-15,0.2).setFillColor(new Color(255, 0, 0)))
                .addBubble(new Bubble(0,-40, 0.2).setFillColor(new Color(248, 195, 6)))
                .addBubble(new Bubble(50, -30, 0.15).setFillColor(new Color(58, 170, 231))));
        tree.addBranch(new Branch().setGarland(new Garland(new Color(192, 186, 186)))
                .addBubble(new Bubble(-50, -40,0.25).setFillColor(new Color(112, 246, 97)))
                .addBubble(new Bubble(10,-15,0.21).setFillColor(new Color(236, 153, 16)))
                .addBubble(new Bubble(40,-30,0.16).setFillColor(new Color(66, 34, 114))));
        tree.addBranch(new Branch().setGarland(new Garland(new Color(232, 232, 23)))
                .addBubble(new Bubble(-30, -60,0.25).setFillColor(new Color(131, 246, 213)))
                .addBubble(new Bubble(10,-30,0.21).setFillColor(new Color(86, 8, 45)))
                .addBubble(new Bubble(70,-30,0.16).setFillColor(new Color(255, 6, 6))));

        choinka.addShape(tree);
        choinka.addShape(new Gift(440,500,0.8,0.5));
        choinka.addShape(new Gift(540, 490, 1,0.7)
                .setBoxColor(new Color(4, 45, 18))
                .setRibbonColor(new Color(249, 255, 248)));
        choinka.addShape(new Gift(490, 495, 0.6,0.8)
                .setBoxColor(new Color(211, 5, 200))
                .setRibbonColor(new Color(51, 217, 29)));

        choinka.addShape(new Snow(1000,700,200));
        frame.setContentPane(choinka);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
