/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class ConnectFrame extends JFrame {
    
    private JPanel container;
            
    public ConnectFrame(){
        this.setLayout(new MigLayout());
        this.setTitle("HomeWork 1 ID2212 | Connect");
        this.setContentPane(new ConnectPanel());
        
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
