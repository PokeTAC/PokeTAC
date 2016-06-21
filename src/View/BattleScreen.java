/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import modelo.Movement;
import modelo.Pokemon;
import modelo.Trainer;
import poketac.PokeTAC;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.text.DefaultCaret;
/**
 *
 * @author Usuario
 */
public class BattleScreen extends javax.swing.JPanel{
    private final MainWindow mw;
    private final PokeTAC logicMan;
    private BattleThread bthread;
    private PokemonStatus pc;
    private PokemonStatus user;
    BufferedImage imgUser=null,imgPc=null,background=null;
    //ImageIcon imgUser=null,imgPc=null;
    

    public BattleScreen(MainWindow mw, PokeTAC logicMan) {
        initComponents();
        ((DefaultCaret)txtaLog.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        this.mw = mw;
        this.logicMan = logicMan;
        initPokemonStatus();
    }
    
    private void initPokemonStatus(){
        user = new PokemonStatus(logicMan.getBattle().getEntrenadores().get(0).getActivePokemon());
        pc = new PokemonStatus(logicMan.getBattle().getEntrenadores().get(1).getActivePokemon());
        String route="Files/battleground.png";
        try{
        background=ImageIO.read(new File(route));
        }catch (IOException e) {
                System.out.println("error:PokeImage bg Loader");
                e.printStackTrace();
           }
       //pnlBattle.add(new JLabel(new ImageIcon(background)));
        updatePokemonDisplay();   
    }
    
    public void advanceTurn(){
        bthread = new BattleThread(txtaLog, logicMan, this); bthread.start();
    }
    
    public void battleThreadDone(){
        if(logicMan.getBattle().isBattleOver()){
            String winnerName; if (!logicMan.isUserTurn()) winnerName=logicMan.getBattle().getEntrenadores().get(0).getName(); else winnerName=logicMan.getBattle().getEntrenadores().get(1).getName();
            int i = JOptionPane.showConfirmDialog(pnlActions,"Ganador: " + winnerName + "\nJuego terminado. ¿Desea jugar otra vez?", "Juego terminado", JOptionPane.YES_NO_OPTION);
            if(i==0){
                mw.restart();
            }else{
                mw.dispose();
            }
        }else{
            if(!logicMan.isUserTurn()){
                Movement mov;
                
                switch(mw.getAIType()){
                    case MINMAX:
                        mov = logicMan.selectAIMinMaxMove();
                        break;
                    case ALEATORIO:
                    default:
                         mov = logicMan.selectAIMove();
                }
                
                             
                Pokemon p = logicMan.getBattle().getEntrenadores().get(1).getActivePokemon();
                if(mov!=null){
                    String str = txtaLog.getText() + p.getPokeInfo().getNombre() + " usó " + mov.getName() + ".\n";
                    txtaLog.setText(str);
                }else{
                    String str = "La computadora cambió de Pokemon a: " + p.getPokeInfo().getNombre();
                    bthread.writeToScreen(str);
                }
                advanceTurn();
            }
        }
    }
    
    private void advanceThread(){
        if(bthread.isAlive()){
                synchronized(bthread.monitor){
                bthread.monitor.notify();
            }
        }  
    }
    
    public void updatePokemonDisplay(){
        pnlUser.removeAll();
        pnlAI.removeAll();
        pc.setPokemon(logicMan.getBattle().getEntrenadores().get(1).getActivePokemon());
        user.setPokemon(logicMan.getBattle().getEntrenadores().get(0).getActivePokemon());
        imgUser=logicMan.getBattle().getEntrenadores().get(0).getActivePokemon().getPokeInfo().getImagenB();
        imgPc=logicMan.getBattle().getEntrenadores().get(1).getActivePokemon().getPokeInfo().getImagen();
        
        pnlUser.add(new JLabel(new ImageIcon(imgUser)));
        // pnlUser.add(new JLabel(imgUser));
        pnlUser.add(new Box.Filler(new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 0), new java.awt.Dimension(20, 32767)));
        pnlUser.add(user);
        pnlUser.add(new Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767)));
        
        pnlAI.add(new Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767)));
        pnlAI.add(pc);
        pnlAI.add(new Box.Filler(new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 0), new java.awt.Dimension(40, 32767)));
        pnlAI.add(new JLabel(new ImageIcon(imgPc)));
       // pnlAI.add(new JLabel(imgPc));
        revalidate();
        repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBottom = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtaLog = new javax.swing.JTextArea();
        pnlActions = new javax.swing.JPanel();
        btnFight = new javax.swing.JButton();
        btnPKMN = new javax.swing.JButton();
        btnQuit = new javax.swing.JButton();
        pnlBattle = new javax.swing.JPanel();
        pnlUser = new javax.swing.JPanel();
        pnlAI = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        pnlBottom.setLayout(new java.awt.BorderLayout(10, 0));

        txtaLog.setEditable(false);
        txtaLog.setColumns(20);
        txtaLog.setRows(5);
        txtaLog.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtaLogMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(txtaLog);

        pnlBottom.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlActions.setLayout(new java.awt.GridLayout(2, 2));

        btnFight.setText("FIGHT");
        btnFight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFightActionPerformed(evt);
            }
        });
        pnlActions.add(btnFight);

        btnPKMN.setText("PKMN");
        btnPKMN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPKMNActionPerformed(evt);
            }
        });
        pnlActions.add(btnPKMN);

        btnQuit.setText("QUIT");
        pnlActions.add(btnQuit);

        pnlBottom.add(pnlActions, java.awt.BorderLayout.LINE_END);

        add(pnlBottom, java.awt.BorderLayout.SOUTH);

        pnlBattle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));
        pnlBattle.setLayout(new java.awt.BorderLayout());

        pnlUser.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 5));
        pnlBattle.add(pnlUser, java.awt.BorderLayout.PAGE_END);

        pnlAI.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5));
        pnlBattle.add(pnlAI, java.awt.BorderLayout.PAGE_START);

        add(pnlBattle, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFightActionPerformed
        if(logicMan.isUserTurn()){
            JPopupMenu menu = new JPopupMenu("Movimientos");
            Pokemon p = user.getPokemon();
            for(Movement mov : p.getMovimientos()){
                menu.add(new AbstractAction("" + mov) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String log = txtaLog.getText() + p.getPokeInfo().getNombre() + " usó " + mov.getName() + ".\n";
                        txtaLog.setText(log);
                        logicMan.getBattle().getEntrenadores().get(0).setNextMove(mov);
                        advanceTurn();
                    }
                });
            }
            menu.show(btnFight, btnFight.getX() - menu.getWidth(), btnFight.getY());
        }   
    }//GEN-LAST:event_btnFightActionPerformed

    private void btnPKMNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPKMNActionPerformed
        if(logicMan.isUserTurn()){
            Trainer t = logicMan.getBattle().getEntrenadores().get(0);
            PokemonChange pokeChange = new PokemonChange(mw, true, t.getTeam());
            Pokemon selectedPokemon = pokeChange.displayPokemonChange();
            if(selectedPokemon!=null && selectedPokemon.getId()!=user.getPokemon().getId()){
                int index = t.getTeam().indexOf(selectedPokemon);
                t.changePokemon(index);
                user.setPokemon(selectedPokemon);
                String log =txtaLog.getText()+ t + " cambió de Pokemon a: " + selectedPokemon.getPokeInfo().getNombre() + "\n.";
                txtaLog.setText(log);
                advanceTurn();
            }
        }   
    }//GEN-LAST:event_btnPKMNActionPerformed

    private void txtaLogMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtaLogMouseClicked
        advanceThread();
    }//GEN-LAST:event_txtaLogMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFight;
    private javax.swing.JButton btnPKMN;
    private javax.swing.JButton btnQuit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlAI;
    private javax.swing.JPanel pnlActions;
    private javax.swing.JPanel pnlBattle;
    private javax.swing.JPanel pnlBottom;
    private javax.swing.JPanel pnlUser;
    private javax.swing.JTextArea txtaLog;
    // End of variables declaration//GEN-END:variables
}
