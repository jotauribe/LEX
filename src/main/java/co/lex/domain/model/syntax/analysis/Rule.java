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

    public void setParent(RuleGroup parentRule){

        if(parentRule == null) throw new IllegalArgumentException("Parent rule can not be null");
        this.parentRule = parentRule;

    }

    public String name(){
        return parentRule.name();
    }

    public AnalysisTree evaluate(Token aToken){

        Token previousToken = aToken.previousToken();
        Token currentToken = aToken;
        Token rightmostVisitedToken = aToken;
        Token endToken = aToken;
        List<AnalysisTree> children = new ArrayList<>();

        for (Symbol s : this.symbols) {

            if(currentToken == null){

                AnalysisTree emptyTree = AnalysisTree.emptyTree(previousToken, null);
                emptyTree.addChildren(children);
                return emptyTree;

            }

            previousToken = currentToken.previousToken();
            AnalysisTree tempTree = s.evaluate(currentToken);

            if(tempTree.rightmostVisitedToken() == null){
                rightmostVisitedToken = null;
            }

            if(rightmostVisitedToken != null)
                if (rightmostVisitedToken.position() < tempTree.rightmostVisitedTokenPosition())
                    rightmostVisitedToken = tempTree.rightmostVisitedToken();

            if (tempTree.length() > 0){

                endToken = tempTree.endToken();
                currentToken = endToken.nextToken();

                if (tempTree.length() > 0) children.add(tempTree);

            }
            else{
                AnalysisTree emptyTree = AnalysisTree.emptyTree(tempTree.lastValidToken(), rightmostVisitedToken);
                return emptyTree;
            }
        }

        AnalysisTree s = new AnalysisTree(aToken, endToken, rightmostVisitedToken, this);
        s.addChildren(children);
        return s;

    }

}
