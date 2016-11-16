package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.Token;

/**
 * Created by jotauribe on 13/11/16.
 */
public class SyntaxErrorException extends Exception {

    private Token token;

    public SyntaxErrorException(String message, Token token){
        super(message);
    }

}
