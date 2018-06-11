package Automatos.AFDs;

import Automatos.EstruturaGrafo.Vertice;
import IO.Reader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class AFD{
    
    private Estado[] Vertices;
   
    private int Inicio;
    private int qtdElementosAlfabeto;
    private char[] ElementosAlfabeto;
    private int NumeroEstados;
    public String classe;   //classe do token
    public boolean reservado;
   
    
    public AFD(String arq) throws FileNotFoundException, IOException{ 
       
        //System.out.println("Carregando automato de: " + arq);
        Reader br = new Reader(arq); 

        String linha = br.readLn();
        NumeroEstados = Integer.parseInt(linha);
       
        Vertices = new Estado[NumeroEstados];
        for (int i = 0; i < NumeroEstados; i++) 
            Vertices[i] = new Estado(i, NumeroEstados);
        
        linha = br.readLn();
        Inicio = Integer.parseInt(linha);

        linha = br.readLn();
        qtdElementosAlfabeto = Integer.parseInt(linha);
                
        linha = br.readLn();
        String tokens[] = linha.split(" ");
        
        
        
        ElementosAlfabeto = new char[qtdElementosAlfabeto];
        for (int i = 0; i < qtdElementosAlfabeto; i++){
            char elemento = tokens[i].charAt(0);
            ElementosAlfabeto[i] = elemento;
            
        }

        int qtdFuncTrans;        
        linha = br.readLn();
        qtdFuncTrans = Integer.parseInt(linha);
        
        int origem,destino;
        char rotulo;
        for (int i = 0; i < qtdFuncTrans; i++) {
            linha = br.readLn();
            tokens = linha.split(" ");
            origem = Integer.parseInt(tokens[0]);
            rotulo = tokens[1].charAt(0); 
            destino = Integer.parseInt(tokens[2]);
            adicionarArco(origem,rotulo,destino);
            
        }
        
        int qtdEstadosFinais, EstadoFinal;
        linha = br.readLn();
        qtdEstadosFinais = Integer.parseInt(linha);
        
        linha = br.readLn();
        tokens = linha.split(" ");
        for (int i = 0; i < qtdEstadosFinais; i++) {            
            EstadoFinal = Integer.parseInt(tokens[i]);
            Vertices[EstadoFinal].setEstadoFinal(true);
        }
        
        linha = br.readLn();
        linha.trim();
        classe = linha;
        reservado = false;

        br.close();
    }
    
    public AFD(String caminho, String arq) throws FileNotFoundException, IOException{ 
       
        //System.out.println("Carregando automato de: " + arq);
        Reader br = new Reader(caminho + arq); 

        String linha = br.readLn();
        NumeroEstados = Integer.parseInt(linha);
       
        Vertices = new Estado[NumeroEstados];
        for (int i = 0; i < NumeroEstados; i++) 
            Vertices[i] = new Estado(i, NumeroEstados);
        
        linha = br.readLn();
        Inicio = Integer.parseInt(linha);

        linha = br.readLn();
        qtdElementosAlfabeto = Integer.parseInt(linha);
                
        linha = br.readLn();
        String tokens[] = linha.split(" ");
        
        
        
        ElementosAlfabeto = new char[qtdElementosAlfabeto];
        for (int i = 0; i < qtdElementosAlfabeto; i++){
            char elemento = tokens[i].charAt(0);
            ElementosAlfabeto[i] = elemento;
            
        }

        int qtdFuncTrans;        
        linha = br.readLn();
        qtdFuncTrans = Integer.parseInt(linha);
        
        int origem,destino;
        char rotulo;
        for (int i = 0; i < qtdFuncTrans; i++) {
            linha = br.readLn();
            tokens = linha.split(" ");
            origem = Integer.parseInt(tokens[0]);
            rotulo = tokens[1].charAt(0);
            destino = Integer.parseInt(tokens[2]);
            adicionarArco(origem,rotulo,destino);
            
        }
        
        int qtdEstadosFinais, EstadoFinal;
        linha = br.readLn();
        qtdEstadosFinais = Integer.parseInt(linha);
        
        linha = br.readLn();
        tokens = linha.split(" ");
        for (int i = 0; i < qtdEstadosFinais; i++) {            
            EstadoFinal = Integer.parseInt(tokens[i]);
            Vertices[EstadoFinal].setEstadoFinal(true);
        }
        
        linha = br.readLn();
        linha.trim();
        classe = linha;
        reservado = true;

        br.close();
    }
    
    //Usado para criar o token de WS (WhiteSpace)
    public AFD()
    { 
        NumeroEstados = 2;
       
        Vertices = new Estado[NumeroEstados];
        for (int i = 0; i < NumeroEstados; i++) 
            Vertices[i] = new Estado(i, NumeroEstados);
        
        Inicio = 0;

        qtdElementosAlfabeto = 3;
                
        char tokens[] = {' ', '\n', '\t'};       
        
        ElementosAlfabeto = new char[qtdElementosAlfabeto];
        System.arraycopy(tokens, 0, ElementosAlfabeto, 0, qtdElementosAlfabeto);

        int qtdFuncTrans;        
        qtdFuncTrans = 3;
        
        adicionarArco(0, ' ', 1);
        adicionarArco(0, '\n', 1);
        adicionarArco(0, '\t', 1);
        
        int qtdEstadosFinais, EstadoFinal;
        qtdEstadosFinais = 1;        
        EstadoFinal = 1;
        Vertices[EstadoFinal].setEstadoFinal(true);
        
        classe = "BRANCO";            
    }
    
    public void setInicio(int indiceInicio){
        Inicio =indiceInicio;
    }
    public void setqtdElementosAlfabeto(int qtdElementos){
        qtdElementosAlfabeto = qtdElementos;
        ElementosAlfabeto = new char[qtdElementos];
    }
    public void setAlfabeto(int i,char valor){
        
    }
    public int getInicio(){
        return Inicio;
    }
    public int getQtdElementosAlfabeto(){
        return qtdElementosAlfabeto;
    }
    public char[] getAlfabeto(){
        return ElementosAlfabeto;
    }
    
    public void adicionarArco(int origem,char rotulo, int destino) 
    {
        int tamanho = getNumeroDeVertices();
        if ((origem < tamanho) && (destino < tamanho)) {
            Estado verticeOrigem = getVertice(origem);
            Estado verticeDestino = getVertice(destino);
            if ((verticeOrigem != null) && (verticeDestino != null)) {
                DiagramaDeTransicao arco = new DiagramaDeTransicao(verticeOrigem, rotulo, verticeDestino);
                verticeOrigem.adicionarArcoDeSaida(arco);
                verticeOrigem.incrementarGrauDeSaida();
                verticeDestino.adicionarArcoDeEntrada(arco);
                verticeDestino.incrementarGrauDeEntrada();
            }
        }
    }  
        
    public int getNumeroDeVertices() {
        return NumeroEstados;
    }

    public Estado getVertice(int i) 
    {
        if (i < getNumeroDeVertices())
            return Vertices[i];
        return null;
    }

    public Vertice[] getVertices(){
        return Vertices;
    }
    
    public boolean ChecarAFD()
    {	 
	for(int i = 0; i < getNumeroDeVertices(); i++){
		
            ArrayList<Character> Elementos = new ArrayList<Character>();
            Iterator it = getVertice(i).getListaDeSaida().iterator();
                      
            while( it.hasNext() ){
                DiagramaDeTransicao a = (DiagramaDeTransicao) it.next();
                
                if(!Elementos.contains(a.getRotulo())) {
                    Elementos.add(a.getRotulo());
                }
            }

            if(Elementos.size() != getVertice(i).getListaDeSaida().size())
                return false;
        }
        return true;			
    }
    
    public int ReconhecerToken(String palavra)
    {
        String TokenTemp;
        int UltimoEstado = -1;
        for(int i = 1; i <= palavra.length() ; i++){
            TokenTemp = palavra.substring(0, i);
            //System.out.println(TokenTemp);
            if( ReconhecerAFD(TokenTemp) == 0 ){
                //System.out.println("Não esta em estado final");
                UltimoEstado = 0;
            }
            else if( ReconhecerAFD(TokenTemp) == 1 ){
                //System.out.println("Em estado final");
                UltimoEstado = 1;
            }
            else if( ReconhecerAFD(TokenTemp) == 2 ){
                //System.out.println("Saida Inesperada");
                UltimoEstado = 2;
            }
        }

        return UltimoEstado;
    }
    
    private int ReconhecerAFD(String palavra)
    {        
        int EstadoAtual = getInicio();
        
        for(int i = 0; i < palavra.length();i++){
            char ProximoRotulo = palavra.charAt(i);
            Iterator itEstado = getVertice(EstadoAtual).getListaDeSaida().iterator();
            boolean next = true;
            while(itEstado.hasNext() && next){
                DiagramaDeTransicao ArcoAtualIt = (DiagramaDeTransicao) itEstado.next();
                if(ArcoAtualIt.getRotulo() == ProximoRotulo){
                    EstadoAtual = ArcoAtualIt.getFinal().getIndice();
                    next = false;
                }
            }
            if(next)
                return 2;
        }
        
        if(getVertice(EstadoAtual).isEstadoFinal() == true)
            return 1;
        return 0;
    }
}
