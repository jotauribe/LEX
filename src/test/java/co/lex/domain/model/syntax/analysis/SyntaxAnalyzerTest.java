package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.LexicalAnalyzer;
import co.lex.domain.model.lexicon.analysis.Token;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jotauribe on 13/11/16.
 */

@RunWith(Parameterized.class)
public class SyntaxAnalyzerTest {

    private String suppliedString;

    private int expectedLenght;

    private int lastValidTokenPosition;

    private int rightmostValidTokenPosition;

    public SyntaxAnalyzerTest(String suppliedString, int expectedLenght, int lastValidTokenPosition, int rightmostValidTokenPosition){
        this.expectedLenght = expectedLenght;
        this.suppliedString = suppliedString;
        this.lastValidTokenPosition = lastValidTokenPosition;
        this.rightmostValidTokenPosition = rightmostValidTokenPosition;
    }

    @Test
    public void parse() throws Exception {
        LexicalAnalyzer a = new LexicalAnalyzer();
        SyntaxAnalyzer sa = new SyntaxAnalyzer();
        List<Token> t = a.tokenize(suppliedString);
        AnalysisTree s = sa.parse(t);
        //System.out.print("\nS.SENTENCE = "+s.length()+" ENDTOKEN: "+s.lastValidToken());
        assertEquals( expectedLenght, s.length());

    }

    @Test
    public void lastValidTokenPosition(){
        LexicalAnalyzer a = new LexicalAnalyzer();
        SyntaxAnalyzer sa = new SyntaxAnalyzer();
        List<Token> t = a.tokenize(suppliedString);
        AnalysisTree s = sa.parse(t);
        System.out.print("\nS.SENTENCE = "+s.startToken()+" LVTP POSITION: "+s.lastValidTokenPosition());

        assertEquals(lastValidTokenPosition, s.lastValidTokenPosition());
    }

    @Test
    public void rightmostValidTokenPosition(){
        LexicalAnalyzer a = new LexicalAnalyzer();
        SyntaxAnalyzer sa = new SyntaxAnalyzer();
        List<Token> t = a.tokenize(suppliedString);
        AnalysisTree s = sa.parse(t);
        //System.out.print("\nS.SENTENCE = "+s.startToken()+" RightmostVisited POSITION: "+s.rightmostVisitedTokenPosition());
        //System.out.print("\nS.SENTENCE = "+s.startToken()+" RightmostValid POSITION: "+s.rightmostValidTokenPosition());
        //System.out.print("\nS.SENTENCE = "+s.startToken()+" ENDTOKEN : "+s.endToken());
        //System.out.print("\nS.SENTENCE = "+s.startToken()+" LASTVALIDTOKEN : "+s.lastValidToken());
        //System.out.print("\nS.SENTENCE = "+s.startToken()+" RIGHTMOST : "+s.rightmostVisitedToken());
        assertEquals(rightmostValidTokenPosition, s.rightmostValidTokenPosition());
    }

