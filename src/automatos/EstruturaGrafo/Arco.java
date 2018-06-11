/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Automatos.EstruturaGrafo;

public class Arco {
    
    private Vertice U;
    private Vertice V;

    public Arco(Vertice u, Vertice v) {
        U = u;
        V = v;
    }

    public Arco(Arco a) {
        U = a.getInicio();
        V = a.getFinal();
    }

    public Vertice getInicio() {
        return U;
    }

    public Vertice getFinal() {
        return V;
    }
};

