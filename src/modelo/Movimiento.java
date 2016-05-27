/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author DiegoAndres
 */
public class Movimiento {
    private int id;
    private String nombre;
    private int base_power;
    private int accuracy;
    private int idTipo;
    
    // efectos adicionales
    private boolean dormir;
    private boolean disminuir_velocidad;
    private boolean disminuir_ataque;
    
    
    public int getBasePower()
    {
        return base_power;
    }
    
}
