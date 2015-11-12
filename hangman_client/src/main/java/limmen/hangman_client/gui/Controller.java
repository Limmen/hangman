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
import javax.swing.JOptionPane;
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
        connectFrame = new ConnectFrame(contr);
    }        
    
    /**
     * Mainmethod. Entry point of the program.
     * Creates a frame for connecting to a server.
     * @param args
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public static void main(String[] args){
        new Controller();
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
    /**
     *This method is called when a connection attempt fails
     * @param host hostname of the failed connection
     * @param port portnumber of the failed connection
     */
    public void failedConnectionAttempt(final String host, final int port){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "Could'nt connect to " + host + " " + port,
                        "failedConnection", JOptionPane.INFORMATION_MESSAGE);    
            }
        });
    }
    /**
     * This method is called when a connection was lost
     */
    public void connectionWasLost(){
            gameFrame.disconnect();            
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                connectFrame.setVisible(true);
                JOptionPane.showMessageDialog(null, "Connection was lost",
                        "Connection was lost", JOptionPane.INFORMATION_MESSAGE);    
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
            if(hostField.getText().length() > 0 && portField.getText().length() > 0){
                try{
                    new ConnectWorker(contr, Integer.parseInt(portField.getText()), hostField.getText()).execute();   
                }
                catch(NumberFormatException formatExc){
                    JOptionPane.showMessageDialog(null, "port need to be a valid number", 
                        "Invalid portNumber", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else
                JOptionPane.showMessageDialog(null, "You need to fill in hostname and portnumber", 
                        "Invalid host/port", JOptionPane.INFORMATION_MESSAGE);
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
                new WriteWorker(gameFrame.getOutputStream(), (Protocol) new Protocol(Command.GUESS, guessField.getText()), contr).execute();                
            }
            else
                JOptionPane.showMessageDialog(null, "A guess needs to be a letter or a word", 
                        "Invalid guess", JOptionPane.INFORMATION_MESSAGE);
            guessField.setText("");
        }
    }
    /*
    * Listener for when user requests a new word
    */
    class NewWordListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new WriteWorker(gameFrame.getOutputStream(), (Protocol) new Protocol(Command.NEWWORD), contr).execute();
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
            new WriteWorker(gameFrame.getOutputStream(), (Protocol) new Protocol(Command.RESTART), contr).execute();
        }
    }         
}
