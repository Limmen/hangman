/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hangman_client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import limmen.hangman_client.client.ConnectWorker;
import limmen.hangman_client.client.DisconnectWorker;
import limmen.hangman_client.client.ReadWorker;
import limmen.hangman_client.client.WriteWorker;
import limmen.hangman_client.client.model.HangMan;
import limmen.hangman.util.CommunicationProtocol;
import limmen.hangman.util.Congratulations;
import limmen.hangman.util.GameOver;
import limmen.hangman.util.Guess;
import limmen.hangman.util.Result;
import limmen.hangman.util.Start;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class MainWindow {
    
    private JFrame connectFrame;
    private JFrame gameFrame;
    
    private JPanel scorePanel;
    private JPanel gamePanel;
    private JPanel logPanel;
    private JPanel connectedPanel;
    private JTextArea log;
    
    private final Font Plain = new Font("Serif", Font.PLAIN, 12);
    private final Font Title = new Font("Serif", Font.PLAIN, 14);
    private final Font Word = new Font("Serif", Font.PLAIN, 25);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    
    private boolean connected;
    private String hostname;
    private int port;
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ReadWorker readWorker;
    private ConnectWorker connectWorker;
    private WriteWorker writeWorker;
    private DisconnectWorker disconnectWorker;
    private HangMan game;
    
    public MainWindow(){
        connected = false;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                showGUI();
            }
        });
    }
    
    private void showGUI(){
        if(connected){
            createGameFrame();
            showGameFrame();
        }            
        else{
            createConnectFrame();
            showConnectFrame();
        }           
    }
    private void createConnectFrame(){
        connectFrame = new JFrame("HomeWork 1 ID2212 | Connect");
        connectFrame.setLayout(new MigLayout());
        JPanel container = new JPanel(new MigLayout("wrap 2"));
        JLabel lbl;
        lbl = new JLabel("host: ");
        lbl.setFont(PBold);
        container.add(lbl, "span 1");
        final JTextField hostField = new JTextField(25);
        hostField.setFont(Plain);
        container.add(hostField);
        lbl = new JLabel("port: ");
        lbl.setFont(PBold);
        container.add(lbl, "span 1");
        final JTextField portField = new JTextField(25);
        portField.setFont(Plain);
        container.add(portField);
        final JButton connectButton = new JButton("Connect");
        connectButton.setFont(Title);
        connectButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    connect(hostField.getText(), Integer.parseInt(portField.getText()));
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });        
        container.add(connectButton, "span 2, gaptop 5");
        connectFrame.add(container, BorderLayout.CENTER);        
        
    }
    private void createGameFrame(){
        gameFrame = new JFrame("HomeWork 1 ID2212 | HangMan");
        gameFrame.setLayout(new MigLayout());
        JPanel container = new JPanel(new MigLayout("wrap 3"));
        scorePanel = new JPanel(new MigLayout("wrap 2"));
        gamePanel = new JPanel(new MigLayout("wrap 1"));   
        logPanel = new JPanel(new MigLayout("wrap 1"));
        connectedPanel = new JPanel(new MigLayout("wrap 2"));
        createScorePanel();
        createGamePanel();
        createLogPanel();
        createConnectedPanel();
        container.add(scorePanel, "span 1");
        container.add(gamePanel, "span 1, gapleft 50, gapright 50");
        container.add(logPanel, "span 1");
        container.add(connectedPanel, "span 3, gaptop 60");
        gameFrame.add(container, BorderLayout.CENTER); 
        
    }        
    private void createScorePanel(){
        scorePanel.removeAll();
        JLabel lbl;        
        lbl = new JLabel("Attempts left: ");
        lbl.setFont(PBold);        
        scorePanel.add(lbl, "span 1");
        lbl = new JLabel(Integer.toString(game.getAttemptsLeft()));
        lbl.setFont(Plain);
        scorePanel.add(lbl, "span 1");
        lbl = new JLabel("Score: ");
        lbl.setFont(PBold);
        scorePanel.add(lbl, "span 1");
        lbl = new JLabel(Integer.toString(game.getScore()));
        lbl.setFont(Plain);
        scorePanel.add(lbl, "span 1");
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(Title);
        restartButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    //makeGuess();
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });        
        scorePanel.add(restartButton, "span 2, gaptop 60");    
        
    }
    private void createGamePanel(){
        gamePanel.removeAll();
        JLabel lbl;            
        JPanel hangManPanel = new JPanel(new MigLayout("wrap 1"));
        System.out.println("Setting game getWord: " + game.getWord());
        lbl = new JLabel(game.getWord());
        lbl.setFont(Word);
        hangManPanel.add(lbl, "span 1, align center");
        JPanel guessPanel = new JPanel(new MigLayout("wrap 1"));
        final JTextField guessField = new JTextField(20);
        guessField.setFont(Plain);
        guessPanel.add(guessField, "span 1");
        JButton guessButton = new JButton("Guess");
        guessButton.setFont(Title);
        guessButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    makeGuess(guessField.getText());
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });        
        guessPanel.add(guessButton, "span 1");        
        gamePanel.add(hangManPanel, "span 1");
        gamePanel.add(guessPanel, "span 1, gaptop 60");
        
        
    }
    private void createLogPanel(){
        logPanel.removeAll();
        JLabel lbl;        
        lbl = new JLabel("Game log");
        lbl.setFont(Plain);
        logPanel.add(lbl, "span 1");
        log = new JTextArea("");
        log.setLineWrap(true);
        log.setEditable(false);
        log.setFont(Plain);
        JScrollPane logPane = new JScrollPane(log);
        logPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logPane.setPreferredSize(new Dimension(150, 250));
        logPanel.add(logPane, "span 1");
      
    }   
    private void createConnectedPanel(){
        connectedPanel.removeAll();
        JLabel lbl;        
        lbl = new JLabel("Connected to: ");
            lbl.setFont(Title);
            connectedPanel.add(lbl, "span 2, gapbottom 10, align center");
            lbl = new JLabel("Host: ");
            lbl.setFont(PBold);
            connectedPanel.add(lbl, "span 1");
            lbl = new JLabel(this.hostname);
            lbl.setFont(Plain);
            connectedPanel.add(lbl, "span 1");
            lbl = new JLabel("Port: ");
            lbl.setFont(PBold);
            connectedPanel.add(lbl, "span 1");
            lbl = new JLabel(Integer.toString(this.port));
            lbl.setFont(Plain);
            connectedPanel.add(lbl, "span 1");
            JButton disconnect = new JButton("Disconnect");
            disconnect.setFont(Title);
            disconnect.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent arg0)
                {
                    try {
                        disconnect();
                    }
                    catch(Exception e)
                    {
                        
                    }
                    
                }
            });
            connectedPanel.add(disconnect, "span 2, gaptop 5");
        
    }   
    private void showConnectFrame(){
        if(gameFrame != null)
                gameFrame.setVisible(false);
        if(connectFrame != null){            
            connectFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                exit();
                System.exit(0);
            }
        });
        connectFrame.pack();
        connectFrame.setLocationRelativeTo(null);    // centers on screen
            connectFrame.setVisible(true);            
        }
    }
    private void showGameFrame(){
        if(connectFrame != null)
            connectFrame.setVisible(false);
        if(gameFrame != null){
            gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                exit();
                System.exit(0);
            }
        });
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);    // centers on screen
            gameFrame.setVisible(true);            
        }
    }
    
    
    
    private void connect(String host, int port){
        connectWorker = new ConnectWorker(this, port, host);
        connectWorker.execute();
    }
    
    public void connected(String host, int port, Socket clientSocket, ObjectInputStream in, ObjectOutputStream out){
        this.hostname = host;
        this.port = port;
        this.connected = true;
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
        this.game = new HangMan();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGameFrame();
                showGameFrame();
            }
        });
        readWorker = new ReadWorker(in, this);
        readWorker.execute();
        writeWorker = new WriteWorker(out, (CommunicationProtocol) new Start());
        writeWorker.execute();
    }
    
    public void updateGame(Result result){
        System.out.println("Client received state of: " + result.getState());
        log.setText(log.getText() + result.getLog() + "\n");
        game.setScore(result.getScore());
        game.setAttempts(result.getAttemptsleft());
        System.out.println("state: " + result.getState());
        game.setWord(game.spacify(result.getState()));
        createScorePanel();
        createGamePanel();
        gameFrame.pack();        
    }
    public void Congratulations(Congratulations con){
        
    }
    public void GameOver(GameOver go){        
        log.setText(log.getText() + go.getLog() + "\n");
        game.setScore(go.getScore());
        game.setAttempts(go.getAttemptsleft());
        System.out.println("state: " + go.getState());
        game.setWord("<html><font color=red>" + game.spacify(go.getState()) + "</font></html>");
        createScorePanel();
        createGamePanel();
        gameFrame.pack();     
    }            
    private void makeGuess(String guess){
        writeWorker = new WriteWorker(out, (CommunicationProtocol) new Guess(guess));
        writeWorker.execute();
    }
    private void disconnect(){
        if(connectWorker != null)
            connectWorker.cancel(true);
        if(readWorker != null)
            readWorker.cancel(true);
        if(writeWorker != null)
            writeWorker.cancel(true);
        if(connected)
        {
            connected = false;
            disconnectWorker = new DisconnectWorker(clientSocket, out, in);
            disconnectWorker.execute();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    showConnectFrame();
                }
            });
        }
        
    }
    
    private void exit(){
        if(connectWorker != null)
            connectWorker.cancel(true);
        if(readWorker != null)
            readWorker.cancel(true);
        if(writeWorker != null)
            writeWorker.cancel(true);
        if(connected)
        {
            connected = false;
            disconnectWorker = new DisconnectWorker(clientSocket, out, in);
            disconnectWorker.execute();
            try {
                disconnectWorker.get();
            } catch (InterruptedException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }        
    }
    
}
    

