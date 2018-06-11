/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Automatos.EstruturaGrafo;
import java.util.ArrayList;

public class Vertice {

    private int Indice;
    private int GrauDeEntrada;
    private int GrauDeSaida;
    private ArrayList<Arco> ListaDeSaida;
    private ArrayList<Arco> ListaDeEntrada;
    private double[] Custo;
    private boolean[] NoDownset;

    public  Vertice(int i, int totalDeNos){
        Indice = i;
        GrauDeEntrada = 0;
        GrauDeSaida = 0;
        ListaDeSaida = new ArrayList<Arco>();
        ListaDeEntrada = new ArrayList<Arco>();
        Custo = new double[totalDeNos];
        NoDownset = new boolean[totalDeNos];
        for (int j = 0; j < totalDeNos; j++) {
            Custo[j] = 99999999;
            NoDownset[j] = false;
        }
    }

    public void adicionarCusto(Vertice vertice, double custo){
        Custo[vertice.getIndice()] = custo;
        NoDownset[vertice.getIndice()] = false;
    }
    
    public void adicionarRelacao(Vertice vertice){
        NoDownset[vertice.getIndice()] = true;
        Custo[vertice.getIndice()] = 0.0;
    }
    
    public void adicionarRelacao(int v){
        NoDownset[v] = true;
        Custo[v] = 0.0;
    }
    
    public double obterCustoParaVertice(int v) {
        return Custo[v];       
    }
    
    public boolean estaNoDownset(int v) {
        return NoDownset[v];
    }

    public void adicionarArcoDeSaida(Arco a) {
        ListaDeSaida.add(a);
        adicionarRelacao(a.getFinal());
    }
    
    public void adicionarArcoDeSaida(Arco a, double custo) {
        adicionarCusto(a.getFinal(),custo);
    }

    public void adicionarArcoDeEntrada(Arco a) {
        ListaDeEntrada.add(a);
    }

    public void removerArcoDeSaida(Arco a) {
        ListaDeSaida.remove(a);
    }
    
    public int  getGrauDeEntrada() {
        return GrauDeEntrada;
    }
    
    public void incrementarGrauDeEntrada() {
        GrauDeEntrada ++;
    }
    
    public void decrementarGrauDeEntrada() {
        GrauDeEntrada --;
    }

    public int  getGrauDeSaida() {
        return GrauDeSaida;
    }
    
    public void incrementarGrauDeSaida() {
        GrauDeSaida ++;
    }
    
    public void decrementarGrauDeSaida() {
        GrauDeSaida --;
    }
    
    public int getIndice() {
        return Indice;
    }
    
    public ArrayList<Arco> getListaDeSaida() {
        return ListaDeSaida;
    }
    
    public ArrayList<Arco> getListaDeEntrada() {
        return ListaDeEntrada;
    }
 
};

