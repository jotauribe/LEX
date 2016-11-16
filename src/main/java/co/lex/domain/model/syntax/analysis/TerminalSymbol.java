package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.Token;
import co.lex.domain.model.lexicon.analysis.TokenType;

/**
 * Created by jotauribe on 13/11/16.
 */
public class TerminalSymbol implements Symbol, ProductionRule{

    private TokenType tokenType;

    public TerminalSymbol(TokenType aTokenType){
        setTokenType(aTokenType);
    }

    private void setTokenType(TokenType aTokenType){
        if (aTokenType == null) throw new IllegalArgumentException("TokenType can not be null");
        this.tokenType = aTokenType;
    }

    @Override
    public Sentence evaluate(Token aToken) {
        //System.out.print("\nFROM TERMINAL SYMBOL "+aToken+"\n");

        //System.out.print("\nFROM TERMINAL SYMBOL "+this.tokenType.name()+" = "+aToken.type().equals(this.tokenType.name())+"\n");
        if(aToken.type().equals(this.tokenType.name())) return new Sentence(aToken, aToken, this);
        return new Sentence();
    }

    @Override
    public String name() {
        return tokenType.name();
    }
}
