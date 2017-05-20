import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by evanphoward on 5/16/17.
 */
public class Enemy {
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    private boolean isDead,isEscaped,isEscaping;
private int x,y,direction,health,speed;
private ImageIcon texture;

    public Enemy(int x, int y, int direction, int health, int speed, ImageIcon texture) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.health = health;
        this.speed = speed;
        this.texture = texture;
        isDead = false;
    }

    public void tick() {
        if(health==0)
            isDead = true;

        if(Gameboard.map[y/Gameboard.SQUARESIZE][x/Gameboard.SQUARESIZE]==3)
            isEscaping=true;

        if(x<0-texture.getIconWidth() || x>Gameboard.IMAGEWIDTH+texture.getIconWidth() || y<0-texture.getIconHeight() || y>Gameboard.IMAGEWIDTH+texture.getIconHeight())
            isEscaped = true;

        if(!isEscaping)
        switch(direction) {
            case 0:
                if(Gameboard.map[(y+40)/Gameboard.SQUARESIZE-1][x/Gameboard.SQUARESIZE]==1)
                    if(Gameboard.map[(y+40)/Gameboard.SQUARESIZE][x/Gameboard.SQUARESIZE+1]==0)
                        direction=EAST;
                    else
                        direction=WEST;
                y -= speed;
                break;
            case 1:
                if(Gameboard.map[y/Gameboard.SQUARESIZE][x/Gameboard.SQUARESIZE+1]==1)
                    if(Gameboard.map[y/Gameboard.SQUARESIZE+1][x/Gameboard.SQUARESIZE]==0)
                        direction=SOUTH;
                    else
                        direction=NORTH;
                x += speed;
                break;
            case 2:
                if(Gameboard.map[y/Gameboard.SQUARESIZE+1][x/Gameboard.SQUARESIZE]==1)
                    if(Gameboard.map[y/Gameboard.SQUARESIZE][x/Gameboard.SQUARESIZE+1]==0)
                        direction=EAST;
                    else
                        direction=WEST;
                y += speed;
                break;
            case 3:
                if(Gameboard.map[y/Gameboard.SQUARESIZE][(x+40)/Gameboard.SQUARESIZE-1]==1)
                    if(Gameboard.map[y/Gameboard.SQUARESIZE+1][(x+40)/Gameboard.SQUARESIZE]==0)
                        direction=SOUTH;
                    else
                        direction=NORTH;
                x -= speed;
                break;
        }
        else
            switch(direction) {
                case 0:
                    y-=speed;
                    break;
                case 1:
                    x+=speed;
                    break;
                case 2:
                    y+=speed;
                    break;
                case 3:
                    x-=speed;
                    break;
            }
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isEscaped() {
        return isEscaped;
    }

    public void draw(Graphics g) {
        g.drawImage(texture.getImage(),x,y,null);
    }
}
