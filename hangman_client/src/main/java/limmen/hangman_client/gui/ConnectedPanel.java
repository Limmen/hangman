/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 * JPanel that contains information om a current socket-connection.
 * @author kim
 */
public class ConnectedPanel extends JPanel {
    private final Font Plain = new Font("Serif", Font.PLAIN, 10);
    private final Font Title = new Font("Serif", Font.PLAIN, 10);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    
    private final GameFrame frame;
    
    /**
     * Class constructor.
     * @param hostname hostname
     * @param port portnumber
     * @param frame Frame for sending UI-updates if user disconnects.
     */
    public ConnectedPanel(String hostname, int port, GameFrame frame){
        this.frame = frame;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl;        
        lbl = new JLabel("Host: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        lbl = new JLabel(hostname);
        lbl.setFont(Plain);
        add(lbl, "span 1");
        lbl = new JLabel("Port: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        lbl = new JLabel(Integer.toString(port));
        lbl.setFont(Plain);
        add(lbl, "span 1");
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
        add(disconnect, "span 2, gaptop 5");
    }
    private void disconnect(){
        frame.disconnect();
    }
    
}
