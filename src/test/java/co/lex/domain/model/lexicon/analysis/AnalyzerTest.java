package co.lex.domain.model.lexicon.analysis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
        ArrayList<Token> tokenArrayList = (ArrayList<Token>) analyzer.tokenize(this.suppliedString);

        for (int i = 0; i < expectedTokens.size(); i++) {
            Token t = expectedTokens.get(i);
            assertTrue(t.equals(tokenArrayList.get(i)));
        }


    }

    @Test
    public void linkTokens_GivenATokenListGeneratedFromAnalyzer_AProperlyLinkedTokenList(){
        Analyzer analyzer = new Analyzer();
        ArrayList<Token> tokenArrayList = (ArrayList<Token>) analyzer.tokenize(this.suppliedString);

        for(int i = 0; i < tokenArrayList.size()-1; i++){
            assertEquals(tokenArrayList.get(i+1), tokenArrayList.get(i).nextToken());
            if(tokenArrayList.get(i).nextToken() == null){
                assertEquals(i, tokenArrayList.size()-1);
            }
        }

        assertNull(tokenArrayList.get(tokenArrayList.size()-1).nextToken());
    }

    @Test
    public void position(){
        Analyzer analyzer = new Analyzer();
        ArrayList<Token> tokenArrayList = (ArrayList<Token>) analyzer.tokenize(this.suppliedString);

        for(int i = 1;i < tokenArrayList.size()-1; i++){
            assertTrue(tokenArrayList.get(i).position() < tokenArrayList.get(i).nextToken().position());
            assertTrue(tokenArrayList.get(i).position() > tokenArrayList.get(i).previousToken().position());
        }
        for(int i = tokenArrayList.size()-1;i > 0; i--){
            assertTrue(tokenArrayList.get(i).position() > tokenArrayList.get(i).previousToken().position());
        }
    }

    @Test
    public void normalizedPosition(){
        Analyzer analyzer = new Analyzer();
        ArrayList<Token> tokenArrayList = (ArrayList<Token>) analyzer.tokenize(this.suppliedString);

        for(int i = 1;i < tokenArrayList.size()-1; i++){
            assertTrue(tokenArrayList.get(i).position() < tokenArrayList.get(i).nextToken().position());
            assertTrue(tokenArrayList.get(i).position() > tokenArrayList.get(i).previousToken().position());
        }
        for(int i = tokenArrayList.size()-1;i > 0; i--){
            assertTrue(tokenArrayList.get(i).position() > tokenArrayList.get(i).previousToken().position());
        }
    }

    @Parameters
    public static Iterable<Object[]> tokensProvider(){
        List<Object[]> parameters = new ArrayList<>();

        //1st Set
        List<Token> expected1 = new ArrayList<>();
        expected1.add(new Token(TokenType.RW_DATATYPE_INTEGER, "entero"));
        expected1.add(new Token(TokenType.MATH_OPERATOR, "-"));
        expected1.add(new Token(TokenType.VALUE_REAL, "23.78"));
        
        String suppliedString1 = "entero  -23.78";
        
        Object[] objectsSet1 = new Object[]{expected1, suppliedString1};
        parameters.add(objectsSet1);
        
        /////////////////////////////////////////////////////////////////////////

        //2nd Set
        List<Token> expected2 = new ArrayList<>();
        expected2.add(new Token(TokenType.IDENTIFIER, "identificador"));
        expected2.add(new Token(TokenType.MATH_OPERATOR, "-"));
        expected2.add(new Token(TokenType.VALUE_STRING, "\"cadena cadena\""));

        String suppliedString2 = "identificador  -  \"cadena cadena\"";

        Object[] objectsSet2 = new Object[]{expected2, suppliedString2};
        parameters.add(objectsSet2);

        /////////////////////////////////////////////////////////////////////////

        //3nd Set
        List<Token> expected3 = new ArrayList<>();
        expected3.add(new Token(TokenType.IDENTIFIER, "identificador"));
        expected3.add(new Token(TokenType.GROUPER_BRACKET_LEFT, "["));
        expected3.add(new Token(TokenType.MATH_OPERATOR, "-"));
        expected3.add(new Token(TokenType.VALUE_STRING, "\"cadena cadena\""));

        String suppliedString3 = "identificador[ \n -  \"cadena cadena\"";

        Object[] objectsSet3 = new Object[]{expected3, suppliedString3};
        parameters.add(objectsSet3);


        /////////////////////////////////////////////////////////////////////////

        //4nd Set
        List<Token> expected4 = new ArrayList<>();
        expected4.add(new Token(TokenType.IDENTIFIER, "identificador"));
        expected4.add(new Token(TokenType.RW_CONTROLSTRUCTURE_ITERATIVE_WHILE_START, "mientras"));
        expected4.add(new Token(TokenType.IDENTIFIER, "identificadormientras"));
        expected4.add(new Token(TokenType.RW_CONTROLSTRUCTURE_ITERATIVE_FOR, "para"));
        expected4.add(new Token(TokenType.VALUE_STRING, "\"cadena cadena\""));

        String suppliedString4 = "identificador mientras \n identificadormientras para\"cadena cadena\"";

        Object[] objectsSet4 = new Object[]{expected4, suppliedString4};
        parameters.add(objectsSet4);


        return parameters;
    }

}