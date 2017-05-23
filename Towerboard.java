//Evan Howard, 11 May 2017
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Towerboard extends JPanel
{
    private boolean spawnBasic;
   private JButton[][] towers;
   private JLabel coinLabel;
   private int coin;
   public Towerboard()
   {
       spawnBasic=false;
       coin = 0;
      setLayout(new BorderLayout());
      setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));

      coinLabel = new JLabel("Coins: "+coin);
       coinLabel.setFont(new Font("Monospaced",Font.BOLD,24));
       add(coinLabel,BorderLayout.NORTH);
      towers = new JButton[2][3];
      ImageIcon[] towerTextures = new ImageIcon[6];

      for(int i=0;i<towerTextures.length;i++)
          towerTextures[i] = new ImageIcon(new ImageIcon("Textures/Towers/Tower"+i+".png").getImage().getScaledInstance(64,72, Image.SCALE_SMOOTH));

      JPanel sCancel = new JPanel();
      sCancel.setBackground(Scoreboard.BACKGROUND);
      sCancel.setLayout(new GridLayout(2,1,0,10));

      JPanel shop = new JPanel();
      shop.setBackground(Scoreboard.BACKGROUND);
      shop.setLayout(new GridLayout(3,2,10,10));

      int count = 0;

      for(int x=0;x<2;x++)
          for(int y=0;y<3;y++) {
            towers[x][y]=new JButton(towerTextures[count]);
              towers[x][y].setBackground(Scoreboard.BACKGROUND);
              towers[x][y].addActionListener(new towerListener(count));
              towers[x][y].setPreferredSize(new Dimension(64,72));
              shop.add(towers[x][y]);
              count++;
          }

         JButton cancel = new JButton("Cancel");
      cancel.addActionListener(new cancelListener());
         cancel.setFont(new Font("Monospaced",Font.BOLD, 24));

         sCancel.add(shop);
       sCancel.add(cancel);
         add(sCancel,BorderLayout.SOUTH);

      setBackground(Scoreboard.BACKGROUND);
   }

   private class cancelListener implements ActionListener {
       @Override
       public void actionPerformed(ActionEvent actionEvent) {
           for(int i=0;i<towers.length;i++)
               setSpawn(i);
       }
   }

    private class towerListener implements ActionListener {

        int myTower;
        public towerListener(int x) {
            myTower = x;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch(myTower) {
                case 0: spawnBasic=true;
                break;
            }
        }
        }

        public boolean getSpawn(int x) {
       switch(x) {
           case 0: return spawnBasic;
           default: return false;
       }
        }

        public void setSpawn(int x) {
       switch(x) {
           case 0: spawnBasic = false;
           break;
       }

        }
   public void addCoins(int coin) {
       this.coin+=coin;
   }
   public void update() {
       coinLabel.setText("Coins: "+coin);
   }
}