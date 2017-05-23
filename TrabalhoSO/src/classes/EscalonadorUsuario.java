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
public class EscalonadorUsuario implements Runnable{
    private static List<Processo> filaFeed1 = new ArrayList<Processo>();
    private static List<Processo> filaFeed2 = new ArrayList<Processo>();
    private static List<Processo> filaFeed3 = new ArrayList<Processo>();
    private int quantum = 2;
    
    public EscalonadorUsuario(){
        
    }
    
    public void run(){
       System.out.println("falta fazer ainda");
    }
    
    public static void insereProcesso(Processo processo){
        switch(processo.getPrioridade()){
            case 1: filaFeed1.add(processo);
                break;
            case 2: filaFeed2.add(processo);
                break;
            case 3: filaFeed3.add(processo);
                break;
            
        }
    }
    
    public static void suspendeProcesso(Processo processo){
        int tamanhoProcesso = processo.getMemoria();
        int qtdBlocos = tamanhoProcesso/GerenciadorMemoria.tamanhoBloco; 
        if(tamanhoProcesso%GerenciadorMemoria.tamanhoBloco != 0){
            qtdBlocos++;//Vai ter um bloco que não vai ficar cheio
        }
        
        int t = qtdBlocos;
        while(t>0){
            Processo p = filaFeed3.get((filaFeed3.size()-1)); //pega o ultimo processo a entrar na fila 3
            t = t - p.getIndices().length;
            GerenciadorMemoria.removeProcesso(p, Despachante.listaBlocos);
        }
    }
    
}
