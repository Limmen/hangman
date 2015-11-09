/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.gui;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import limmen.hangman.util.Command;
import limmen.hangman.util.Protocol;
import limmen.hangman_client.client.model.DisconnectWorker;
import limmen.hangman_client.client.model.ReadWorker;
import limmen.hangman_client.client.model.WriteWorker;
import net.miginfocom.swing.MigLayout;

/**
 * JFrame that contains stuff to represent a HangMan game.
 * @author kim
 */
public class GameFrame extends JFrame {
        
    private JPanel container;
    private ScorePanel scorePanel;
    private GamePanel gamePanel;
    private ConnectedPanel connectedPanel;
    private LogPanel logPanel;
    private final String hostname;
    private final int port;
    private final Socket serverSocket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private ReadWorker readWorker;
    private WriteWorker writeWorker;
    private final ConnectFrame frame;
    
    /**
     * Class constructor.
     * @param host hostname
     * @param port portnumber
     * @param serverSocket socket connection to server
     * @param in ObjectInputStream to server
     * @param out ObjectOutputStream to server
     * @param frame ConnectFrame, need to be shown when user disconnects
     */
    public GameFrame(String host, int port, Socket serverSocket, ObjectInputStream in, ObjectOutputStream out, ConnectFrame frame){
        this.hostname = host;
        this.port = port;
        this.serverSocket = serverSocket;
        this.in = in;
        this.out = out;
        this.frame = frame;
        
        setup();        
    }
    /*
    * Creates the ui
    */
    private void setup(){                        
        setLayout(new MigLayout());
        setTitle("HomeWork 1 ID2212 | Connect");
        createContainer();
        setContentPane(new JScrollPane(container));
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
        
        readWorker = new ReadWorker(in, this);
        readWorker.execute();
        writeWorker = new WriteWorker(out, (Protocol) new Protocol(Command.START));
        writeWorker.execute();
    }
    /*
    * Creates the main container
    */
    private void createContainer(){
        container = new JPanel(new MigLayout("wrap 3, insets 50 50 50 50")); //insets T,L,B,R
        scorePanel = new ScorePanel(out, 0, 0);
        gamePanel = new GamePanel(out, "");
        connectedPanel = new ConnectedPanel(hostname, port, this);
        logPanel = new LogPanel();        
        container.add(gamePanel, "span 3, align center, gaptop 50");
        container.add(connectedPanel, "span 1, gapbottom 50");
        container.add(scorePanel, "span 1, gapbottom 50");
        container.add(logPanel, "span 1, gaptop 70");
    }    

    /**
     * Disconnect to server. Show connect-frame and dispose game-frame.
     */
    public void disconnect(){       
            new DisconnectWorker(serverSocket, out, in).execute();
            frame.setVisible(true);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dispose();
                }
            });
    }
    
    /**
     * Update game with new data (log, attemptsleft, score, wordstate)
     * @param msg message received from server, contains game info.
     */
    public void updateGame(Protocol msg){
        logPanel.updateLog(msg.getLog());        
        scorePanel.updateScore(msg.getAttemptsLeft(), msg.getScore());
        gamePanel.updateGame(spacify(msg.getState()));
        gamePanel.enableGuess();
        pack();        
    }

    /**
     * Show congratulation message to user.
     * @param msg message received from server, contains game info.
     */
    public void congratulations(Protocol msg){
        logPanel.updateLog(msg.getLog());
        scorePanel.updateScore(msg.getAttemptsLeft(), msg.getScore());        
        gamePanel.updateGame("<html><font color=green>" + spacify(msg.getState()) + "</font></html>");
        gamePanel.greyOut();
        pack();
    }

    /**
     * Show gameover message to user.
     * @param msg message received from server, contains game info.
     */
    public void gameOver(Protocol msg){        
        logPanel.updateLog(msg.getLog());
        scorePanel.updateScore(msg.getAttemptsLeft(), msg.getScore());        
        gamePanel.updateGame("<html><font color=red>" + spacify(msg.getState()) + "</font></html>");
        gamePanel.greyOut();
        pack();
    }
    
    /**
     * Insert spaces between the letters of a string.
     * @param word String to spacify
     * @return spacified string
     */
    public String spacify(String word){
        return word.replace("", " ").trim().toUpperCase();
    }
}
