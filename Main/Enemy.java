package Main;

import javax.swing.*;
import java.awt.*;

/**
 * Created by evanphoward on 5/16/17.
 */
public class Enemy {
    //final variables correspond to each direction
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    //booleans to keep track if it is dead, escaped the map, or on the final square (escaping)
    private boolean isDead,isEscaped,isEscaping,isFrozen;
    //x and y are the location
    //direction is which way it is currently moving
    //speed is how many pixels it moves every tick
private int x,y,direction,speed,level,freezetimer;
//the texture it uses
private ImageIcon texture;

    //this constructor is used to create an identical copy of a current enemy
    //used in the bullet class to see where an enemy will be in a few ticks
    public Enemy(Enemy e) {
        this.x=e.getX();
        this.y=e.getY();
        this.speed = e.getSpeed();
        this.direction = e.getDirection();
        this.isEscaping = e.isEscaping();
        this.isEscaped = e.isEscaped();
        this.texture = e.getTexture();
        this.freezetimer = e.getFreezetimer();
    }

    public Enemy(Gameboard g, int level) {
        //the enemy starts at the x and y values of the entrance of the map
        this.y = ((int)(g.getEntrance().getY())*Gameboard.SQUARESIZE);
        this.x = ((int)(g.getEntrance().getX())*Gameboard.SQUARESIZE);

        this.texture = new ImageIcon("Textures/Enemies/enemy"+level+".png");

        //it checks which direction from the entrance is a path, that directions is the direction in which the enemy will move
        //if that square is outside of the array, it obviously can't be that direction, so it catches the exception and moves on
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

        //if it is on the left side
        if(x<=30) {
            //it decreases the x by the width of the image, making it off the screen
            x-=texture.getIconWidth()-1;
            //randomly varies the y-value on the entrance
            y+=(int)(Math.random()*(Gameboard.SQUARESIZE-texture.getIconHeight()-(int)((Gameboard.SQUARESIZE-texture.getIconHeight())*0.8)));
        }
        //if it is on the right
        else if(x>=Gameboard.IMAGEWIDTH-Gameboard.SQUARESIZE) {
            //put it off the screen
            x+=Gameboard.SQUARESIZE-1;
            //vary the y-value across the entrance
            y+=(int)(Math.random()*(Gameboard.SQUARESIZE-texture.getIconHeight()));
        }
        //if it is on the top
        else if(y<=30) {
            //off the screen
            y-=texture.getIconHeight()-1;
            //random x-value
            x+=(int)(Math.random()*(Gameboard.SQUARESIZE-texture.getIconWidth()));
        }
        //bottom of the screen
        else if(y>=Gameboard.IMAGEWIDTH-Gameboard.SQUARESIZE) {
            //off the screen
            y+=Gameboard.SQUARESIZE-1;
            //random x-value
            x+=(int)(Math.random()*(Gameboard.SQUARESIZE-texture.getIconWidth()));
        }


        //level,speed, and texture are all specified by the constructor
        this.level = level;
        setSpeed();
        //it starts out alive
        isDead = false;
        freezetimer = 0;
    }

    public void freeze(int length) {
        if(freezetimer==0) {
            isFrozen=true;
            freezetimer = length;
        }
    }

    public void setSpeed() {
        switch(level) {
            case 1: speed=5;
                break;
            case 2: speed=6;
                break;
            case 3: speed=8;
                break;
            case 4: speed=10;
                break;
        }
    }

    //method that runs every tick of the game
    public void tick() {

        texture=new ImageIcon("Textures/Enemies/enemy"+level+".png");
        setSpeed();

        //if it is outside the map, it has escaped
        if (x < 0 - texture.getIconWidth() || x > Gameboard.IMAGEWIDTH || y < 0 - texture.getIconHeight() || y > Gameboard.IMAGEWIDTH)
            isEscaped = true;

        //if it hasn't escaped yet
        if (!isEscaped && !isEscaping)
            //and it is in the exit square
            if (Gameboard.map[y / Gameboard.SQUARESIZE][x / Gameboard.SQUARESIZE] == 3)
                //it is in the process of escaping
                isEscaping = true;

        if (freezetimer == 0) {
            isFrozen=false;
            //if it isn't in the exit square
            if (!isEscaping)
                //switch the direction it is going
                switch (direction) {
                    //if it is going north
                    case 0:
                        //if the current s
                        if (Gameboard.map[(y + 40) / Gameboard.SQUARESIZE - 1][x / Gameboard.SQUARESIZE] == 1)
                            if (Gameboard.map[(y + 40) / Gameboard.SQUARESIZE][x / Gameboard.SQUARESIZE + 1] == 0 || Gameboard.map[(y + 40) / Gameboard.SQUARESIZE][x / Gameboard.SQUARESIZE + 1] == 3)
                                direction = EAST;
                            else
                                direction = WEST;
                        y -= speed;
                        break;
                    case 1:
                        if (Gameboard.map[y / Gameboard.SQUARESIZE][x / Gameboard.SQUARESIZE + 1] == 1)
                            if (Gameboard.map[y / Gameboard.SQUARESIZE + 1][x / Gameboard.SQUARESIZE] == 0 || Gameboard.map[y / Gameboard.SQUARESIZE + 1][x / Gameboard.SQUARESIZE] == 3)
                                direction = SOUTH;
                            else
                                direction = NORTH;
                        x += speed;
                        break;
                    case 2:
                        if (Gameboard.map[y / Gameboard.SQUARESIZE + 1][x / Gameboard.SQUARESIZE] == 1)
                            if (Gameboard.map[y / Gameboard.SQUARESIZE][x / Gameboard.SQUARESIZE + 1] == 0 || Gameboard.map[y / Gameboard.SQUARESIZE][x / Gameboard.SQUARESIZE + 1] == 3)
                                direction = EAST;
                            else
                                direction = WEST;
                        y += speed;
                        break;
                    case 3:
                        if (Gameboard.map[y / Gameboard.SQUARESIZE][(x + 40) / Gameboard.SQUARESIZE - 1] == 1)
                            if (Gameboard.map[y / Gameboard.SQUARESIZE + 1][(x + 40) / Gameboard.SQUARESIZE] == 0 || Gameboard.map[y / Gameboard.SQUARESIZE + 1][(x + 40) / Gameboard.SQUARESIZE] == 3)
                                direction = SOUTH;
                            else
                                direction = NORTH;
                        x -= speed;
                        break;
                }
            else
                switch (direction) {
                    case 0:
                        y -= speed;
                        break;
                    case 1:
                        x += speed;
                        break;
                    case 2:
                        y += speed;
                        break;
                    case 3:
                        x -= speed;
                        break;
                }
        }
        else {
            freezetimer--;
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

    public int getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void removeHealth(int damage) {
        if(level-damage<1)
            isDead=true;
        else
        this.level-=damage;
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

    public boolean isEscaping() {
        return isEscaping;
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,texture.getIconWidth(),texture.getIconHeight());
    }

    public ImageIcon getTexture() {
        return texture;
    }

    public int getFreezetimer() { return freezetimer; }

    public boolean isFrozen() { return isFrozen; }

    public void draw(Graphics g) {
        g.drawImage(texture.getImage(),x,y,null);
    }
}
