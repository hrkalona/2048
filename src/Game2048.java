
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class Game2048 extends Game {
  private JLabel[][] board;
  private Number[][] numbers; 
  private int score;
  private int moves;
  private int best_score;
  private Game2048 ptr;
  private boolean won = false;
  private ScoreThread score_thread;

    
    Game2048() {
        
        super();
        
        ptr = this;
        
        if(!load()) {
            best_score = 0;
        }
        
                  
        score = 0;
        moves = 0;
        
        numbers = new Number[4][4];

        board = new JLabel[4][4];

        board_panel = new JPanel();
        board_panel.setPreferredSize(new Dimension( 4 * 88 + 5 * 10, 4 * 88 + 5 * 10));
        board_panel.setBackground(new Color(187, 173, 160));
        board_panel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));

        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                 board[i][j] = new JLabel();
                 board[i][j].setPreferredSize(new Dimension(88, 88));
                 URL imageURL = getClass().getResource("/Icons/" + (numbers[i][j] == null ? 0 : numbers[i][j].getNumber()) + ".png" );
                 Image image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
                 ImageIcon icon = new ImageIcon(image2);
                 board[i][j].setIcon(icon);
                 board_panel.add(board[i][j]);
            }
        }
        

        /*SCORE */
        score_panel = new JPanel();
        score_panel.setPreferredSize(new Dimension(90, 52));
        score_panel.setBackground(new Color(187, 173, 160));
        score_panel.setLayout(new GridLayout(2, 1));
        
        JLabel score_label_1 = new JLabel("SCORE", SwingConstants.HORIZONTAL);
        score_label_1.setFont(new Font("default", Font.BOLD , 10 ));
        score_label_1.setForeground(new Color(239, 239, 238));
        
        score_label = new JLabel("" + score, SwingConstants.HORIZONTAL);
        score_label.setFont(new Font("default", Font.BOLD , 24 ));
        score_label.setForeground(new Color(239, 239, 238));
        
        score_panel.add(score_label_1);
        score_panel.add(score_label);
        
        
        moves_panel = new JPanel();
        moves_panel.setPreferredSize(new Dimension(68, 52));
        moves_panel.setBackground(new Color(187, 173, 160));
        moves_panel.setLayout(new GridLayout(2, 1));
        
        JLabel moves_label_1 = new JLabel("MOVES", SwingConstants.HORIZONTAL);
        moves_label_1.setFont(new Font("default", Font.BOLD , 10 ));
        moves_label_1.setForeground(new Color(239, 239, 238));
        
        moves_label = new JLabel("" + moves, SwingConstants.HORIZONTAL);
        moves_label.setFont(new Font("default", Font.BOLD , 24 ));
        moves_label.setForeground(new Color(239, 239, 238));
        
        moves_panel.add(moves_label_1);
        moves_panel.add(moves_label);
        
        
        /* BEST SCORE */
        best_score_panel = new JPanel();
        best_score_panel.setPreferredSize(new Dimension(90, 52));
        best_score_panel.setBackground(new Color(187, 173, 160));
        best_score_panel.setLayout(new GridLayout(2, 1));
        
        JLabel best_score_label_1 = new JLabel("BEST", SwingConstants.HORIZONTAL);
        best_score_label_1.setFont(new Font("default", Font.BOLD , 10 ));
        best_score_label_1.setForeground(new Color(239, 239, 238));
        
        best_score_label = new JLabel("" + best_score, SwingConstants.HORIZONTAL);
        best_score_label.setFont(new Font("default", Font.BOLD , 24 ));
        best_score_label.setForeground(new Color(239, 239, 238));
        
        best_score_panel.add(best_score_label_1);
        best_score_panel.add(best_score_label);
        
        JLabel label_2048 = new JLabel ("2048");

        label_2048.setFont(new Font("default", Font.BOLD , 60 ));
        label_2048.setForeground(new Color(119, 110, 101));
        
        top_panel = new JPanel();
        top_panel.setPreferredSize(new Dimension(4 * 88 + 5 * 10, 80));
        top_panel.setLayout(new FlowLayout());
        
        top_panel.add(label_2048);
        top_panel.add(moves_panel);
        top_panel.add(score_panel);
        top_panel.add(best_score_panel);
        
        
        top_panel2 = new JPanel();
        top_panel2.setPreferredSize(new Dimension(4 * 88 +  10, 30));
        top_panel2.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
        
        JLabel label2 = new JLabel("Join the numbers and get to the");
        label2.setFont(new Font("default", Font.PLAIN , 13 ));
        JLabel label3 = new JLabel(" 2048 tile!");
        label3.setFont(new Font("default", Font.BOLD , 13 ));
        top_panel2.add(label2);
        top_panel2.add(label3);
        
        JPanel top_panel3 = new JPanel();
        top_panel3.setPreferredSize(new Dimension(30, 30));
        top_panel3.setLayout(new FlowLayout());
        
        final JLabel sound = new JLabel();
        URL imageURL = getClass().getResource("/Icons/sound.png" );
        Image image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        ImageIcon icon = new ImageIcon(image2);
        sound.setIcon(icon);
        
        sound.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(sound_option) {
                    sound_option = false;
                    URL imageURL = getClass().getResource("/Icons/no-sound.png" );
                    Image image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
                    ImageIcon icon = new ImageIcon(image2);
                    sound.setIcon(icon);
                }
                else {
                    sound_option = true;
                    URL imageURL = getClass().getResource("/Icons/sound.png" );
                    Image image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
                    ImageIcon icon = new ImageIcon(image2);
                    sound.setIcon(icon);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

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
        
        top_panel3.add(sound);

        
        JPanel temp_panel = new JPanel();
        temp_panel.setPreferredSize(new Dimension(111, 15));
        temp_panel.setBackground(new Color(239, 239, 238));
        
        score_plus_label = new JLabel("", SwingConstants.HORIZONTAL);
        score_plus_label.setFont(new Font("default", Font.BOLD , 15 ));
        score_plus_label.setPreferredSize(new Dimension(90, 15));
        score_plus_label.setForeground(new Color(119, 110, 101));

        main_panel = new JPanel();
        main_panel.setPreferredSize(new Dimension(4 * 88 + 5 * 10 + 40, 4 * 88 + 5 * 10 + 240));
        main_panel.setBackground(new Color(239, 239, 238));
  
        main_panel.add(temp_panel);
        main_panel.add(score_plus_label);
        main_panel.add(top_panel);
        main_panel.add(top_panel2);
        main_panel.add(top_panel3);
        main_panel.add(board_panel);
    
        setLayout(new FlowLayout());
        add(main_panel);
        
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {

                if(state == StateOfGame.PLAY) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT :
                        case KeyEvent.VK_KP_LEFT:
                            normal_move(LEFT);
                            break;
                        case KeyEvent.VK_RIGHT:
                        case KeyEvent.VK_KP_RIGHT:
                            normal_move(RIGHT);
                            break;
                        case KeyEvent.VK_UP :
                        case KeyEvent.VK_KP_UP:
                            normal_move(UP);
                            break;
                        case KeyEvent.VK_DOWN :
                        case KeyEvent.VK_KP_DOWN:
                            normal_move(DOWN);
                            break;

                        case KeyEvent.VK_SPACE:
                            //Auto test = new Auto(ptr);
                            //test.start();
                            break;
                        }
                    }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
            }
            
        });
        
        state = StateOfGame.HOW_TO;
        
        new HowToThread(ptr).start();

    }
    
    @Override
    protected void startGame() {
        
        if(!load()) {
            best_score = 0;
                
            score = 0;
            moves = 0;
            numbers = new Number[4][4];
           
                        
            Random generator = new Random();

            do {
                int index_i = generator.nextInt(numbers.length);
                int index_j = generator.nextInt(numbers.length);

                if(numbers[index_i][index_j] == null) {
                    numbers[index_i][index_j] = new Number();
                    break;
                }

            } while(true);


            do {
                int index_i = generator.nextInt(numbers.length);
                int index_j = generator.nextInt(numbers.length);

                if(numbers[index_i][index_j] == null) {
                    numbers[index_i][index_j] = new Number();
                    break;
                }

            } while(true);
        }
               
        won = false;
        
        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                 URL imageURL = getClass().getResource("/Icons/" + (numbers[i][j] == null ? 0 : numbers[i][j].getNumber()) + ".png" );
                 Image image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
                 ImageIcon icon = new ImageIcon(image2);
                 board[i][j].setIcon(icon);
            }
        }
        
        score_label.setText("" + score);
        
        best_score_label.setText("" + best_score);
        
        moves_label.setText("" + moves);
        
        state = StateOfGame.PLAY; 
        
    }
    
    public Number[][] getNumbers() {
        return numbers;
    }
    
    @Override
    public void normal_move( int where ) {
        
        
        if(!won) {
            winCheck();
        }
        
        gameOverCheck();
        
        int[] result = move(where);
        
        /* insert random number */
        if(result[0] == 1) {
            
            for(int i = 0; i < numbers.length; i++) {
                for(int j = 0; j < numbers[i].length; j++) {
                    if(numbers[i][j] != null) {
                        numbers[i][j].resetNotUsed();
                    }
                }
            }
            
            Random generator = new Random();
            
            int index_i;
            int index_j;

            do {
                index_i = generator.nextInt(numbers.length);
                index_j = generator.nextInt(numbers.length);

                if(numbers[index_i][index_j] == null) {
                    numbers[index_i][index_j] = new Number();
                    break;
                }

            } while(true);
            
            if(sound_option) {
                playWav("/Sounds/tick.wav");
            }
            
            moves++;
            
            for(int i = 0; i < numbers.length; i++) {
                for(int j = 0; j < numbers[i].length; j++) {
                     URL imageURL = getClass().getResource("/Icons/" + (numbers[i][j] == null ? 0 : numbers[i][j].getNumber())  + ".png" );
                     Image image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
                     ImageIcon icon = new ImageIcon(image2);
                     board[i][j].setIcon(icon);                      
                }
            }
            
            score += result[1];
            score_label.setText("" + score);
            
            moves_label.setText("" + moves);

            if(score > best_score) {
                best_score = score;
                best_score_label.setText("" + best_score);
            }
            
            if(result[1] != 0) {
                if(score_thread != null && score_thread.isAlive()) {
                    score_thread.setClear(false);
                }
                score_thread = new ScoreThread(score_plus_label, result[1]);
                score_thread.start();
            }

        }

    }

    private void winCheck() {
         

         for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                if(numbers[i][j] != null && numbers[i][j].is2048()) {
                    won = true;
                    break;
                }
            }
        }
         
        if(won) {
            win();
        }
         
    }
    
    private void gameOverCheck() {
         boolean full = true;
        
        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                if(numbers[i][j] == null) {
                    full = false;
                }
            }
        }

        
        if(full) {
            boolean can_move = false;

            for(int i = 0; i < numbers.length; i++) {
                for(int j = 0; j < numbers[i].length; j++) {
                    if(i < numbers.length - 1 && numbers[i][j].getNumber() == numbers[i + 1][j].getNumber()) {
                        can_move = true;
                        break;
                    }

                    if(i > 0 && numbers[i][j].getNumber() == numbers[i - 1][j].getNumber()) {
                        can_move = true;
                        break;
                    }

                    if(j < numbers.length - 1 && numbers[i][j].getNumber() == numbers[i][j + 1].getNumber()) {
                        can_move = true;
                        break;
                    }

                    if(j > 0 && numbers[i][j].getNumber() == numbers[i][j - 1].getNumber()) {
                        can_move = true;
                        break;
                    }
                }
            }

            if(!can_move) {
                gameOver();
            }
        }
    }
    
    public int[] move(int where) {
        
        int moved = 0;
        
        int achieved_score = 0;
        
        if(where == UP) {
            for(int j = 0; j < numbers.length; j++) {
                //int boundary = 0;
                for(int i = 0; i <  numbers[j].length; i++) {
                     int k = i - 1;
                     while(k >= 0) {
                         if(numbers[k][j] == null && numbers[k + 1][j] != null) {
                             numbers[k][j] = numbers[k + 1][j];
                             numbers[k + 1][j] = null;
                             moved = 1;
                         }
                         else if(numbers[k][j] != null && numbers[k + 1][j] != null && numbers[k][j].getNumber() == numbers[k + 1][j].getNumber() && numbers[k][j].notUsed() && numbers[k + 1][j].notUsed()) {//&& k >= boundary) {
                             numbers[k][j].times2();
                             achieved_score += numbers[k][j].getNumber();
                             numbers[k + 1][j] = null;
                             moved = 1;
                             //boundary = k + 1;
                         }
                         k--;
                     }
                }
            }
        }
        else if(where == DOWN) {
            for(int j = 0; j < numbers.length; j++) {
                //int boundary = numbers[j].length;
                for(int i = numbers[j].length - 1; i >= 0 ; i--) {
                    int k = i + 1;
                    while(k < numbers[j].length) {
                         if(numbers[k][j] == null && numbers[k - 1][j] != null) {
                             numbers[k][j] = numbers[k - 1][j];
                             numbers[k - 1][j] = null;
                             moved = 1;
                         }
                         else if(numbers[k][j] != null && numbers[k - 1][j] != null && numbers[k][j].getNumber() == numbers[k - 1][j].getNumber() && numbers[k][j].notUsed() && numbers[k - 1][j].notUsed()) { //&& k <= boundary) {
                             numbers[k][j].times2();
                             achieved_score += numbers[k][j].getNumber();
                             numbers[k - 1][j] = null;
                             moved = 1;
                             //boundary = k - 1;
                         }
                         k++;
                    }              
                }
            }
        }
        else if(where == LEFT) {
            for(int i = 0; i < numbers.length; i++) {
                //int boundary = 0;
                for(int j = 0; j < numbers[i].length; j++) {
                    int k = j - 1;
                    while(k >= 0) {
                         if(numbers[i][k] == null && numbers[i][k + 1] != null) {
                             numbers[i][k] = numbers[i][k + 1];
                             numbers[i][k + 1] = null;
                             moved = 1;
                         }
                         else if(numbers[i][k] != null &&  numbers[i][k + 1] != null && numbers[i][k].getNumber() == numbers[i][k + 1].getNumber() && numbers[i][k].notUsed() && numbers[i][k + 1].notUsed()) {//k >= boundary) {
                             numbers[i][k].times2();
                             achieved_score += numbers[i][k].getNumber();
                             numbers[i][k + 1] = null;
                             moved = 1;
                             //boundary = k + 1;
                         }
                         k--;
                    }
                }
            }
        }
        else if(where == RIGHT) {
            for(int i = 0; i < numbers.length; i++) {
                //int boundary = numbers[i].length;
                for(int j = numbers[i].length - 1; j >= 0; j--) {
                     int k = j + 1;
                     while(k < numbers[i].length) {
                         if(numbers[i][k] == null && numbers[i][k - 1] !=null) {
                             numbers[i][k] = numbers[i][k - 1];
                             numbers[i][k - 1] = null;
                             moved = 1;
                         }
                         else if(numbers[i][k] != null && numbers[i][k - 1] != null && numbers[i][k].getNumber() == numbers[i][k - 1].getNumber() && numbers[i][k].notUsed() && numbers[i][k - 1].notUsed()) {//k <= boundary) {
                             numbers[i][k].times2();
                             achieved_score += numbers[i][k].getNumber();
                             numbers[i][k - 1] = null;
                             moved = 1;
                             //boundary = k - 1;
                         }
                         k++;
                    }
                }
            }
        }

        int[] result = new int[2];
        
        result[0] = moved;
        result[1] = achieved_score;
        
        return result;
    }
    
   
    
    @Override
    protected void restartGame() {
        numbers = new Number[4][4];
        
        score = 0;
        
        moves = 0;
        
        won = false;
        
        Random generator = new Random();
        
        do {
            int index_i = generator.nextInt(numbers.length);
            int index_j = generator.nextInt(numbers.length);
            
            if(numbers[index_i][index_j] == null) {
                numbers[index_i][index_j] = new Number();
                break;
            }
            
        } while(true);
        
        do {
            int index_i = generator.nextInt(numbers.length);
            int index_j = generator.nextInt(numbers.length);
            
            if(numbers[index_i][index_j] == null) {
                numbers[index_i][index_j] = new Number();
                break;
            }
            
        } while(true);

        
        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                 URL imageURL = getClass().getResource("/Icons/" + (numbers[i][j] == null ? 0 : numbers[i][j].getNumber()) + ".png" );
                 Image image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
                 ImageIcon icon = new ImageIcon(image2);
                 board[i][j].setIcon(icon);
            }
        }

        score_label.setText("" + score);
        
        moves_label.setText("" + moves);
        
        state = StateOfGame.PLAY;

    }
    
    @Override
    protected void save() {
        ObjectOutputStream file = null;
        try {
            file = new ObjectOutputStream(new FileOutputStream("Gamesave.dat"));
            
            for(int i = 0; i < numbers.length; i++) {
                for(int j = 0; j < numbers[i].length; j++) {
                    if(numbers[i][j] != null) {
                        numbers[i][j].resetNotUsed();
                    }
                }
            }
            
            file.writeObject(numbers);
            file.writeInt(score);
            file.writeInt(best_score);
            file.writeInt(moves);
            
            file.close();
        } catch (IOException ex) {

        }
    }
    
    @Override
    protected boolean load() {
        ObjectInputStream file = null;
        try {
            file = new ObjectInputStream(new FileInputStream("Gamesave.dat"));

            numbers = (Number[][]) file.readObject();
            score = file.readInt();
            best_score = file.readInt();
            moves = file.readInt();
            
            file.close();
        }
        catch (FileNotFoundException ex) {
            return false;
        }
        catch (IOException ex) {
            return false;
        }
        catch (ClassNotFoundException ex) {
            return false;
        }
        catch (Exception ex) {
            return false;
        }
        
        return true;

            
    }

    public JPanel getBoardPanel() {
        
        return board_panel;
        
    }
    
    public static void main(String[] args) {
        
        Game2048 game = new Game2048();
        
        game.setVisible(true);
        
    }
    
}
