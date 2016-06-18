/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author DiegoAndres
 */
public class Battle {
    
    static int idCounter = 0;
    
    private final int id;
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
        for (Trainer trainer : original.trainers) {
            trainers.add(new Trainer(trainer));
        }
        
        turnCount = original.turnCount;
        nextToMove = original.nextToMove;
        
    }
    
    
    
    // << ExternalMethods >>
    
    public Trainer activeTrainer()
    {   
        return trainers.get(nextToMove);
    }
    public Trainer inactiveTrainer()
    {   
        int inactiveTrainerIndex;
        
        if (nextToMove==0) inactiveTrainerIndex = 1; else inactiveTrainerIndex = 0;
        
        return trainers.get(inactiveTrainerIndex);
    }
    
    public List<String> proccessTurnLogic()
    {
        List<String> log = new ArrayList<>();
        Trainer trainer = activeTrainer();
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
                double prob = (((double)pokeInfo.getVelocidad()*1.5/(double)pokeInfoOp.getVelocidad())*((double)trainer.getNextMove().getAccuracy())/100);
                Random rnd = new Random();
                //Revisar probabilidad de ser affectado
                if (prob>rnd.nextDouble())
                {
                    //Calcular daño: Damage = (Atck/Def_op)*base_power 
                    double damage = ((double)pokeInfo.getAtaque() / (double)pokeInfoOp.getDefensa()) * (double)trainer.getNextMove().getBasePower();// - pokeInfoOp.getVelocidad()*10;
                    //Multiplicador de tipo
                    double multiplier = 1;
                    try {
                        multiplier = trainer.getNextMove().getPokeType().getMultiplier(trainerOp.getActivePokemon().getPokeInfo().getPokeTypes());
                    }
                    catch(Exception e){
                        System.out.println("error:PokeMultiplier use");
                        e.printStackTrace();
                    }

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
            proccessEffects(trainer, log);
            proccessEffects(trainerOp, log);
            
            
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
    
    public boolean isBattleOver()
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

    public Trainer getLoser()
    {
        boolean result = true;
        
        for (Trainer trainer : trainers)
        {
            result = true;
          
            List<Pokemon> ipokemons = trainer.getTeam();           
            for (Pokemon ipokemon : ipokemons)
            {
                if (ipokemon.getHitPoints()>0) 
                {
                    result = false;
                    break;
                }
            }
            
            if(result == true) return trainer;
        }
        
        return null;
    }
    
    private static void proccessEffects(Trainer trainer, List<String> log) {
        
        List<Effect> effects = trainer.getActivePokemon().getActiveEffects();
        for (int i = 0; i < effects.size(); i++) {
            //En caso de veneno
            if (effects.get(i).getEffectInfo()==EffectInfo.Poison){
                int dmg = effects.get(i).getEffectInfo().POISON_DAMAGE;
                trainer.getActivePokemon().setHitPoints(trainer.getActivePokemon().getHitPoints()-dmg);
                log.add(trainer.getActivePokemon().getPokeInfo().getNombre() + 
                        " recibió " + dmg + " puntos de daño por el veneno.");
            }
            
            //Reducir turnos de efectos
            effects.get(i).decreaseRemainingTurns();
            if (effects.get(i).getRemainingTurns()==0)
            {
                Effect e = effects.remove(i);
                log.add(trainer.getActivePokemon().getPokeInfo().getNombre() + 
                        " se libró del efecto: " + e.getEffectInfo().name() + ".");
                i--;
            }
        }
    }
    
    public boolean isUserTurn(){
        return nextToMove == 0;
    }
    
    
    
    
}
