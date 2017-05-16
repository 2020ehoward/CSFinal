//Evan Howard, 11 May 2017
import javax.swing.*;
import java.awt.*;

 public class Gameboard extends JPanel
{
    private final int WIDTH = 12;
    private int[][] map = {
            {1,1,0,1,1,1,1,1,1,1,1,1,1,},
            {1,1,0,0,0,0,1,1,1,1,1,1,1,},
            {1,1,1,1,1,0,1,1,1,1,1,1,1,},
            {1,1,1,1,1,0,0,0,0,1,1,1,1,},
            {1,1,1,1,1,1,1,1,0,1,1,1,1,},
            {1,1,1,1,1,1,1,1,0,1,1,1,1,},
            {0,0,0,0,1,1,1,1,0,1,1,1,1,},
            {1,1,1,0,1,1,1,1,0,1,1,1,1,},
            {1,1,1,0,0,0,0,0,0,1,1,1,1,},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,}
    };
    private JButton[][] board;
   public Gameboard()
   {
        board = new JButton[WIDTH][WIDTH];


       ImageIcon grass = new ImageIcon("Textures/Grass.jpg");
       ImageIcon dirt = new ImageIcon("Textures/Dirt.jpg");

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
                   else {
                   board[x][y].setIcon(dirt);
                   board[x][y].setDisabledIcon(dirt);
               }
               board[x][y].setEnabled(false);
               add(board[x][y]);
       }
   }
   
}