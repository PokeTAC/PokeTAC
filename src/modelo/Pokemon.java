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
public class Pokemon {
    static int counter = 0;
    
    private int id;
    private String name;
    private PokeInfo pokeInfo;
    private int hitPoints;
    private int idEntrenador;
    private List<Movimiento> moves;
    
    private List<Effect> activeEffects;

    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return name;
    }

    public void setNombre(String nombre) {
        this.name = nombre;
    }

    public PokeInfo getIdPokemon() {
        return pokeInfo;
    }

    public void setIdPokemon(PokeInfo pokemon) {
        this.pokeInfo = pokemon;
    }

    public int getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(int idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public List<Movimiento> getMovimientos() {
        return moves;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.moves = movimientos;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        
        if (hitPoints < 0)
            this.hitPoints = 0;
        else
            this.hitPoints = hitPoints;
    }
    
    public PokeInfo getPokeInfo()
    {
        return pokeInfo;
    }
    
    public Pokemon(PokeInfo pokemon, List<Movimiento> moves)
    {
        id = counter++;
        
        this.pokeInfo = pokemon;
        
        this.moves = moves;
        
        this.activeEffects = new ArrayList<Effect>();
        
    }
    
    
    
    
    
    
    
    
    
    
}
