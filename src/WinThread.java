
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author hrkalona2
 */
public class WinThread extends Thread {
  private Game ptr;
  
    public WinThread(Game ptr) {
        this.ptr = ptr;
    }

    public void run() {

        ptr.setStateOfGame(StateOfGame.WIN);      
        
        final JPanel p = new JPanel() {
            public void paintComponent(Graphics g) {
                g.setColor(new Color(237, 194, 46, 200));
                g.fillRect(0, 0, getWidth(), getHeight());

            }
        };

        p.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        p.setLayout(new GridBagLayout());

        JLabel label = new JLabel("You Win!");
        label.setFont(new Font("default", Font.BOLD, 70));
        label.setForeground(new Color(239, 239, 238));

        final JLabel label2 = new JLabel("Keep Going");
        label2.setFont(new Font("default", Font.BOLD, 40));
        label2.setForeground(new Color(239, 239, 238));

        label2.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                p.setVisible(false);
                ptr.setStateOfGame(StateOfGame.PLAY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        final JLabel label3 = new JLabel("Try Again");
        label3.setFont(new Font("default", Font.BOLD, 40));
        label3.setForeground(new Color(239, 239, 238));

        label3.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                p.setVisible(false);
                ptr.restartGame();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;

        p.add(label, c);

        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;

        p.add(new JLabel("      "), c);

        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 2;

        p.add(label2, c);

        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 3;

        p.add(label3, c);

        ptr.setGlassPane(p);

        p.setVisible(true);

        ptr.revalidate();
        ptr.repaint();
   
    }
}