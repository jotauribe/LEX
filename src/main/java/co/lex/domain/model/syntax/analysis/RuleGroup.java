package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.Token;

import java.util.List;

/**
 * Created by jotauribe on 13/11/16.
 */

/**
 * ESTRUCTURA DE UN GRUPO DE REGLAS
 *
 * <RULE-GROUP>   =   <RULE> | <RULE> | <RULE> ...
 */
public class RuleGroup implements ProductionRule{

    private String name;

    private List<Rule> rules;

    public RuleGroup(String name){
        this.name = name;
    }

    public List<Rule> rules(){
        return this.rules;
    }

    public String name(){
        return name;
    }

    public void setRules(List<Rule> rules){
        this.rules = rules;
    }

    public Sentence evaluate(Token aToken) {

        Token previousToken = aToken.previousToken();
        Token currentToken = aToken;
        Token endToken = aToken;
        Token lastValidToken = aToken;
        Sentence childSentence = null;
        Boolean flag = false;


        for (Rule r : this.rules) {
            Token tempToken = aToken;

            Sentence ts = r.evaluate(tempToken);
            System.out.print("\n-----------FROM RULEGROUP "+ this.name()+" LASTVALID TOKEN "+ts.lastValidToken()+"\n");

            if(ts.length() > 0){
                flag = true;
                if(childSentence == null){
                    childSentence = ts;
                    endToken = ts.endToken();
                }
                else {
                    if(childSentence.length() < ts.length()){
                        childSentence = ts;
                        endToken = ts.endToken();
                    }
                }
            } else{ //TODO Verificar
                if(ts.lastValidToken() != null){
                    if(lastValidToken.position() < ts.lastValidToken().position()){
                        lastValidToken = ts.lastValidToken();
                    }
                }
            }
        }

        if(flag ){
            Sentence s = new Sentence(aToken, endToken, this);
            s.addSubsentence(childSentence);
            return s; //TODO
        }

        Sentence errorSentence = Sentence.errorSentence(lastValidToken);
        return errorSentence;
    }

}
