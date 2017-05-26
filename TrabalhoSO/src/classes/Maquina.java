
package classes;

public class Maquina {
    private static Maquina instancia;
    
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
        
    }
    
    public static synchronized Maquina getInstance(){
        if(instancia==null){
            instancia = new Maquina();   
        }
        return instancia;
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
