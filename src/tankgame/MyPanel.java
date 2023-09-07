package tankgame;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    //define my tank
    myTank hero = null;
    public MyPanel(){
        hero=new myTank(100,100);
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.fillRect(0,0,1000,750);
        drawTank(hero.getX(), hero.getY(), g,0,0);
    }

    public void drawTank(int x, int y, Graphics g, int direction, int type){
        switch (type){
            case 0:// my Tank
                g.setColor(Color.cyan);
                break;
            case 1: // foes Tank
                g.setColor(Color.yellow);
                break;
        }
        switch (direction){
            case 0:
                g.fill3DRect(x,y,10,60,false); //left
                g.fill3DRect(x+30,y,10,60,false);//right
                g.fill3DRect(x+10,y+10,20,40,false);//lip
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y,x+20,y+30);
        }
    }
}
