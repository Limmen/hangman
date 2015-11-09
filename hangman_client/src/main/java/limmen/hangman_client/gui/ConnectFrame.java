/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

/**
 * JFrame for that contains a form for connecting to a server
 * @author kim
 */
public class ConnectFrame extends JFrame {
                
    /**
     * Class constructor.
     * @param contr Controller instance
     */
    public ConnectFrame(Controller contr){
        this.setLayout(new MigLayout());
        this.setTitle("HomeWork 1 ID2212 | Connect");      
        this.setContentPane(new JScrollPane(new ConnectPanel(contr)));        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                System.exit(0);
            }
        });
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);      
    }
    
    
    
 
}
