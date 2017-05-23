
package classes;

public class Maquina {
    public static int memoria = 1024;
    int impressora = 2;
    int scanner = 1;
    int modem = 1;
    int cdDriver = 2;

    public Maquina() {
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
