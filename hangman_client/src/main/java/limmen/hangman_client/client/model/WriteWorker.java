/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.client.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.SwingWorker;
import limmen.hangman.util.Protocol;

/**
 *
 * @author kim
 */
public class WriteWorker extends SwingWorker <Boolean, Integer> {
    
    private ObjectOutputStream out = null;
    private final Protocol msg;
    
    /**
     *
     * @param msg
     * @param out
     */
    public WriteWorker(ObjectOutputStream out, Protocol msg){
        this.msg = msg;
        this.out = out;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {             
        respond(msg);
        return true;
    }
    
    private void respond(Protocol obj){
        try {
            out.writeObject(obj);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    
}