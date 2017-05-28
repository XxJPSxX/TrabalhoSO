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
public class EscalonadorUsuario implements Runnable{
    private static EscalonadorUsuario instancia;
    
    private static List<Processo> filaFeed1 = new ArrayList<Processo>();
    private static List<Processo> filaFeed2 = new ArrayList<Processo>();
    private static List<Processo> filaFeed3 = new ArrayList<Processo>();
    private Processo processoAtual;
    private int quantum = 2;
    
    private EscalonadorUsuario(){
        
    }
    
    public static synchronized EscalonadorUsuario getInstance(){
        if(instancia==null){
            instancia = new EscalonadorUsuario();
        }
        return instancia;
    }
    
    public void run(){
       if(EscalonadorTempoReal.filaFCFS.size()==0){//só executa os processos de FEEDBACK caso nenhum de TEMPO REAL esteja na fila de FCFS
           
           if(filaFeed1.size() != 0){
               CPU cpuAtual = null;
               int NumCpuAtual = proximaCPULivre();
               if(NumCpuAtual == -1){
                  return;//não existem CPU's livres para que o escalonador de feedback possa trabalhar. O escalonador de feedback não irá tirar processos.
               }else{
                   cpuAtual= Maquina.getInstance().listaCPU[NumCpuAtual];
                   int contador = 0;
                   while((cpuAtual != null) && filaFeed1.size() != 0){//se ainda tem CPU e ainda tem processos na fila 1
                        cpuAtual.setProcessoExecutando(filaFeed1.get(contador), NumCpuAtual);
                        cpuAtual.ProcessoExecutando.setTempoInicioExec(TelaPrincipal.momentoAtual);
                        filaFeed1.remove(0);
                        NumCpuAtual = proximaCPULivre();
                        if(NumCpuAtual == -1){
                            break;//não existem CPU's livres para que o escalonador de feedback possa trabalhar. O escalonador de feedback não irá tirar processos.
                        }
                        cpuAtual= Maquina.getInstance().listaCPU[NumCpuAtual];
                   }
                   if(NumCpuAtual == -1){
                       //saiu porque não tinha mais CPU's livres, logo não tem mais nada para o escalonador fazer
                       return;
                   }
               }
           }
           if(filaFeed2.size() != 0){
               CPU cpuAtual = null;
               int NumCpuAtual = proximaCPULivre();
               if(NumCpuAtual == -1){
                  return;//não existem CPU's livres para que o escalonador de feedback possa trabalhar. O escalonador de feedback não irá tirar processos.
               }else{
                   cpuAtual= Maquina.getInstance().listaCPU[NumCpuAtual];
                   int contador = 0;
                   while((cpuAtual != null) && filaFeed2.size() != 0){//se ainda tem CPU e ainda tem processos na fila 1
                        cpuAtual.setProcessoExecutando(filaFeed2.get(contador), NumCpuAtual);
                        cpuAtual.ProcessoExecutando.setTempoInicioExec(TelaPrincipal.momentoAtual);
                        filaFeed2.remove(0);
                        NumCpuAtual = proximaCPULivre();
                        if(NumCpuAtual == -1){
                            break;//não existem CPU's livres para que o escalonador de feedback possa trabalhar. O escalonador de feedback não irá tirar processos.
                        }
                        cpuAtual= Maquina.getInstance().listaCPU[NumCpuAtual];
                   }
                   if(NumCpuAtual == -1){
                       //saiu porque não tinha mais CPU's livres, logo não tem mais nada para o escalonador fazer
                       return;
                   }
               }
           }
           if(filaFeed3.size() != 0){
               CPU cpuAtual = null;
               int NumCpuAtual = proximaCPULivre();
               if(NumCpuAtual == -1){
                  return;//não existem CPU's livres para que o escalonador de feedback possa trabalhar. O escalonador de feedback não irá tirar processos.
               }else{
                   cpuAtual= Maquina.getInstance().listaCPU[NumCpuAtual];
                   int contador = 0;
                   while((cpuAtual != null) && filaFeed3.size() != 0){//se ainda tem CPU e ainda tem processos na fila 1
                        cpuAtual.setProcessoExecutando(filaFeed3.get(contador), NumCpuAtual);
                        cpuAtual.ProcessoExecutando.setTempoInicioExec(TelaPrincipal.momentoAtual);
                        filaFeed3.remove(0);
                        NumCpuAtual = proximaCPULivre();
                        if(NumCpuAtual == -1){
                            break;//não existem CPU's livres para que o escalonador de feedback possa trabalhar. O escalonador de feedback não irá tirar processos.
                        }
                        cpuAtual= Maquina.getInstance().listaCPU[NumCpuAtual];
                   }
                   if(NumCpuAtual == -1){
                       //saiu porque não tinha mais CPU's livres, logo não tem mais nada para o escalonador fazer
                       return;
                   }
               }
           }
           
           
       }
    }
    
    public static void insereProcesso(Processo processo){
        switch(processo.getPrioridadeSimbolica()){
            case 1: filaFeed1.add(processo);
                break;
            case 2: filaFeed2.add(processo);
                break;
            case 3: filaFeed3.add(processo);
                break;
            
        }
    }
    
    //tem que corrigir
    @Deprecated
    public static void abreEspacoMemoria(Processo processo){
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
    private int proximaCPULivre(){//se retorna -1 nenhuma CPU está livre
        int i=0;
        while(i<Maquina.getInstance().listaCPU.length){
            CPU atual = Maquina.getInstance().listaCPU[i];
            if(atual.ProcessoExecutando == null){
                return i;
            }
        }
        return -1;
    }

    
}
