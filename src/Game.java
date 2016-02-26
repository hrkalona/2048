
import java.awt.Dimension;
import java.awt.Toolkit;
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
  protected JPanel top_panel3;
  protected JLabel score_label;
  protected JPanel score_panel;
  protected JLabel best_score_label;
  protected JPanel best_score_panel;
  protected JLabel moves_label;
  protected JPanel moves_panel;
  protected JPanel undo_panel;
  protected JPanel new_game_panel;
  protected JPanel offset_panel;
  protected JPanel offset_panel2;
  protected JLabel label_2048;
  protected JLabel message_label1;
  protected JLabel message_label2;
  protected JLabel undo;
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
        
        setUndecorated(true);
        
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
         
         new WinThread(this).start();
        
    }
    
    protected void gameOver() {
        
        new GameOverThread(this).start();
    
    }
    
    protected void endOfNumbers() {
        
        new EndOfNumbersThread(this).start();
    
    }
    
    public void howTo() {
        
        new HowToThread(this).start();
        
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
    
     public void setStateOfGame(StateOfGame state) {
        
        this.state = state;
        
    }
    
    public StateOfGame getStateOfGame() {
        
        return state;
        
    }
    
    protected abstract void startGame();
    
    protected abstract void restartGame();
    
    protected  abstract void save();
      
    protected abstract boolean load();
    
    public abstract void normal_move( int where );
    
}
