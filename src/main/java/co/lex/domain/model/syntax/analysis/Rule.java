package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.Token;
import co.lex.domain.model.lexicon.analysis.TokenType;

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

        Token currentToken = aToken;
        Token endToken2 = null;
        List<Sentence> sentences = new ArrayList<>();
        Sentence lastSentence = null;

        for (Symbol s : this.symbols) {
            //System.out.print(""+"\nFROM RULE \n: Token evaluado"+currentToken+"\n evaluado por "+s.toString());
            if(currentToken == null){
                //System.out.print("\nFROM RULE "+": NO SE CUMPLE LA REGLA");
                return new Sentence();
            }
            if(s instanceof NonTerminalSymbol){
                //System.out.print("\nFROM RULE: ENTRADA A REGLA NO TERMINAL");
            }
            Sentence ts = s.evaluate(currentToken);

            if (ts.length() > 0){
                //System.out.print(""+"\nFROM RULE 2 : "+ts.endToken().tokenType());
                currentToken = ts.endToken().nextToken();
                lastSentence = ts;
                endToken2 = ts.endToken();
                if (ts.length() > 1){
                    sentences.add(ts);
                }
            }
            else{
                return new Sentence();
            }

        }
        Token endToken = endToken2;
        Sentence s = new Sentence(aToken, lastSentence.endToken(), this);
        //System.out.print("\n================================\nFROM RULE RETURN: "+endToken.type()+" "+endToken.position());
        s.setSubSentences(sentences);
        return s;
    }

}
