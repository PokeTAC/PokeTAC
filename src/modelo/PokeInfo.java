/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
import java.awt.image.*;
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
    private BufferedImage  imagen;
    private BufferedImage imagenB;
    
    private List<PokeType> tipos;
    
    private List<Movement> moves;

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

    public List<PokeType> getPokeTypes() {
        return tipos;
    }

    public void setTipos(List<PokeType> tipos) {
        this.tipos = tipos;
    }

    /**
     * @return the moves
     */
    public List<Movement> getMoves() {
        return moves;
    }
    public void setMoves(List<Movement> moves){
        this.moves=moves;
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * @return the imagen
     */
    public BufferedImage getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }

    /**
     * @return the imagenB
     */
    public BufferedImage getImagenB() {
        return imagenB;
    }

    /**
     * @param imagenB the imagenB to set
     */
    public void setImagenB(BufferedImage imagenB) {
        this.imagenB = imagenB;
    }
}
