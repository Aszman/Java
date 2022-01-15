import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.LocalTime;

public class ClockWithGui extends JPanel {

    ClockThread clThread = new ClockThread();

    LocalTime time = LocalTime.now();

    int radius = 200;
    int offsetNumber = 25;
    int offsetThickLine = 15;
    int offsetSlimLine = 10;

    ClockWithGui(){
        clThread.start();
    }

    class ClockThread extends Thread{
        @Override
        public void run() {
            for(;;){
                time = LocalTime.now();
                System.out.printf("%02d:%02d:%02d\n",time.getHour(),time.getMinute(),time.getSecond());

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                repaint();
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        g2d.translate(this.getWidth()/2, this.getHeight()/2);

        //draw clock's frame
        Stroke saveS = g2d.getStroke();
        g2d.setStroke(new BasicStroke(5));
        g2d.drawOval(-this.radius, -this.radius, 2*this.radius, 2*this.radius);


        // draw numbers
        for(int i = 1; i < 13; ++i){
            AffineTransform at = new AffineTransform();
            at.rotate(i * 2 * Math.PI/12);

            Point2D src = new Point2D.Float(0,-(this.radius - this.offsetNumber));
            Point2D trg = new Point2D.Float();
            at.transform(src,trg);

            String text = Integer.toString(i);
            g2d.drawString(text,
                        (int)trg.getX() - g2d.getFontMetrics().stringWidth(text)/2,
                        (int)trg.getY() + g2d.getFontMetrics().getAscent()/2);
        }

        //draw lines
        AffineTransform saveAT = g2d.getTransform();
        for(int i = 0; i < 60; ++i){
            if(i%5 == 0) {
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(0, -this.radius, 0, -(this.radius - this.offsetThickLine));
                g2d.setStroke(saveS);

            }else{
                g2d.drawLine(0, -this.radius, 0, -(this.radius - this.offsetSlimLine));
            }
            g2d.rotate(2* Math.PI/60);
        }
        g2d.setTransform(saveAT);


        //draw hour hand
        g2d.rotate((time.getHour()%12*3600 + 60*time.getMinute() + time.getSecond()) * 2*Math.PI/(43200));
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g2d.drawLine(0,0,0,-(this.radius/2));
        g2d.setTransform(saveAT);

        //draw minute hand
        g2d.rotate((time.getMinute()*60 + time.getSecond())*2*Math.PI/3600);
        g2d.drawLine(0,0,0,-(this.radius - this.offsetNumber - this.offsetThickLine));
        g2d.setTransform(saveAT);

        //draw second hand
        Color saveC = g2d.getColor();
        g2d.rotate(time.getSecond()*2*Math.PI/60);
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g2d.setColor(new Color(215, 0, 0));
        g2d.drawLine(0,0,0,-(this.radius - this.offsetNumber));

        g2d.setTransform(saveAT);
        g2d.setStroke(saveS);
        g2d.setColor(saveC);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock");
        frame.setContentPane(new ClockWithGui());
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);

    }
}
