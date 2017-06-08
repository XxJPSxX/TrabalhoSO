package classes;

import view.TelaPrincipal;


public class Processo {
    
    //private static int n=0; 
    
    private  int numero;
    //private String nome;
    private int momentoChegada;
    private int prioridade; // 0 = tempo real != 0 = usuarios, quanto maior o valor menos prioridade
    private int prioridadeSimbolica; //Prioridade simbolica é utilizada para controlar em que fila o processo vai entrar, ela será alterada durante a execucao
    private int duracao;
    private int memoria;
    private int impressora;//0 = não usa
    private int scanner;
    private int modem;
    private int cdDriver;
    private int cpuAlocada = -1;
    
    private int[] indices; //indices dos blocos de memoria em que o processo esta alocado
    //private int tempoRestante;
    
    private int tempoInicioExec = -1; //-1 significa que o processo ainda n foi executado nenhuma vez
    private int tempoPrimeiraExec = -1;
    private int tempoJaExecutado = 0;
    private int quantumRestante = 0;
    
    public Processo(int numero, int momentoChegada, int prioridade, int duracao, int memoria, int impressora, int scanner, int modem, int cdDriver) {
        //n++;
        this.numero = numero;
        this.momentoChegada = momentoChegada;
        this.prioridade = prioridade;
        this.prioridadeSimbolica = prioridade;
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
    
    public void setCpuAlocada(int cpuAlocada) {
        this.cpuAlocada = cpuAlocada;
    }

    public int getCpuAlocada() {// se = -1 não tem cpu alocada
        return cpuAlocada;
    }

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
    
    
    
    public int getTempoExec(){
        return tempoJaExecutado + (TelaPrincipal.momentoAtual - tempoInicioExec);
    }
    
    public int getTempoInicioExec(){
        return tempoInicioExec;
    }
    
    public void setTempoInicioExec(int n){
        if(tempoPrimeiraExec==-1){
            tempoPrimeiraExec = n;
        }
        tempoInicioExec = n;
    }
    
    public int getTempoPrimeiraExec(){
        return tempoPrimeiraExec;
    }
    
    //o setTempoPrimeiraExec esta implicito dentro do setTempoInicio
    
    public int getTempoJaExecutado(){
        return tempoJaExecutado;
    }
    
    public void setTempoJaExecutado(int n){
        tempoJaExecutado = n;
    }

    public int getQuantumRestante() {
        return quantumRestante;
    }

    public void setQuantumRestante(int quantumRestante) {
        this.quantumRestante = quantumRestante;
    }

    public int getPrioridadeSimbolica() {
        return prioridadeSimbolica;
    }

    public void setPrioridadeSimbolica(int prioridadeSimbolica) {
        this.prioridadeSimbolica = prioridadeSimbolica;
    }
    
    
}
