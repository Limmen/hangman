/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class LogPanel extends JPanel {
    private final Font Plain = new Font("Serif", Font.PLAIN, 12);
    
    private JTextArea log;
    
    public LogPanel(){
        setLayout(new MigLayout("wrap 1"));
        JLabel lbl;        
        lbl = new JLabel("Game log");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        log = new JTextArea("");
        log.setLineWrap(true);
        log.setEditable(false);
        log.setFont(Plain);
        JScrollPane logPane = new JScrollPane(log);
        logPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logPane.setPreferredSize(new Dimension(400, 250));
        add(logPane, "span 1");
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    log.setText("");
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });
        clearButton.setFont(Plain);
        add(clearButton, "span 1");
    }
    
    public void updateLog(String text){
        log.setText(log.getText() + text + "\n");
    }
}
