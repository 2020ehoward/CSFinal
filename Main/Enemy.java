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

    //booleans to keep track if it is dead, escaped the map, on the final square (escaping), or if it has been frozen by the freeze tower
    private boolean isDead,isEscaped,isEscaping,isFrozen;
    //x and y are the location
    //direction is which way it is currently moving
    //speed is how many pixels it moves every tick
    //freezetimer keeps track of how much time left it stays frozen
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

        setTexture();

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


        //level is specified by the constructor
        this.level = level;
        //speed is dependant on level, set using method
        setSpeed();
        //it starts out alive
        isDead = false;
        //it starts out not frozen
        freezetimer = 0;
    }

    public void freeze(int length) {
        //if it is not already frozen
        if(freezetimer==0) {
            //it becomes frozen for the specified amount of time
            isFrozen=true;
            freezetimer = length;
        }
    }

    //used to set the speed of an enemy based on their level
    public void setSpeed() {
        //each level has a corresponding speed
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

    //used to set the texture of an enemy based on their level
    public void setTexture() {
        //textures follow the naming convention of enemy"level".png
        texture=new ImageIcon("Textures/Enemies/enemy"+level+".png");
        //each level will be a different size
        switch(level) {
            case 1: this.texture = new ImageIcon(texture.getImage().getScaledInstance((int)(Gameboard.SQUARESIZE*0.8),(int)(Gameboard.SQUARESIZE*0.8),Image.SCALE_SMOOTH));
                break;
            case 2: this.texture = new ImageIcon(texture.getImage().getScaledInstance((int)(Gameboard.SQUARESIZE*(13.0/15.0)),(int)(Gameboard.SQUARESIZE*(13.0/15.0)),Image.SCALE_SMOOTH));
                break;
            case 3: this.texture = new ImageIcon(texture.getImage().getScaledInstance((int)(Gameboard.SQUARESIZE*(14.0/15.0)),(int)(Gameboard.SQUARESIZE*(14.0/15.0)),Image.SCALE_SMOOTH));
                break;
            case 4: this.texture = new ImageIcon(texture.getImage().getScaledInstance(Gameboard.SQUARESIZE,Gameboard.SQUARESIZE,Image.SCALE_SMOOTH));
                break;
        }
    }

    //method that runs every tick of the game
    public void tick() {

        //texture and speed will be updated in case the level has changed (if it has been damaged)
        setTexture();
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

        //if it is not being frozen anymore
        if (freezetimer == 0) {
            //it is not frozen anymore
            isFrozen=false;
            //if it isn't in the exit square
            if (!isEscaping)
                //each direction checks if the next square is not path, it will check the neighboring squares and whichever one is path will go in that direction
                switch (direction) {
                    case 0:
                        if (Gameboard.map[(y + (int)(Gameboard.SQUARESIZE*(2.0/3.0))) / Gameboard.SQUARESIZE - 1][x / Gameboard.SQUARESIZE] == 1)
                            if (Gameboard.map[(y + (int)(Gameboard.SQUARESIZE*(2.0/3.0))) / Gameboard.SQUARESIZE][x / Gameboard.SQUARESIZE + 1] == 0 || Gameboard.map[(y + (int)(Gameboard.SQUARESIZE*(2.0/3.0))) / Gameboard.SQUARESIZE][x / Gameboard.SQUARESIZE + 1] == 3)
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
                        if (Gameboard.map[y / Gameboard.SQUARESIZE][(x + (int)(Gameboard.SQUARESIZE*(2.0/3.0))) / Gameboard.SQUARESIZE - 1] == 1)
                            if (Gameboard.map[y / Gameboard.SQUARESIZE + 1][(x + (int)(Gameboard.SQUARESIZE*(2.0/3.0))) / Gameboard.SQUARESIZE] == 0 || Gameboard.map[y / Gameboard.SQUARESIZE + 1][(x + (int)(Gameboard.SQUARESIZE*(2.0/3.0))) / Gameboard.SQUARESIZE] == 3)
                                direction = SOUTH;
                            else
                                direction = NORTH;
                        x -= speed;
                        break;
                }
                //if it is escaping, it just keeps moving in that direction
            //this is so it doesn't check a square outside of the map
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

    //getters used to create a copy for the bullet class
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

    public ImageIcon getTexture() {
        return texture;
    }

    public int getFreezetimer() { return freezetimer; }

    //removes a specified amount of damage
    //if the level is below 1, it is dead
    public void removeHealth(int damage) {
        if(level-damage<1)
            isDead=true;
        else
        this.level-=damage;
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

    //used to check if it is intersecting a bullet
    public Rectangle getBounds() {
        return new Rectangle(x,y,texture.getIconWidth(),texture.getIconHeight());
    }

    //standard draw method
    public void draw(Graphics g) {
        g.drawImage(texture.getImage(),x,y,null);
    }
}
