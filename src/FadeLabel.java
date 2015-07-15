
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;

public class FadeLabel extends JLabel {

    private boolean fade_state;
    private int time;
    private volatile float alpha;

    private class Fader implements Runnable {

        public void run() {

            if(fade_state) { //fade in
                while(!Thread.interrupted() && alpha < 1.0f) {
                    repaint();
                    alpha = Math.min(1.0f, alpha + 0.01f);
                    try {
                        Thread.sleep(time);
                    }
                    catch(InterruptedException ignored) {
                    }
                }
            }
            else { //fade out
                while (!Thread.interrupted() && alpha > 0.0f) {
                    repaint();
                    alpha = Math.max(0.0f, alpha - 0.01f);
                    try {
                         Thread.sleep(time);
                    }
                    catch (InterruptedException ignored) {
                    }
               }
            }

        }
    }


    //~ Methods --------------------------------------------------------------------------------------
    public FadeLabel(String string, int opts, boolean fade_state, int time) {
        super(string, opts);

        this.fade_state = fade_state;
        this.time = time;
        
        if(fade_state) { //fade in
            alpha = 1.0f;
        }
        else { //fade out
            alpha = 0.0f;
        }

    }

    public void paintComponent(Graphics aGraphics) {
        Graphics2D g2 = (Graphics2D)aGraphics;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        super.paintComponent(g2);
    }

    public void startFade() {
        if(fade_state) { //fade in
            alpha = 0.0f;
        }
        else { //fade out
            alpha = 1.0f;
        }
        
        new Thread(new Fader()).start();
    }
}
