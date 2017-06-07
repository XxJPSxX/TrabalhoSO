
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
        /* CASO QUEIRAMOS IMPLEMENTAR A FUNÇÃO DE LIMPAR O PROGRAMA PARA INSERIR OUTRO ARQUIVO TEREMOS QUE USAR ALGO DESTE TIPO
        public static void meLimpe(){
            listaProcessos = new ArrayList<Processo>();
            filaEntrada = new ArrayList<Processo>();
            listaBlocos = GerenciadorMemoria.criaListaDeBlocos();
            listaSuspensos = new ArrayList<Processo>();
        }
        */
        public static synchronized Despachante getInstance(){
            if(instancia==null){
                instancia = new Despachante();
            }
            return instancia;
        }
        
        private static void checaFilaRotina(List<Processo> lista){
            int tam = lista.size();
            List<Processo> aux = new ArrayList<Processo>();
            
            
            for(int j=0;j<lista.size();j++){
                Processo p = lista.get(j);
                //if(p.getPrioridade()!=0){  //condicao desnecessaria pois assume-se que processos de TR nunca ficam suspensos
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
                            //todo processo que nao esta em memoria
                            //e colocado em memoria baseado em sua prioridade original   
                            
                            int n = EscalonadorUsuario.abreEspacoMemoria(p);
                            
                            if(n==0){
                                int i = GerenciadorMemoria.insereProcesso(p, listaBlocos);
                                //if(resultado==0){ condicao desnecessaria pois o espaço ja foi liberado
                                    EscalonadorUsuario.getInstance().insereProcesso(p);
                                    aux.add(p);
                                //}
                        
                            }
                            else{ //entao nao conseguiu alocar memoria
                                if(lista==filaEntrada){
                                    listaSuspensos.add(p);
                                    aux.add(p);
                                    TelaPrincipal.setTextoFilaSuspesos(TelaPrincipal.listToString(listaSuspensos));
                                }
                            }
                        }
                    }
                    else{
                        if(lista==filaEntrada){
                            listaSuspensos.add(p);
                            aux.add(p);
                            TelaPrincipal.setTextoFilaSuspesos(TelaPrincipal.listToString(listaSuspensos));
                        }
                    }
                //}
            }    
            //tira da fila os processos que foram colocados em memoria (ou em outra fila)
            for(int k=0;k<aux.size();k++){
                lista.remove(aux.get(k));
            }
        }
        
        //foi assumido que processos de TR nao podem ficar na fila de suspenso
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
            
            if(filaEntrada.size()!=0){
                List<Processo> auxFE = new ArrayList<Processo>();
                List<Processo> auxSusp = new ArrayList<Processo>(); 
                
                int tam = filaEntrada.size();
                for(int i=0;i<tam;i++){
                    Processo p = filaEntrada.get(i);
                    if(p.getPrioridade()==0){
                        int resultado = GerenciadorMemoria.insereProcesso(p, listaBlocos);
                        if(resultado==0){
                            EscalonadorTempoReal.getInstance().insereProcesso(p);
                            auxFE.add(p);
                        }
                        else{
                            //e possivel que a memoria esteja cheia
                            //apenas com processos de TR?
                            
                            EscalonadorUsuario.getInstance().abreEspacoMemoria(p);
                            
                            //insere processo
                            GerenciadorMemoria.insereProcesso(p, listaBlocos);
                            EscalonadorTempoReal.getInstance().insereProcesso(p);
                            auxFE.add(p);
                        }
                    }
                }
                for(int k=0;k<auxFE.size();k++){
                    filaEntrada.remove(auxFE.get(k));
                }
            }
            //divisao em mais de uma repeticao 
            //para colocar na memoria primeiro os processos de TR
            //diminuindo assim o numero de swap-outs
                
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
        public void processa(){ //NÃO TESTADA!!!!!!!!!!!!!!!
            //incrementa tempo já processado dos processos que tem CPU. 
            //remove processos que já terminaram de ser executados tempoJaExecutado == duracao
            //re insere em alguma das filas de feedback os processos que acabaram com o quantumRestante
            int i=0;
            int tempoJaExecutadoAtual = 0;
            Processo processoAtual = null;
            while(i<Maquina.getInstance().listaCPU.size()){
                processoAtual = Maquina.getInstance().listaCPU.get(i).ProcessoExecutando;
                if(processoAtual != null){//se a CPU está executando algum processo
                    tempoJaExecutadoAtual = processoAtual.getTempoJaExecutado();
                    processoAtual.setTempoJaExecutado(tempoJaExecutadoAtual++);//incrementa tempo já executado
                    if(processoAtual.getTempoJaExecutado() == processoAtual.getDuracao()){
                        //processo já acabou, tem que retirar ele. ANDRE VER SE NÃO TA CONFLITANDO COM O QUE VOCÊ FEZ!!!!
                        //remove texto da Tela que indica que a CPU i está executando algum processo
                        switch(i){
                            case 0:
                                TelaPrincipal.setTextoProcessadorA("");
                                TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"CPU "+i+" livre");
                                break;
                            case 1:
                                TelaPrincipal.setTextoProcessadorA("");
                                TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"CPU "+i+" livre");

                                break;
                            case 2:
                                TelaPrincipal.setTextoProcessadorA("");
                                TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"CPU "+i+" livre");
                                break;
                            case 3:
                                TelaPrincipal.setTextoProcessadorA("");
                                TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"CPU "+i+" livre");
                                break;
                        }
                        Maquina.getInstance().scannerDisp += processoAtual.getScanner();
                        Maquina.getInstance().impressoraDisp += processoAtual.getImpressora();
                        Maquina.getInstance().modemDisp += processoAtual.getModem();
                        Maquina.getInstance().cdDriverDisp += processoAtual.getCdDriver();
                        GerenciadorMemoria.removeProcesso(processoAtual, Despachante.listaBlocos);
                        Maquina.getInstance().listaCPU.get(i).setProcessoExecutando(null, i);//seta CPU i como livre
                        
                        //remove texto da Tela que indica que a CPU i está executando algum processo

                    }
                    if((processoAtual.getPrioridade() != 0) && Maquina.getInstance().listaCPU.get(i).ProcessoExecutando != null){
                        //se não for processo de tempo real ele é de FEEDBACK, então tem que manipular o quantum
                        int quantumAtual = Maquina.getInstance().listaCPU.get(i).ProcessoExecutando.getQuantumRestante();
                        Maquina.getInstance().listaCPU.get(i).ProcessoExecutando.setQuantumRestante(quantumAtual--);
                        if(Maquina.getInstance().listaCPU.get(i).ProcessoExecutando.getQuantumRestante() == 0){
                            //se quantum restante é zero ele tem que voltar pra fila de feedback, como está depois do if que verifica se já acabou ele tem que realmente voltar pra fila
                            //Prioridade simbolica é utilizada para controlar em que fila o processo vai entrar
                            int prioridadeSimbolica = Maquina.getInstance().listaCPU.get(i).ProcessoExecutando.getPrioridadeSimbolica();
                            prioridadeSimbolica++;
                            if(prioridadeSimbolica == 4){
                                prioridadeSimbolica = 1;//se atingir 4 tem que voltar pra fila 1
                            }
                            Maquina.getInstance().listaCPU.get(i).ProcessoExecutando.setPrioridadeSimbolica(prioridadeSimbolica);
                            //processo já está em memória então não precisa ser inserido novamente, pode-se inserir na fila de feedback direto
                            EscalonadorUsuario.getInstance().insereProcesso(Maquina.getInstance().listaCPU.get(i).ProcessoExecutando);
                        }
                    }
                }
            }
        }
        /*
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
        */
}

