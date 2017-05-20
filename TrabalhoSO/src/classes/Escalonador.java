
package classes;

import java.io.File;
import java.util.Scanner;
import javax.swing.JOptionPane;
import view.TelaPrincipal;


public class Escalonador {
        public static Maquina maquina = new Maquina();
        public Processo[] listaProcessos;
        public Processo[] leArquivoProcessos(String nome){
            Scanner bd;
            try{
                bd = new Scanner(new File(nome));
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
                Processo[] processos = new Processo[contador];
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
                    processos[contador-1] = novoProcesso;
                    TelaPrincipal.setTextoLog(TelaPrincipal.getTextoLog()+"\n"+novoProcesso.toString());
                }
                
                return processos;
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Arquivo de entrada não localizado");
                System.out.println(e);
                return null;
                
            }

    }
}

