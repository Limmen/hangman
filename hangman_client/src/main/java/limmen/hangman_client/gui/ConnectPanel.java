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
import limmen.hangman_client.client.ConnectWorker;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class ConnectPanel extends JPanel {
    
    private final Font Plain = new Font("Serif", Font.PLAIN, 12);
    private final Font Title = new Font("Serif", Font.PLAIN, 14);
    private final Font Word = new Font("Serif", Font.PLAIN, 25);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    
    private JTextField hostField;
    private JTextField portField;
    private ConnectWorker connectWorker;
    private ConnectFrame frame;
    
    public ConnectPanel(ConnectFrame frame){
        this.frame = frame;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl;
        lbl = new JLabel("host: ");
        lbl.setFont(PBold);
        add(lbl, "span 1, gapleft 50, gaptop 50");
        hostField = new JTextField(25);
        hostField.setFont(Plain);
        add(hostField, "span 1, gaptop 50");
        lbl = new JLabel("port: ");
        lbl.setFont(PBold);
        add(lbl, "span 1, gapleft 50");
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
        add(connectButton, "span 2, gaptop 5, gapleft 50");
    }
    
    private void connect(String host, int port){
        connectWorker = new ConnectWorker(this, port, host);
        connectWorker.execute();
    }
    
    public void connected(final String host, final int port, final Socket clientSocket, final ObjectInputStream in, final ObjectOutputStream out){        
        frame.setVisible(false);
        hostField.setText("");
        portField.setText("");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameFrame(host, port, clientSocket, in, out, frame);
            }
        });        
    }
    
}
