package co.lex.domain.model.lexicon.analysis;

import java.util.List;

/**
 * Created by jotauribe on 24/11/16.
 */
public class LexicalAnalysisReport {

    private List<Token> errorStack;

    private List<Token> linkedTokenList;

    private void setErrorStack(List<Token> errorStack){
        if(errorStack == null)
            throw new IllegalArgumentException("Error stack can not be null");
        this.errorStack = errorStack;
    }

    private void setLinkedTokenList(List<Token> linkedTokenList){
        if(linkedTokenList == null)
            throw new IllegalArgumentException("Error stack can not be null");
        this.linkedTokenList = linkedTokenList;
    }

    public LexicalAnalysisReport(List<Token> linkedTokenList, List<Token> errorStack){
        setLinkedTokenList(linkedTokenList);
        setErrorStack(errorStack);
    }

    public List<Token> getTokenList(){
        return this.linkedTokenList;
    }

    public List<Token> getErrorList(){
        return this.errorStack;
    }

    private Token searchFirstToken(){
        Token firstToken = null;
        if(!linkedTokenList.isEmpty())
            firstToken = linkedTokenList.get(0);
        while(firstToken != null){
            if(firstToken.position() == 0)
                break;
            Token nextToken;
            if(firstToken.nextToken() != null) {
                nextToken = firstToken.nextToken();
                if(firstToken.position() > nextToken.position())
                    firstToken = nextToken;
            }
        }
        return firstToken;
    }

    public Token firstToken(){
        return searchFirstToken();
    }

}
