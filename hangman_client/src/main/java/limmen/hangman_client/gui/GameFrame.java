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
import limmen.hangman.util.CommunicationProtocol;
import limmen.hangman.util.Congratulations;
import limmen.hangman.util.GameOver;
import limmen.hangman.util.Result;
import limmen.hangman.util.Start;
import limmen.hangman_client.client.DisconnectWorker;
import limmen.hangman_client.client.ReadWorker;
import limmen.hangman_client.client.WriteWorker;
import limmen.hangman_client.client.model.HangMan;
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
    private HangMan game;
    
    public GameFrame(String host, int port, Socket clientSocket, ObjectInputStream in, ObjectOutputStream out){
        this.hostname = host;
        this.port = port;
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
        this.game = new HangMan();
        
        setup();        
    }
    private void setup(){                        
        this.setLayout(new MigLayout());
        this.setTitle("HomeWork 1 ID2212 | Connect");
        createContainer();
        this.setContentPane(container);
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
        
        readWorker = new ReadWorker(in, this);
        readWorker.execute();
        writeWorker = new WriteWorker(out, (CommunicationProtocol) new Start());
        writeWorker.execute();
    }
    private void createContainer(){
        container = new JPanel(new MigLayout("wrap 2"));
        scorePanel = new ScorePanel(game.getAttemptsLeft(), game.getScore());
        gamePanel = new GamePanel(out, game.getWord());
        connectedPanel = new ConnectedPanel(hostname, port, this);
        logPanel = new LogPanel();        
        container.add(scorePanel, "span 1");
        container.add(gamePanel, "span 1");
        container.add(connectedPanel, "span 1, gaptop 70");
        container.add(logPanel, "span 1, gaptop 70");
    }    
    public void disconnect(){       
            new DisconnectWorker(clientSocket, out, in).execute();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    dispose();
                }
            });
    }
    
    public void updateGame(Result result){
        logPanel.updateLog(result.getLog());
        game.setScore(result.getScore());
        game.setAttempts(result.getAttemptsleft());
        System.out.println("state: " + result.getState());
        game.setWord(game.spacify(result.getState()));
        scorePanel.updateScore(game.getAttemptsLeft(), game.getScore());
        gamePanel.updateGame(game.getWord());
        pack();        
    }
    public void Congratulations(Congratulations con){
        
    }
    public void GameOver(GameOver go){        
        logPanel.updateLog(go.getLog());
        game.setScore(go.getScore());
        game.setAttempts(go.getAttemptsleft());
        System.out.println("state: " + go.getState());
        game.setWord("<html><font color=red>" + game.spacify(go.getState()) + "</font></html>");
        scorePanel.updateScore(game.getAttemptsLeft(), game.getScore());        
        gamePanel.updateGame(game.getWord());
        pack();
    }            
}
