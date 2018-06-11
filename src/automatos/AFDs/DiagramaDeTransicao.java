package Automatos.AFDs;

import Automatos.EstruturaGrafo.Arco;
import Automatos.EstruturaGrafo.Vertice;

public class DiagramaDeTransicao extends Arco {
    
    private char rotulo;

    public DiagramaDeTransicao(Vertice u,char ROTULO, Vertice v) {
        super(u,v);
        rotulo = ROTULO;
    }
    public char getRotulo(){
        return rotulo;
    }
};
