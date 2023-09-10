package tankgame;

public class Tank {
    private int x;//tank x
    private int y;//tank y
    private int direction;
    private int speed = 1;
    boolean isLive =true;


    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void up(){
        y -= speed;
    }
    public void right(){
        x += speed;
    }
    public void down(){
        y += speed;
    }
    public void left(){
        x -= speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
