/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import limmen.hangman_client.client.model.ConnectWorker;
import net.miginfocom.swing.MigLayout;

/**
 * JPanel that contains a form for connecting to a server.
 * @author kim
 */
public class ConnectPanel extends JPanel {
    
    private final Font Plain = new Font("Serif", Font.PLAIN, 12);
    private final Font Title = new Font("Serif", Font.PLAIN, 14);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    
    private JTextField hostField;
    private JTextField portField;
    private ConnectWorker connectWorker;
    private final ConnectFrame frame;
    
    /**
     * Class constructor
     * @param frame JFrame for communication to the connect-frame that a connection was established
     */
    public ConnectPanel(ConnectFrame frame){
        this.frame = frame;
        setLayout(new MigLayout("wrap 2, insets 50 50 50 50"));  //insets T, L, B, R
        JLabel lbl;
        lbl = new JLabel("host: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        hostField = new JTextField(25);
        hostField.setFont(Plain);
        add(hostField, "span 1");
        lbl = new JLabel("port: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        portField = new JTextField(25);
        portField.setFont(Plain);
        add(portField);
        JButton connectButton = new JButton("Connect");
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
        add(connectButton, "span 2, gaptop 5");
    }
    
    private void connect(String host, int port){
        connectWorker = new ConnectWorker(this, port, host);
        connectWorker.execute();
    }
    
    /**
     * This method is called by the connectWorker
     * when a connection was established.
     * @param host hostname
     * @param port portnumber
     * @param serverSocket socket connection to server
     * @param in ObjectInputStream to server
     * @param out ObjectOutputStream to server
     */
    public void connected(final String host, final int port, final Socket serverSocket, final ObjectInputStream in, final ObjectOutputStream out){        
        frame.setVisible(false);
        hostField.setText("");
        portField.setText("");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameFrame(host, port, serverSocket, in, out, frame);
            }
        });        
    }
    
}
