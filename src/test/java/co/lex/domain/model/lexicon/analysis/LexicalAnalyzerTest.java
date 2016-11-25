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
public class LexicalAnalyzerTest {

    private String suppliedString;

    private List<Token> expectedTokens;

    public LexicalAnalyzerTest(List<Token> expectedTokens, String aSuppliedString){
        this.expectedTokens = expectedTokens;
        this.suppliedString = aSuppliedString;
    }

    @Test
    public void parse_GivenACompletelyValidStringToAnalyze_ReturnATokenArrayWithSizeLongerThanZero() throws Exception {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        ArrayList<Token> tokenArrayList = (ArrayList<Token>) lexicalAnalyzer.tokenize(this.suppliedString);

        for (int i = 0; i < expectedTokens.size(); i++) {
            Token t = expectedTokens.get(i);
            assertTrue(t.equals(tokenArrayList.get(i)));
        }


    }

    @Test
    public void linkTokens_GivenATokenListGeneratedFromAnalyzer_AProperlyLinkedTokenList(){
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        ArrayList<Token> tokenArrayList = (ArrayList<Token>) lexicalAnalyzer.tokenize(this.suppliedString);

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
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        ArrayList<Token> tokenArrayList = (ArrayList<Token>) lexicalAnalyzer.tokenize(this.suppliedString);

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
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        ArrayList<Token> tokenArrayList = (ArrayList<Token>) lexicalAnalyzer.tokenize(this.suppliedString);

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
        expected1.add(new Token(TokenType.RW_DATATYPE_INTEGER, new Word("entero", 0, 5)));
        expected1.add(new Token(TokenType.MATH_OPERATOR, new Word("-", 6, 6)));
        expected1.add(new Token(TokenType.VALUE_REAL, new Word("23.78", 7, 12)));
        
        String suppliedString1 = "entero  -23.78";
        
        Object[] objectsSet1 = new Object[]{expected1, suppliedString1};
        parameters.add(objectsSet1);
        
        /////////////////////////////////////////////////////////////////////////

        //2nd Set
        List<Token> expected2 = new ArrayList<>();
        expected2.add(new Token(TokenType.IDENTIFIER, new Word("identificador", 0, 13)));
        expected2.add(new Token(TokenType.MATH_OPERATOR, new Word("-", 0, 6)));
        expected2.add(new Token(TokenType.VALUE_STRING, new Word("\"cadena cadena\"", 0 , 6)));

        String suppliedString2 = "identificador  -  \"cadena cadena\"";

        Object[] objectsSet2 = new Object[]{expected2, suppliedString2};
        parameters.add(objectsSet2);

        /////////////////////////////////////////////////////////////////////////

        //3nd Set
        List<Token> expected3 = new ArrayList<>();
        expected3.add(new Token(TokenType.IDENTIFIER, new Word("identificador", 0 , 8)));
        expected3.add(new Token(TokenType.GROUPER_BRACKET_LEFT, new Word("[", 0 , 8)));
        expected3.add(new Token(TokenType.MATH_OPERATOR, new Word("-", 0 , 8)));
        expected3.add(new Token(TokenType.VALUE_STRING, new Word("\"cadena cadena\"", 0 , 9) ));

        String suppliedString3 = "identificador[ \n -  \"cadena cadena\"";

        Object[] objectsSet3 = new Object[]{expected3, suppliedString3};
        parameters.add(objectsSet3);


        /////////////////////////////////////////////////////////////////////////

        //4nd Set
        List<Token> expected4 = new ArrayList<>();
        expected4.add(new Token(TokenType.IDENTIFIER, new Word("identificador", 0, 10) ));
        expected4.add(new Token(TokenType.RW_CONTROLSTRUCTURE_ITERATIVE_WHILE_START, new Word("mientras", 0 , 10)));
        expected4.add(new Token(TokenType.IDENTIFIER, new Word("identificadormientras", 9, 20) ));
        expected4.add(new Token(TokenType.RW_CONTROLSTRUCTURE_ITERATIVE_FOR_START, new Word("Para", 21, 30)));
        expected4.add(new Token(TokenType.VALUE_STRING, new Word("\"cadena cadena\"", 31, 40) ));

        String suppliedString4 = "identificador mientras \n identificadormientras Para\"cadena cadena\"";

        Object[] objectsSet4 = new Object[]{expected4, suppliedString4};
        parameters.add(objectsSet4);


        return parameters;
    }

}