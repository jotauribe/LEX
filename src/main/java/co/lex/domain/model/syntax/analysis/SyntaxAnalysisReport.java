package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.LexicalAnalysisReport;
import co.lex.domain.model.lexicon.analysis.Token;

import java.util.List;

/**
 * Created by jotauribe on 24/11/16.
 */
public class SyntaxAnalysisReport {

    private LexicalAnalysisReport lexicalAnalysisReport;

    private AnalysisTree analysisTree;

    public void setLexicalAnalysisReport(LexicalAnalysisReport lexicalAnalysisReport){
        if(lexicalAnalysisReport == null)
            throw new IllegalArgumentException("Lexical analysis report can not be null");
        this.lexicalAnalysisReport = lexicalAnalysisReport;
    }

    public void setAnalysisTree(AnalysisTree analysisTree){
        if(analysisTree == null)
            throw new IllegalArgumentException("Lexical analysis report can not be null");
        this.analysisTree = analysisTree;
    }

    public SyntaxAnalysisReport(LexicalAnalysisReport lexicalAnalysisReport,
                                AnalysisTree analysisTree){
        setLexicalAnalysisReport(lexicalAnalysisReport);
        setAnalysisTree(analysisTree);
    }

    public List<Token> tokenList(){
        return this.lexicalAnalysisReport.getTokenList();
    }

    public List<Token> lexicalErrorList(){
        return lexicalAnalysisReport.getErrorList();
    }

    public AnalysisTree syntaxAnalysisTree(){
        return analysisTree;
    }

    public Boolean isEverythingOK(){
        if(lexicalAnalysisReport.getErrorList().size() > 0)
            return false;
        if (analysisTree.lastValidToken() == null)
            return false;
        if(analysisTree.lastValidToken().nextToken() != null)
            return false;
        return true;
    }

}
