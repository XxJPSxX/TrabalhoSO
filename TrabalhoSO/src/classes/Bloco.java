
package classes;


public class Bloco {
    private int indice; 
    private Processo processoAssociado;
    private int livre; //0:livre 1:ocupado

    public Bloco(int indice, Processo processoAssociado, int livre) {
        this.indice = indice;
        this.processoAssociado = processoAssociado;
        this.livre = livre;
    }

    public int getIndice() {
        return indice;
    }

    public Processo getProcessoAssociado() {
        return processoAssociado;
    }

    public int getLivre() {
        return livre;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void setProcessoAssociado(Processo processoAssociado) {
        this.processoAssociado = processoAssociado;
    }

    public void setLivre(int livre) {
        this.livre = livre;
    }
    
    
}
