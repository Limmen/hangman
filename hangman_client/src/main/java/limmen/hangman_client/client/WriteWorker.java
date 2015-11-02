/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.hangman_client.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.SwingWorker;

/**
 *
 * @author kim
 */
public class WriteWorker extends SwingWorker <Boolean, Integer> {
    
    private ObjectOutputStream out = null;
    private final Object obj;
    
    /**
     *
     * @param message
     * @param out
     */
    public WriteWorker(Object obj, ObjectOutputStream out){
        this.obj = obj;
        this.out = out;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {             
        respond(obj);
        return true;
    }
    
    private void respond(Object obj){
        try {
            out.writeObject(obj);
            out.flush();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    
}