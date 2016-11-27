package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.LexicalAnalysisReport;
import co.lex.domain.model.lexicon.analysis.LexicalAnalyzer;
import co.lex.domain.model.lexicon.analysis.Token;

import java.util.List;

/**
 * Created by jotauribe on 11/11/16.
 */

public class SyntaxAnalyzer {

    private Grammar grammar;

    private LexicalAnalyzer lexicalAnalyzer;

    public SyntaxAnalyzer(){
        grammar = new Grammar();
        lexicalAnalyzer = new LexicalAnalyzer();
    }

    public AnalysisTree parse(List<Token> aTokenList){

        Token currentToken = aTokenList.get(0);
        RuleGroup start = this.grammar.rules().get(18);

        if(start != null)
            return start.evaluate(currentToken);
        else
            return new AnalysisTree();
    }

    public SyntaxAnalysisReport evaluate(String aString){
        LexicalAnalysisReport lexicalAnalysisReport = lexicalAnalyzer.evaluate(aString);
        AnalysisTree analysisTree = parse(lexicalAnalysisReport.getTokenList());
        SyntaxAnalysisReport syntaxAnalysisReport = new SyntaxAnalysisReport(lexicalAnalysisReport, analysisTree);

        return syntaxAnalysisReport;
    }

}