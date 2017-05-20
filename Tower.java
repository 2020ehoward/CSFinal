import Towers.Bullet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by evanphoward on 5/16/17.
 */
public abstract class Tower {
    private Bullet myBullet;
    private ImageIcon myTexture;
    private int x,y;

    public Tower(Bullet myBullet, ImageIcon myTexture, int x, int y) {
        this.myBullet = myBullet;
        this.myTexture = myTexture;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.drawImage(myTexture.getImage(),x*Gameboard.SQUARESIZE,y*Gameboard.SQUARESIZE,null);
    }
}
