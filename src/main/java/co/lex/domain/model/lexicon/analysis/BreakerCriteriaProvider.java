package co.lex.domain.model.lexicon.analysis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jotauribe on 10/11/16.
 */
public class BreakerCriteriaProvider {

    /**
     * Iterates over the defined TokenTypes and return a List<Token> with them
     *
     * @return
     */
    public static List<TokenType> tokenTypes(){
        ArrayList<TokenType> tokenTypes = new ArrayList<>();
        for (TokenType tt :
                TokenType.values()) {
            tokenTypes.add(tt);
        }
        return tokenTypes;
    }
}
