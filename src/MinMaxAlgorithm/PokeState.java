/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinMaxAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import modelo.Battle;
import modelo.Movement;
import modelo.Pokemon;
import modelo.Trainer;

/**
 *
 * @author Usuario
 */
public class PokeState extends MinMaxState{
    private Battle battle;
    private Movement chosenMove;
    
    public PokeState(Battle battle){
        this.battle = battle;
    }
    
    public Movement getChosenMove(){
        return this.chosenMove;
    }
    
    public Battle getBattle(){
        return this.battle;
    }

    @Override
    public List<MinMaxState> findChildren() {
        Trainer t = battle.activeTrainer();
        
        List<MinMaxState> result = new ArrayList<>();
        Pokemon activePoke = t.getActivePokemon();
        
        for(Movement m : activePoke.getMovimientos()){
            Battle cpyBattle = new Battle(battle);
            cpyBattle.activeTrainer().setNextMove(m);
            cpyBattle.proccessTurnLogic();
            PokeState child = new PokeState(cpyBattle);
            child.chosenMove = m;
            result.add(child);
        }
        
        for(int i = 0; i< t.getTeam().size(); i++){
            Pokemon p = t.getTeam().get(i);
            if(p.getHitPoints()>0 && p.getId() != activePoke.getId()){
                Battle cpyBattle = new Battle(battle);
                cpyBattle.activeTrainer().changePokemon(i);
                cpyBattle.proccessTurnLogic();
                PokeState child = new PokeState(cpyBattle);
                child.chosenMove = null;
                result.add(child);
            }
        }
        
        return result;
    }

    @Override
    public void calculateHValue() {
        
        //Si usa Heuristca 2, en funcion a pesos
        if (battle.getEntrenadores().get(0).getWeights()!=null)
        {
            //Ratio de Hitpoints
            int inactvHP = 0, activeHP = 0;
            for(Pokemon p : battle.activeTrainer().getTeam()){
                activeHP += p.getHitPoints();
            }
            for(Pokemon p : battle.inactiveTrainer().getTeam()){
                inactvHP += p.getHitPoints();
            }
            double hpRate = (inactvHP<=0)? 100 : (double)activeHP/inactvHP;
            
            //Ratio de poquemon vivos
            int alive = 0;
            for(Pokemon p : battle.activeTrainer().getTeam()){
                alive += (p.getHitPoints()>0)? 1 : 0;
            }
            double aliveRate = (double)alive/4;
            
            double[] w = battle.activeTrainer().getWeights();
            
            setHValue((int)((w[0]*hpRate + w[1]*aliveRate)*1000));
        }
        //Si usa Heuristca 1, diferencia de puntos
        else
        {
            int pcHP = 0, userHP = 0;
            for(Pokemon p : battle.getEntrenadores().get(0).getTeam()){
                userHP += p.getHitPoints();
            }
            for(Pokemon p : battle.getEntrenadores().get(1).getTeam()){
                pcHP += p.getHitPoints();
            }
            setHValue(pcHP - userHP);
        }
    }
    
    @Override
    boolean isLeaf() {
        return battle.isBattleOver();
    }
    
}
