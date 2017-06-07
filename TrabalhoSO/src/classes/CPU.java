/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import view.TelaPrincipal;

/**
 *
 * @author André
 */

public class CPU {
    
    public Processo ProcessoExecutando;
    
    public CPU(){
        
    }

    public void setProcessoExecutando(Processo ProcessoExecutando, int numeroCPU) {
        this.ProcessoExecutando = ProcessoExecutando;
        //coloca o texto refente a CPU que está sendo utilizada
        if(ProcessoExecutando == null){
            ProcessoExecutando = null;
            switch(numeroCPU){
            case 0: TelaPrincipal.setTextoProcessadorA("");
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\n CPU "+numeroCPU+" livre");
                    break;
            case 1: TelaPrincipal.setTextoProcessadorB("");
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\n CPU "+numeroCPU+" livre");
                    break;
            case 2: TelaPrincipal.setTextoProcessadorC("");
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\n CPU "+numeroCPU+" livre");
                    break;
            case 3: TelaPrincipal.setTextoProcessadorD("");
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\n CPU "+numeroCPU+" livre");
                    break;
            
        }
            return;//se o processo recebido como parâmetro for nulo significa que estou apenas setando a CPU como livre
        }
        switch(numeroCPU){
            case 0: TelaPrincipal.setTextoProcessadorA(""+ProcessoExecutando.getNumero());
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+ProcessoExecutando.getNumero()+" executando na CPU "+numeroCPU);
                    ProcessoExecutando.setCpuAlocada(numeroCPU);
                    break;
            case 1: TelaPrincipal.setTextoProcessadorB(""+ProcessoExecutando.getNumero());
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+ProcessoExecutando.getNumero()+" executando na CPU "+numeroCPU);
                    ProcessoExecutando.setCpuAlocada(numeroCPU);
                    break;
            case 2: TelaPrincipal.setTextoProcessadorC(""+ProcessoExecutando.getNumero());
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+ProcessoExecutando.getNumero()+" executando na CPU "+numeroCPU);
                    ProcessoExecutando.setCpuAlocada(numeroCPU);
                    break;
            case 3: TelaPrincipal.setTextoProcessadorD(""+ProcessoExecutando.getNumero());
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+ProcessoExecutando.getNumero()+" executando na CPU "+numeroCPU);
                    ProcessoExecutando.setCpuAlocada(numeroCPU);
                    break;
            
        }
        
    }
    
    
}
