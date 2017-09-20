/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

/**
 *
 * @author Justin
 */

import core.Board;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import javax.swing.*;

public class BoggleUI
{
    // Member variables used in the GUI
    private JFrame frame;
    private JMenu menu;
    private JMenuBar menuBar;
    private JMenuItem newGame;
    private JMenuItem exit;
    private JPanel words;
    private JButton[][] diceButton;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private JTextArea text;
    private JLabel curWord;
    private JButton submitWord;
    private JPanel info;
    private JLabel time;
    private JButton shake;
    private JLabel score;
    private Timer timer;
    private resetGame resetChoice;
    private exitGame exitChoice;
    private startTimer timerListener;
    private buttonCheck check;
    private int minutes = 3;
    private int seconds = 0;
    private int playerScore = 0;
    private ArrayList<JButton> clicked = new ArrayList();
    private Board myBoard;
            
    public BoggleUI(Board inputBoard)
    { 
        myBoard = inputBoard;
        resetChoice = new resetGame();
        exitChoice = new exitGame();
        timerListener = new startTimer();
        initComponents();
    }
    
    // Handles submitting the word
    private class submitWord implements ActionListener
    {
        private ArrayList<String> used = new ArrayList();
        private int scoreValue[] = {0, 0, 1, 1, 3, 5, 7, 11};
        
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            // Checks if the word is a valid word
            if(myBoard.getDictData().contains(curWord.getText().toLowerCase()))
            {
                // Checks if word has already been submitted
                if(used.contains(curWord.getText()))
                {
                    JOptionPane.showMessageDialog(null, "This word has"
                            + " already been submitted.");
                }
                
                else
                {    
                    // Add word to list of used words
                    used.add(curWord.getText());
                    int wordLength = curWord.getText().length();
                    
                    // Assigns appropriate points depending on word length
                    if(wordLength <= 2)
                        JOptionPane.showMessageDialog(null, "This word is too short.");
                    
                    else
                    {
                        if(wordLength >= 8)
                            playerScore += scoreValue[7];
                    
                        else
                            playerScore += scoreValue[wordLength - 1];
                    
                        // Adds word and score to appropriate panels
                        text.append(curWord.getText() + "\n");
                        score.setText("" + playerScore);
                    }
                }
            }
            
            else
                JOptionPane.showMessageDialog(null, "This is not a valid word.");
            
            curWord.setText("");
            
