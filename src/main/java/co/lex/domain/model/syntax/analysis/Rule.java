package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jotauribe on 13/11/16.
 */

/**
 * RULE STRUCTURE
 *
 * <RULE>   =   <SYMBOL> <SYMBOL> <SYMBOL> ...
 * <SYMBOL> =   <TERMINAL> | <NON-TERMINAL>
 */
public class Rule implements ProductionRule{

    private RuleGroup parentRule;

    private List<Symbol> symbols;

    public Rule(List<Symbol> symbols){
        this.symbols = symbols;
    }

    public Rule(RuleGroup parentRule, List<Symbol> symbols){
        this.parentRule = parentRule;
        this.symbols = symbols;
    }

    public List<Symbol> symbols(){
        return this.symbols;
    }

    public String name(){
        return parentRule.name();
    }

    public Sentence evaluate(Token aToken){
        Token previousToken = aToken.previousToken();
        Token currentToken = aToken;
        Token endToken = null;
        List<Sentence> sentences = new ArrayList<>();

        for (Symbol s : this.symbols) {

            //SI LA CADENA DE TOKENS TERMINA ANTES DE ACABAR LA COMPROBACION DE LA REGLA
            //SE RETORNA UN OBJETC DE TIPO SENTENCE CON INFORMACION SOBRE EL ULTIMO TOKEN VALIDO
            if(currentToken == null){
                Sentence errorSentence = Sentence.errorSentence(previousToken);
                errorSentence.setSubSentences(sentences);
                return errorSentence;
            }

            previousToken = currentToken.previousToken();
            Sentence ts = s.evaluate(currentToken);

            if (ts.length() > 0){
                currentToken = ts.endToken().nextToken();
                endToken = ts.endToken();
                if (ts.length() > 1){
                    sentences.add(ts);
                }
            }
            else{
                Sentence errorSentence = Sentence.errorSentence(ts.lastValidToken()); //TODO REVISAR
                return errorSentence;
            }
        }
        Sentence s = new Sentence(aToken, endToken, this);
        s.setSubSentences(sentences);
        return s;
    }

}
