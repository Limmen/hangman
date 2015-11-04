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
import limmen.hangman.util.CommunicationProtocol;
import limmen.hangman.util.Guess;
import limmen.hangman_client.client.WriteWorker;
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
    
    public GamePanel(ObjectOutputStream out, String word){        
        this.out = out;
        setLayout(new MigLayout("wrap 1"));
        JLabel lbl;            
        JPanel hangManPanel = new JPanel(new MigLayout("wrap 1"));
        System.out.println("Setting game getWord: " + word);
        wordLabel = new JLabel(word);
        wordLabel.setFont(Word);
        hangManPanel.add(wordLabel, "span 1, align center");
        JPanel guessPanel = new JPanel(new MigLayout("wrap 1"));
        final JTextField guessField = new JTextField(20);
        guessField.setFont(Plain);
        guessPanel.add(guessField, "span 1");
        JButton guessButton = new JButton("Guess");
        guessButton.setFont(Title);
        guessButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {                    
                    makeGuess(guessField.getText());
                    guessField.setText("");
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });        
        guessPanel.add(guessButton, "span 1");        
        add(hangManPanel, "span 1");
        add(guessPanel, "span 1, gaptop 60");
    }
    private void makeGuess(String guess){
        new WriteWorker(out, (CommunicationProtocol) new Guess(guess)).execute();
    }
    public void updateGame(String word){
        wordLabel.setText(word);
    }
    
    
}
