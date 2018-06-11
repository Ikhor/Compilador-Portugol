package compiladores;

import Interface.Buffers;

 /**
 * @author Felipe
 */
public class Buffer implements Buffers{
    
    public String programa;
    public int lexemBegin;
    public int forward;
    
    private char[] buffer1;
    private char[] buffer2;
    private short nBuf;
    private final int TAM = 60;             //tamanho de cada buffer
    public int indicePrograma;    
    
    public Buffer(String Programa){
        buffer1 = new char[TAM];
        buffer2 = new char[TAM];
        programa = Programa;
        lexemBegin = forward = indicePrograma = 0;
        nBuf = 0;
        PreencherBuffer1();
        PreencherBuffer2();
    }   
    
    @Override
    public char nextChar() {
                
        if(forward < TAM)
            return buffer1[forward++];
        else if(forward >= TAM && forward < (2*TAM))
        {
            nBuf = 1;
            return buffer2[forward++ % TAM];
        }
        else if(forward >= (2*TAM))
        {
            nBuf = 0;
            forward = 0;
            PreencherBuffer1();
            PreencherBuffer2();            
        }
        
        return buffer1[forward++];
    }
    
    public boolean hasMoreChar()
    {
        if(indicePrograma < programa.length())
            return true;
        else if(nBuf == 0 && forward < TAM && buffer1[forward] != '\0')
            return true;
        else if(nBuf == 0 && forward == TAM && buffer2[forward - TAM] != '\0')
            return true;
        else if(nBuf == 1 && forward < 2*TAM && buffer2[forward - TAM] != '\0')
            return true;
            
        
        return false;
    }

    @Override
    public void voltarforward() {
        forward = lexemBegin;
    }

    @Override
    public void adiantarLexemBegin() {
        lexemBegin = forward - 1;
    }
    
    public void PreencherBuffer1(){
        int pontoInicial = indicePrograma;
        if(indicePrograma < programa.length())
            for(int i = pontoInicial; i < (pontoInicial + TAM); i++)
            {
                if(i < programa.length())
                    buffer1[i - pontoInicial] = programa.charAt(i);
                else
                    buffer1[i - pontoInicial] = '\0'; //procurar depois o caractere EOF para por aki.
                indicePrograma++;
            }
        else
            for(int u = 0; u < TAM; u++)
                buffer1[u] = '\0';
    }
    public void PreencherBuffer2(){
        int pontoInicial = indicePrograma;
        if(indicePrograma < programa.length())
            for(int i = pontoInicial; i < (pontoInicial + TAM); i++)
            {
                if(i < programa.length())
                    buffer2[i - pontoInicial] = programa.charAt(i);
                else
                    buffer2[i - pontoInicial] = '\0'; //procurar depois o caractere EOF para por aki.
                indicePrograma++;
            }
        else
            for(int u = 0; u < TAM; u++)
                buffer2[u] = '\0';
    }
    
    public void MostrarBuffer1(){
        for(int i = 0; i < TAM; i++){
            System.out.print(buffer1[i]);
        }
        System.out.println("");
    }
    
    public void MostrarBuffer2(){
        for(int i = 0; i < TAM; i++){
            System.out.print(buffer2[i]);
        }
        System.out.println("");
    }
}