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
            int count = 0; //conta o numero de CPU's executando processos de usuario
            
            int i = 0;
            while((i<Maquina.getInstance().listaCPU.size())&&(filaFCFS.size()!=0)){
                CPU atual = Maquina.getInstance().listaCPU.get(i);
                if(atual.ProcessoExecutando==null){
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+filaFCFS.get(0).getNumero()+" removido da fila FCFS");
                    atual.setProcessoExecutando(filaFCFS.get(0), i);
                    
                    atual.ProcessoExecutando.setTempoInicioExec(TelaPrincipal.momentoAtual);
                    removeProcessoFCFS(filaFCFS.get(0));
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
                        
                        TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+atual.ProcessoExecutando.getNumero()+" interrompido ");
                        
                        Processo p = atual.ProcessoExecutando;
                        if(p.getPrioridadeSimbolica()<3)
                            p.setPrioridadeSimbolica(p.getPrioridadeSimbolica()+1);
                        else
                            p.setPrioridadeSimbolica(1);
                        EscalonadorUsuario.insereProcesso(p);

                        TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+filaFCFS.get(0).getNumero()+" removido da fila FCFS");
                        atual.setProcessoExecutando(filaFCFS.get(0), i);
                        atual.ProcessoExecutando.setTempoInicioExec(TelaPrincipal.momentoAtual);
                        removeProcessoFCFS(filaFCFS.get(0));
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
        System.out.println("TempoReal0");
        filaFCFS.add(processo);
        TelaPrincipal.setTextoFilaFCFS(TelaPrincipal.listToString(filaFCFS));
        TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+processo.getNumero()+" inserido na fila FCFS");
    }
    
    private static void removeProcessoFCFS(Processo processo){
        filaFCFS.remove(processo);
        TelaPrincipal.setTextoFilaFCFS(TelaPrincipal.listToString(filaFCFS));
        System.out.println(TelaPrincipal.listToString(filaFCFS)+"AAAAA");
    }
}
