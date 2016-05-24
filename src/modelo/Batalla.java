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
public class Batalla {
    
    static int idCounter = 0;
    
    private int id;
    private List<Entrenador> trainers;
    private int turnCount; //Cuenta los turnos en total
    private int nextToMove; //Indica a que entrenador le toca siguiente

    public int getId() {
        return id;
    }

    public List<Entrenador> getEntrenadores() {
        return trainers;
    }
    
    
    // << Constructors >>    
    
    public Batalla(Entrenador trainer1, Entrenador trainer2)
    {
        id = idCounter++;
        
        trainers = new ArrayList<>();
        trainers.add(trainer1);
        trainers.add(trainer2);
        
        turnCount = 0;
        
        nextToMove = 0;
    }
    
    
    // << ExternalMethods >>
    
    public Entrenador nextTrainer()
    {   
        return trainers.get(nextToMove);
    }
    
    
    public void proccessTurnLogic()
    {
        Entrenador trainer = nextTrainer();
        
        
        //**Logia delataque............
        
        //trainer.getNextPoke();
        //trianer.getNextMove();
        
        //.............
        
        trainer.clearNextMove();
        
        nextToMove++; if (nextToMove==trainers.size()) nextToMove = 0;
    }
    
    public boolean isBattlerOver()
    {
        for (Entrenador trainer : trainers)
        {
            List<InstanciaPokemon> ipokemons = trainer.getTeam();
            
            for (InstanciaPokemon ipokemon : ipokemons)
            {
                if (ipokemon.getHitPoints()>0) return false;
            }
        }
        
        return true;
    }
    
    
    
    
}
