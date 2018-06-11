/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Automatos.EstruturaGrafo;

/**
 *
 * @author Felipe
 */
public class GrafoDirecionado {
    
    private Vertice[] Vertices;
    private int numVertice;

    public GrafoDirecionado(int numVertices){
        Vertices = new Vertice[numVertices];
        this.numVertice = numVertices;
        for (int i = 0; i < numVertices; i++) {
            Vertices[i] = new Vertice(i, numVertices);
        }
    }

    public int getNumeroDeVertices() {
        return numVertice;
    }

    public Vertice getVertice(int i) {
        if (i < getNumeroDeVertices())
            return Vertices[i];
        return null;
    }

    public Arco adicionarArco(int origem,char rotulo, int destino) {
        int tamanho = getNumeroDeVertices();
        if ((origem < tamanho) && (destino < tamanho)) {
            Vertice verticeOrigem = getVertice(origem);
            Vertice verticeDestino = getVertice(destino);
            if ((verticeOrigem != null) && (verticeDestino != null)) {
                Arco arco = new Arco(verticeOrigem, verticeDestino);
                verticeOrigem.adicionarArcoDeSaida(arco);
                verticeOrigem.incrementarGrauDeSaida();
                verticeDestino.adicionarArcoDeEntrada(arco);
                verticeDestino.incrementarGrauDeEntrada();
                return arco;
            }
        }
        return null;
    }
    public Vertice[] getVertices(){
        return Vertices;
    }
}
