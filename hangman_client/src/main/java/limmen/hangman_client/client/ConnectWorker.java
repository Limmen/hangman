/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.client;

import static java.awt.SystemColor.window;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;
import javax.swing.SwingWorker;
import limmen.hangman_client.gui.ConnectPanel;

/**
 *
 * @author kim
 */
public class ConnectWorker extends SwingWorker<Socket, Socket> {
    
    private final int port;
    private final String host;
    private Socket clientSocket;
    private final ConnectPanel panel;
    private ObjectInputStream in;
    private ObjectOutputStream out = null; 
    
    /**
     *
     * @param window
     * @param port
     * @param host
     */
    public ConnectWorker(ConnectPanel panel, int port, String host){
        this.port = port;
        this.host = host;
        this.panel = panel;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Socket doInBackground() throws Exception {        
        try
        {
            clientSocket = new Socket(host,port);
        } catch (UnknownHostException e)
        {
            System.err.println("Don't know about host: " + host + ".");
            return null;
        } catch (IOException e)
        {
            System.err.println("Couldn't get I/O for " +
                    "the connection to: " + port + "");
            return null;
        }
        try{
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch(Exception e){
        }
        return clientSocket;
    }
    
    /**
     *
     */
    @Override
    protected void done()
    {
        panel.connected(this.host, this.port, this.clientSocket, this.in, this.out);        
    }        
}
