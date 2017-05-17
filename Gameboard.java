//Evan Howard, 11 May 2017
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

 public class Gameboard extends JPanel
{
    private static final int WIDTH = 13;
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
    private JButton[][] board;
    private ImageIcon water,grass,dirt;
   public Gameboard()
   {
        board = new JButton[WIDTH][WIDTH];

       water = new ImageIcon("Textures/water.jpg");
       grass = new ImageIcon("Textures/Grass.jpg");
       dirt = new ImageIcon("Textures/Dirt.jpg");

       Border emptyBorder = BorderFactory.createEmptyBorder();

       Image iWater = water.getImage();
       Image resizedWater = iWater.getScaledInstance(800/WIDTH,800/WIDTH,java.awt.Image.SCALE_SMOOTH);
       water = new ImageIcon(resizedWater);

       Image iGrass = grass.getImage();
       Image resizedGrass = iGrass.getScaledInstance(800/WIDTH,800/WIDTH,java.awt.Image.SCALE_SMOOTH);
       grass = new ImageIcon(resizedGrass);

       Image iDirt = dirt.getImage();
       Image resizedDirt = iDirt.getScaledInstance(800/WIDTH,800/WIDTH,java.awt.Image.SCALE_SMOOTH);
       dirt = new ImageIcon(resizedDirt);

       setLayout(new GridLayout(WIDTH,WIDTH));
       for(int x=0;x<WIDTH;x++)
           for(int y=0;y<WIDTH;y++) {
               board[x][y] = new JButton();
               if(map[x][y]==1) {
                   board[x][y].setIcon(grass);
                   board[x][y].setDisabledIcon(grass);
               }
               else if(map[x][y]==4) {
                   board[x][y].setIcon(water);
                   board[x][y].setDisabledIcon(water);
               }
                   else if(map[x][y]==0 || map[x][y]==2 || map[x][y]==3){
                   board[x][y].setIcon(dirt);
                   board[x][y].setDisabledIcon(dirt);
               }
               board[x][y].setEnabled(false);
               board[x][y].setBorder(emptyBorder);
               add(board[x][y]);
       }
   }
   
}