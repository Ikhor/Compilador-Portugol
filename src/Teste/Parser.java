/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Teste;

import IO.Reader;
import compiladores.ElementTable;
import compiladores.Scaner;
import compiladores.TokenComp;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author Ikhor
 */
public class Parser {

    private Scaner scaner;
    private JTextArea output;
    private TokenComp x;

    public Parser(String nomePrograma, ArrayList<ElementTable> tab, JTextArea output)
            throws FileNotFoundException, IOException {
        this.output = output;
        Reader r = new Reader(nomePrograma);
        String program = "";
        String entrada = r.readLn();
        while (entrada != null) {
            program += entrada;
            entrada = r.readLn();
            program += "\n";
        }


        tab = new ArrayList();
        scaner = new Scaner(program, tab, output);

    }

    public void analisarSintatica() {


        boolean resposta = false;
        x = scaner.nextToken();
        if (x.lexema.equals("algoritmo")) {
            x = scaner.nextToken();
            if (x.lexema.equals(";")) {
                x = scaner.nextToken();
                if (x.classe.equals("VARIAVEIS")) {
                    x = scaner.nextToken();
                    if (DECLARACAO()) {
                        if (x.classe.equals("FUNCAO_DECLARA")) {
                            x = scaner.nextToken();
                            if (FUNCAO()) {
                                if (MAIN()) {
                                    resposta = true;
                                }
                            }
                        }
                    }
                }
            }
        }

       // System.out.println(x.classe);
      //  System.out.println(x.lexema);

        if (resposta) {
            System.out.print("Sucessu !");
            output.append("Análise sintática finalizada com sucesso.\n");
        } else {
            System.out.print("Faio!");
            output.append("Falha na análise sintática.\n");
        }


    }

    boolean DECLARACAO() {
        if (TIPO()) {

            if (x.classe.equals("DOIS_PONTOS")) {
                x = scaner.nextToken();
                if (x.classe.equals("ID")) {
                    x = scaner.nextToken();
                    if (x.lexema.equals(";")) {
                        x = scaner.nextToken();
                        if (DECLARACAO()) {
                            return true;
                        } else {
                            return true;
                        }
                    }
                }
            }
            return true;
        }
        return true;
    }

    boolean MAIN() {

        if (x.classe.equals("INICIO")) {
            x = scaner.nextToken();
            if (DATA()) {
                if (x.classe.equals("FIM")) {
                    x = scaner.nextToken();
                    while(scaner.hasMoreTokens()&& x.classe.equals("BRANCO")){
                         x = scaner.nextToken();
                    }
                    if(scaner.hasMoreTokens())
                        return false;
                    return true;
                }
            }
        }
        return false;
    }

    boolean TIPO() {

        if (x.classe.equals("INTEIRO") || x.classe.equals("REAL") || x.classe.equals("CHAR")
                || x.classe.equals("BOLEANO")) {
            x = scaner.nextToken();
            return true;
        }
        return false;
    }

    boolean DATA() {
        if (DESVIO()) {
            if (DATA()) {
            } else {
                return true;
            }
        } else if (REPETICAO()) {
            if (DATA()) {
            } else {
                return true;
            }
        } else if (INSTRUCAO()) {
            if (DATA()) {
            } else {
                return true;
            }
        }
        else if(x.classe.equals("FUNC")){
            x = scaner.nextToken();
            if(INVOKA())
                if(DATA())
                    return true;
        }

        return true;

    }

