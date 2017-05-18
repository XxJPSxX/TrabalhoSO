package classes;


public class Processo {
    int numero;
    //String nome;
    int momentoChegada;
    int prioridade; // 0 = tempo real != 0 = usuarios, quanto maior o valor menos prioridade
    int duracao;
    int memoria;
    int impressora;//0 = não usa
    int scanner;
    int modem;
    int cdDriver;

    public Processo(int numero, int momentoChegada, int prioridade, int duracao, int memoria, int impressora, int scanner, int modem, int cdDriver) {
        this.numero = numero;
        this.momentoChegada = momentoChegada;
        this.prioridade = prioridade;
        this.duracao = duracao;
        this.memoria = memoria;
        this.impressora = impressora;
        this.scanner = scanner;
        this.modem = modem;
        this.cdDriver = cdDriver;
    }

    @Override
    public String toString() {
        return "Processo " +numero + "{ momentoChegada=" + momentoChegada + ", prioridade=" + prioridade + ", duracao=" + duracao + ", memoria=" + memoria + ", impressora=" + impressora + ", scanner=" + scanner + ", modem=" + modem + ", cdDriver=" + cdDriver + '}';
    }
    
    public int getNumero() {
        return numero;
    }

    public int getMomentoChegada() {
        return momentoChegada;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public int getDuracao() {
        return duracao;
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

    public int getCdDriver() {
        return cdDriver;
    }

    public void setMomentoChegada(int momentoChegada) {
        this.momentoChegada = momentoChegada;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setMemoria(int memoria) {
        this.memoria = memoria;
    }

    public void setImpressora(int impressora) {
        this.impressora = impressora;
    }

    public void setScanner(int scanner) {
        this.scanner = scanner;
    }

    public void setCdDriver(int cdDriver) {
        this.cdDriver = cdDriver;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}
