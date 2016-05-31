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
    
    public Battle(Battle oBattle){
        
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
              
            //== Procesar daño
            //Calcular daño: Damage = (Atck/Def_op)*base_power – speed_op*10  ???
            double damage = (pokeInfo.getAtaque() / pokeInfoOp.getDefensa()) * trainer.getNextMove().getBasePower() - pokeInfoOp.getVelocidad()*10;
            //Multiplicador de tipo
            double multiplier = trainer.getNextMove().getPokeType().getMultiplier(trainerOp.getActivePokemon().getPokeInfo().getPokeTypes());
            damage *= multiplier;
            //Nuevo hitpoints
            int newHitPoints = (int)(trainer.getActivePokemon().getHitPoints() - damage);
            trainerOp.getActivePokemon().setHitPoints(newHitPoints);
            
            //== Procesar efecto
            if (true) //TODO: Revisar probabilidad de ser affectado
            {
                EffectInfo effect = trainer.getNextMove().getPokeEffect();
                trainerOp.getActivePokemon().activateEffect(effect);
            }
            
            //Consumir movimiento
            trainer.setNextMove(null);
            
            
            //==Procesar daños y turnos de efectos 
            ProccessEffects(trainer);
            ProccessEffects(trainerOp);
            
            
            //Verificar si el poquemon murio y cambiarlo por otro vivo
            if (trainerOp.getActivePokemon().getHitPoints()==0)
            {                
                List<Pokemon> pokeTeamOp = trainerOp.getTeam();
                
                for (int i = 0; i < pokeTeamOp.size(); i++) {
                    
                    if (pokeTeamOp.get(i).getHitPoints()>0)
                    {
                        trainerOp.changePokemon(i);
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

    private static void ProccessEffects(Trainer trainer) {
        
        List<Effect> effects = trainer.getActivePokemon().getActiveEffects();
        for(Effect effect : effects)
        {
            //En caso de veneno
            if (effect.getEffectInfo()==EffectInfo.Poison){
                trainer.getActivePokemon().setHitPoints(trainer.getActivePokemon().getHitPoints()-effect.getEffectInfo().POISON_DAMAGE);
            }
            
            //Reducir turnos de efectos
            effect.decreaseRemainingTurns();
            if (effect.getRemainingTurns()==0)
            {
                effects.remove(effect); //No se si esto funciona bien
            }
        }
    }
    
    public boolean isUserTurn(){
        return nextToMove == 0;
    }
    
    
    
    
}
