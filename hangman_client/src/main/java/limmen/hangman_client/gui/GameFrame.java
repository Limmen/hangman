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
import javax.swing.SwingUtilities;
import javax.xml.transform.Result;
import limmen.hangman.util.Command;
import limmen.hangman.util.Protocol;
import limmen.hangman_client.client.model.DisconnectWorker;
import limmen.hangman_client.client.model.ReadWorker;
import limmen.hangman_client.client.model.WriteWorker;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class GameFrame extends JFrame {
        
    private JPanel container;
    private ScorePanel scorePanel;
    private GamePanel gamePanel;
    private ConnectedPanel connectedPanel;
    private LogPanel logPanel;
    private String hostname;
    private int port;
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ReadWorker readWorker;
    private WriteWorker writeWorker;
    private ConnectFrame frame;
    
    /**
     *
     * @param host
     * @param port
     * @param clientSocket
     * @param in
     * @param out
     * @param frame
     */
    public GameFrame(String host, int port, Socket clientSocket, ObjectInputStream in, ObjectOutputStream out, ConnectFrame frame){
        this.hostname = host;
        this.port = port;
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
        this.frame = frame;
        
        setup();        
    }
    private void setup(){                        
        setLayout(new MigLayout());
        setTitle("HomeWork 1 ID2212 | Connect");
        createContainer();
        setContentPane(container);
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
    private void createContainer(){
        container = new JPanel(new MigLayout("wrap 3"));
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
     *
     */
    public void disconnect(){       
            new DisconnectWorker(clientSocket, out, in).execute();
            frame.setVisible(true);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dispose();
                }
            });
    }
    
    /**
     *
     * @param msg
     */
    public void updateGame(Protocol msg){
        logPanel.updateLog(msg.getLog());        
        scorePanel.updateScore(msg.getAttemptsLeft(), msg.getScore());
        gamePanel.updateGame(spacify(msg.getState()));
        pack();        
    }

    /**
     *
     * @param msg
     */
    public void congratulations(Protocol msg){
        logPanel.updateLog(msg.getLog());
        scorePanel.updateScore(msg.getAttemptsLeft(), msg.getScore());        
        gamePanel.updateGame("<html><font color=green>" + spacify(msg.getState()) + "</font></html>");
        gamePanel.greyOut();
        pack();
    }

    /**
     *
     * @param msg
     */
    public void gameOver(Protocol msg){        
        logPanel.updateLog(msg.getLog());
        scorePanel.updateScore(msg.getAttemptsLeft(), msg.getScore());        
        gamePanel.updateGame("<html><font color=red>" + spacify(msg.getState()) + "</font></html>");
        gamePanel.greyOut();
        pack();
    }
    
    /**
     *
     * @param word
     * @return
     */
    public String spacify(String word){
        return word.replace("", " ").trim().toUpperCase();
    }
}
