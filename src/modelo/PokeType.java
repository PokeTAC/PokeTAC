/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Dictionary;
import java.util.Map;

/**
 *
 * @author DiegoAndres
 */
public class PokeType {
    private int id;
    private String name;
    private Map<PokeType,Double> multiplicators;

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
}
