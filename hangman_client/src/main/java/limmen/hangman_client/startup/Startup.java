/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.startup;

import javax.swing.SwingUtilities;
import limmen.hangman_client.gui.ConnectFrame;

/**
 *
 * @author kim
 */
public class Startup {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ConnectFrame();
            }
        });
    }
    
}