    boolean DESVIO() {

        if (x.classe.equals("SE")) {
            x = scaner.nextToken();
            if (EXPRESSAO()) {
                if (x.classe.equals("ENTAO")) {
                    x = scaner.nextToken();
                    if (DATA()) {
                        if (DESVIO2()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    boolean DESVIO2() {

        if (x.classe.equals("FIM_SE")) {
            x = scaner.nextToken();
            return true;
        } else if (x.classe.equals("SENAO")) {
            x = scaner.nextToken();
            if (DATA()) {

                if (x.classe.equals("FIM_SE")) {
                    x = scaner.nextToken();
                    return true;
                }
            }
        }

        return false;
    }

    boolean EXPRESSAO() {

        if (x.classe.equals("ABRE_PAR")) {
            x = scaner.nextToken();
            if (EXPRESSAO()) {
                if (x.classe.equals("FECHA_PAR")) {
                    x = scaner.nextToken();
                    if (EXPRESSAO2()) {
                        return true;
                    }
                }
            }
        } else if (x.classe.equals("ID")) {
            x = scaner.nextToken();
            if (EXPRESSAO2()) {
                return true;
            }
        } else if (x.classe.equals("VERDADEIRO")) {
            x = scaner.nextToken();
            if (EXPRESSAO2()) {
                return true;
            }
        } else if (x.classe.equals("FALSO")) {
            x = scaner.nextToken();
            if (EXPRESSAO2()) {
                return true;
            }
        } else if (x.classe.equals("NUMEROINTEIRO")) {
            x = scaner.nextToken();
            if (EXPRESSAO2()) {
                return true;
            }
        } else if (x.classe.equals("NUMEROREAL")) {
            x = scaner.nextToken();
            if (EXPRESSAO2()) {
                return true;
            }
        }
        return false;
    }

    boolean EXPRESSAO2() {
        if (OPERADOR_REL()) {
            if (EXPRESSAO()) {
                if (EXPRESSAO2()) {
                    return true;
                }
            }
        }
        return true;

    }

    boolean OPERADOR_REL() {
        if (x.classe.equals("OPERADOR_REL")) {
            x = scaner.nextToken();
            return true;
        }
        return false;
    }

    boolean OPERADOR_ARIT() {

        if (x.classe.equals("OPERADOR_ARIT")) {
            x = scaner.nextToken();
            return true;
        }
        return false;
    }

    boolean REPETICAO() {

        if (x.classe.equals("ENQUANTO")) {
            x = scaner.nextToken();
            if (EXPRESSAO()) {
                if (DATA()) {
                    if (x.classe.equals("FIM_ENQUANTO")) {
                        x = scaner.nextToken();
                        return true;
                    }
                }
            }

        } else if (x.classe.equals("PARA")) {
            x = scaner.nextToken();
            if (x.classe.equals("ID")) {
                x = scaner.nextToken();
                if (x.classe.equals("ATRIBUICAO")) {
                    x = scaner.nextToken();
                    if (REPETICAO2()) {
                        if (DATA()) {
                            if (x.classe.equals("FIM_PARA")) {
                                x = scaner.nextToken();
                                return true;
                            }
                        }
                    }
                }
            }

        } else if (x.classe.equals("REPITA")) {
            x = scaner.nextToken();
            if (DATA()) {
                if (x.classe.equals("ATE")) {
                    x = scaner.nextToken();
                    if (EXPRESSAO()) {
                        if (x.classe.equals("PONTO_VIRGULA")) {
                            x = scaner.nextToken();
                            return true;
                        }
                    }
                }
            }
        }


        return false;
    }

    boolean REPETICAO2() {

        if (x.classe.equals("NUMEROINTEIRO")) {
            x = scaner.nextToken();
            if (x.classe.equals("NUMEROINTEIRO")) {
                x = scaner.nextToken();
                return true;
            }
            if (x.classe.equals("ID")) {
                x = scaner.nextToken();
                return true;
            }
        } else if (x.classe.equals("ID")) {
            x = scaner.nextToken();
            if (x.classe.equals("NUMEROINTEIRO")) {
                x = scaner.nextToken();
                return true;
            }
            if (x.classe.equals("ID")) {
                x = scaner.nextToken();
                return true;
            }
        }
        return false;
    }

    boolean OPERACAO() {

        if (x.classe.equals("ABRE_PAR")) {
            x = scaner.nextToken();
            if (OPERACAO()) {
                if (x.classe.equals("FECHA_PAR")) {
                    x = scaner.nextToken();
                    if (OPERACAO2()) {
                        return true;
                    }
                }
            }
        } else if (x.classe.equals("ID")) {
            x = scaner.nextToken();
            if (OPERACAO2()) {
                return true;
            }
        } else if (x.classe.equals("NUMEROINTEIRO")) {
            x = scaner.nextToken();
            if (OPERACAO2()) {
                return true;
            }
        } else if (x.classe.equals("NUMEROREAL")) {
            x = scaner.nextToken();
            if (OPERACAO2()) {
                return true;
            }
        }
        return false;
    }

    boolean OPERACAO2() {
        if (OPERADOR_ARIT()) {
            if (OPERACAO()) {
                if (OPERACAO2()) {
                    return true;
                }
            }
        }
        return true;
    }

    boolean INSTRUCAO() {

        if (x.classe.equals("LEIA")) {
            x = scaner.nextToken();
            if (x.classe.equals("ABRE_PAR")) {
                x = scaner.nextToken();
                if (x.classe.equals("ID")) {
                    x = scaner.nextToken();
                    if (x.classe.equals("FECHA_PAR")) {
                        x = scaner.nextToken();
                        if (x.classe.equals("PONTO_VIRGULA")) {
                            x = scaner.nextToken();
                            return true;
                        }
                    }
                }
            }
        } else if (x.classe.equals("IMPRIMA")) {
            x = scaner.nextToken();
            if (x.classe.equals("ABRE_PAR")) {
                x = scaner.nextToken();
                if (INSTRUCAO3()) {

                    if (x.classe.equals("FECHA_PAR")) {
                        x = scaner.nextToken();
                        if (x.classe.equals("PONTO_VIRGULA")) {
                            x = scaner.nextToken();
                            return true;
                        }
                    }
                }
            }
        } else if (x.classe.equals("ID")) {
            x = scaner.nextToken();
            if (x.classe.equals("ATRIBUICAO")) {
                x = scaner.nextToken();
                if (INSTRUCAO2()) {
                    return true;
                }
            }
        }

        return false;
    }

    boolean INSTRUCAO2() {

        if (x.classe.equals("LITERAL_SIMPLES")) {
            x = scaner.nextToken();
            if (x.classe.equals("PONTO_VIRGULA")) {
                x = scaner.nextToken();
                return true;
            }

        } else if (OPERACAO()) {

            if (x.classe.equals("PONTO_VIRGULA")) {
                x = scaner.nextToken();
                return true;
            }
        } else if (EXPRESSAO()) {

            if (x.classe.equals("PONTO_VIRGULA")) {
                x = scaner.nextToken();
                return true;
            }
        }
        return false;
    }

    boolean INSTRUCAO3() {

        if (x.classe.equals("ID") || x.classe.equals("LITERAL")) {
            x = scaner.nextToken();
            return true;
        }
        return false;
    }

    //FUNÇÂO INICIO
    boolean FUNCAO() {
        if (x.classe.equals("INICIOFUNCAO")) {
            x = scaner.nextToken();
            if (ASSINATURA()) {
                if (DATA()) {
                    if (x.classe.equals("FIMFUNCAO")) {
                        x = scaner.nextToken();
                        if (FUNCAO()) {
                            return true;
                        }
                    }
                }
            }

            return true;
        }
        return true;
    }

    boolean ASSINATURA() {
        if (TIPO()) {
            if (x.classe.equals("ID")) {
                x = scaner.nextToken();
                if (x.classe.equals("ABRE_PAR")) {
                    x = scaner.nextToken();
                    if (ARGUMENTOSLISTA()) {
                        if (x.classe.equals("FECHA_PAR")) {
                            x = scaner.nextToken();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    boolean ARGUMENTOSLISTA() {
        if(ARGUMENTO())
            if(ARGUMENTOSLISTA2())
                return true;
        return true;
    }
    boolean ARGUMENTOSLISTA2() {
        if (x.classe.equals("VIRGULA")) {
                x = scaner.nextToken();
                if(ARGUMENTO())
                    if(ARGUMENTOSLISTA())
                        return true;
        }
        return true;
    }
    boolean ARGUMENTO() {
        if(TIPO())
            if (x.classe.equals("ID")) {
                x = scaner.nextToken();
                return true;
            }
        return false;
    }
    
    boolean INVOKA(){
        if (x.classe.equals("ID")) {
                x = scaner.nextToken();
                if (x.classe.equals("ABRE_PAR")) {
                    x = scaner.nextToken();
                    if (INVOKA_ARGUMENTOSLISTA()) {
                        if (x.classe.equals("FECHA_PAR")) {
                            x = scaner.nextToken();
                            return true;
                        }
                    }
                }
            }
        return false;
    }
    
    boolean INVOKA_ARGUMENTOSLISTA(){
        if (x.classe.equals("ID")) {
                x = scaner.nextToken();
                if(INVOKA_ARGUMENTOSLISTA2())
                    return true;
        }
        return true;
    }
    
    boolean INVOKA_ARGUMENTOSLISTA2(){
        if (x.classe.equals("VIRGULA")) {
                x = scaner.nextToken();
                 if (x.classe.equals("ID")) {
                     x = scaner.nextToken();
                     if(INVOKA_ARGUMENTOSLISTA())
                         return true;
                 }
        }
        return true;
    }
}
