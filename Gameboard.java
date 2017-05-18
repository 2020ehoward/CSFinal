//Evan Howard, 11 May 2017
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.*;

public class Gameboard extends JPanel
{
    private static final int WIDTH = 13;
    private final int IMAGEWIDTH = 780;
    private static final int[][] map = {
            {1,1,1,1,1,1,1,3,1,1,1,1,1,},
            {1,1,0,0,0,0,0,0,1,1,1,4,1,},
            {1,1,0,1,1,1,1,1,1,4,4,4,1,},
            {1,1,0,1,1,1,1,1,1,1,4,4,1,},
            {1,1,0,1,1,1,1,1,1,1,4,4,4,},
            {1,1,0,1,0,0,0,0,0,1,1,1,1,},
            {1,1,0,0,0,1,1,1,0,1,1,1,1,},
            {1,1,1,1,1,1,1,1,0,0,0,0,1,},
            {2,0,0,0,0,1,1,1,1,1,1,0,1,},
            {1,1,1,1,0,1,1,4,4,1,1,0,1,},
            {1,1,1,1,0,1,1,1,1,1,1,0,1,},
            {1,1,1,1,0,0,0,0,0,0,0,0,1,},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,}
    };
    private BufferedImage myImage = new BufferedImage(IMAGEWIDTH, IMAGEWIDTH, 1);
    private Graphics myBuffer;
    private ImageIcon[][] board;
    private ImageIcon water,grass,dirt;
   public Gameboard()
   {
       myBuffer = myImage.getGraphics();

        board = new ImageIcon[WIDTH][WIDTH];

       water = new ImageIcon("Textures/water.jpg");
       grass = new ImageIcon("Textures/Grass.jpg");
       dirt = new ImageIcon("Textures/Dirt.jpg");

       Image iWater = water.getImage();
       Image resizedWater = iWater.getScaledInstance(IMAGEWIDTH/WIDTH,IMAGEWIDTH/WIDTH,java.awt.Image.SCALE_SMOOTH);
       water = new ImageIcon(resizedWater);

       Image iGrass = grass.getImage();
       Image resizedGrass = iGrass.getScaledInstance(IMAGEWIDTH/WIDTH,IMAGEWIDTH/WIDTH,java.awt.Image.SCALE_SMOOTH);
       grass = new ImageIcon(resizedGrass);

       Image iDirt = dirt.getImage();
       Image resizedDirt = iDirt.getScaledInstance(IMAGEWIDTH/WIDTH,IMAGEWIDTH/WIDTH,java.awt.Image.SCALE_SMOOTH);
       dirt = new ImageIcon(resizedDirt);

       for(int x=0;x<WIDTH;x++)
           for(int y=0;y<WIDTH;y++) {
               if(map[x][y]==1) {
                   board[x][y] = new ImageIcon(grass.getImage());
               }
               else if(map[x][y]==4) {
                   board[x][y] = new ImageIcon(water.getImage());
               }
                   else if(map[x][y]==0 || map[x][y]==2 || map[x][y]==3){
                   board[x][y] = new ImageIcon(dirt.getImage());
               }
               myBuffer.drawImage(board[x][y].getImage(),(IMAGEWIDTH/WIDTH)*y,(IMAGEWIDTH/WIDTH)*x,(ImageObserver)null);
       }
   }
    public void paint(Graphics g) {
        g.drawImage(this.myImage, 0, 0, this.getWidth(), this.getHeight(), (ImageObserver)null);
    }
   public void update() {
       repaint();
   }
   
}