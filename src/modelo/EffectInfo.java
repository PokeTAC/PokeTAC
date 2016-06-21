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
public enum EffectInfo {
    None,
    Paralyze,
    Sleep,
    Poison;
    
    public final int EFFECT_DURATION = 3; //Como los archivos no indican nada, el efecto siempre durara 3 turnos
    public final int POISON_DAMAGE = 10;
}
