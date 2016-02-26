
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hrkalona2
 */
public class Game2048 extends Game {

    private FadeLabel[][] board;
    private Number[][] numbers;
    private Number[][] backup_numbers;
    private int backup_score;
    private int backup_best_score;
    private int backup_moves;
    private int score;
    private int moves;
    private int best_score;
    private boolean used_undo;
    private Game2048 ptr;
    private boolean won = false;
    private boolean light_mode;

    Game2048() {

        super();

        setTitle("2048");

        ptr = this;

        if(!load()) {
            best_score = 0;
        }

        getContentPane().setBackground(new Color(239, 239, 238));

        score = 0;
        moves = 0;

        backup_score = score;
        backup_best_score = best_score;
        backup_moves = moves;

        used_undo = false;

        numbers = new Number[4][4];
        backup_numbers = new Number[4][4];

        board = new FadeLabel[4][4];

        board_panel = new RoundedPanel(false, true, false, 15);
        board_panel.setPreferredSize(new Dimension(4 * 88 + 5 * 10, 4 * 88 + 5 * 10));
        board_panel.setBackground(new Color(187, 173, 160));
        board_panel.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 10));

        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                board[i][j] = new FadeLabel("", 0, true, 6);
                board[i][j].setPreferredSize(new Dimension(88, 88));
                board[i][j].setIcon(getIcon("/Icons/" + (numbers[i][j] == null ? 0 : numbers[i][j].getNumber()) + ".png"));
                board_panel.add(board[i][j]);
            }
        }


        /*SCORE */
        score_panel = new RoundedPanel(false, true, false, 14);
        score_panel.setPreferredSize(new Dimension(90, 52));
        score_panel.setBackground(new Color(187, 173, 160));
        score_panel.setLayout(new GridLayout(2, 1));

        JLabel score_label_1 = new JLabel("SCORE", SwingConstants.HORIZONTAL);
        score_label_1.setFont(new Font("default", Font.BOLD, 10));
        score_label_1.setForeground(new Color(239, 239, 238));

        score_label = new JLabel("" + score, SwingConstants.HORIZONTAL);
        score_label.setFont(new Font("default", Font.BOLD, 20));
        score_label.setForeground(new Color(239, 239, 238));

        score_panel.add(score_label_1);
        score_panel.add(score_label);

        moves_panel = new RoundedPanel(false, true, false, 14);
        moves_panel.setPreferredSize(new Dimension(68, 52));
        moves_panel.setBackground(new Color(187, 173, 160));
        moves_panel.setLayout(new GridLayout(2, 1));

        JLabel moves_label_1 = new JLabel("MOVES", SwingConstants.HORIZONTAL);
        moves_label_1.setFont(new Font("default", Font.BOLD, 10));
        moves_label_1.setForeground(new Color(239, 239, 238));

        moves_label = new JLabel("" + moves, SwingConstants.HORIZONTAL);
        moves_label.setFont(new Font("default", Font.BOLD, 20));
        moves_label.setForeground(new Color(239, 239, 238));

        moves_panel.add(moves_label_1);
        moves_panel.add(moves_label);


        /* BEST SCORE */
        best_score_panel = new RoundedPanel(false, true, false, 14);
        best_score_panel.setPreferredSize(new Dimension(90, 52));
        best_score_panel.setBackground(new Color(187, 173, 160));
        best_score_panel.setLayout(new GridLayout(2, 1));

        JLabel best_score_label_1 = new JLabel("BEST", SwingConstants.HORIZONTAL);
        best_score_label_1.setFont(new Font("default", Font.BOLD, 10));
        best_score_label_1.setForeground(new Color(239, 239, 238));

        best_score_label = new JLabel("" + best_score, SwingConstants.HORIZONTAL);
        best_score_label.setFont(new Font("default", Font.BOLD, 20));
        best_score_label.setForeground(new Color(239, 239, 238));

        best_score_panel.add(best_score_label_1);
        best_score_panel.add(best_score_label);

        label_2048 = new JLabel("2048");

        label_2048.setFont(new Font("default", Font.BOLD, 60));
        label_2048.setForeground(new Color(119, 110, 101));

        top_panel = new JPanel();
        top_panel.setPreferredSize(new Dimension(4 * 88 + 5 * 10, 80));
        top_panel.setBackground(new Color(239, 239, 238));
        top_panel.setLayout(new FlowLayout());

        top_panel.add(label_2048);
        top_panel.add(moves_panel);
        top_panel.add(score_panel);
        top_panel.add(best_score_panel);

        top_panel2 = new JPanel();
        top_panel2.setPreferredSize(new Dimension(4 * 80 - 54, 30));
        top_panel2.setBackground(new Color(239, 239, 238));
        top_panel2.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 6));

        message_label1 = new JLabel("Join the numbers and get to the");
        message_label1.setFont(new Font("default", Font.PLAIN, 13));
        message_label2 = new JLabel(" 2048 tile!");
        message_label2.setFont(new Font("default", Font.BOLD, 13));
        top_panel2.add(message_label1);
        top_panel2.add(message_label2);

        top_panel3 = new JPanel();
        top_panel3.setPreferredSize(new Dimension(30, 30));
        top_panel3.setBackground(new Color(239, 239, 238));
        top_panel3.setLayout(new FlowLayout());

        final JLabel sound = new JLabel();
        sound.setPreferredSize(new Dimension(23, 23));
        sound.setIcon(getIcon("/Icons/sound.png"));

        sound.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if(sound_option) {
                    sound_option = false;
                    sound.setIcon(getIcon("/Icons/no-sound.png"));
                }
                else {
                    sound_option = true;
                    sound.setIcon(getIcon("/Icons/sound.png"));
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

        undo_panel = new RoundedPanel(false, true, false, 14);
        undo_panel.setPreferredSize(new Dimension(40, 24));
        undo = new JLabel("UNDO", SwingConstants.HORIZONTAL);
        undo.setFont(new Font("default", Font.BOLD, 12));
        undo.setForeground(new Color(239, 239, 238));
        undo_panel.setBackground(new Color(187, 173, 160));

        undo.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

                if(!used_undo) {
                    used_undo = true;

                    if(!light_mode) {
                        undo_panel.setBackground(new Color(239, 239, 238));
                    }
                    else {
                        undo_panel.setBackground(new Color(119, 110, 101));
                        undo.setForeground(new Color(119, 110, 101));
                    }

                    for(int i = 0; i < numbers.length; i++) {
                        for(int j = 0; j < numbers[i].length; j++) {
                            if(backup_numbers[i][j] != null) {
                                numbers[i][j] = new Number(backup_numbers[i][j]);
                            }
                            else {
                                numbers[i][j] = null;
                            }
                        }
                    }

                    score = backup_score;
                    best_score = backup_best_score;
                    moves = backup_moves;

                    for(int i = 0; i < numbers.length; i++) {
                        for(int j = 0; j < numbers[i].length; j++) {
                            board[i][j].setIcon(getIcon("/Icons/" + (numbers[i][j] == null ? 0 : numbers[i][j].getNumber()) + ".png"));
                            board[i][j].startFade();
                        }
                    }

                    score_label.setText("" + score);

                    best_score_label.setText("" + best_score);

                    moves_label.setText("" + moves);

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

        undo_panel.add(undo);

        new_game_panel = new RoundedPanel(false, true, false, 14);
        new_game_panel.setPreferredSize(new Dimension(40, 24));
        JLabel new_game = new JLabel("NEW", SwingConstants.HORIZONTAL);
        new_game.setFont(new Font("default", Font.BOLD, 12));
        new_game.setForeground(new Color(239, 239, 238));
        new_game_panel.setBackground(new Color(187, 173, 160));

        new_game.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                restartGame();
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

        new_game_panel.add(new_game);

        top_panel3.add(sound);

        offset_panel = new JPanel();
        offset_panel.setPreferredSize(new Dimension(206, 15));
        offset_panel.setBackground(new Color(239, 239, 238));

        offset_panel2 = new JPanel();
        offset_panel2.setPreferredSize(new Dimension(65, 15));
        offset_panel2.setBackground(new Color(239, 239, 238));

        score_plus_label = new FadeLabel("", SwingConstants.HORIZONTAL, false, 10);
        score_plus_label.setFont(new Font("default", Font.BOLD, 15));
        score_plus_label.setPreferredSize(new Dimension(90, 15));
        score_plus_label.setForeground(new Color(119, 110, 101));

        final RoundedPanel exit_panel = new RoundedPanel(false, true, false, 14);
        exit_panel.setPreferredSize(new Dimension(25, 25));
        exit_panel.setBackground(new Color(216, 73, 55));

        GridBagConstraints c = new GridBagConstraints();
        exit_panel.setLayout(new GridBagLayout());

        JLabel x = new JLabel("X");
        x.setFont(new Font("default", Font.BOLD, 15));

        c.fill = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;

        exit_panel.add(x, c);

        exit_panel.addMouseListener(new MouseListener() {

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
                exit_panel.setBackground(new Color(255, 255, 193));

                if(sound_option) {
                    playWav("/Sounds/tick.wav");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exit_panel.setBackground(new Color(216, 73, 55));
            }

        });

        main_panel = new JPanel();
        main_panel.setPreferredSize(new Dimension(4 * 88 + 5 * 10 + 40, 4 * 88 + 5 * 10 + 240));
        main_panel.setBackground(new Color(239, 239, 238));

        main_panel.add(offset_panel);
        main_panel.add(score_plus_label);
        main_panel.add(offset_panel2);
        main_panel.add(exit_panel);
        main_panel.add(top_panel);
        main_panel.add(top_panel2);
        main_panel.add(undo_panel);
        main_panel.add(new_game_panel);
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
                        case KeyEvent.VK_LEFT:
                        case KeyEvent.VK_KP_LEFT:
                            normal_move(LEFT);
                            break;
                        case KeyEvent.VK_RIGHT:
                        case KeyEvent.VK_KP_RIGHT:
                            normal_move(RIGHT);
                            break;
                        case KeyEvent.VK_UP:
                        case KeyEvent.VK_KP_UP:
                            normal_move(UP);
                            break;
                        case KeyEvent.VK_DOWN:
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

        Calendar calendar = new GregorianCalendar();

        if(calendar.get(Calendar.HOUR_OF_DAY) >= 18 || calendar.get(Calendar.HOUR_OF_DAY) < 9) {
            setNightMode();
        }
        else {
            setDayMode();
        }

        state = StateOfGame.HOW_TO;

        howTo();

    }

    @Override
    protected void startGame() {

        if(!load()) {
            best_score = 0;

            score = 0;
            moves = 0;

            backup_score = score;
            backup_best_score = best_score;
            backup_moves = moves;

            used_undo = false;

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

            backup_numbers = new Number[4][4];

            for(int i = 0; i < numbers.length; i++) {
                for(int j = 0; j < numbers[i].length; j++) {
                    if(numbers[i][j] != null) {
                        backup_numbers[i][j] = new Number(numbers[i][j]);
                    }
                    else {
                        backup_numbers[i][j] = null;
                    }
                }
            }
        }

        won = false;

        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                if(numbers[i][j] != null && numbers[i][j].getNumber() >= 2048) {
                    won = true;
                    break;
                }
            }
        }

        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                board[i][j].setIcon(getIcon("/Icons/" + (numbers[i][j] == null ? 0 : numbers[i][j].getNumber()) + ".png"));
                board[i][j].startFade();
            }
        }

        score_label.setText("" + score);

        best_score_label.setText("" + best_score);

        moves_label.setText("" + moves);

        if(used_undo) {
            if(!light_mode) {
                undo_panel.setBackground(new Color(239, 239, 238));
            }
            else {
                undo_panel.setBackground(new Color(119, 110, 101));
                undo.setForeground(new Color(119, 110, 101));
            }
        }

        state = StateOfGame.PLAY;

    }

    public Number[][] getNumbers() {
        return numbers;
    }

    @Override
    public void normal_move(int where) {

        if(endOfNumbersCheck()) {
            return;
        }

        if(gameOverCheck()) {
            return;
        }

        if(!won) {
            if(winCheck()) {
                return;
            }
        }

        Number[][] backup_numbers2 = new Number[4][4];

        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                if(numbers[i][j] != null) {
                    backup_numbers2[i][j] = new Number(numbers[i][j]);
                }
                else {
                    backup_numbers2[i][j] = null;
                }
            }
        }

        int backup_score2 = score;
        int backup_best_score2 = best_score;
        int backup_moves2 = moves;

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
                    board[i][j].setIcon(getIcon("/Icons/" + (numbers[i][j] == null ? 0 : numbers[i][j].getNumber()) + ".png"));

                    if(i == index_i && j == index_j) {
                        board[i][j].startFade();
                    }
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
                //if(score_thread != null && score_thread.isAlive()) {
                //score_thread.setClear(false);
                //}
                //score_thread = new ScoreThread(score_plus_label, result[1]);
                //score_thread.start();

                score_plus_label.setText("+" + result[1]);
                score_plus_label.startFade();

            }

            for(int i = 0; i < backup_numbers.length; i++) {
                for(int j = 0; j < backup_numbers[i].length; j++) {
                    if(backup_numbers2[i][j] != null) {
                        backup_numbers[i][j] = new Number(backup_numbers2[i][j]);
                    }
                    else {
                        backup_numbers[i][j] = null;
                    }
                }
            }

            backup_score = backup_score2;
            backup_best_score = backup_best_score2;
            backup_moves = backup_moves2;

        }

    }

    private boolean winCheck() {

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
            return true;
        }

        return false;

    }

    private boolean endOfNumbersCheck() {

        boolean found = false;

        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                if(numbers[i][j] != null && numbers[i][j].is65536()) {
                    found = true;
                    break;
                }
            }
        }

        if(found) {
            endOfNumbers();
            return true;
        }

        return false;
    }

    private boolean gameOverCheck() {
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
                return true;
            }
        }

        return false;
    }

    public int[] move(int where) {

        int moved = 0;

        int achieved_score = 0;

        if(where == UP) {
            for(int j = 0; j < numbers.length; j++) {
                //int boundary = 0;
                for(int i = 0; i < numbers[j].length; i++) {
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
                for(int i = numbers[j].length - 1; i >= 0; i--) {
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
                        else if(numbers[i][k] != null && numbers[i][k + 1] != null && numbers[i][k].getNumber() == numbers[i][k + 1].getNumber() && numbers[i][k].notUsed() && numbers[i][k + 1].notUsed()) {//k >= boundary) {
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
                        if(numbers[i][k] == null && numbers[i][k - 1] != null) {
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
        backup_numbers = new Number[4][4];

        score = 0;

        moves = 0;

        backup_score = score;
        backup_best_score = best_score;
        backup_moves = moves;

        won = false;

        used_undo = false;

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
                if(numbers[i][j] != null) {
                    backup_numbers[i][j] = new Number(numbers[i][j]);
                }
                else {
                    backup_numbers[i][j] = null;
                }
            }
        }

        for(int i = 0; i < numbers.length; i++) {
            for(int j = 0; j < numbers[i].length; j++) {
                board[i][j].setIcon(getIcon("/Icons/" + (numbers[i][j] == null ? 0 : numbers[i][j].getNumber()) + ".png"));
                board[i][j].startFade();
            }
        }

        if(!light_mode) {
            undo_panel.setBackground(new Color(187, 173, 160));
        }
        else {
            undo_panel.setBackground(new Color(187, 173, 160));
            undo.setForeground(new Color(239, 239, 238));
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

            file.writeBoolean(used_undo);

            file.writeObject(backup_numbers);
            file.writeInt(backup_score);
            file.writeInt(backup_best_score);
            file.writeInt(backup_moves);

            file.close();
        }
        catch(IOException ex) {
        }
    }

    @Override
    protected boolean load() {
        ObjectInputStream file = null;
        try {
            file = new ObjectInputStream(new FileInputStream("Gamesave.dat"));

            numbers = (Number[][])file.readObject();
            score = file.readInt();
            best_score = file.readInt();
            moves = file.readInt();

            used_undo = file.readBoolean();

            backup_numbers = (Number[][])file.readObject();
            backup_score = file.readInt();
            backup_best_score = file.readInt();
            backup_moves = file.readInt();

            file.close();
        }
        catch(FileNotFoundException ex) {
            return false;
        }
        catch(IOException ex) {
            return false;
        }
        catch(ClassNotFoundException ex) {
            return false;
        }
        catch(Exception ex) {
            return false;
        }

        return true;

    }

    public JPanel getBoardPanel() {

        return board_panel;

    }

    private void setNightMode() {

        getContentPane().setBackground(new Color(119, 110, 101));
        main_panel.setBackground(new Color(119, 110, 101));
        top_panel.setBackground(new Color(119, 110, 101));
        top_panel2.setBackground(new Color(119, 110, 101));
        top_panel3.setBackground(new Color(119, 110, 101));
        offset_panel.setBackground(new Color(119, 110, 101));
        offset_panel2.setBackground(new Color(119, 110, 101));

        label_2048.setForeground(new Color(239, 239, 238));
        message_label1.setForeground(Color.white);
        message_label2.setForeground(Color.white);
        score_plus_label.setForeground(new Color(239, 239, 238));

        light_mode = true;
    }

    private void setDayMode() {

        getContentPane().setBackground(new Color(239, 239, 238));
        main_panel.setBackground(new Color(239, 239, 238));
        top_panel.setBackground(new Color(239, 239, 238));
        top_panel2.setBackground(new Color(239, 239, 238));
        top_panel3.setBackground(new Color(239, 239, 238));
        offset_panel.setBackground(new Color(239, 239, 238));
        offset_panel.setBackground(new Color(239, 239, 238));

        label_2048.setForeground(new Color(119, 110, 101));
        message_label1.setForeground(Color.BLACK);
        message_label2.setForeground(Color.BLACK);
        score_plus_label.setForeground(new Color(119, 110, 101));

        light_mode = false;
    }

    public static void main(String[] args) {

        final Game2048 game = new Game2048();
        game.setVisible(true);

    }
}
