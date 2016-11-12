package co.lex.domain.model.lexicon.analysis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by jotauribe on 10/11/16.
 */

@RunWith(Parameterized.class)
public class AnalyzerTest {

    private String suppliedString;

    private List<Token> expectedTokens;

    public AnalyzerTest(List<Token> expectedTokens, String aSuppliedString){
        this.expectedTokens = expectedTokens;
        this.suppliedString = aSuppliedString;
    }

    @Test
    public void parse_GivenACompletelyValidStringToAnalyze_ReturnATokenArrayWithSizeLongerThanZero() throws Exception {
        Analyzer analyzer = new Analyzer();
        ArrayList<Token> tokenArrayList = (ArrayList<Token>) analyzer.parse(this.suppliedString);

        for (int i = 0; i < expectedTokens.size(); i++) {
            Token t = expectedTokens.get(i);
            assertTrue(t.equals(tokenArrayList.get(i)));
        }


    }

    @Parameters
    public static Iterable<Object[]> tokensProvider(){
        List<Object[]> parameters = new ArrayList<>();

        //1st Set
        List<Token> expected1 = new ArrayList<>();
        expected1.add(new Token(TokenType.RW_DATATYPE_INTEGER, "entero"));
        expected1.add(new Token(TokenType.WHITESPACE, "  "));
        expected1.add(new Token(TokenType.MATH_OPERATOR, "-"));
        expected1.add(new Token(TokenType.VALUE_REAL, "23.78"));
        
        String suppliedString1 = "entero  -23.78";
        
        Object[] objectsSet1 = new Object[]{expected1, suppliedString1};
        parameters.add(objectsSet1);
        
        /////////////////////////////////////////////////////////////////////////

        //2nd Set
        List<Token> expected2 = new ArrayList<>();
        expected2.add(new Token(TokenType.IDENTIFIER, "identificador"));
        expected2.add(new Token(TokenType.WHITESPACE, "  "));
        expected2.add(new Token(TokenType.MATH_OPERATOR, "-"));
        expected2.add(new Token(TokenType.WHITESPACE, "  "));
        expected2.add(new Token(TokenType.VALUE_STRING, "\"cadena cadena\""));

        String suppliedString2 = "identificador  -  \"cadena cadena\"";

        Object[] objectsSet2 = new Object[]{expected2, suppliedString2};
        parameters.add(objectsSet2);

        /////////////////////////////////////////////////////////////////////////

        //2nd Set
        List<Token> expected3 = new ArrayList<>();
        expected3.add(new Token(TokenType.IDENTIFIER, "identificador"));
        expected3.add(new Token(TokenType.GROUPER_BRACKET_LEFT, "["));
        expected3.add(new Token(TokenType.WHITESPACE, " \n "));
        expected3.add(new Token(TokenType.MATH_OPERATOR, "-"));
        expected3.add(new Token(TokenType.WHITESPACE, "  "));
        expected3.add(new Token(TokenType.VALUE_STRING, "\"cadena cadena\""));

        String suppliedString3 = "identificador[ \n -  \"cadena cadena\"";

        Object[] objectsSet3 = new Object[]{expected3, suppliedString3};
        parameters.add(objectsSet3);


        /////////////////////////////////////////////////////////////////////////

        //2nd Set
        List<Token> expected4 = new ArrayList<>();
        expected4.add(new Token(TokenType.IDENTIFIER, "identificador"));
        expected4.add(new Token(TokenType.WHITESPACE, " "));
        expected4.add(new Token(TokenType.RW_CONTROLSTRUCTURE_ITERATIVE_WHILE, "mientras"));
        expected4.add(new Token(TokenType.WHITESPACE, " \n "));
        expected4.add(new Token(TokenType.IDENTIFIER, "identificadormientras"));
        expected4.add(new Token(TokenType.WHITESPACE, " "));
        expected4.add(new Token(TokenType.RW_CONTROLSTRUCTURE_ITERATIVE_FOR, "para"));
        expected4.add(new Token(TokenType.VALUE_STRING, "\"cadena cadena\""));

        String suppliedString4 = "identificador mientras \n identificadormientras para\"cadena cadena\"";

        Object[] objectsSet4 = new Object[]{expected4, suppliedString4};
        parameters.add(objectsSet4);


        return parameters;
    }

}