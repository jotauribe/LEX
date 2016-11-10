package co.lex.domain.model.lexicon.analizer;

import com.sun.javafx.fxml.expression.Expression;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jotauribe on 10/11/16.
 */
@RunWith(value = Parameterized.class)
public class TokenTest {

    private TokenType tokenType;

    private String word;

    public TokenTest(TokenType aTokenType, String aString){
        this.tokenType = aTokenType;
        this.word = aString;
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNewToken_NotAllowedWord_ExceptionThrown(){
        Token token = new Token(this.tokenType, this.word);
    }

    @Parameters
    public static Iterable<Object[]> parameters(){
        List<Object[]> parameters = new ArrayList<>();
        parameters.add(new Object[]{TokenType.END_OF_STATEMENT, "aString_1234_;"});
        parameters.add(new Object[]{TokenType.END_OF_STATEMENT, "      ;"});
        parameters.add(new Object[]{TokenType.END_OF_STATEMENT, "aString_1234_"});
        parameters.add(new Object[]{TokenType.END_OF_STATEMENT, "    "});
        parameters.add(new Object[]{TokenType.END_OF_STATEMENT, " ;"});
        return parameters;
    }

}