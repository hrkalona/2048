
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class ScoreThread extends Thread {
  private JLabel score_plus_label;
  private long time;
  private int score;
  private boolean clear;
  
    ScoreThread(JLabel score_plus_label, int score) {
        this.score_plus_label = score_plus_label;
        this.score = score;
        time = System.currentTimeMillis();
        clear = true;
    }
    
    @Override
    public void run() {


        score_plus_label.setText("+" + score);
        while(System.currentTimeMillis() - time < 450) {
            yield();
        }
        
        if(clear) {
            score_plus_label.setText("");
        }
    }
    
    public void setClear(boolean opt) {
        
        clear = opt;
        
    }
    
}
