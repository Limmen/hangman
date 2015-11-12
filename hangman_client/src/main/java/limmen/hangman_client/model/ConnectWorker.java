/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hangman_client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;
import javax.swing.SwingWorker;
import limmen.hangman_client.gui.Controller;

/**
 * A worker thread to connect to a server.
 * Neccessary for not freezing the UI in case of network latency
 * @author kim
 */
public class ConnectWorker extends SwingWorker<Socket, Socket> {
    
    private final int port;
    private final String host;
    private Socket serverSocket;
    private final Controller contr;
    private ObjectInputStream in;
    private ObjectOutputStream out = null;
    private boolean successful = true;
    
    /**
     * Class constructor
     *
     * @param contr Controller instance
     * @param port portnumber
     * @param host hostname
     */
    public ConnectWorker(Controller contr, int port, String host){
        this.port = port;
        this.host = host;
        this.contr = contr;
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
            successful = false;
            return null;
        } catch (IOException e)
        {
            System.err.println("Couldn't get I/O for " +
                    "the connection to: " + port + "");
            successful = false;
            return null;
        }
        try{
            out = new ObjectOutputStream(serverSocket.getOutputStream());
            in = new ObjectInputStream(serverSocket.getInputStream());
        }
        catch(Exception e){
            successful = false;
        }
        return serverSocket;
    }
    
    /**
     * Called when doInBackground() returns.
     */
    @Override
    protected void done()
    {
        if(successful)
            contr.connected(this.host, this.port, this.serverSocket, this.in, this.out);
        else
            contr.failedConnectionAttempt(this.host, this.port);
    }
}
