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
public class Entrenador {
    
    static int counter = 0;
    
    private int id;
    private String name;
    private List<InstanciaPokemon> pokemons;
    private InstanciaPokemon nextPoke;
    private Movimiento nextMove;
    
    public List<InstanciaPokemon> getTeam(){
        return pokemons;
    }
    public void setTeam(List<InstanciaPokemon> pokemonTeam) {
        pokemons = pokemonTeam;
    }
    public InstanciaPokemon getNextPoke() {
        return nextPoke;
    }
    public Movimiento getNextMove() {
        return nextMove;
    }

    
    //private int idBatalla;
    
    
    
    
    public Entrenador(String name)
    {
        id = counter++;
        
        this.name = name;        
    }
    

    public void selectNextMove(InstanciaPokemon pokemon, Movimiento move)
    {
        //**verificar que el pokemon pertenee al team y que el move pertenee al poquemon.
        
        nextPoke = pokemon;
        nextMove = move;
    }
    
    public void clearNextMove()
    {
        nextPoke = null;
        nextMove = null;
    }


}
