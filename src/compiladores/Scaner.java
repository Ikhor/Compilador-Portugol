package compiladores;

import Automatos.AFDs.AFD;
import IO.Reader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Scaner{
    
    private Buffer buffer;
    private ArrayList<ElementTable> tabelaSimbolos;     //adicionar a tabela de simbolos apenas os ID's
    private ArrayList<AFD> afds;                        //automatos reconhecedores
    
    private JTextArea output;                           //objeto de saída de texto
        
    public Scaner(String program, ArrayList<ElementTable> tabela, JTextArea s) throws FileNotFoundException, IOException 
    {        
        buffer = new Buffer(program);
        
        Reader r = new Reader("tokensautomatos2.txt");
        
        afds = new ArrayList();
        tabelaSimbolos = tabela;
        output = s;
        
        String nomeArquivo = r.readLn();
        while(nomeArquivo != null)
        {
            print("Carregando automato de: " + nomeArquivo);
            afds.add(new AFD("tokens2/", nomeArquivo));
            nomeArquivo = r.readLn();
        }
        r.close();
        
        r = new Reader("tokensautomatos1.txt");
        nomeArquivo = r.readLn();
        afds.add(new AFD());
        while(nomeArquivo != null)
        {
            print("Carregando automato de: " + nomeArquivo);
            afds.add(new AFD("tokens/" + nomeArquivo));
            nomeArquivo = r.readLn();
        }
        r.close();
        //continuar aqui.
    }
    
    public TokenComp nextToken()
    {
        TokenComp saida = new TokenComp();
        saida.classe = "ERROR";
        saida.lexema = "Token não reconhecido";
        
        boolean ctrl = true, tokenEncontrado = false;
        for(int u = 0; u < afds.size() && (!tokenEncontrado); u++)
        {
            ctrl = true; tokenEncontrado = false;
            String word = "" + buffer.nextChar();
            int resultado; //0 - não está em estado final; 1 - estado final; 2 - estado de morte
            
            TokenComp tk = new TokenComp();
            while (ctrl) 
            {
                resultado = afds.get(u).ReconhecerToken(word);
                if (resultado == 0) {
                    word += "" + buffer.nextChar();
                } else if (resultado == 1) {
                    //System.out.println("word: " + word + " classe: " + afds.get(u).classe);
                    if(afds.get(u).reservado)
                    {                        
                        char aux = buffer.nextChar();
                        
                        if( ! (aux >= 'a' && aux <= 'z' || aux >= '0' && aux <= '9' || aux >= 'A' && aux <= 'Z' )  )
                        {
                            tk.lexema = word;
                            word += "" + aux;
                            buffer.adiantarLexemBegin();
                            tokenEncontrado = true;
                            ctrl = false;
                        }
                        else
                        {
                            buffer.voltarforward();
                            ctrl = false;
                        }                        
                    }
                    else
                    {
                        tk.lexema = word;
                        word += "" + buffer.nextChar();
                        buffer.adiantarLexemBegin();
                        tokenEncontrado = true;
                    }
                    
                } else if (resultado == 2) {
                    buffer.voltarforward();
                    ctrl = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Erro1", "Erro2",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
            if (tokenEncontrado) {
                tk.classe = afds.get(u).classe;
                if(afds.get(u).classe.equals("ID"))
                {
                    ElementTable element = new ElementTable(tk.lexema);

                    boolean contem = false;
                    for (int e = 0; e < tabelaSimbolos.size(); e++) 
                    {
                        if (element.lexema.equals(tabelaSimbolos.get(e).lexema)) 
                        {
                            contem = true;
                        }
                    }
                    if (!contem) 
                    {
                        System.out.println("Adicionando a tabela o ID: " + tk.lexema);
                        tabelaSimbolos.add(element);
                    }
                }
                
                
                if(tk.classe.equals("BRANCO")){
                    tokenEncontrado = !tokenEncontrado;
                    u = 0;
                }
               
                saida = tk;
            }
        }
        
        return saida;
    }
    
    public boolean hasMoreTokens()
    {
        return buffer.hasMoreChar();        
    }
    
    public void print(String s)
    {
        //System.out.print(s);
        output.append(s + "\n");
    }
}
