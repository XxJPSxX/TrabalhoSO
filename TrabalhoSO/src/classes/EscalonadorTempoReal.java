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
    
    public static boolean terminou = false;
    
    private EscalonadorTempoReal(){    
        
    }
    /* CASO QUEIRAMOS IMPLEMENTAR A FUNÇÃO DE LIMPAR O PROGRAMA PARA INSERIR OUTRO ARQUIVO TEREMOS QUE USAR ALGO DESTE TIPO
    public static void meLimpe(){
        filaFCFS = new ArrayList<Processo>();
        terminou = false;
    }
    */
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
            while((i<Maquina.getInstance().listaCPU.size())&&(filaFCFS.size()!=0)){
                CPU atual = Maquina.getInstance().listaCPU.get(i);
                if(atual.ProcessoExecutando==null){
                    atual.setProcessoExecutando(filaFCFS.get(0), i);
                    
                    atual.ProcessoExecutando.setTempoInicioExec(TelaPrincipal.momentoAtual);
                    filaFCFS.remove(0);
                    System.out.println("REMOVEU");
                    TelaPrincipal.setTextoFilaSuspesos(TelaPrincipal.listToString(filaFCFS));
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
                while((i<Maquina.getInstance().listaCPU.size())&&(filaFCFS.size()!=0)){
                    CPU atual = Maquina.getInstance().listaCPU.get(i);
                    
                    if(atual.ProcessoExecutando.getPrioridade()!=0){
                        int n = atual.ProcessoExecutando.getTempoExec(); //guarda o tempo total que o processo ja foi executado
                        atual.ProcessoExecutando.setTempoJaExecutado(n);
                        
                        atual.setProcessoExecutando(filaFCFS.get(0), i);
                        atual.ProcessoExecutando.setTempoInicioExec(TelaPrincipal.momentoAtual);
                        filaFCFS.remove(0);
                        System.out.println("REMOVEU");
                        TelaPrincipal.setTextoFilaSuspesos(TelaPrincipal.listToString(filaFCFS));
                        //count--;
                    }
                    
                    i++;
                }
            }               
        }
        terminou = true;
        Observador.startEscalonadorU();
    }
    
    public static void insereProcesso(Processo processo){
        filaFCFS.add(processo);
        TelaPrincipal.setTextoFilaFCFS(TelaPrincipal.listToString(filaFCFS));
    }
}
