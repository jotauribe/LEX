package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.Token;

/**
 * Created by jotauribe on 13/11/16.
 */
public class NonTerminalSymbol implements Symbol, ProductionRule{

    private RuleGroup linkedRuleGroup;

    public NonTerminalSymbol(RuleGroup rg){
        linkedRuleGroup = rg;
    }

    @Override
    public Sentence evaluate(Token aToken) {
        Sentence s = linkedRuleGroup.evaluate(aToken);
        return s;
    }

    @Override
    public String name() {
        return linkedRuleGroup.name();
    }
}
