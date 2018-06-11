/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Automatos.AFDs;

import Automatos.EstruturaGrafo.Vertice;

public class Estado extends Vertice{

    private boolean estadoFinal;
   
    public Estado(int i, int totalDeNos){
        super(i,totalDeNos);
    }

   public boolean isEstadoFinal() {
        return estadoFinal;
    }
 
    public void setEstadoFinal(boolean estadoFinal) {
        this.estadoFinal = estadoFinal;
    }

};

