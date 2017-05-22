
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author André
 */

public class teste {
    
    public static class Processo {
        private int num;
        public Processo(int i) {
            this.num = i;
        }
        public int getNum(){
            return num;
        }
    }
    
    public static void main(String args[]){
        List<Processo> lista = new ArrayList<Processo>();
        
        for(int i=0;i<10;i++){
            lista.add(new Processo(i));
        }
        for(int i=0;i<lista.size();i++){
            System.out.println(" " + lista.get(i).getNum());
        }
        
        for(int i=0; i < lista.size(); i++) {
            Processo p = lista.get(i);
            if((p.getNum() == 3)||(p.getNum() == 8)){
                //filaEntrada.add(p);
                lista.remove(i);
            }
        }
    }

}
