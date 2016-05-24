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
public class TipoxTipo {
    // el tipo fuerte
    private int idTipoFuerte;
    
    // el tipo debil
    private int idTipoDebil;
    
    // porcentaje adicional de daño
    private int daño;

    public int getIdTipoFuerte() {
        return idTipoFuerte;
    }

    public void setIdTipoFuerte(int idTipoFuerte) {
        this.idTipoFuerte = idTipoFuerte;
    }

    public int getIdTipoDebil() {
        return idTipoDebil;
    }

    public void setIdTipoDebil(int idTipoDebil) {
        this.idTipoDebil = idTipoDebil;
    }

    public int getDaño() {
        return daño;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }
}
