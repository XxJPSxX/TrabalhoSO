/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;
import java.util.List;
import view.TelaPrincipal;

/**
 *
 * @author André
 */
public class EscalonadorTempoReal implements Runnable{
    private static EscalonadorTempoReal instancia;
    public static List<Processo> filaFCFS = new ArrayList<Processo>();
    
    private EscalonadorTempoReal(){    
        
    }
    
    public static synchronized EscalonadorTempoReal getInstance(){
        if(instancia==null){
            instancia = new EscalonadorTempoReal();
        }
        return instancia;
    }
    
    public void run(){
        if(filaFCFS.size()!=0){
            
            int count = 0;
            
            int i = 0;
            while((i<Maquina.getInstance().listaCPU.length)&&(filaFCFS.size()!=0)){
                CPU atual = Maquina.getInstance().listaCPU[i];
                if(atual.ProcessoExecutando==null){
                    atual.ProcessoExecutando = filaFCFS.get(0);
                    atual.ProcessoExecutando.setTempoInicioExec(TelaPrincipal.momentoAtual);
                    filaFCFS.remove(0);
                }
                else{
                    if(atual.ProcessoExecutando.getPrioridade()!=0){
                        count++;
                    }
                }
                i++;
            }
            //dividido em duas repeticoes para ocupar todas as CPUs ociosas primeiro
            i = 0;
            if((count!=0)&&(filaFCFS.size()!=0)){
                while((i<Maquina.getInstance().listaCPU.length)&&(filaFCFS.size()!=0)){
                    CPU atual = Maquina.getInstance().listaCPU[i];
                    
                    if(atual.ProcessoExecutando.getPrioridade()!=0){
                        int n = atual.ProcessoExecutando.getTempoExec(); //guarda o tempo total que o processo ja foi executado
                        atual.ProcessoExecutando.setTempoJaExecutado(n);
                        
                        atual.ProcessoExecutando = filaFCFS.get(0);
                        atual.ProcessoExecutando.setTempoInicioExec(TelaPrincipal.momentoAtual);
                        filaFCFS.remove(0);
                        //count--;
                    }
                    
                    i++;
                }
            }               
        }    
    }
    
    public static void insereProcesso(Processo processo){
        filaFCFS.add(processo);
    }
}
