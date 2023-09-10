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
    Vector<Bomb> bombs = new Vector<>();
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    public MyPanel(){
        hero=new MyTank(700,100);
        hero.setSpeed(4);
        for (int i=0;i<enemyTankSize;i++){
            EnemyTank et = new EnemyTank((100*(i+2)),0);
            new Thread(et).start();
            et.setDirection(2);
            enemyTanks.add(et);


            Shot shot = new Shot(et.getX() + 20, et.getY() + 60, et.getDirection());
            et.shots.add(shot);
            new Thread(shot).start();
        }

        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/image1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/image2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/image3.png"));
        Bomb bomb1 = new Bomb(10, 10);
        bombs.add(bomb1);
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.fillRect(0,0,1000,750);
        if (hero != null && hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 0);
        }


//        if (hero.shot != null && (hero.shot.isLive)){
//        g.draw3DRect(hero.shot.getX(),hero.shot.getY(),2,2,false);
//        }
        for (int i=0;i<hero.shots.size();i++){
            Shot shot = hero.shots.get(i);
            if (hero.shot != null && (hero.shot.isLive)){
            g.draw3DRect(shot.getX(),shot.getY(),2,2,false);
        }else {
                hero.shots.remove(shot);
            }
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

        for (int i =0;i<bombs.size();i++){
            Bomb bomb = bombs.get(i);
            if (bomb.life > 10){
                g.drawImage(image1,bomb.x,bomb.y,60,60,this);
            }else if (bomb.life>5){
                g.drawImage(image2,bomb.x,bomb.y,60,60,this);
            }else {
                g.drawImage(image3,bomb.x,bomb.y,60,60,this);
            }
            bomb.liferDown();
            if (bomb.life==0){
                bombs.remove(bomb);
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
                if (hero.getY()>0) {
                    hero.up();
                }
                break;
            case KeyEvent.VK_D:
                hero.setDirection(1);
                if (hero.getX() +60 < 1000) {
                    hero.right();
                }
                break;
            case KeyEvent.VK_S:
                hero.setDirection(2);
                if (hero.getY()+60 < 750) {
                    hero.down();
                }
                break;
            case KeyEvent.VK_A:
                hero.setDirection(3);
                if (hero.getX()>0) {
                    hero.left();
                }
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

    public void shotsHitTanks(){

        for (int j=0;j<hero.shots.size();j++) {
            Shot shot = hero.shots.get(j);
            if (shot != null && shot.isLive) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    public void hitMyTank(){
        for (int i =0;i<enemyTanks.size();i++){
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j=0;j<enemyTank.shots.size();j++){
                Shot shot = enemyTank.shots.get(j);
                if (hero.isLive && shot.isLive){
                    hitTank(shot,hero);
                }

            }
        }
    }
    public void hitTank(Shot s, Tank t){
        switch (t.getDirection()){
            case 0:
            case 2:
              if (s.getX() > t.getX() && s.getX() < (t.getX() +40)
                      && s.getY() > t.getY() && s.getY() < (t.getY()+60)){
                  s.isLive=false;
                  t.isLive=false;
                  Bomb bomb = new Bomb(t.getX(), t.getY());
                  bombs.add(bomb);
                  enemyTanks.remove(t);
                  hero.shots.remove(s);
              }
                break;
            case 1:
            case 3:
                if (s.getX() > t.getX() && s.getX() < (t.getX() +60)
                        && s.getY() > t.getY() && s.getY() < (t.getY()+40)){
                    s.isLive=false;
                    t.isLive=false;
                    Bomb bomb = new Bomb(t.getX(), t.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(t);
                    hero.shots.remove(s);
                }
                break;
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
        shotsHitTanks();
        hitMyTank();
            this.repaint();
        }
    }
}
