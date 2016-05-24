/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;

/**
 *
 * @author DiegoAndres
 */
public class InstanciaPokemon {
    static int counter = 0;
    private int id;
    private String nombre;
    private Pokemon pokemon;
    private int idEntrenador;
    private List<Movimiento> moves;
    private String estado;
    private int hitPoints;
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pokemon getIdPokemon() {
        return pokemon;
    }

    public void setIdPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
    
    
    
    public InstanciaPokemon(Pokemon pokemon, List<Movimiento> moves)
    {
        id = counter++;
        
        this.pokemon = pokemon;
        
        this.moves = moves;
        
    }
    
    
    
    
    
    
    
    
    
    
}
