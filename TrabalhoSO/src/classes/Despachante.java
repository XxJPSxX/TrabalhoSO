
package classes;

import java.io.File;
import java.util.Scanner;
import javax.swing.JOptionPane;
import view.TelaPrincipal;
import classes.Processo;
import java.util.ArrayList;
import java.util.List;

public class Despachante{
        private static Despachante instancia;
        //public static Maquina maquina = new Maquina();
        
        //public Processo[] listaProcessos;
        public static List<Processo> listaProcessos = new ArrayList<Processo>();
        public static List<Processo> filaEntrada = new ArrayList<Processo>();
        public static List<Bloco> listaBlocos = GerenciadorMemoria.criaListaDeBlocos();
        public static List<Processo> listaSuspensos = new ArrayList<Processo>();
        
        private Despachante(){
        }
        
        public static synchronized Despachante getInstance(){
            if(instancia==null){
                instancia = new Despachante();
            }
            return instancia;
        }
        
        private static void checaFilaRotina(List<Processo> lista){
                List<Processo> aux = new ArrayList<Processo>();
                
                //o tamanho da lista pode mudar durante o for
                //caso haja algum processo que sofra swap-out e entre na fila de suspensos
                int tam = lista.size();
                
                for(int i=0;i<tam;i++){
                    Processo p = lista.get(i);
                    if(p.getPrioridade()==0){
                        int resultado = GerenciadorMemoria.insereProcesso(p, listaBlocos);
                        if(resultado==0){
                            EscalonadorTempoReal.getInstance().insereProcesso(p);
                            aux.add(p);
                        }
                        else{
                            //e possivel que a memoria esteja cheia
                            //apenas com processos de TR?
                            
                            EscalonadorUsuario.getInstance().suspendeProcesso(p);
                            
                            //insere processo
                            GerenciadorMemoria.insereProcesso(p, listaBlocos);
                            EscalonadorTempoReal.getInstance().insereProcesso(p);
                            aux.add(p);
                        }
                    }
                }
                //divisao em duas repeticoes 
                //para colocar na memoria primeiro os processos de TR
                //diminuindo assim o numero de swap-outs
                
                //o tamanho da lista pode mudar durante o for
                //caso haja algum processo que sofra swap-out e entre na fila de suspensos
                tam = lista.size();
                
                for(int j=0;j<lista.size();j++){
                    Processo p = lista.get(j);
                    if(p.getPrioridade()!=0){ 
                        if((p.getScanner()<=Maquina.getInstance().scannerDisp)&&(p.getImpressora()<=Maquina.getInstance().impressoraDisp)&&(p.getModem()<=Maquina.getInstance().modemDisp)&&(p.getCdDriver()<=Maquina.getInstance().cdDriverDisp)){
                            int resultado = GerenciadorMemoria.insereProcesso(p, listaBlocos);
                            if(resultado==0){
                                EscalonadorUsuario.getInstance().insereProcesso(p);
                                
                                Maquina.getInstance().scannerDisp -= p.getScanner();
                                Maquina.getInstance().impressoraDisp -= p.getImpressora();
                                Maquina.getInstance().modemDisp -= p.getModem();
                                Maquina.getInstance().cdDriverDisp -= p.getCdDriver();
                                
                                aux.add(p);
                            }
                            else{
                                //Se entra um de prioridade 1 e acaba a memoria
                                //devo tirar um processo de prioridade 3??
                                
                                listaSuspensos.add(p);
                            }
                        }
                        else{
                            listaSuspensos.add(p);
                        }
                    }
                }
                //tira da fila os processos que foram colocados em memoria
                for(int k=0;k<aux.size();k++){
                    lista.remove(aux.get(k));
                }
        }
        
