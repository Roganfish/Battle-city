package tankgame;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    Vector<Shot> shots = new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {
        while (true){
            switch (getDirection()){
                case 0:
                    for (int i=0;i<30;i++){
                        if (getY()>0) {
                            up();
                        }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    }
                    break;
                case 1:
                    for (int i=0;i<30;i++){
                        if (getX() +60 < 1000) {
                            right();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2:
                    for (int i=0;i<30;i++){
                        if (getY()+60 < 750) {
                            down();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3:
                    for (int i=0;i<30;i++){
                        if (getX()>0) {
                            left();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }


            setDirection((int)(Math.random()*4));
            if (!isLive){
                break;
            }
        }
    }
}