    @Test
    public void test(){
        SyntaxAnalyzer sa = new SyntaxAnalyzer();
        AnalysisTree t = sa.evaluate(suppliedString).syntaxAnalysisTree();
        System.out.print("ARBOL: "+t.startToken());
        assertEquals(rightmostValidTokenPosition, t.rightmostValidTokenPosition());
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> parameterProvider(){

        List<Object[]> parameters = new ArrayList<>();

        //CODE-STREAM - LENGTH - LAST_VALID_TOKEN - RIGTHMOST_VALID_TOKEN - RIGHTMOST_VISITED_TOKEN
        parameters.add(new Object[]{"identificador[3][6][8]           \n[12] = a15 +    \n bacanoesto17 +", 17, 17, 18}); //T0
        parameters.add(new Object[]{"identificador = a3 +    \n bacanoesto5 + whiletu7 * *", 7, 7, 8}); //T1
        parameters.add(new Object[]{"identificador[3] = a6 +    \n bacanoesto8 mientras av10 > 12 : b14", 8, 8, 14});  //T2
        parameters.add(new Object[]{"identificador[18906] = a +    \n bacanoesto[56][87] + whiletu[7890][80]+67+09+68", 28, 28, 28}); //T3
        parameters.add(new Object[]{"entero i[][][][][][][][][][][][], u, i, ui=89", 34, 34, 34}); //T4
        parameters.add(new Object[]{"ui = 89 mientras a", 3, 3, 5}); //T5
        parameters.add(new Object[]{"ui=var[45][]", 6, 6, 7}); //T6
        parameters.add(new Object[]{"entero ui=var", 4, 4, 4});  //T7
        parameters.add(new Object[]{"entero ui=var, u78 = 45", 8, 8, 8});  //T8
        parameters.add(new Object[]{"ui[67][890]     =        var+var-90]]]]]", 13, 13, 13}); //T9
        parameters.add(new Object[]{"enteroui[67][890]     =        var+var11-90}", 13, 13, 13});  //T10
        parameters.add(new Object[]{"enteroui[67][890]     =        var+var11-90}", 13, 13, 13});  //T11
        parameters.add(new Object[]{"34 < 56", 0, 0, 0}); //T12
        parameters.add(new Object[]{"56 < 67 + TU", 0, 0, 0}); //T13
        parameters.add(new Object[]{"TU < 67 + TU", 0, 1, 1}); //T14
        parameters.add(new Object[]{"TU < TU", 0, 1, 1}); //T15
        parameters.add(new Object[]{"TU+< TU", 0, 1, 1}); //16 // TODO VERIFICAR
        parameters.add(new Object[]{"si (tu < tu): a = var finsi", 11, 11, 11});  //T17
        parameters.add(new Object[]{"si (tu < tu): a = var sino var = 23+78 finsi", 17, 17, 17});  //T18
        parameters.add(new Object[]{"segun a: Caso 4: a = 78 FinSegun", 10, 10, 10}); //T19
        parameters.add(new Object[]{"segun a: Caso 4: a = 78 Caso 6: b = var FinSegun", 16, 16, 16}); //T20
        parameters.add(new Object[]{"mientras a < b : a = 23 segun a: Caso 4: a = 78 Caso 6: b = 56+ 67 FinSegun  FinMientras", 27, 27, 27});  //T21
        parameters.add(new Object[]{"mientras a < b : a = 23 segun a: Caso 4: si(a==56): mientras a< 34: a = 0 FinMientras finsi Caso 6: b = 56+ 67 FinSegun  FinMientras", 41, 41, 41}); //T22
        parameters.add(new Object[]{"cadena a = 34 + 45", 6, 6, 6});  //T23
        parameters.add(new Object[]{"cadena a ", 2, 2, 2});  //T24
        parameters.add(new Object[]{"segun a: Caso 4:entero a  \nHacerSiempre: a = 0 FinSegun", 14, 14, 14});  //T25
        parameters.add(new Object[]{"entero ui=var, un=90))", 8, 8, 8});  //T26
        parameters.add(new Object[]{"segun a: Caso 4:entero a = 0  \nmientras a<b : a = 45 FinMientras FinSegun", 20, 20, 20});  //T27
        parameters.add(new Object[]{"mientras a < b : cadena a b =  + 67 FinMientras", 0, 7, 9});  //T28
        parameters.add(new Object[]{"entero a 56 < 4", 2, 2, 2}); //T29
        parameters.add(new Object[]{"mientras a < 8 :  mientras a < z: a =    FinMientras FinSegun", 0, 12, 12}); //T30
        parameters.add(new Object[]{" si (a == 9):  mientras a < 8 : a =  0  HacerSiempre: a =  FinSegun", 0, 15, 15}); //T31
        parameters.add(new Object[]{"segun a: Caso 4:entero a = 0  \nmientras a<b : a = 45 FinMientras FinSegun", 20, 20, 20});  //T32
        parameters.add(new Object[]{"segun a: Caso 4:entero a  \nHacerSiempre: b =  ", 0, 8, 12});  //T33
        parameters.add(new Object[]{"entero a =0 segun a: Caso 4:entero a   \nmientras a < b : Tu = 0", 4, 4, 20});  //T34
        parameters.add(new Object[]{"entero a =0 segun : Caso 4:entero a   \nmientras a < b : Tu = 0", 4, 4, 5});  //T35
        parameters.add(new Object[]{"entero a =0 segun a: Caso :entero a   \nmientras a < b : Tu = 0", 4, 4, 8});  //T36
        parameters.add(new Object[]{"entero a =0 segun a: Caso 4: 56 a   \nmientras a < b : Tu = 0", 4, 4, 10});  //T37
        parameters.add(new Object[]{"entero a =0 segun a: Caso 4: a  = 0 tu+<45 \nmientras a < b : Tu = 0", 4, 4, 14});  //T38
        parameters.add(new Object[]{"mientras a < b : cadena a = \"cadena\" Subrutina entero misubrutina (cadena a, entero b) a = 0 FinSubrutina FinMientras", 24, 24, 24});  //T39
        parameters.add(new Object[]{"mientras a < b : cadena a = \"cadena\" Para (entero a = 6 a < b a = 7 + b): a = 0 FinPara FinMientras", 30, 30, 30});  //T40
        parameters.add(new Object[]{"Leer a[34] Escribir \"cadena\"", 7, 7, 7});  //T41
        parameters.add(new Object[]{"#?%~^@Leer a[34] Escribir \"cadena\" a = b / c b = a * b[9] / c", 22, 22, 22});  //T42
        parameters.add(new Object[]{"#?%~^@Leer a[34] Escribir \"cadena\" a = b / c b = a * b[9] / c identificador(a, 89)", 28, 28, 28});  //T43

        return parameters;
    }

}


