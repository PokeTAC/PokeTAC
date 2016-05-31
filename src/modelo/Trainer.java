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
public class Trainer {
    
    static int counter = 0;
    
    private int id;
    private String name;
    private List<Pokemon> pokemons;
    private int activePokemon;
    private Movement nextMove;
    
    public List<Pokemon> getTeam(){
        return pokemons;
    }
    public void setTeam(List<Pokemon> pokemonTeam) {
        pokemons = pokemonTeam;
    }
    
    public Pokemon getActivePokemon() {
        return pokemons.get(activePokemon);
    }
    
    public int getActivePokemonIndex() {
        return activePokemon;
    }
    
    public Movement getNextMove() {
        return nextMove;
    }

    
    //private int idBatalla;
    
    
    
    
    public Trainer(String name)
    {
        id = counter++;
        activePokemon = 0;
        this.name = name;        
    }
    
    public Trainer(Trainer original)
    {
        id = counter++;
        name = original.name;
        pokemons = new ArrayList();
        for (int i = 0; i < original.pokemons.size(); i++) {
            pokemons.add(new Pokemon(original.pokemons.get(i)));
        }
        activePokemon = original.activePokemon;
        nextMove = original.nextMove;
    }
    
    public void changePokemon(int index)
    {
        activePokemon = index;
        /*
        if (pokemons.contains(pokemon))
        {
            if (pokemon.getHitPoints()>0)
            {
                
            }
            else
            {
                //throw new Exception("Pokemon no tiene vida restante.");
            }
        }
        else
        {
            //throw new Exception("Pokemon no pertenece al equipo.");
        }
        */
    }

    public void setNextMove(Movement move)
    {
        nextMove = move;
    }

    @Override
    public String toString() {
        return name; //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
