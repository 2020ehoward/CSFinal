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
private int x,y,direction,health,speed,level;
private ImageIcon texture;

    public Enemy(Gameboard g, int health, int speed, ImageIcon texture) {
        this.y = ((int)(g.getEntrance().getY())*Gameboard.SQUARESIZE);
        this.x = ((int)(g.getEntrance().getX())*Gameboard.SQUARESIZE);

        try {
            if (Gameboard.map[y/Gameboard.SQUARESIZE - 1][x/Gameboard.SQUARESIZE] == 0)
                direction = WEST;
        } catch(ArrayIndexOutOfBoundsException e) {
        }
        try {
        if (Gameboard.map[y/Gameboard.SQUARESIZE][x/Gameboard.SQUARESIZE+1] == 0)
            direction = SOUTH;
    } catch(ArrayIndexOutOfBoundsException e) {
    }
        try {
        if (Gameboard.map[y/Gameboard.SQUARESIZE][x/Gameboard.SQUARESIZE-1] == 0)
            direction = NORTH;
} catch(ArrayIndexOutOfBoundsException e) {
        }
        try {
        if (Gameboard.map[y/Gameboard.SQUARESIZE+1][x/Gameboard.SQUARESIZE] == 0)
            direction = EAST;
        } catch(ArrayIndexOutOfBoundsException e) {
        }

        if(x<=30) {
            x-=texture.getIconWidth();
            y+=(int)(Math.random()*(Gameboard.SQUARESIZE-texture.getIconHeight()));
        }
        else if(x>=Gameboard.IMAGEWIDTH-Gameboard.SQUARESIZE) {
            x+=Gameboard.SQUARESIZE;
            y+=(int)(Math.random()*(Gameboard.SQUARESIZE-texture.getIconHeight()));
        }
        else if(y<=30) {
            y-=texture.getIconHeight();
            x+=(int)(Math.random()*(Gameboard.SQUARESIZE-texture.getIconWidth()));
        }
        else if(y>=Gameboard.IMAGEWIDTH-Gameboard.SQUARESIZE) {
            x+=(int)(Math.random()*(Gameboard.SQUARESIZE-texture.getIconWidth()));
            y+=Gameboard.SQUARESIZE;
        }


        this.level = health;
        this.health = health;
        this.speed = speed;
        this.texture = texture;
        isDead = false;
    }

    public void tick() {
        if(health==0)
            isDead = true;

        if(x<0-texture.getIconWidth() || x>Gameboard.IMAGEWIDTH || y<0-texture.getIconHeight() || y>Gameboard.IMAGEWIDTH)
            isEscaped = true;

        if(!isEscaped)
        if(Gameboard.map[y/Gameboard.SQUARESIZE][x/Gameboard.SQUARESIZE]==3)
            isEscaping=true;

        if(!isEscaping)
        switch(direction) {
            case 0:
                if(Gameboard.map[(y+40)/Gameboard.SQUARESIZE-1][x/Gameboard.SQUARESIZE]==1)
                    if(Gameboard.map[(y+40)/Gameboard.SQUARESIZE][x/Gameboard.SQUARESIZE+1]==0 || Gameboard.map[(y+40)/Gameboard.SQUARESIZE][x/Gameboard.SQUARESIZE+1]==3)
                        direction=EAST;
                    else
                        direction=WEST;
                y -= speed;
                break;
            case 1:
                if(Gameboard.map[y/Gameboard.SQUARESIZE][x/Gameboard.SQUARESIZE+1]==1)
                    if(Gameboard.map[y/Gameboard.SQUARESIZE+1][x/Gameboard.SQUARESIZE]==0 || Gameboard.map[y/Gameboard.SQUARESIZE+1][x/Gameboard.SQUARESIZE] == 3)
                        direction=SOUTH;
                    else
                        direction=NORTH;
                x += speed;
                break;
            case 2:
                if(Gameboard.map[y/Gameboard.SQUARESIZE+1][x/Gameboard.SQUARESIZE]==1)
                    if(Gameboard.map[y/Gameboard.SQUARESIZE][x/Gameboard.SQUARESIZE+1]==0 || Gameboard.map[y/Gameboard.SQUARESIZE][x/Gameboard.SQUARESIZE+1] == 3)
                        direction=EAST;
                    else
                        direction=WEST;
                y += speed;
                break;
            case 3:
                if(Gameboard.map[y/Gameboard.SQUARESIZE][(x+40)/Gameboard.SQUARESIZE-1]==1)
                    if(Gameboard.map[y/Gameboard.SQUARESIZE+1][(x+40)/Gameboard.SQUARESIZE]==0 || Gameboard.map[y/Gameboard.SQUARESIZE+1][(x+40)/Gameboard.SQUARESIZE]==3)
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLevel() {
        return level;
    }

    public void removeHealth(int damage) {
        this.health-=damage;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isEscaped() {
        return isEscaped;
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,texture.getIconWidth(),texture.getIconHeight());
    }

    public void draw(Graphics g) {
        g.drawImage(texture.getImage(),x,y,null);
    }
}
