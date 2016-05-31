/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MinMaxAlgorithm;

import java.util.ArrayList;
import java.util.List;
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
        Trainer t = battle.nextTrainer();
        
        List<MinMaxState> result = new ArrayList<>();
        Pokemon activePoke = t.getActivePokemon();
        
        for(Movement m : activePoke.getMovimientos()){
            Battle cpyBattle = new Battle(battle);
            cpyBattle.nextTrainer().setNextMove(m);
            cpyBattle.proccessTurnLogic();
            PokeState child = new PokeState(cpyBattle);
            child.chosenMove = m;
            result.add(child);
        }
        
        for(int i = 0; i< t.getTeam().size(); i++){
            Pokemon p = t.getTeam().get(i);
            if(p.getHitPoints()>0 && p.getId() != activePoke.getId()){
                Battle cpyBattle = new Battle(battle);
                cpyBattle.nextTrainer().changePokemon(i);
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
        int pcHP = 0, userHP = 0;
        for(Pokemon p : battle.getEntrenadores().get(0).getTeam()){
            userHP += p.getHitPoints();
        }
        for(Pokemon p : battle.getEntrenadores().get(1).getTeam()){
            pcHP += p.getHitPoints();
        }
        setHValue(pcHP - userHP);
    }

    @Override
    boolean isLeaf() {
        return battle.isBattlerOver();
    }
    
}
