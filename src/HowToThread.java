
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
public class HowToThread extends Thread {

    private Game ptr;

    public HowToThread(Game ptr) {
        this.ptr = ptr;
    }

    public void run() {

        ptr.setStateOfGame(StateOfGame.HOW_TO);

        final JPanel p = new JPanel() {
            public void paintComponent(Graphics g) {
                g.setColor(new Color(187, 173, 160, 200));
                g.fillRect(0, 0, getWidth(), getHeight());

            }
        };

        p.setOpaque(false);

        GridBagConstraints c = new GridBagConstraints();
        p.setLayout(new GridBagLayout());

        JPanel how_panel = new JPanel();

        how_panel.setPreferredSize(new Dimension(4 * 88 + 5 * 10, 80));
        how_panel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        how_panel.setOpaque(false);

        JLabel label4 = new JLabel("HOW TO PLAY: ");
        label4.setFont(new Font("default", Font.BOLD, 15));
        JLabel label5 = new JLabel("use your ");
        label5.setFont(new Font("default", Font.PLAIN, 15));
        JLabel label6 = new JLabel("arrows keys ");
        label6.setFont(new Font("default", Font.BOLD, 15));
        JLabel label7 = new JLabel("to move the tiles.");
        label7.setFont(new Font("default", Font.PLAIN, 15));
        JLabel label8 = new JLabel("When two tiles with the same number touch, they merge");
        label8.setFont(new Font("default", Font.PLAIN, 15));
        JLabel label9 = new JLabel("into one!");
        label9.setFont(new Font("default", Font.PLAIN, 15));

        how_panel.add(label4);
        how_panel.add(label5);
        how_panel.add(label6);
        how_panel.add(label7);
        how_panel.add(label8);
        how_panel.add(label9);

        JPanel note_panel = new JPanel();

        note_panel.setPreferredSize(new Dimension(4 * 88 + 5 * 10, 80));
        note_panel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        note_panel.setOpaque(false);

        JLabel label10 = new JLabel("NOTE: ");
        label10.setFont(new Font("default", Font.BOLD, 15));
        JLabel label11 = new JLabel("you may use the undo option once per game. ");
        label11.setFont(new Font("default", Font.PLAIN, 15));

        note_panel.add(label10);
        note_panel.add(label11);

        JPanel arrows = new JPanel();
        arrows.setPreferredSize(new Dimension(3 * 59 + 4 * 3, 2 * 59 + 3 * 3));
        arrows.setLayout(new FlowLayout(FlowLayout.LEADING, 3, 3));
        arrows.setOpaque(false);

        JLabel label_up = new JLabel();
        label_up.setPreferredSize(new Dimension(59, 59));
        label_up.setIcon(ptr.getIcon("/Icons/up.png"));

        JLabel label_left = new JLabel();
        label_left.setPreferredSize(new Dimension(59, 59));
        label_left.setIcon(ptr.getIcon("/Icons/left.png"));

        JLabel label_down = new JLabel();
        label_down.setPreferredSize(new Dimension(59, 59));
        label_down.setIcon(ptr.getIcon("/Icons/down.png"));

        JLabel label_right = new JLabel();
        label_right.setPreferredSize(new Dimension(59, 59));
        label_right.setIcon(ptr.getIcon("/Icons/right.png"));

        JLabel temp = new JLabel("");
        temp.setPreferredSize(new Dimension(59, 59));

        JLabel temp2 = new JLabel("");
        temp2.setPreferredSize(new Dimension(59, 59));

        arrows.add(temp);
        arrows.add(label_up);
        arrows.add(temp2);
        arrows.add(label_left);
        arrows.add(label_down);
        arrows.add(label_right);

        final JLabel label2 = new JLabel("Start");
        label2.setFont(new Font("default", Font.BOLD, 40));
        label2.setForeground(new Color(239, 239, 238));

        label2.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                p.setVisible(false);
                ptr.startGame();
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
        
        p.add(new JLabel("      "), c);
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        
        p.add(new JLabel("      "), c);
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 2;
        
        p.add(new JLabel("      "), c);
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 3;

        p.add(how_panel, c);  

        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 4;
        
        p.add(note_panel, c);  

         
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 5;
        
        p.add(arrows, c); 
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 6;
        
        p.add(new JLabel("      "), c);
  
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 7;
        
        p.add(label2, c);

        ptr.setGlassPane(p);

        p.setVisible(true);

        ptr.revalidate();
        ptr.repaint();

    }
}
