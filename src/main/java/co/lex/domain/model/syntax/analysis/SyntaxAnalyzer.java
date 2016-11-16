package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jotauribe on 11/11/16.
 */
public class SyntaxAnalyzer {

    private Grammar grammar;

    public SyntaxAnalyzer(){
        grammar = new Grammar();
    }

    public SyntaxAnalyzer(Grammar grammar){
        this.grammar = grammar;

    }

    public Sentence parse(List<Token> aTokenList){

        Token currentToken = aTokenList.get(0);

        List<RuleGroup> rules = this.grammar.rules();

        List<Sentence> childSentences = new ArrayList<>();

        Sentence sentence = new Sentence();




        if(grammar.rules().size() > 0){

            for(int i = 0; i < rules.size(); i++){
                Sentence s = rules.get(i).evaluate(currentToken);
                if (s.length() > sentence.length()) sentence = s;
            }
            return sentence;

        }

        return null;
    }

}
