
package classes;

import java.io.File;
import java.util.Scanner;
import javax.swing.JOptionPane;
import view.TelaPrincipal;
import classes.Processo;
import java.util.ArrayList;
import java.util.List;

public class Despachante implements Runnable{
        public static Maquina maquina = new Maquina();
        //public Processo[] listaProcessos;
        public List<Processo> listaProcessos = new ArrayList<Processo>();
        public List<Processo> filaEntrada = new ArrayList<Processo>();
        
        public void run(){
            
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
}

