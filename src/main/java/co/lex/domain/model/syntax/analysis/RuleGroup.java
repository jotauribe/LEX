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

        Token currentToken = aToken;
        Token endToken = aToken;
        Sentence childSentence = null;
        Boolean flag = false;


        for (Rule r : this.rules) {
            Token tempToken = aToken;

            Sentence ts = r.evaluate(tempToken);
            //System.out.print("\nFROM RULEGROUP AFTER EVALUATE : " +this.name()+" : "+ts.length());
            //System.out.print("\nFROM RULEGROUP : "+ts.endToken());

            if(ts.length() > 0){
                flag = true;
                if(childSentence == null){
                    childSentence = ts;
                    endToken = ts.endToken();
                }
                else {
                    if(childSentence.length() < ts.length()){ //TODO Revisar conveniencia de la comparacion <= o <
                        childSentence = ts;
                        endToken = ts.endToken();
                    }
                }
            }
        }

        if(flag){
            Sentence s = new Sentence(aToken, endToken, this);
            //System.out.print("\nFROM RULEGROUP : " +this.name()+" RETURN : "+s.length());
            //System.out.print("\nFROM RULEGROUP : " +this.name()+" RETURN : "+s.endToken());
            s.addSubsentence(childSentence);
            return childSentence; //TODO
        }

        return new Sentence();
    }

}
