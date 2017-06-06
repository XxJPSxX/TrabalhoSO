/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author André
 */
public class Observador {
    public static void startEscalonadorU(){
        Thread threadEscalonadorU = new Thread(EscalonadorUsuario.getInstance());
        threadEscalonadorU.start();
    }
}
