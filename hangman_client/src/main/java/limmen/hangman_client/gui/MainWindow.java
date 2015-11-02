/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hangman_client.gui;

import java.awt.BorderLayout;
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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import limmen.hangman_client.client.ConnectWorker;
import limmen.hangman_client.client.DisconnectWorker;
import limmen.hangman_client.client.ReadWorker;
import limmen.hangman_client.client.WriteWorker;
import limmen.hangman_client.util.Congratulations;
import limmen.hangman_client.util.GameOver;
import limmen.hangman_client.util.Result;
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
    private JPanel guessPanel;
    private JPanel connectedPanel;
    
    private final Font Plain = new Font("Serif", Font.PLAIN, 12);
    private final Font Title = new Font("Serif", Font.PLAIN, 14);
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
        
    }        
    private void createScorePanel(){
        
    }
    private void createGamePanel(){
        
    }
    private void createLogPanel(){
        
    }
    private void createGuessPanel(){
        
    }
    private void createConnectedPanel(){
        
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
        if(gameFrame != null)
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
        
    }
    
    public void connected(String host, int port, Socket clientSocket, ObjectInputStream in, ObjectOutputStream out){
        this.hostname = host;
        this.port = port;
        this.connected = true;
        this.clientSocket = clientSocket;
        this.in = in;
        this.out = out;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGameFrame();
                showGameFrame();
            }
        });
        readWorker = new ReadWorker(in, this);
        readWorker.execute();
    }
    
    public void updateGame(Result result){
        
    }
    public void Congratulations(Congratulations con){
        
    }
    public void GameOver(GameOver go){
        
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
    

