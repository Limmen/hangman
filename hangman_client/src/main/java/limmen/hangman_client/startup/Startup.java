/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.startup;

import limmen.hangman_client.gui.Controller;

/**
 * Startup class for HangMan-Client
 * @author kim
 */
public class Startup {
    
    private static Controller contr;
    /**
     * Mainmethod. Entry point of the program.
     * Creates a frame for connecting to a server.
     * @param args
     */
    public static void main(String[] args){
        contr = new Controller();
    }
    
}
