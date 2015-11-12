/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.SwingWorker;
import limmen.hangman.util.Protocol;
import limmen.hangman_client.gui.Controller;

/**
 * Worker thread to write messages to server
 * @author kim
 */
public class WriteWorker extends SwingWorker <Boolean, Integer> {
    
    private final ObjectOutputStream out;
    private final Protocol msg;
    private final Controller contr;
    
    /**
     * Class constructor. Sending msg to out.
     * @param msg message to send to server
     * @param out ObjectOutputStream to serverconnection
     * @param contr Controller instance
     */
    public WriteWorker(ObjectOutputStream out, Protocol msg, Controller contr){
        this.contr = contr;
        this.msg = msg;
        this.out = out;
    }

    /**
     * Sends message to server
     * @return boolean
     * @throws Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {             
        respond(msg);
        return true;
    }
    /*
    * Sends message through ObjectOutputStream to server
    */
    private void respond(Protocol obj){
        try {
            out.writeObject(obj);
            out.flush();
        } catch (IOException e) {
            contr.connectionWasLost();
        }
    }

    
}