        public void checaFilaEntrada(){
            
            int atual = TelaPrincipal.getMomentoAtual();
            
            if(listaProcessos.size()!=0){
                List<Processo> l = new ArrayList<Processo>();
                for(int i=0; i < listaProcessos.size(); i++) {
                    Processo p = listaProcessos.get(i);
                    if(p.getMomentoChegada() == atual){
                        filaEntrada.add(p);
                        l.add(p);
                    }
                }
                for(int i=0;i < l.size(); i++){
                    listaProcessos.remove(l.get(i));
                }
            }
            
            if(listaSuspensos.size()!=0){
                Despachante.checaFilaRotina(listaSuspensos);
            }
            
            if(filaEntrada.size()!=0){
                Despachante.checaFilaRotina(filaEntrada);
            }
        }
        
        public List<Processo> leArquivoProcessos(String nome){
            Scanner bd;
            List<Processo> lista = new ArrayList<Processo>();
            try{
                bd = new Scanner(new File(nome));
                //ainda preciso fazer isso abaixo?
                int contador = 0;
                while(bd.hasNextLine()){
                    bd.nextLine();
                    contador++;
                }
                
                if(contador == 0){
                    JOptionPane.showMessageDialog(null, "Arquivo inválido");
                    TelaPrincipal.setTextoLog("Arquivo inválido");
                    return null;
                }
                //Processo[] processos = new Processo[contador];
                bd = new Scanner(new File(nome));
                contador = 0;
                TelaPrincipal.setTextoLog("Processos:");
                while(bd.hasNextLine()){
                    contador++;
                    String linha = bd.nextLine();
                    linha = linha.replaceAll("\\s+","");
                    String[] partes = linha.split(",");
                    if(partes.length != 8){
                        JOptionPane.showMessageDialog(null, "Arquivo inválido");
                        TelaPrincipal.setTextoLog("Arquivo inválido");
                        return null;
                    }
                    Processo novoProcesso = new Processo(contador, Integer.parseInt(partes[0]), Integer.parseInt(partes[1]), Integer.parseInt(partes[2]), Integer.parseInt(partes[3]), Integer.parseInt(partes[4]), Integer.parseInt(partes[5]), Integer.parseInt(partes[6]), Integer.parseInt(partes[7]));
                    //processos[contador-1] = novoProcesso;
                    lista.add(novoProcesso);
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\n"+novoProcesso.toString());
                }
                //return processos;
                return lista;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Arquivo de entrada não localizado");
                System.out.println(e);
                return null;
                
            }
            
        }
        public void testeGerenciadorMemoria(){
            System.out.println("Tamanho do array de blocos: "+listaBlocos.size());
            System.out.println("Quantidade de blocos: "+GerenciadorMemoria.memoriaDisponivel(listaBlocos)/32);
            System.out.println("Quantidade de memória disponível: "+ GerenciadorMemoria.memoriaDisponivel(listaBlocos));
            int resultado;
            for(int i=0;i<listaProcessos.size();i++){
                resultado = GerenciadorMemoria.insereProcesso(listaProcessos.get(i), listaBlocos);

                if(resultado == 0){
                    System.out.println("Processo "+ listaProcessos.get(i).getNumero() +" Inserido com sucesso");
                }else{
                    System.out.println("Faltaram "+resultado+" blocos para inserir o processo "+listaProcessos.get(i).getNumero());
                }
                System.out.println("Quantidade de blocos: "+GerenciadorMemoria.memoriaDisponivel(listaBlocos)/32);
                System.out.println("Quantidade de memória disponível: "+ GerenciadorMemoria.memoriaDisponivel(listaBlocos));
            }
            System.out.println("Quantidade de blocos: "+GerenciadorMemoria.memoriaDisponivel(listaBlocos)/32);
            System.out.println("Quantidade de memória disponível: "+ GerenciadorMemoria.memoriaDisponivel(listaBlocos));
            GerenciadorMemoria.removeProcesso(listaProcessos.get(2), listaBlocos);
            
            System.out.println("Quantidade de blocos: "+GerenciadorMemoria.memoriaDisponivel(listaBlocos)/32);
            System.out.println("Quantidade de memória disponível: "+ GerenciadorMemoria.memoriaDisponivel(listaBlocos));
        }
}

