/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Ulysses gomes
 */
public class Reader 
{
    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader  br;
    
    public Reader(String arq) throws FileNotFoundException
    {
        is = new FileInputStream(arq);
        isr = new InputStreamReader(is);
        br = new BufferedReader(isr);
    }
    
    public String readLn() throws IOException
    {
        //br.
        return br.readLine();
    }
    
    public void close() throws IOException
    { br.close();}
    
}
