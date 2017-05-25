//Evan Howard, 11 May 2017
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Towerboard extends JPanel
{
    private boolean infoBasic,spawnBasic,infoCircle,spawnCircle;
   private JButton[][] towers;
   private JLabel coinLabel,infoLabel;
   private int coin;
   public Towerboard()
   {
       spawnBasic=infoBasic=infoCircle=spawnCircle=false;
       coin = 650;
      setLayout(new BorderLayout());
      setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));

      JPanel labelPanel = new JPanel();
      labelPanel.setBackground(Scoreboard.BACKGROUND);
      labelPanel.setLayout(new BorderLayout());

      infoLabel = new JLabel();
      infoLabel.setFont(new Font("Monospaced",Font.BOLD,12));
      infoLabel.setPreferredSize(new Dimension(130,175));
      labelPanel.add(infoLabel,BorderLayout.SOUTH);

      coinLabel = new JLabel("Coins: "+coin);
       coinLabel.setFont(new Font("Monospaced",Font.BOLD,24));
       labelPanel.add(coinLabel,BorderLayout.NORTH);

       add(labelPanel,BorderLayout.NORTH);

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
              towers[x][y].setEnabled(false);
              towers[x][y].addMouseListener(new mouseListener(count));
              shop.add(towers[x][y]);
              count++;
          }
        towers[0][1].setEnabled(true);
          towers[0][0].setEnabled(true);

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

   private class mouseListener implements MouseListener {

       private int myTower;
       public mouseListener(int myTower) {
           this.myTower=myTower;
       }
       @Override
       public void mouseClicked(MouseEvent mouseEvent) {

       }

       @Override
       public void mousePressed(MouseEvent mouseEvent) {

       }

       @Override
       public void mouseReleased(MouseEvent mouseEvent) {

       }

       @Override
       public void mouseEntered(MouseEvent mouseEvent) {
        switch(myTower) {
            case 0: infoBasic=true;
            case 1: infoCircle=true;
            break;
        }
       }

       @Override
       public void mouseExited(MouseEvent mouseEvent) {
        switch(myTower) {
            case 0: infoBasic=false;
            case 1: infoCircle=false;
            break;
        }
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
                case 1: spawnCircle=true;
                break;
            }
        }
        }

        public boolean getSpawn(int x) {
       switch(x) {
           case 0: return spawnBasic;
           case 1: return spawnCircle;
           default: return false;
       }
        }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
        if(coin>=200)
            towers[0][0].setEnabled(true);
        else
            towers[0][0].setEnabled(false);
        if(coin>=360)
            towers[0][1].setEnabled(true);
        else
            towers[0][1].setEnabled(false);
    }

    public void setSpawn(int x) {
       switch(x) {
           case 0: spawnBasic = false;

           break;
       }



        }
   public void update() {
       if(infoBasic) {
           infoLabel.setText("<html><center>200 Coins<br>Spaghetti & Meatballs<br>A standard tower that shoots moderately fast</center></html>");
       }
       else if(infoCircle)
           infoLabel.setText("<html><center>360 Coins<br>Pepperoni Pizza<br>A slow tower that shoots in a circle</center></html>");
       else
           infoLabel.setText("");
       coinLabel.setText("Coins: "+coin);
   }
}