/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import limmen.hangman.util.Command;
import limmen.hangman.util.Protocol;
import limmen.hangman_client.model.ConnectWorker;
import limmen.hangman_client.model.WriteWorker;

/**
 *
 * @author kim
 */
public class Controller {
    
    private final ConnectFrame connectFrame;
    private GameFrame gameFrame;
    private final Controller contr = this;
    
    /**
     * Controller constructor
     * Will create a connectFrame
     */
    public Controller(){
        connectFrame = new ConnectFrame(this);
    }        
    
    /**
     * Called when client succesful connected to server.
     * Will hide the connectFrame and create a new Frame for the HangMangame.
     * @param host hostname
     * @param port postnumber
     * @param serverSocket socket for conneciton to the server
     * @param in inputstream
     * @param out outputstream
     */
    public void connected(final String host, final int port, final Socket serverSocket, final ObjectInputStream in, final ObjectOutputStream out){
         SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                connectFrame.setVisible(false);
                gameFrame = new GameFrame(contr, host, port, serverSocket, in, out);
            }
        });                        
    }
    /*
    * Listener for when user tries to connect to a server.
    */
    class ConnectListener implements ActionListener {
        private final JTextField hostField;
        private final JTextField portField;
        
        ConnectListener(JTextField hostField, JTextField portField){
            this.hostField = hostField;
            this.portField = portField;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            new ConnectWorker(contr, Integer.parseInt(portField.getText()), hostField.getText()).execute();
            hostField.setText("");
            portField.setText("");
        }
    }
    /*
    * Listener for when user guesses a word/char
    */
    class GuessListener implements ActionListener {
        private final JTextField guessField;
        
        GuessListener(JTextField guessField){
            this.guessField = guessField;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(guessField.getText().length() > 0){
                new WriteWorker(gameFrame.getOutputStream(), (Protocol) new Protocol(Command.GUESS, guessField.getText())).execute();                
                guessField.setText("");
            }
        }
    }
    /*
    * Listener for when user requests a new word
    */
    class NewWordListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new WriteWorker(gameFrame.getOutputStream(), (Protocol) new Protocol(Command.NEWWORD)).execute();
        }
    }
    /*
    * Listener for when user disconnects 
    */
    class DisconnectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameFrame.disconnect();
            connectFrame.setVisible(true);
        }
    }
    /*
    * Listener for when user restarts the game
    */
    class RestartListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new WriteWorker(gameFrame.getOutputStream(), (Protocol) new Protocol(Command.RESTART)).execute();
        }
    }         
}
