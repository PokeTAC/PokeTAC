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
    private Pokemon activePokemon;
    private Movimiento nextMove;
    
    public List<Pokemon> getTeam(){
        return pokemons;
    }
    public void setTeam(List<Pokemon> pokemonTeam) {
        pokemons = pokemonTeam;
    }
    public Pokemon getActivePokemon() {
        return activePokemon;
    }
    public Movimiento getNextMove() {
        return nextMove;
    }

    
    //private int idBatalla;
    
    
    
    
    public Trainer(String name)
    {
        id = counter++;
        
        this.name = name;        
    }
    
    public void changePokemon(Pokemon pokemon)
    {
        activePokemon = pokemon;
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

    public void setNextMove(Movimiento move)
    {
        nextMove = move;
    }
    
}
