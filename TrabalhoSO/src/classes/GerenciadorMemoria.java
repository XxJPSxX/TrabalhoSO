
package classes;

import java.util.ArrayList;
import java.util.List;
import view.TelaPrincipal;


public class GerenciadorMemoria {
    public static int tamanhoBloco = 32;
    public static int tamanhoMemoria = Maquina.getInstance().getMemoria();
    
    public static List<Bloco> criaListaDeBlocos(){
        //cria uma lista de blocos com o tamanho de bloco e tamanho da memoria fornecidos
        //inicializa os objetos Bloco pertencentes a lista
        int qtdBlocos = tamanhoMemoria/tamanhoBloco;
        List<Bloco> listaBlocos = new ArrayList<Bloco>();
        for(int i=0;i<qtdBlocos;i++){
            
            listaBlocos.add(new Bloco(i, null, 0));
        }
        return listaBlocos;
    }
    public static int insereProcesso(Processo processo, List<Bloco> listaBlocos){
        //0:inserido com sucesso
        //!=0: numero de blocos que faltam
        int tamanhoProcesso = processo.getMemoria();
        int qtdBlocos = tamanhoProcesso/tamanhoBloco; 
        if(tamanhoProcesso%tamanhoBloco != 0){
            qtdBlocos++;//Vai ter um bloco que não vai ficar cheio
        }
        int qtdLivres = qtdLivres(listaBlocos);
        if(qtdBlocos > qtdLivres){
            return qtdBlocos - qtdLivres;//retorna quantos blocos faltam para processo poder ser colocado na memoria
        }
        Bloco blocoAtual = null;
        int[] indices = new int[qtdBlocos];
        int contador = 0;
        for(int i=0;contador<qtdBlocos;i++){
            blocoAtual = listaBlocos.get(i);
            if(blocoAtual.getLivre() == 0){
                blocoAtual.setLivre(1);//nao esta mais livre
                blocoAtual.setProcessoAssociado(processo);
                indices[contador] = i;//seta que o Bloco i pertecence a tal processo
                contador++;
            }
        }
        TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+processo.getNumero()+" Inserido com sucesso"
                + "\nQuantidade de memória disponível: "+ GerenciadorMemoria.memoriaDisponivel(listaBlocos));
        processo.setIndices(indices);
        return 0;
    }
    private static int qtdLivres(List<Bloco> listaBlocos){
        int qtd = 0;
        for(int i=0;i<listaBlocos.size();i++){
            if(listaBlocos.get(i).getLivre() == 0){
                qtd++;//se = 0 significa que o bloco está livre
            }
        }
        return qtd;
    }
    public static void removeProcesso(Processo processo, List<Bloco> listaBlocos){
        if(processo.getIndices() == null){
            System.out.println("Processo não está em memória principal!!!");
            return;
        }
        for(int i = 0;i<processo.getIndices().length;i++){
            //System.out.println(processo.getIndices()[i]);
            //System.out.println(processo.getIndices().length);
            listaBlocos.get(processo.getIndices()[i]).setLivre(0);//seta valor livre para o bloco i
            listaBlocos.get(processo.getIndices()[i]).setProcessoAssociado(null);
        }
        //limpa os indices que estavam em processo
        processo.setIndices(null);
        
        TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\nProcesso "+processo.getNumero()+" Removido da memória com sucesso"
                + "\nQuantidade de memória disponível: "+ GerenciadorMemoria.memoriaDisponivel(listaBlocos));
    }
    public static int memoriaDisponivel(List<Bloco> listaBlocos){
        return qtdLivres(listaBlocos)*tamanhoBloco;
    }
}
