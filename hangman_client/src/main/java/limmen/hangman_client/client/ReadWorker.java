/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hangman_client.client;

import static java.awt.SystemColor.window;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import limmen.hangman.util.CommunicationProtocol;
import limmen.hangman.util.Congratulations;
import limmen.hangman.util.GameOver;
import limmen.hangman.util.Result;
import limmen.hangman_client.gui.GameFrame;

/**
 *
 * @author kim
 */
public class ReadWorker extends SwingWorker <Boolean, Integer> {
    
    private final ObjectInputStream in;
    private boolean running;
    private final GameFrame frame;
    private CommunicationProtocol msg;
    
    /**
     *
     * @param area
     * @param in
     * @param window
     */
    public ReadWorker(ObjectInputStream in, GameFrame frame){
        this.in = in;
        this.frame = frame;
    }
    
    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {
        running = true;
        while(running){
            msg = read();
            if(msg != null){                                
                if(msg instanceof Result){
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            frame.updateGame((Result) msg);
                        }
                    });
                }
                if(msg instanceof Congratulations){
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            frame.Congratulations((Congratulations) msg);
                        }
                    });
                }
                if(msg instanceof GameOver){
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            frame.GameOver((GameOver) msg);
                        }
                    });
                }
            }
        }
        
        return false;
    }
    
    
    private CommunicationProtocol read(){
        Object input;
        try {
            input = in.readObject();
        } catch (ClassNotFoundException cnfe) {
            running = false;
            return null;
        } catch (OptionalDataException ode) {
            running = false;
            return null;
        } catch (IOException ioe) {
            running = false;
            return null;
        }
        if (input instanceof CommunicationProtocol) {
            return (CommunicationProtocol) input;
        }
        else{
            return null;
        }
    }
    
}