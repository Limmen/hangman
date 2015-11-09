/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.gui;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import limmen.hangman_client.model.ConnectWorker;
import limmen.hangman_client.gui.Controller.ConnectListener;
import net.miginfocom.swing.MigLayout;

/**
 * JPanel that contains a form for connecting to a server.
 * @author kim
 */
public class ConnectPanel extends JPanel {
    
    private final Font Plain = new Font("Serif", Font.PLAIN, 12);
    private final Font Title = new Font("Serif", Font.PLAIN, 14);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    
    private final JTextField hostField;
    private final JTextField portField;
    private final Controller contr;
    
    /**
     * Class constructor
     * @param contr Controller instance
     */
    public ConnectPanel(Controller contr){
        this.contr = contr;
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
        connectButton.addActionListener(contr.new ConnectListener(hostField, portField));
        add(connectButton, "span 2, gaptop 5");
    }
    
}
