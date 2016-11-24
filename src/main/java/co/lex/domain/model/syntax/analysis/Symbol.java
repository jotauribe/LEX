package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.Token;

/**
 * Created by jotauribe on 13/11/16.
 */
public interface Symbol {

    public AnalysisTree evaluate(Token aToken);



}
