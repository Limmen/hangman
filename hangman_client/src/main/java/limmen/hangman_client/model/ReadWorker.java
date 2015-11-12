/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.hangman_client.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import limmen.hangman.util.BadProtocolException;
import limmen.hangman.util.Protocol;
import limmen.hangman_client.gui.Controller;
import limmen.hangman_client.gui.GameFrame;

/**
 * Worker thread to read messages from socket and add events to EDT
 * when neccessary.
 * @author kim
 */
public class ReadWorker extends SwingWorker <Boolean, Integer> {
    
    private final ObjectInputStream in;
    private boolean running;
    private final GameFrame frame;
    private Protocol msg;
    private Controller contr;
    
    /**
     * Class constructor.
     * Frame is neccessary for updating the UI later.
     * @param in ObjectInputStream to the socket connection
     * @param frame JFrame for adding UI-updated to EDT when neccessary
     * @param contr Controller instance. 
     */
    public ReadWorker(ObjectInputStream in, GameFrame frame, Controller contr){
        this.contr = contr;
        this.in = in;
        this.frame = frame;
    }
    
    /**
     * Loop-read a inputstream and add events to EDT when neccessary
     * @return boolean 
     * @throws Exception
     */
    @Override
    protected Boolean doInBackground() throws Exception {
        running = true;
        while(running){
            try{
                msg = read();
                if(msg != null){
                    switch (msg.getCommand()) {
                        case RESULT:
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    frame.updateGame(msg);
                                }
                            });
                            break;
                        case CONGRATULATIONS:
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    frame.congratulations(msg);
                                }
                            });
                            break;
                        case GAMEOVER:
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    frame.gameOver(msg);
                                }
                            });
                            break;
                    }
                }
            }
            catch(BadProtocolException e){
                contr.connectionWasLost();
            }
        }
        
        return false;
    }
    
    /*
    * Reads ObjectInputStream from server.
    */
    private Protocol read() throws BadProtocolException{
        Object input;
        try {
            input = in.readObject();
        } catch (ClassNotFoundException cnfe) {
            contr.connectionWasLost();
            running = false;
            return null;
        } catch (OptionalDataException ode) {
            contr.connectionWasLost();
            running = false;
            return null;
        } catch (IOException ioe) {
            contr.connectionWasLost();
            running = false;
            return null;
        }
        if (input instanceof Protocol) {
            return (Protocol) input;
        }
        else{
            throw new BadProtocolException();
        }
    }
    
}