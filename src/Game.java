
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;




/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
enum StateOfGame {HOW_TO, PLAY, WIN, END_GAME, MAX_VALUE_REACHED};

public abstract class Game extends JFrame {
  protected JPanel board_panel;
  protected JPanel main_panel;
  protected FadeLabel score_plus_label;
  protected JPanel top_panel;
  protected JPanel top_panel2;
  protected JLabel score_label;
  protected JPanel score_panel;
  protected JLabel best_score_label;
  protected JPanel best_score_panel;
  protected JLabel moves_label;
  protected JPanel moves_panel;
  protected JPanel undo_panel;
  protected boolean sound_option;
  protected StateOfGame state;
  public static int UP = 0;
  public static int DOWN = 1;
  public static int RIGHT = 2;
  public static int LEFT = 3;
    
    public Game() {
        super();
        setSize(4 * 88 + 5 * 10 + 40, 4 * 88 + 5 * 10 + 220);
        setResizable(false);
        
        //ptr = this;
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (ClassNotFoundException ex) {} 
        catch (InstantiationException ex) {} 
        catch (IllegalAccessException ex) {} 
        catch (UnsupportedLookAndFeelException ex) {}
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // Get the current screen size
	Dimension scrnsize = toolkit.getScreenSize();
        
        if(scrnsize.getHeight() > getHeight()) {
            setLocation((int)((scrnsize.getWidth() / 2) - (getWidth() / 2)), (int)((scrnsize.getHeight() / 2) - (getHeight() / 2)) - 23);
        }
        else {
            setLocation((int)((scrnsize.getWidth() / 2) - (getWidth() / 2)), 0);
        }
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                if(state != StateOfGame.HOW_TO) {
                    save();
                }
                
                System.exit(0);
  
            }
        });

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icons/game_icon.png")));
        
        sound_option = true;
        
        
    }
    
     protected void win() {
        
        state = StateOfGame.WIN;
        
        final JPanel win_game = new JPanel();
        win_game.setPreferredSize(new Dimension(getWidth(), getHeight()));
        win_game.setBackground(new Color(237, 194, 46, 200));
        win_game.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
      


        JLabel label = new JLabel("You Win!");
        label.setFont(new Font("default", Font.BOLD , 70 ));
        label.setForeground(new Color(239, 239, 238));
 
        
        
        final JLabel label2 = new JLabel("Keep Going");
        label2.setFont(new Font("default", Font.BOLD , 40 ));
        label2.setForeground(new Color(239, 239, 238));

        
        
        label2.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                getGlassPane().setVisible(false);
                state = StateOfGame.PLAY;
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
        label3.setFont(new Font("default", Font.BOLD , 40 ));
        label3.setForeground(new Color(239, 239, 238));
        
        label3.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                getGlassPane().setVisible(false);
                restartGame();
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

        win_game.add(label, c);  
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        
        win_game.add(new JLabel("      "), c);
  
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 2;
        
        win_game.add(label2, c); 
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 3;
        
        win_game.add(label3, c); 
        
        //add(win_game);
        
        setGlassPane(win_game); 
        getGlassPane().setVisible(true);
        
    }
    
    protected void gameOver() {
        
        state = StateOfGame.END_GAME;
        
        final JPanel end_game = new JPanel();
        end_game.setPreferredSize(new Dimension(getWidth(), getHeight()));
        end_game.setBackground(new Color(187, 173, 160, 200));
        end_game.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
      


        JLabel label = new JLabel("Game Over!");
        label.setFont(new Font("default", Font.BOLD , 70 ));
        label.setForeground(new Color(239, 239, 238));
 
        
        
        final JLabel label2 = new JLabel("Try Again");
        label2.setFont(new Font("default", Font.BOLD , 40 ));
        label2.setForeground(new Color(239, 239, 238));
        
        
        label2.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                getGlassPane().setVisible(false);
                restartGame();
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
         
        
        final JLabel label3 = new JLabel("Exit");
        label3.setFont(new Font("default", Font.BOLD , 40 ));
        label3.setForeground(new Color(239, 239, 238));
        
        label3.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(state != StateOfGame.HOW_TO) {
                    save();
                }
                
                System.exit(0);
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

        end_game.add(label, c);  
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        
        end_game.add(new JLabel("      "), c);
  
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 2;
        
        end_game.add(label2, c); 
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 3;
        
        end_game.add(label3, c); 
        
        add(end_game);
        
        setGlassPane(end_game); 
        getGlassPane().setVisible(true);
    
    }
    
    protected void endOfNumbers() {
        
        state = StateOfGame.MAX_VALUE_REACHED;
        
        final JPanel end_game = new JPanel();
        end_game.setPreferredSize(new Dimension(getWidth(), getHeight()));
        end_game.setBackground(new Color(34, 177, 76, 200));
        end_game.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
      


        JLabel label = new JLabel("The End!");
        label.setFont(new Font("default", Font.BOLD , 70 ));
        label.setForeground(new Color(239, 239, 238));
 
        
        
        final JLabel label2 = new JLabel("Try Again");
        label2.setFont(new Font("default", Font.BOLD , 40 ));
        label2.setForeground(new Color(239, 239, 238));
        
        
        label2.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                getGlassPane().setVisible(false);
                restartGame();
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
         
        
        final JLabel label3 = new JLabel("Exit");
        label3.setFont(new Font("default", Font.BOLD , 40 ));
        label3.setForeground(new Color(239, 239, 238));
        
        label3.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(state != StateOfGame.HOW_TO) {
                    save();
                }
                
                System.exit(0);
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

        end_game.add(label, c);  
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        
        end_game.add(new JLabel("      "), c);
  
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 2;
        
        end_game.add(label2, c); 
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 3;
        
        end_game.add(label3, c); 
        
        add(end_game);
        
        setGlassPane(end_game); 
        getGlassPane().setVisible(true);
    
    }
    
    protected void howTo() {
        
        state = StateOfGame.HOW_TO;
        
        final JPanel how_to_panel = new JPanel();
        how_to_panel.setPreferredSize(new Dimension(getWidth(), getHeight()));
        how_to_panel.setBackground(new Color(187, 173, 160, 200));
        how_to_panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        
        JPanel how_panel = new JPanel();
        
        how_panel.setPreferredSize(new Dimension(4 * 88 + 5 * 10, 80));
        how_panel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        how_panel.setOpaque(false);
        
        JLabel label4 = new JLabel("HOW TO PLAY: ");
        label4.setFont(new Font("default", Font.BOLD , 15 ));
        JLabel label5 = new JLabel("use your ");
        label5.setFont(new Font("default", Font.PLAIN , 15 ));
        JLabel label6 = new JLabel("arrows keys ");
        label6.setFont(new Font("default", Font.BOLD , 15 ));
        JLabel label7 = new JLabel("to move the tiles.");
        label7.setFont(new Font("default", Font.PLAIN , 15 ));
        JLabel label8 = new JLabel("When two tiles with the same number touch, they merge");
        label8.setFont(new Font("default", Font.PLAIN , 15 ));
        JLabel label9 = new JLabel("into one!");
        label9.setFont(new Font("default", Font.PLAIN , 15 ));
 
        
        how_panel.add(label4);
        how_panel.add(label5);
        how_panel.add(label6);
        how_panel.add(label7);
        how_panel.add(label8);
        how_panel.add(label9);
      

        JPanel arrows = new JPanel();
        arrows.setPreferredSize(new Dimension(3 * 59 + 4 * 3, 2 * 59 + 3 * 3));
        arrows.setLayout(new FlowLayout(FlowLayout.LEADING, 3, 3));
        arrows.setOpaque(false);


        JLabel label_up = new JLabel();
        label_up.setPreferredSize(new Dimension(59, 59));
        label_up.setIcon(getIcon("/Icons/up.png"));
        

        JLabel label_left = new JLabel();
        label_left.setPreferredSize(new Dimension(59, 59));
        label_left.setIcon(getIcon("/Icons/left.png"));
        

        JLabel label_down = new JLabel();
        label_down.setPreferredSize(new Dimension(59, 59));
        label_down.setIcon(getIcon("/Icons/down.png"));

        JLabel label_right = new JLabel();
        label_right.setPreferredSize(new Dimension(59, 59));
        label_right.setIcon(getIcon("/Icons/right.png"));
        
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
        label2.setFont(new Font("default", Font.BOLD , 40 ));
        label2.setForeground(new Color(239, 239, 238));
        
        
        label2.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                getGlassPane().setVisible(false);
                startGame();
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

        how_to_panel.add(how_panel, c);  

        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;

        how_to_panel.add(arrows, c);  
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 2;
        
        how_to_panel.add(new JLabel("      "), c);
  
        
        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 3;
        
        how_to_panel.add(label2, c); 

        add(how_to_panel);
        
        setGlassPane(how_to_panel); 
        getGlassPane().setVisible(true);
        
    }
    
    protected AudioStream playWav(String path) {

        InputStream sound_stream_in = null;
        try {
            sound_stream_in = getClass().getResource(path).openStream();
        }
        catch(IOException ex) {}


        AudioStream as = null;
        try {
            as = new AudioStream(sound_stream_in);
        }
        catch(IOException ex) {}

        AudioPlayer.player.start(as);

        return as;

    }
    
    protected ImageIcon getIcon(String path) {
        
        return new ImageIcon(getClass().getResource(path));
    }
    
    protected abstract void startGame();
    
    protected abstract void restartGame();
    
    protected  abstract void save();
      
    protected abstract boolean load();
    
    public abstract void normal_move( int where );
    
}
