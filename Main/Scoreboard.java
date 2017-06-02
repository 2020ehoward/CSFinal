package Main;//Evan Howard, 11 May 2017
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Scoreboard extends JPanel
{   
   public static final Color BACKGROUND = new Color(255, 227, 191);

   private boolean isPaused;
   private int life,round,speed;
   private JLabel lifeLabel,roundLabel;
   private JToggleButton pause,speed1,speed2,speed3;
   private MainPanel mPanel;
   public Scoreboard(MainPanel mPanel)
   {
       this.mPanel = mPanel;
       life = 150;
       round = 0;
       speed = 10;
       isPaused=true;

       setLayout(new BorderLayout());

       JPanel speedPanel = new JPanel();
       speedPanel.setBackground(BACKGROUND);

       pause = new JToggleButton(new ImageIcon("Textures/Buttons/Speed0.png"),true);
       pause.addActionListener(new speedListener(0));
       speedPanel.add(pause);

       speed1 = new JToggleButton(new ImageIcon("Textures/Buttons/Speed1.png"));
       speed1.addActionListener(new speedListener(1));
       speedPanel.add(speed1);

       speed2 = new JToggleButton(new ImageIcon("Textures/Buttons/Speed2.png"));
       speed2.addActionListener(new speedListener(2));
       speedPanel.add(speed2);

       speed3 = new JToggleButton(new ImageIcon("Textures/Buttons/Speed3.png"));
       speed3.addActionListener(new speedListener(3));
       speedPanel.add(speed3);

       add(speedPanel,BorderLayout.WEST);

        lifeLabel = new JLabel("        Life Remaining: "+life);
        lifeLabel.setFont(new Font("Monospaced",Font.BOLD,16));
        roundLabel = new JLabel("Round: "+round+"    ");
       roundLabel.setFont(new Font("Monospaced",Font.BOLD,16));

        add(lifeLabel,BorderLayout.CENTER);
        add(roundLabel,BorderLayout.EAST);

      setBackground(BACKGROUND);
   }

    public void loseLife(int damage) {
        this.life-=damage;
    }

    public int getLife() {
        return life;
    }

    public void nextRound() {
        this.round++;
    }

    public int getRound() {
        return round;
    }

    public void pause() {
       pause.doClick();
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void endGame() {
       pause();
       pause.setEnabled(false);
       speed1.setEnabled(false);
        speed2.setEnabled(false);
        speed3.setEnabled(false);
    }

    private class speedListener implements ActionListener {

       int mySpeed;
        public speedListener(int x) {
            mySpeed = x;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            JToggleButton temp = (JToggleButton)e.getSource();
            if(temp.isSelected()) {
                switch (mySpeed) {
                    case 0:
                        isPaused=true;
                        break;
                    case 1:
                        isPaused=false;
                        speed = 50;
                        break;
                    case 2:
                        isPaused=false;
                        speed = 10;
                        break;
                    case 3:
                        isPaused=false;
                        speed = 2;
                        break;
                }
                if(!pause.equals(temp) && pause.isSelected())
                    pause.doClick();
                if(!speed1.equals(temp) && speed1.isSelected())
                    speed1.doClick();
                if(!speed2.equals(temp) && speed2.isSelected())
                    speed2.doClick();
                if(!speed3.equals(temp) && speed3.isSelected())
                    speed3.doClick();
                mPanel.setSpeed(speed);
            }
            else {
                switch(mySpeed) {
                    case 0:
                        if(isPaused)
                            temp.doClick();
                        break;
                    case 1:
                        if(speed == 50 && !isPaused)
                            temp.doClick();
                        break;
                    case 2:
                        if(speed == 10 && !isPaused)
                            temp.doClick();
                        break;
                    case 3:
                        if(speed == 2 && !isPaused)
                            temp.doClick();
                        break;
                }
            }
        }
    }
    public void update() {
        lifeLabel.setText("        Life Remaining: "+life);
        roundLabel.setText("Round: "+round+"    ");

        if(life==0)
            endGame();
   }
   
}