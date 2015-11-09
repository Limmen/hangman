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
import javax.swing.SwingWorker;
/**
 * Worker thread to disconnect from server
 * Neccessary for not freezing the UI in case of network latency
 * @author kim
 */
public class DisconnectWorker extends SwingWorker <Boolean, Integer> {
  
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private final Socket serverSocket;
    
    /**
     * Class constructor
     * @param serverSocket socket connection to the server
     * @param out ObjectOutput stream to the server
     * @param in ObjectInputStream to the server
     */
    public DisconnectWorker(Socket serverSocket, ObjectOutputStream out, ObjectInputStream in){
        this.serverSocket = serverSocket;
        this.out = out;
        this.in = in;
    }

    /**
     * Closing socket connection and the streams
     * @return boolean wether the disconnection was successful or not.
     * @throws Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {             
        try {
            out.close();
            in.close();
            serverSocket.close();
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }          

    
}