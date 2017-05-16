//Evan Howard, 11 May 2017
import javax.swing.*;
import java.awt.*;

 public class Gameboard extends JPanel
{
    private int[][] map = {
            {1,1,0,1,1,1,1,1,1,1},
            {1,1,0,0,0,0,1,1,1,1},
            {1,1,1,1,1,0,1,1,1,1},
            {1,1,1,1,1,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,0,1},
            {1,1,1,1,1,1,1,1,0,1},
            {0,0,0,0,1,1,1,1,0,1},
            {1,1,1,0,1,1,1,1,0,1},
            {1,1,1,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1},
    };
    private JButton[][] board;
   public Gameboard()
   {
        board = new JButton[10][10];
       ImageIcon grass = new ImageIcon("Textures/Grass.jpg");
       ImageIcon dirt = new ImageIcon("Textures/Dirt.jpg");
       setLayout(new GridLayout(10,10));
       for(int x=0;x<10;x++)
           for(int y=0;y<10;y++) {
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