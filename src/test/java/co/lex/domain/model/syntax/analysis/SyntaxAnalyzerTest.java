package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.Analyzer;
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

    public SyntaxAnalyzerTest(String suppliedString, int expectedLenght){
        this.expectedLenght = expectedLenght;
        this.suppliedString = suppliedString;
    }


    @Test
    public void parse() throws Exception {
        Analyzer a = new Analyzer();
        SyntaxAnalyzer sa = new SyntaxAnalyzer();
        List<Token> t = a.tokenize(suppliedString);
        Sentence s = sa.parse(t);
        //System.out.print("\nS.SENTENCE = "+s.length()+" ENDTOKEN: "+s.lastValidToken());
        assertEquals( expectedLenght, s.length());

    }

    @Test
    public void lastValidTokenPosition(){
        Analyzer a = new Analyzer();
        SyntaxAnalyzer sa = new SyntaxAnalyzer();
        List<Token> t = a.tokenize(suppliedString);
        Sentence s = sa.parse(t);
        System.out.print("\nS.SENTENCE = "+s.length()+" ENDTOKEN: "+s.lastValidToken());
        assertEquals(expectedLenght, s.lastValidToken().position());
    }

    @Parameterized.Parameters
    public static Iterable<Object[]> parameterProvider(){

        List<Object[]> parameters = new ArrayList<>();
        /**
        parameters.add(new Object[]{"identificador[18906][506][89]           \n[34] = a +    \n bacanoesto + whiletu", 19});
        parameters.add(new Object[]{"identificador = a +    \n bacanoesto + whiletu", 7});
        parameters.add(new Object[]{"identificador[18906] = a +    \n bacanoesto + whiletu+67+09+68", 16});
        parameters.add(new Object[]{"identificador[18906] = a +    \n bacanoesto[56][87] + whiletu[7890][80]+67+09+68", 28});
        parameters.add(new Object[]{"entero i[][][][][][][][][][][][], u, i, ui=89", 34});
        parameters.add(new Object[]{"ui=89",3});
        parameters.add(new Object[]{"ui=var[45][67]", 9});
        parameters.add(new Object[]{"entero ui=var", 4});
        parameters.add(new Object[]{"entero ui=var, u78 = 45", 8});
        parameters.add(new Object[]{"ui[67][890]     =        var+var-90]]]]]", 13});
        parameters.add(new Object[]{"enteroui[67][890]     =        var+var-90}", 13});
        parameters.add(new Object[]{"34 enteroui[67][890]     =        var+var-90}", 1});
        parameters.add(new Object[]{"34 < 56", 3});
        parameters.add(new Object[]{"56 < 67 + TU", 5});
        parameters.add(new Object[]{"TU < 67 + TU", 5});
        parameters.add(new Object[]{"TU < TU", 3});
        parameters.add(new Object[]{"TU+< TU", 1});
        parameters.add(new Object[]{"si (tu < tu): a = var finsi", 11});
        parameters.add(new Object[]{"si (tu < tu): a = var sino var = 23+78 finsi", 17});
        parameters.add(new Object[]{"segun a: Caso 4: a = 78 FinSegun", 10});
        parameters.add(new Object[]{"segun a: Caso 4: a = 78 Caso 6: b = var FinSegun", 16});
        parameters.add(new Object[]{"mientras a < b : a = 23 segun a: Caso 4: a = 78 Caso 6: b = 56+ 67 FinSegun  FinMientras", 27});
        parameters.add(new Object[]{"mientras a < b : a = 23 segun a: Caso 4: si(a==56): mientras a< 34: a = 0 FinMientras finsi Caso 6: b = 56+ 67 FinSegun  FinMientras", 41});
        parameters.add(new Object[]{"cadena a = 34 + 45", 6});
        parameters.add(new Object[]{"cadena a ", 2});
        parameters.add(new Object[]{"segun a: Caso 4:entero a  \nHacerSiempre: a = 0 FinSegun", 14});
        parameters.add(new Object[]{"entero ui=var, un=90))", 8});
        parameters.add(new Object[]{"segun a: Caso 4:entero a  \nHacerSiempre: a = 0 FinSegun", 14});
         */
        //parameters.add(new Object[]{"segun a: Caso 4:entero a  \nHacerSiempre: = 45  0 FinSegun", 10});

        parameters.add(new Object[]{"mientras a < b : cadena a b =  + 67 FinMientras", 7});

        return parameters;
    }


}