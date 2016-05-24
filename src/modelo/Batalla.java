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
public class Batalla {
    private int id;
    private List<Entrenador> entrenadores;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Entrenador> getEntrenadores() {
        return entrenadores;
    }

    public void setEntrenadores(List<Entrenador> entrenadores) {
        this.entrenadores = entrenadores;
    }
}
