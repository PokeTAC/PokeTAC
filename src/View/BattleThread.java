/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import poketac.PokeTAC;

/**
 *
 * @author Usuario
 */
public class BattleThread extends Thread{
    private final JTextArea txta;
    private final BattleScreen bscreen;
    private final PokeTAC logicMan; 
    public final Object monitor = new Object();
    
    public BattleThread(JTextArea txta, PokeTAC logicMan, BattleScreen bscreen){
        this.txta = txta;
        this.logicMan = logicMan;
        this.bscreen = bscreen;
    }

    @Override
    public void run() {
        logicMan.getBattle().proccessTurnLogic(this);
        bscreen.battleThreadDone();
    }
    
    public void writeToScreen(String str){
        txta.setText(txta.getText() + str + "\n");
        bscreen.updatePokemonDisplay();
        try {
            synchronized(monitor){
                monitor.wait();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(BattleThread.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }
    
}
