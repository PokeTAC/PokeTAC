/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.List;
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
    public String getName() {
        return name;
    }
    public double getMultiplier(List<PokeType> pokeTypes) {
      
        double multiplier = 1;
        
        for (Map.Entry<PokeType, Double> entry : multiplicators.entrySet())
        {
            for (PokeType pokeType : pokeTypes)
            {
                if (entry.getKey()==pokeType)
                {
                    multiplier *= entry.getValue();
                }
            }
        }
        
        return multiplier;
    }

    public PokeType(String name)
    {
        this.name = name;
    }

    public void SetMultiplicators(Map<PokeType,Double> multiplicators)
    {
        this.multiplicators = multiplicators;
    }
    
}

