/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author André
 */
public class EscalonadorTempoReal implements Runnable{
    private static List<Processo> filaFCFS = new ArrayList<Processo>();
    
    public EscalonadorTempoReal(){
        
    }
    
    public void run(){
        System.out.println("falta fazer ainda");
    }
    
    public static void insereProcesso(Processo processo){
        filaFCFS.add(processo);
    }
}
