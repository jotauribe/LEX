package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jotauribe on 13/11/16.
 */
public class Sentence {

    private Token startToken;

    private Token endToken;

    private ProductionRule acceptanceRule;

    private List<Sentence> subSentences;

    private Boolean isBroken;

    public Sentence(){ }

    public Sentence(Token endToken){
        this.endToken = endToken;
    }

    public Sentence(Token startToken, Token endToken, ProductionRule aRule){
        this.startToken = startToken;
        this.endToken = endToken;
        this.acceptanceRule = aRule;
        subSentences = new ArrayList<>();
        this.isBroken = false;
    }

    public Sentence(Token startToken, Token endToken, ProductionRule aRule, Boolean isBroken){
        this.startToken = startToken;
        this.endToken = endToken;
        this.acceptanceRule = aRule;
        subSentences = new ArrayList<>();
        this.isBroken = isBroken;
    }

    public static Sentence errorSentence(Token endToken){
        return new Sentence(endToken);
    }

    public static Sentence brokenSentence(Token startToken, Token endToken, ProductionRule acceptanceRule){
        return new Sentence(startToken, endToken, acceptanceRule, false);
    }

    public Token startToken() {
        return startToken;
    }

    public void setStartToken(Token startToken) {
        this.startToken = startToken;
    }

    public Token endToken() {
        return endToken;
    }

    public void setEndToken(Token endToken) {
        this.endToken = endToken;
    }

    public ProductionRule acceptanceRule() {
        return acceptanceRule;
    }

    public void setAcceptanceRule(ProductionRule acceptanceRule) {
        this.acceptanceRule = acceptanceRule;
    }

    public List<Sentence> subSentences() {
        return subSentences;
    }

    public void setSubSentences(List<Sentence> sentences) {
        subSentences = sentences;
    }

    public void addSubsentence(Sentence aSentence){
        if(aSentence.endToken().position() > endToken.position()
                || aSentence.startToken().position() < startToken.position()){
            throw new IllegalArgumentException("Sentence out of bound");
        }
        this.subSentences.add(aSentence);
    }

    public int length(){
        if((endToken == null) || (startToken == null)) {
            return 0;
        }else {
            if (isBroken){
                Token tempToken = startToken;
                int count = 0;
                while (tempToken != null){
                    count++;
                    tempToken.nextToken();
                }
                return count;
            }
        }
        return endToken.position() - startToken.position() + 1;
    }

    public List<Token> getTokens(){
        List<Token> lt = new ArrayList<>();
        Token t = startToken;
        int count = 0;
        while(count < length()){
            lt.add(t);
            t = t.nextToken();
            count++;
        }
        return lt;
    }

    public Sentence lastValidSentence(){
        Sentence lastValidSentence = subSentences().get(subSentences().size() -1);
        return lastValidSentence;
    }

    public Token lastValidToken(){
        return endToken();
    }
}