            // Enable all buttons for next word
            for(int row = 0; row < Board.GRID; row++)
                for(int col = 0; col < Board.GRID; col++)
                    diceButton[row][col].setEnabled(true);
            
            
            clicked.clear(); 
        }
    }
    
    // Finds which button has been pressed and enables/disables
    // the surrounding buttons
    private class buttonCheck implements ActionListener
    {
        private JButton temp;
        
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            // Find button that has been pressed
            temp = (JButton) ae.getSource();
            curWord.setText(curWord.getText() + temp.getText());
            
            // Passes the correct button that has been clicked
            // to the range function
            for(int i = 0; i < Board.GRID; i++)
                for(int j = 0; j < Board.GRID; j++)
                    if(diceButton[i][j] == temp)
                    {
                        // Find appropriate buttons to enable
                        checkRange(i-1, j-1);
                        // Adds to array of buttons that
                        // have been pressed
                        clicked.add(diceButton[i][j]);
                    }
                  
            // Checks if the button has already been pressed
            // and adds to array of unusable buttons
            for(int i = 0; i < Board.GRID; i++)
                for(int j = 0; j < Board.GRID; j++)
                    if(clicked.contains(diceButton[i][j]))
                        diceButton[i][j].setEnabled(false);
        }
    
    }
    
    // Checks which buttons should be enabled depending on
    // their distance from the origin button
    private void checkRange(int x, int y)
    {
        for(int row = 0; row < Board.GRID; row++)
            for(int col = 0; col < Board.GRID; col++)
                diceButton[row][col].setEnabled(false);
        
        // Finding which buttons should be enabled/disabled
        if(x < 0 || y < 0)
        {
            int originalX = x+1, originalY = y+1;
            
            if(originalX+1 < 4)
                diceButton[originalX+1][originalY].setEnabled(true);
            
            if(originalX+1 < 4 && originalY+1 < 4)
                diceButton[originalX+1][originalY+1].setEnabled(true);
            
            if(originalY+1 < 4)
                diceButton[originalX][originalY+1].setEnabled(true);
            
            if(originalY-1 >= 0)
                diceButton[originalX][originalY-1].setEnabled(true);
            
            if(originalX-1 >= 0 && originalY-1 >= 0)
                diceButton[originalX-1][originalY-1].setEnabled(true);
            
            if(originalX-1 >= 0)
                diceButton[originalX-1][originalY].setEnabled(true);
            
            if(originalX-1 >= 0 && originalY+1 < 4)
                diceButton[originalX-1][originalY+1].setEnabled(true);
            
            if(originalX+1 < 4 && originalY-1 >= 0)
                diceButton[originalX+1][originalY-1].setEnabled(true);
        }
        
        else
            for(int i = x; i < x+3; i++)
                for(int j = y; j < y+3; j++)
                    if(i < 4 && j < 4)
                        diceButton[i][j].setEnabled(true);
    }

    // Handles making a new Boggle Game
    private class resetGame implements ActionListener
    {
        // ArrayList to hold new letters from dice
        private ArrayList<String> letters = new ArrayList<>();
        
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            letters = myBoard.shakeDice();
                        
            for(int row = 0; row < Board.GRID; row++)
                for(int col = 0; col < Board.GRID; col++)
                {
                    diceButton[row][col].setText(letters.get(4*row+col));
                    diceButton[row][col].setPreferredSize(new Dimension(100, 100));
                    buttonPanel.add(diceButton[row][col]);
                }
            
            // Resets the frame for a new game
            text.setText("");
            Font font = new Font("Times New Roman", Font.PLAIN, 14);
            text.setFont(font);
            score.setText("0");
            curWord.setText("");
            time.setText("3:00");
            frame.revalidate();
            frame.repaint();
            timer.stop();
            minutes = 3;
            seconds = 0;
            timer.start();
            
            for(int row = 0; row < Board.GRID; row++)
            for(int col = 0; col < Board.GRID; col++)
                diceButton[row][col].setEnabled(true);
        }
    }
    
    // Handles when the user wants to exit the game
    private class exitGame implements ActionListener
    { 
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            int choice = JOptionPane.showConfirmDialog(null, "Confirm to exit "
                    + "Boggle?", "Exit?", JOptionPane.YES_NO_OPTION);
            
            if(choice == JOptionPane.YES_OPTION)
                System.exit(0);
        }
    }
    
    // Handles the timer for game length
    private class startTimer implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            // Stop the timer when it reaches 0
            if(minutes == 0 && seconds == 1)
            {
               // Setting correct font and calculating players score.
               // Computer searches for words and they are compared
               // to those entered by the player
               time.setText("0:00");
               String enteredWords[] = text.getText().split("\\r?\\n");
               int numWordsEntered = enteredWords.length;
               Font font = new Font("Times New Roman", Font.PLAIN, 14);
               Map fontAtt = font.getAttributes();
               fontAtt.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
               Font strikeFont = new Font(fontAtt);
               text.setFont(strikeFont);
               Random random = new Random();
               JOptionPane.showMessageDialog(null, "Game Over! The computer is comparing words.");
               int wordsMatched = random.nextInt(numWordsEntered+1);
               JOptionPane.showMessageDialog(null, "The computer found " + 
                       wordsMatched + " of Player's " + numWordsEntered);
               score.setText("" + (playerScore - numWordsEntered));
                             
               timer.stop();
            }
            
            // Reset seconds when 0 is reached
            if(seconds == 0)
            {
                seconds = 60;
                minutes--;
            }
            
            seconds--;
            
            // Formatting to look like a real clock
            if(seconds < 10)
                time.setText(minutes + ":0" + seconds);
            
            else
                time.setText(minutes + ":" + seconds);   
        }
    }

    // Initializes all components of the GUI
    public void initComponents()
    {
        timer = new Timer(1000, timerListener);

        // Creates basic frame for UI
        frame = new JFrame();
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Boggle");
        
        // Creates a menubar at the top of the frame
        menuBar = new JMenuBar();
        menu = new JMenu("Boggle");
        menuBar.add(menu);
        
        // Option on menubar
        newGame = new JMenuItem("New Game");
        newGame.addActionListener(resetChoice);
        menu.add(newGame);
        
        // Option on menubar
        exit = new JMenuItem("Exit");
        exit.addActionListener(exitChoice);
        menu.add(exit);
        
        // Creates panel at bottom of GUI
        words = new JPanel(new FlowLayout());
        words.setPreferredSize(new Dimension(500, 150));
        words.setMinimumSize(new Dimension(100, 100));
        words.setBorder(BorderFactory.createTitledBorder("Current Word"));
        
        // Shows the current word found by the player
        curWord = new JLabel();
        curWord.setBorder(BorderFactory.createTitledBorder("Current Word"));
        curWord.setPreferredSize(new Dimension(200, 75));
        words.add(curWord);
        
        // Allows player to submit the word for scoring
        submitWord = new JButton("Submit Word");
        submitWord.setPreferredSize(new Dimension(200, 75));
        submitWord.addActionListener(new submitWord());
        words.add(submitWord);
        
        // Displays the player's current score
        score = new JLabel();
        score.setBorder(BorderFactory.createTitledBorder("Score"));
        
        // Add score box to the bottom panel
        score.setPreferredSize(new Dimension(200, 75));
        words.add(score);
        
        // Creates panel on right of GUI
        info = new JPanel();
        info.setPreferredSize(new Dimension(300, 300));
        
        // Text area to enter words
        text = new JTextArea(10, 20);
        
        // Places scrollbars on the sides of the text area
        scrollPane = new JScrollPane(text);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Enter Words Found"));
        info.add(scrollPane);
        
        // Displays the amount of time remaining in the game
        time = new JLabel("3:00", SwingConstants.CENTER);
        time.setFont(new Font("Times New Roman", Font.PLAIN, 36));
        time.setPreferredSize(new Dimension(150, 100));
        time.setBorder(BorderFactory.createTitledBorder("Time Remaining"));
        
        info.add(time);
        
        // Create a button to randomly display the dice on the game board
        shake = new JButton("Shake Dice");
        shake.setPreferredSize(new Dimension(200, 75));
        shake.addActionListener(resetChoice);
        
        // Add the shake button to the info panel
        info.add(shake);
        
        // Add panels to the frame and set visible
        frame.setJMenuBar(menuBar);
        frame.add(words, BorderLayout.SOUTH);
        makeButtons();
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(info, BorderLayout.EAST);
        frame.setVisible(true);
        frame.pack();
        
        timer.start();
    }
    
    // Method to create the 4X4 game board 
    public void makeButtons()
    {
        diceButton = new JButton[Board.GRID][Board.GRID];
        buttonPanel = new JPanel(new GridLayout(4, 4));
        buttonPanel.setPreferredSize(new Dimension(400, 400));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Boggle Board"));
                
        // Creates each individual button
        for(int row = 0; row < Board.GRID; row++)
            for(int col = 0; col < Board.GRID; col++)
            {
                diceButton[row][col] = new JButton();
                diceButton[row][col].addActionListener(new buttonCheck());
                diceButton[row][col].setText(myBoard.getGameData().get(4*row+col));
                diceButton[row][col].setPreferredSize(new Dimension(100, 100));
                buttonPanel.add(diceButton[row][col]);
            }
    }
}
