/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.gui;

import java.awt.Font;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 * JPanel representing a HangMan-word and buttons to guess letters/word
 * @author kim
 */
public class GamePanel extends JPanel {
    private final Font Plain = new Font("Serif", Font.PLAIN, 13);
    private final Font Title = new Font("Serif", Font.PLAIN, 15);
    private final Font Word = new Font("Serif", Font.PLAIN, 25);    
    private final ObjectOutputStream out;
    private final JLabel wordLabel;
    private final JButton guessButton;    
    private final Controller contr;
    /**
     * Class constructor
     * @param out ObjectOutputStream to server
     * @param word Current view of the word for the user
     * @param contr
     */
    public GamePanel(ObjectOutputStream out, String word, Controller contr){        
        this.contr = contr;
        this.out = out;
        setLayout(new MigLayout("wrap 1"));          
        JPanel hangManPanel = new JPanel(new MigLayout("wrap 1"));
        wordLabel = new JLabel(word);
        wordLabel.setFont(Word);
        hangManPanel.add(wordLabel, "span 1, align center");
        JPanel guessPanel = new JPanel(new MigLayout("wrap 1"));
        final JTextField guessField = new JTextField(20);
        guessField.setFont(Plain);
        guessPanel.add(guessField, "span 1");
        guessButton = new JButton("Guess");
        guessButton.setFont(Title);
        guessButton.addActionListener(contr.new GuessListener(guessField));
        guessPanel.add(guessButton, "span 1");
        JButton newWordButton = new JButton("New word");
        newWordButton.setFont(Title);
        newWordButton.addActionListener(contr.new NewWordListener());
        guessPanel.add(newWordButton, "span 1");  
        add(hangManPanel, "span 1");
        add(guessPanel, "span 1, gaptop 60");
    }
    
    /**
     * Update UI with new word
     * @param word word to update the UI with
     */
    public void updateGame(String word){
        wordLabel.setText(word);
    }

    /**
     * GreyOut the guess-button
     */
    public void greyOut(){
        guessButton.setEnabled(false); 
    }
    
    /**
     * Enable the guess-button
     */
    public void enableGuess(){
        guessButton.setEnabled(true); 
    }
    
    
}
