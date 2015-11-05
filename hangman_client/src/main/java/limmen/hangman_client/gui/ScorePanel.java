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
import limmen.hangman.util.Command;
import limmen.hangman.util.Protocol;
import limmen.hangman_client.client.WriteWorker;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class ScorePanel extends JPanel {
    private final Font Plain = new Font("Serif", Font.PLAIN, 12);
    private final Font Title = new Font("Serif", Font.PLAIN, 14);
    private final Font Word = new Font("Serif", Font.PLAIN, 25);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private ObjectOutputStream out;
    
    public ScorePanel(ObjectOutputStream out, int attempts, int score){
        this.out = out;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl;        
        lbl = new JLabel("Attempts left: ");
        lbl.setFont(PBold);        
        add(lbl, "span 1");
        attemptsLabel = new JLabel(Integer.toString(attempts));
        attemptsLabel.setFont(Plain);
        add(attemptsLabel, "span 1");
        lbl = new JLabel("Score: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        scoreLabel = new JLabel(Integer.toString(score));
        scoreLabel.setFont(Plain);
        add(scoreLabel, "span 1");
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(Title);
        restartButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    restart();
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });        
        add(restartButton, "span 2");    
    }
    public void updateScore(int attempts, int score){
        attemptsLabel.setText(Integer.toString(attempts));
        scoreLabel.setText(Integer.toString(score));
    }
    
    private void restart(){
        new WriteWorker(out, (Protocol) new Protocol(Command.START)).execute();
    }
}
