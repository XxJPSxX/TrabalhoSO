package classes;


public class Processo {
    
    //private static int n=0; 
    
    private int numero;
    //private String nome;
    private int momentoChegada;
    private int prioridade; // 0 = tempo real != 0 = usuarios, quanto maior o valor menos prioridade
    private int duracao;
    private int memoria;
    private int impressora;//0 = não usa
    private int scanner;
    private int modem;
    private int cdDriver;
    private int[] indices;

    public Processo(int numero, int momentoChegada, int prioridade, int duracao, int memoria, int impressora, int scanner, int modem, int cdDriver) {
        //n++;
        this.numero = numero;
        this.momentoChegada = momentoChegada;
        this.prioridade = prioridade;
        this.duracao = duracao;
        this.memoria = memoria;
        this.impressora = impressora;
        this.scanner = scanner;
        this.modem = modem;
        this.cdDriver = cdDriver;
        this.indices = null;
    }

    @Override
    public String toString() {
        return "Processo " +numero + "{ momentoChegada=" + momentoChegada + ", prioridade=" + prioridade + ", duracao=" + duracao + ", memoria=" + memoria + ", impressora=" + impressora + ", scanner=" + scanner + ", modem=" + modem + ", cdDriver=" + cdDriver + '}';
    }
    
    /*
    public static void resetCount(){
        n = 0;
    }
    */
    
    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }
    
    public int getNumero() {
        return numero;
    }
    
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getMomentoChegada() {
        return momentoChegada;
    }

    public void setMomentoChegada(int momentoChegada) {
        this.momentoChegada = momentoChegada;
    }
    
    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
    
    public int getMemoria() {
        return memoria;
    }

    public void setMemoria(int memoria) {
        this.memoria = memoria;
    }
    
    public int getImpressora() {
        return impressora;
    }

    public void setImpressora(int impressora) {
        this.impressora = impressora;
    }
    
    public int getScanner() {
        return scanner;
    }
    
    public void setScanner(int scanner) {
        this.scanner = scanner;
    }
    
    public int getModem() {
        return modem;
    }
    
    public void setModem(int modem) {
        this.modem = modem;
    }
    
    public int getCdDriver() {
        return cdDriver;
    }

    public void setCdDriver(int cdDriver) {
        this.cdDriver = cdDriver;
    }
    
}
