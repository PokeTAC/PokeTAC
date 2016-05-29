/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DiegoAndres
 */
public class Battle {
    
    final int POKEEFFECT_DURATION = 3; //Como los archivos no indican nada, el efecto siempre durara 3 turnos
    
    static int idCounter = 0;
    
    private int id;
    private List<Trainer> trainers;
    private int turnCount; //Cuenta los turnos en total
    private int nextToMove; //Indica a que entrenador le toca siguiente

    public int getId() {
        return id;
    }

    public List<Trainer> getEntrenadores() {
        return trainers;
    }
    
    
    // << Constructors >>    
    
    public Battle(Trainer trainer1, Trainer trainer2)
    {
        id = idCounter++;
        
        trainers = new ArrayList<>();
        trainers.add(trainer1);
        trainers.add(trainer2);
        
        turnCount = 0;
        
        nextToMove = 0;
    }
    
    
    // << ExternalMethods >>
    
    public Trainer nextTrainer()
    {   
        return trainers.get(nextToMove);
    }
    
    
    public void proccessTurnLogic()
    {
        Trainer trainer = nextTrainer();
        Trainer trainerOp; if (nextToMove==0) trainerOp = trainers.get(1); else trainerOp = trainers.get(0);
        
        //Si el siguiente movimiento es null (hizo cambio de pokemon), pasar el turno
        if (trainer.getNextMove()!=null)
        {
            PokeInfo pokeInfo = trainer.getActivePokemon().getPokeInfo();
            PokeInfo pokeInfoOp = trainerOp.getActivePokemon().getPokeInfo();
            
            //Damage = (Atck/Def_op)*base_power – speed_op*10;
            int damage = (pokeInfo.getAtaque() / pokeInfoOp.getDefensa()) * trainer.getNextMove().getBasePower() - pokeInfoOp.getVelocidad()*10;
            trainerOp.getActivePokemon().setHitPoints(trainer.getActivePokemon().getHitPoints() - damage);
            
            //== Procesar efecto
            
            
            
            //Consumir movimiento
            trainer.setNextMove(null);
            
            //Verificar si el poquemon murio y cambiarlo por otro vivo
            if (trainerOp.getActivePokemon().getHitPoints()==0)
            {                
                List<Pokemon> pokeTeamOp = trainerOp.getTeam();
                
                for (int i = 0; i < pokeTeamOp.size(); i++) {
                    
                    if (pokeTeamOp.get(i).getHitPoints()>0)
                    {
                        trainerOp.changePokemon(pokeTeamOp.get(i));
                        break;
                    }
                } 
            }
        }
        
        
        
        nextToMove++; if (nextToMove==trainers.size()) nextToMove = 0;
    }
    
    public boolean isBattlerOver()
    {
        for (Trainer trainer : trainers)
        {
            List<Pokemon> ipokemons = trainer.getTeam();
            
            for (Pokemon ipokemon : ipokemons)
            {
                if (ipokemon.getHitPoints()>0) return false;
            }
        }
        
        return true;
    }
    
    
    
    
}
