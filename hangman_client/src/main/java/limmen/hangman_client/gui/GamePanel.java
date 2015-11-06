/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import limmen.hangman.util.Command;
import limmen.hangman.util.Protocol;
import limmen.hangman_client.client.model.WriteWorker;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class GamePanel extends JPanel {
    private final Font Plain = new Font("Serif", Font.PLAIN, 12);
    private final Font Title = new Font("Serif", Font.PLAIN, 14);
    private final Font Word = new Font("Serif", Font.PLAIN, 25);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    
    private ObjectOutputStream out;
    private JLabel wordLabel;
    private JButton guessButton;
    
    /**
     *
     * @param out
     * @param word
     */
    public GamePanel(ObjectOutputStream out, String word){        
        this.out = out;
        setLayout(new MigLayout("wrap 1"));
        JLabel lbl;            
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
        guessButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {  
                    if(guessField.getText().length() > 0){
                        makeGuess(guessField.getText());
                        guessField.setText("");
                    }
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });        
        guessPanel.add(guessButton, "span 1");
        JButton newWordButton = new JButton("New word");
        newWordButton.setFont(Title);
        newWordButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {  
                    newWord();
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });        
        guessPanel.add(newWordButton, "span 1");  
        add(hangManPanel, "span 1");
        add(guessPanel, "span 1, gaptop 60");
    }
    private void makeGuess(String guess){
        new WriteWorker(out, (Protocol) new Protocol(Command.GUESS, guess)).execute();
    }
    private void newWord(){
        new WriteWorker(out, (Protocol) new Protocol(Command.NEWWORD)).execute();
    }

    /**
     *
     * @param word
     */
    public void updateGame(String word){
        wordLabel.setText(word);
    }

    /**
     *
     */
    public void greyOut(){
        guessButton.setEnabled(false); 
    }
    
    
}
