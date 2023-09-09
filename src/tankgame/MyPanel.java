package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable{
    //define my tank
    MyTank hero = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int enemyTankSize = 3;
    public MyPanel(){
        hero=new MyTank(100,100);
        hero.setSpeed(4);
        for (int i=0;i<enemyTankSize;i++){
            EnemyTank et = new EnemyTank((100*(i+2)),0);
            et.setDirection(2);
            enemyTanks.add(et);


            Shot shot = new Shot(et.getX() + 20, et.getY() + 60, et.getDirection());
            et.shots.add(shot);
            new Thread(shot).start();

        }
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.fillRect(0,0,1000,750);
        drawTank(hero.getX(), hero.getY(), g,hero.getDirection(),0);


        if (hero.shot != null && (hero.shot.isLive)){
        g.draw3DRect(hero.shot.getX(),hero.shot.getY(),2,2,false);
        }


        for (int i=0;i<enemyTanks.size();i++){
            EnemyTank enemyTank = enemyTanks.get(i);

            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirection(), 1);

                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive) {
                        g.draw3DRect(shot.getX(), shot.getY(), 2, 2, false);
                    } else {
                        enemyTank.shots.remove(shot);
                    }

                }
            }
        }
    }

    public void drawTank(int x, int y, Graphics g, int direction, int type){
        switch (type){
            case 0:// my Tank
                g.setColor(Color.yellow);
                break;
            case 1: // foes Tank
                g.setColor(Color.cyan);
                break;
        }
        // direction(0:up,1:right,2:down,3:left)
        switch (direction){
            case 0:
                g.fill3DRect(x,y,10,60,false); //left
                g.fill3DRect(x+30,y,10,60,false);//right
                g.fill3DRect(x+10,y+10,20,40,false);//lip
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y);
                break;
            case 1:
                g.fill3DRect(x,y,60,10,false);//top
                g.fill3DRect(x,y+30,60,10,false);//down
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x+60,y+20);
                break;
            case 2:
                g.fill3DRect(x,y,10,60,false); //left
                g.fill3DRect(x+30,y,10,60,false);//right
                g.fill3DRect(x+10,y+10,20,40,false);//lip
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y+60);
                break;
            case 3:
                g.fill3DRect(x,y,60,10,false);//top
                g.fill3DRect(x,y+30,60,10,false);//down
                g.fill3DRect(x+10,y+10,40,20,false);
                g.fillOval(x+20,y+10,20,20);
                g.drawLine(x+30,y+20,x,y+20);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                hero.setDirection(0);
                hero.up();
                break;
            case KeyEvent.VK_D:
                hero.setDirection(1);
                hero.right();
                break;
            case KeyEvent.VK_S:
                hero.setDirection(2);
                hero.down();
                break;
            case KeyEvent.VK_A:
                hero.setDirection(3);
                hero.left();
                break;
            case KeyEvent.VK_J:
                hero.shotEnemyTank();
                break;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void hitTank(Shot s, EnemyTank t){
        switch (t.getDirection()){
            case 0:
            case 2:
              if (s.getX() > t.getX() && s.getX() < (t.getX() +40)
                      && s.getY() > t.getY() && s.getY() < (t.getY()+60)){
                  s.isLive=false;
                  t.isLive=false;
              }
            case 1:
            case 3:
                if (s.getX() > t.getX() && s.getX() < (t.getX() +60)
                        && s.getY() > t.getY() && s.getY() < (t.getY()+40)){
                    s.isLive=false;
                    t.isLive=false;
                }
        }

    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (hero.shot != null && hero.shot.isLive){
                for (int i=0;i<enemyTanks.size();i++){
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(hero.shot,enemyTank);
                }
            }
            this.repaint();
        }
    }
}
