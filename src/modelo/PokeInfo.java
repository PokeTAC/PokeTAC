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
public class PokeInfo {
    private int id;
    private String nombre;
    private int hp;
    private int ataque;
    private int defensa;
    private int velocidad;
    
    private List<PokeType> tipos;
    
    private List<Movimiento> moves;

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
    
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public List<PokeType> getTipos() {
        return tipos;
    }

    public void setTipos(List<PokeType> tipos) {
        this.tipos = tipos;
    }

    /**
     * @return the moves
     */
    public List<Movimiento> getMoves() {
        return moves;
    }
}
