package tankgame;

import javax.swing.*;

public class MyFrame extends JFrame {
    MyPanel mp = null;
    public MyFrame(){
        mp = new MyPanel();
        new Thread(mp).start();
        this.add(mp);
        this.setSize(1000,750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        MyFrame mf = new MyFrame();
    }

}
