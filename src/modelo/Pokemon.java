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
    private List<Movement> moves;
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

    public List<Movement> getMovimientos() {
        return moves;
    }

    public void setMovimientos(List<Movement> movimientos) {
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
    
    public Pokemon(PokeInfo pokemon, List<Movement> moves)
    {
        id = counter++;
        
        this.pokeInfo = pokemon;
        
        this.moves = moves;
        
        this.activeEffects = new ArrayList();
        
        this.hitPoints = pokeInfo.getHp();
        
    }

    public Pokemon(Pokemon original) {
        
        id = counter++;
        name = original.name;
        pokeInfo = original.pokeInfo;
        hitPoints = original.hitPoints;
        idEntrenador = original.idEntrenador;
        moves = new ArrayList();
        for (int i = 0; i < original.moves.size(); i++) {
            moves.add(original.moves.get(i));
        }
        activeEffects = new ArrayList();
        for (int i = 0; i < original.activeEffects.size(); i++) {
            activeEffects.add(new Effect(original.activeEffects.get(i).getEffectInfo(), original.activeEffects.get(i).getRemainingTurns()));
        }
    }
    
    
    
    
    
    public List<Effect> getActiveEffects() {
        return activeEffects;
    }

    void activateEffect(EffectInfo effect) {

        for(Effect activeEffect : activeEffects)
        {
            if (activeEffect.getEffectInfo() == effect)
            {
                activeEffects.remove(activeEffect);
                break;
            }
        }
        
        activeEffects.add(new Effect(effect, effect.EFFECT_DURATION));
    }

    @Override
    public String toString() {
        String str = pokeInfo.getNombre();
        str += " HP: " + hitPoints + "/" + pokeInfo.getHp();
        str += " Tipo: ";
        for(PokeType t : pokeInfo.getPokeTypes()) str+= t.getName() + "/";
        str = str.substring(0, str.length()-1);
        return str; 
    }
    
  
    
}
