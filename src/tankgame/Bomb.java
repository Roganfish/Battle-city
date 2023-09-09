package tankgame;

public class Bomb {
    int x,y;
    boolean isLive = true;
    int life = 14;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void liferDown(){
        if (life > 0){
            life--;
        }else {
            isLive = false;
        }
    }
}
