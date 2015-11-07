/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;
import javax.swing.SwingWorker;
import limmen.hangman_client.gui.ConnectPanel;

/** 
 * A worker thread to connect to a server.
 * Neccessary for not freezing the UI in case of network latency
 * @author kim
 */
public class ConnectWorker extends SwingWorker<Socket, Socket> {
    
    private final int port;
    private final String host;
    private Socket serverSocket;
    private final ConnectPanel panel;
    private ObjectInputStream in;
    private ObjectOutputStream out = null; 
    
    /**
     * Class constructor 
     * panel is used to add UI-updates to the EDT after connected.
     * @param panel panel used to add UI-updates to the EDT adter connected
     * @param port portnumber
     * @param host hostname
     */
    public ConnectWorker(ConnectPanel panel, int port, String host){
        this.port = port;
        this.host = host;
        this.panel = panel;
    }

    /**
     * Here we try to connect to the server.
     * @return serverSocket socket connection to the server
     * @throws Exception
     */
    @Override
    protected Socket doInBackground() throws Exception {        
        try
        {
            serverSocket = new Socket(host,port);
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
            out = new ObjectOutputStream(serverSocket.getOutputStream());
            in = new ObjectInputStream(serverSocket.getInputStream());
        }
        catch(Exception e){
        }
        return serverSocket;
    }
    
    /**
     * Called when doInBackground() returns.
     */
    @Override
    protected void done()
    {
        panel.connected(this.host, this.port, this.serverSocket, this.in, this.out);        
    }        
}
