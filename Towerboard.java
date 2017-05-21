//Evan Howard, 11 May 2017
import javax.swing.*;
import java.awt.*;

public class Towerboard extends JPanel
{
   private JButton[][] towers;
   private JLabel coinLabel;
   private int coin;
   public Towerboard()
   {
       coin = 0;
      setLayout(new BorderLayout());
      setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));

      coinLabel = new JLabel("Coins: "+coin);
       coinLabel.setFont(new Font("Monospaced",Font.BOLD,24));
       add(coinLabel,BorderLayout.NORTH);
      towers = new JButton[2][3];

      JPanel sCancel = new JPanel();
      sCancel.setBackground(Scoreboard.BACKGROUND);
      sCancel.setLayout(new GridLayout(2,1,0,10));

      JPanel shop = new JPanel();
      shop.setBackground(Scoreboard.BACKGROUND);
      shop.setLayout(new GridLayout(3,2,10,10));

      for(int x=0;x<2;x++)
         for(int y=0;y<3;y++) {
         towers[x][y] = new JButton();
         towers[x][y].setPreferredSize(new Dimension(64,72));
         shop.add(towers[x][y]);
         }

         JButton cancel = new JButton("Cancel");
         cancel.setFont(new Font("Monospaced",Font.BOLD, 24));

         sCancel.add(shop);
       sCancel.add(cancel);
         add(sCancel,BorderLayout.SOUTH);

      setBackground(Scoreboard.BACKGROUND);
   }
   public void addCoins(int coin) {
       this.coin+=coin;
   }
   public void update() {
       coinLabel.setText("Coins: "+coin);
   }
}