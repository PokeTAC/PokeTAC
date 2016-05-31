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
    
    
    public void proccessTurnLogic()
    {
        Trainer trainer = nextTrainer();
        Trainer trainerOp; if (nextToMove==0) trainerOp = trainers.get(1); else trainerOp = trainers.get(0);
        
        //Si el siguiente movimiento es null (hizo cambio de pokemon), pasar el turno
        if (trainer.getNextMove()!=null)
        {
            //Verificar si se puede mover
            boolean canPlay = true;
            for (Effect effect : trainer.getActivePokemon().getActiveEffects())
                if (effect.getEffectInfo()==EffectInfo.Paralyze || effect.getEffectInfo()==EffectInfo.Sleep)
                    {canPlay = false; break;}
            
            if (canPlay)
            {
                PokeInfo pokeInfo = trainer.getActivePokemon().getPokeInfo();
                PokeInfo pokeInfoOp = trainerOp.getActivePokemon().getPokeInfo();

                //== Procesar daño
                double probabilytie = (((double)pokeInfo.getVelocidad()*2/(double)pokeInfoOp.getVelocidad())*((double)trainer.getNextMove().getAccuracy())/100);
                Random rnd = new Random();
                //Revisar probabilidad de ser affectado
                if (probabilytie>rnd.nextDouble())
                {
                    //Calcular daño: Damage = (Atck/Def_op)*base_power 
                    double damage = ((double)pokeInfo.getAtaque() / (double)pokeInfoOp.getDefensa()) * (double)trainer.getNextMove().getBasePower();// - pokeInfoOp.getVelocidad()*10;
                    //Multiplicador de tipo
                    double multiplier = trainer.getNextMove().getPokeType().getMultiplier(trainerOp.getActivePokemon().getPokeInfo().getPokeTypes());
                    damage *= multiplier;
                    //Nuevo hitpoints
                    int newHitPoints = (int)(trainer.getActivePokemon().getHitPoints() - damage);
                    trainerOp.getActivePokemon().setHitPoints(newHitPoints);

                    //== Procesar efecto
                    EffectInfo effect = trainer.getNextMove().getPokeEffect();
                    trainerOp.getActivePokemon().activateEffect(effect);
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
        for (int i = 0; i < 10; i++) {
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
