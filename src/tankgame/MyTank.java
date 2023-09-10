package tankgame;

import java.util.Vector;

public class MyTank extends Tank{
   public Shot shot = null;
   Vector<Shot> shots = new Vector<>();
    public MyTank(int x, int y){
        super(x,y);
    }

    public void shotEnemyTank(){

        if (shots.size()==5){
            return;
        }
        switch (getDirection()){
            case 0:
                shot = new Shot(getX()+20,getY(),getDirection());
                break;
            case 1:
                shot = new Shot(getX()+60,getY()+20,getDirection());
                break;
            case 2:
                shot = new Shot(getX()+20,getY()+60,getDirection());
                break;
            case 3:
                shot = new Shot(getX(),getY()+20,getDirection());
                break;
        }
        shots.add(shot);
        new Thread(shot).start();

    }
}
