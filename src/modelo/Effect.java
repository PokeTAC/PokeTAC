/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Franco
 */
public class Effect {
    
    private EffectInfo effectInfo;
    private int remainingTurns;
    
    public Effect(EffectInfo effectInfo, int turns){
        this.effectInfo = effectInfo;
        this.remainingTurns = turns;
    }
    
    public EffectInfo getEffectInfo(){
        return effectInfo;
    }
    
    public int getRemainingTurns(){
        return remainingTurns;
    }
    
    public void decreaseRemainingTurns()
    {
        if (remainingTurns>0) remainingTurns--;
    }
            
}
