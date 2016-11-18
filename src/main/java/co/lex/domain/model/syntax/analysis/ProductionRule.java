package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.Token;

/**
 * Created by RMC on 12/11/2016.
 */
public interface ProductionRule {

    public Sentence evaluate(Token aToken) throws SyntaxErrorException;

    public String name();

}
