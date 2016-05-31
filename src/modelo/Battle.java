/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    
    public Battle(Battle original)
    {
        id = idCounter++;
        
        trainers = new ArrayList<>();
        for (int i = 0; i < original.trainers.size(); i++) {
            trainers.add(new Trainer(original.trainers.get(i)));
        }
        
        turnCount = original.turnCount;
        nextToMove = original.nextToMove;
        
    }
    
    
    
    // << ExternalMethods >>
    
    public Trainer nextTrainer()
    {   
        return trainers.get(nextToMove);
    }
    
    
    public List<String> proccessTurnLogic()
    {
        List<String> log = new ArrayList<>();
        Trainer trainer = nextTrainer();
        Trainer trainerOp; if (nextToMove==0) trainerOp = trainers.get(1); else trainerOp = trainers.get(0);
        
        PokeInfo pokeInfo = trainer.getActivePokemon().getPokeInfo();
        PokeInfo pokeInfoOp = trainerOp.getActivePokemon().getPokeInfo();
        
        //Si el siguiente movimiento es null (hizo cambio de pokemon), pasar el turno
        if (trainer.getNextMove()!=null)
        {      
            //Verificar si se puede mover
            boolean canPlay = true;
            for (Effect effect : trainer.getActivePokemon().getActiveEffects())
                if (effect.getEffectInfo()==EffectInfo.Paralyze || effect.getEffectInfo()==EffectInfo.Sleep){
                    log.add(pokeInfo.getNombre() + " no se puede mover!");
                    canPlay = false; 
                    break;
                }
            
            if (canPlay)
            {
                //== Procesar daño
                double probabilytie = (((double)pokeInfo.getVelocidad()*1.5/(double)pokeInfoOp.getVelocidad())*((double)trainer.getNextMove().getAccuracy())/100);
                Random rnd = new Random();
                //Revisar probabilidad de ser affectado
                if (probabilytie>rnd.nextDouble())
                {
                    //Calcular daño: Damage = (Atck/Def_op)*base_power 
                    double damage = ((double)pokeInfo.getAtaque() / (double)pokeInfoOp.getDefensa()) * (double)trainer.getNextMove().getBasePower();// - pokeInfoOp.getVelocidad()*10;
                    //Multiplicador de tipo
                    double multiplier = trainer.getNextMove().getPokeType().getMultiplier(trainerOp.getActivePokemon().getPokeInfo().getPokeTypes());
                    damage *= multiplier;
                    //Para que no baje tan rapido
                    damage /= 5;
                    //Nuevo hitpoints
                    int newHitPoints = (int)(trainerOp.getActivePokemon().getHitPoints() - (int)damage);
                    trainerOp.getActivePokemon().setHitPoints(newHitPoints);
                    log.add(pokeInfoOp.getNombre() + " recibió " + (int)damage + " puntos de daño.");

                    //== Procesar efecto
                    EffectInfo effect = trainer.getNextMove().getPokeEffect();
                    trainerOp.getActivePokemon().activateEffect(effect);
                }else{
                    log.add(pokeInfo.getNombre() + " falló!");
                }                
            }

            
            //Consumir movimiento
            trainer.setNextMove(null);
            
            
            //==Procesar daños y turnos de efectos 
            ProccessEffects(trainer);
            ProccessEffects(trainerOp);
            
            
            //Verificar si el poquemon murio y cambiarlo por otro vivo
            if (trainerOp.getActivePokemon().getHitPoints()==0)
            {
                log.add(pokeInfoOp.getNombre() + " perdió el conocimiento.");
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
        return log;
    }
    
    public boolean isBattlerOver()
    {
        boolean result = true;
        
        for (Trainer trainer : trainers)
        {
            result = true;
            
            List<Pokemon> ipokemons = trainer.getTeam();
            
            for (Pokemon ipokemon : ipokemons)
            {
                if (ipokemon.getHitPoints()>0) result = false;
            }
            
            if(result == true) return result;
        }
        
        return result;
    }

    private static void ProccessEffects(Trainer trainer) {
        
        List<Effect> effects = trainer.getActivePokemon().getActiveEffects();
        for (int i = 0; i < effects.size(); i++) {
                        //En caso de veneno
            if (effects.get(i).getEffectInfo()==EffectInfo.Poison){
                trainer.getActivePokemon().setHitPoints(trainer.getActivePokemon().getHitPoints()-effects.get(i).getEffectInfo().POISON_DAMAGE);
            }
            
            //Reducir turnos de efectos
            effects.get(i).decreaseRemainingTurns();
            if (effects.get(i).getRemainingTurns()==0)
            {
                effects.remove(i);
                i--;
            }
        }
    }
    
    public boolean isUserTurn(){
        return nextToMove == 0;
    }
    
    
    
    
}
