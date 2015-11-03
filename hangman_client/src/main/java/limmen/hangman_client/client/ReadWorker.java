/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hangman_client.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import limmen.hangman_client.gui.MainWindow;
import limmen.hangman_client.util.CommunicationProtocol;
import limmen.hangman_client.util.Congratulations;
import limmen.hangman_client.util.GameOver;
import limmen.hangman_client.util.Result;

/**
 *
 * @author kim
 */
public class ReadWorker extends SwingWorker <Boolean, Integer> {
    
    private final ObjectInputStream in;
    private boolean running;
    private final MainWindow window;
    private CommunicationProtocol msg;
    
    /**
     *
     * @param area
     * @param in
     * @param window
     */
    public ReadWorker(ObjectInputStream in, MainWindow window){
        this.in = in;
        this.window = window;
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
                            window.updateGame((Result) msg);
                        }
                    });
                }
                if(msg instanceof Congratulations){
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            window.Congratulations((Congratulations) msg);
                        }
                    });
                }
                if(msg instanceof GameOver){
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            window.GameOver((GameOver) msg);
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