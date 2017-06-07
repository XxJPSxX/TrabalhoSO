
package classes;

import java.util.ArrayList;
import java.util.List;
import view.TelaPrincipal;

public class Maquina {
    private static Maquina instancia;
    
    private  int numeroCPU = 4;
    public List<CPU> listaCPU = new ArrayList<>();
    //public CPU listaCPU[] =  new CPU[numeroCPU];    
    private int memoria = 1024;
    private int impressora = 2;
    private int scanner = 1;
    private int modem = 1;
    private int cdDriver = 2;
    
    //public int memoriaDisp = memoria;
    public int impressoraDisp = impressora;
    public int scannerDisp = scanner;
    public int modemDisp = modem;
    public int cdDriverDisp = cdDriver;
    
    
    private Maquina() {
        for(int i=0;i<numeroCPU;i++){
            listaCPU.add(new CPU());
        }
    }
    /* CASO QUEIRAMOS IMPLEMENTAR A FUNÇÃO DE LIMPAR O PROGRAMA PARA INSERIR OUTRO ARQUIVO TEREMOS QUE USAR ALGO DESTE TIPO
    public static void meLimpe(){
        //tive que botar tudo estatico
        numeroCPU = 4;
        memoria = 1024;
        impressora = 2;
        scanner = 1;
        modem = 1;
        cdDriver = 2;
        impressoraDisp = impressora;
        scannerDisp = scanner;
        modemDisp = modem;
        cdDriverDisp = cdDriver;
        listaCPU = new ArrayList<>();
    }
    */
    
    public static synchronized Maquina getInstance(){
        if(instancia==null){
            instancia = new Maquina();   
        }
        return instancia;
    }
    
    public void checaProcessos(){
        List<CPU> listaCPU = Maquina.getInstance().listaCPU;
        for(int i=0;i<listaCPU.size();i++){
            Processo p = listaCPU.get(i).ProcessoExecutando;
            
            if((p!=null)&&(p.getTempoExec()==p.getDuracao())){
                //System.out.println("Processo "+p.getNumero()+"terminado");
                TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+p.getNumero()+" concluído ");
                
                listaCPU.get(i).setProcessoExecutando(null, i);
                Maquina.getInstance().scannerDisp += p.getScanner();
                Maquina.getInstance().impressoraDisp += p.getImpressora();
                Maquina.getInstance().modemDisp += p.getModem();
                Maquina.getInstance().cdDriverDisp += p.getCdDriver();
                GerenciadorMemoria.removeProcesso(p, Despachante.listaBlocos);
                
                return;
            }
            
            if((p!=null)&&(p.getPrioridadeSimbolica()!=0)){
                //checa se o quantum e igual ao tempo que o processo esta executando 
                if(p.getQuantumRestante()==(TelaPrincipal.momentoAtual - p.getTempoInicioExec())){ 
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+p.getNumero()+" fim do quantum ");
                
                    listaCPU.get(i).setProcessoExecutando(null, i);
                    
                    if(p.getPrioridadeSimbolica()<3)
                        p.setPrioridadeSimbolica(p.getPrioridadeSimbolica()+1);
                    else
                        p.setPrioridadeSimbolica(1);
                    
                    EscalonadorUsuario.insereProcesso(p);
                }
            }
        }
    }
    
    public int getMemoria() {
        return memoria;
    }

    public int getImpressora() {
        return impressora;
    }

    public int getScanner() {
        return scanner;
    }

    public int getModem() {
        return modem;
    }

    public int getCdDriver() {
        return cdDriver;
    }
    
}
