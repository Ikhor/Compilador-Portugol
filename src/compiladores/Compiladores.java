package compiladores;

import IO.Reader;
import Teste.Parser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 *
 * @author Ulysses gomes
 */
public class Compiladores implements Runnable
{
    private Scaner scaner;
    private Parser parser;
    private ArrayList<ElementTable> tab;
    private String nomePrograma;
    
    private JTextArea output;
    private JButton bCompilar;
    private JButton bSalvar;
    
    public Compiladores(JTextArea s, JButton c, JButton sa)
    {
        output = s;
        bCompilar = c;
        bSalvar = sa;
        
        nomePrograma = "";
    }
    
    
    public void fazerAnalise() throws FileNotFoundException, IOException
    {
       
        tab = new ArrayList();       
        parser = new Parser(nomePrograma, tab, output);
        
        parser.analisarSintatica();
    }
    
    
    @Override
    public void run() {
        print("Iniciando a Análise Léxica...");
        try {
            fazerAnalise();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Compiladores.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Compiladores.class.getName()).log(Level.SEVERE, null, ex);
        }
        print("Análise Léxica finalizado.");
    }
    
    public void setNomePrograma(String n)
    {
        nomePrograma = n;
    }
    
    public void print(String s)
    {
        output.append(s + "\n");
    }

//    public static void main(String[] args) throws FileNotFoundException, IOException {
//                
//        //String program = "ghjh";
//        //String program = "inteiro e ou entao";
//        //String program = "entao";
//        String program = "algoritmo algs; "
//                + "variaveis "
//                + "inteiro: blabla; "
//                + "inicio "
//                + "blabla = 213123 + 4234.234;"
//                + "fim";
//        
//        ArrayList<ElementTable> tab = new ArrayList();
//        Scaner s = new Scaner(program, tab);
//        
//        int cont = 0;
//        while(s.hasMoreTokens())
//        {
//            TokenComp x = s.nextToken();
//            System.out.println("TOKEN RECONHECIDO NÚMERO " + cont++ + ": " + x.classe + " " + x.lexema);
//        }
//    }

}
