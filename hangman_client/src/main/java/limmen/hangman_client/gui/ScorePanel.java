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
import limmen.hangman_client.model.WriteWorker;
import net.miginfocom.swing.MigLayout;

/**
 * JPanel containing the current score and a button to restart.
 * @author kim
 */
public class ScorePanel extends JPanel {
    private final Font Plain = new Font("Serif", Font.PLAIN, 10);
    private final Font Title = new Font("Serif", Font.PLAIN, 10);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    
    private final JLabel attemptsLabel;
    private final JLabel scoreLabel;
    private final Controller contr;
    
    /**
     * Class constructor
     * @param contr instance of Controller
     * @param attempts number of attempts left for the user
     * @param score current score between the user and server
     */
    public ScorePanel(Controller contr, int attempts, int score){
        this.contr = contr;
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
        restartButton.addActionListener(contr.new RestartListener());
        add(restartButton, "span 2");    
    }

    /**
     * Update the current score
     * @param attempts number of attempts left for user
     * @param score current score between user and server
     */
    public void updateScore(int attempts, int score){
        attemptsLabel.setText(Integer.toString(attempts));
        scoreLabel.setText(Integer.toString(score));
    }

}
