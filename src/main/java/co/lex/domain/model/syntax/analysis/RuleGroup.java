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
        if(rules == null) throw new IllegalArgumentException("Rule list cannot be null");
        this.rules = rules;
        setParents();
    }

    private void setParents(){
        for (Rule r :
                rules) {
            r.setParent(this);
        }
    }

    public AnalysisTree evaluate(Token aToken) {

        //System.out.print("\n-----------------------------------"+this.name()+"-------------------------------------\n");

        Token endToken = aToken;
        Token lastValidToken = aToken.previousToken();
        Token rightmostVisitedToken = aToken;
        AnalysisTree largestTree = null;
        Boolean flag = false;


        for (Rule currentRule : this.rules) {

            Token currentToken = aToken;
            AnalysisTree tempTree = currentRule.evaluate(currentToken);

            if(tempTree.rightmostVisitedToken() == null){
                rightmostVisitedToken = null;
            }
            if(rightmostVisitedToken != null)
                if (tempTree.rightmostVisitedTokenPosition() > rightmostVisitedToken.position())
                    rightmostVisitedToken = tempTree.rightmostVisitedToken();


            if(tempTree.length() > 0){

                flag = true;
                if(largestTree == null){

                    largestTree = tempTree;
                    endToken = tempTree.endToken();

                } else {

                    if(largestTree.length() < tempTree.length()){
                        largestTree = tempTree;
                        endToken = tempTree.endToken();

                    }
                }
            } else {

                if(lastValidToken == null)

                    lastValidToken = tempTree.lastValidToken();

                else

                    if(tempTree.lastValidToken() != null)
                        if(lastValidToken.position() < tempTree.lastValidTokenPosition())
                            lastValidToken = tempTree.lastValidToken();
            }
        }

        if(flag){

            AnalysisTree t = new AnalysisTree(aToken, endToken, rightmostVisitedToken, this);
            t.addChild(largestTree);
            return t;

        }

        AnalysisTree emptyTree;
        emptyTree = AnalysisTree.emptyTree(lastValidToken, rightmostVisitedToken);
        //System.out.print("\n"+rightmostVisitedToken+"\n");
        //System.out.print("\n-----------------------------------"+this.name()+"-------------------------------------\n");
        return emptyTree;

    }

}
