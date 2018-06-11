/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author Ulysses gomes
 */
public class Writer 
{
    private OutputStream os;
    private OutputStreamWriter osw;
    private BufferedWriter bw;
    
    public Writer(String arq) throws FileNotFoundException
    {
        os = new FileOutputStream(arq);
        osw = new OutputStreamWriter(os);
        bw = new BufferedWriter(osw);
    }
    
    public void write(String output) throws IOException
    { bw.write(output); }
    
    public void close() throws IOException
    { bw.close(); }
}