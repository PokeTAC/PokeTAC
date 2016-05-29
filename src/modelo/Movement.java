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
public class Movement {
    
    private static int counter = 0;
    
    private int id;
    private String name;
    private int base_power;
    private int accuracy;
    private PokeType pokeType;
    private Effect pokeEffect;
    
    public int getBasePower()    {
        return base_power;
    }
    public Effect getPokeEffect() {
        return pokeEffect;
    }
    public String getName() {
        return name;
    }
    public int getAccuracy() {
        return accuracy;
    }
    public PokeType getPokeType() {
        return pokeType;
    }
    
    
    public Movement(String name, int basePower, int accuracy, PokeType pokeType, Effect pokeEffect)
    {
        this.id = counter++; 
        this.name = name;
        this.base_power = basePower;
        this.accuracy = accuracy;
        this.pokeType = pokeType;
        this.pokeEffect = pokeEffect;
    }


    
}


