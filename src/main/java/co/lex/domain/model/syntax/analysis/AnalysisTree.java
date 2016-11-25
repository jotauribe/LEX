package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jotauribe on 13/11/16.
 */
public class AnalysisTree {

    private Token startToken;

    private Token endToken;

    private Token rightmostVisitedToken;

    private ProductionRule acceptanceRule;

    private List<AnalysisTree> children;

    public AnalysisTree(){ }

    public AnalysisTree(Token endToken, Token rightmostVisitedToken){
        this.endToken = endToken;
        this.rightmostVisitedToken = rightmostVisitedToken;
    }

    public AnalysisTree(Token endToken, Token rightmostVisitedToken, Token rightmostValidToken){
        this.endToken = endToken;
        this.rightmostVisitedToken = rightmostVisitedToken;
        this.rightmostVisitedToken = rightmostValidToken;
    }

    public AnalysisTree(Token startToken, Token endToken, ProductionRule aRule){
        this.startToken = startToken;
        this.endToken = endToken;
        this.acceptanceRule = aRule;
        children = new ArrayList<>();
    }

    public AnalysisTree(Token startToken,
                        Token endToken,
                        Token rightmostVisitedToken,
                        ProductionRule aRule){
        this.startToken = startToken;
        this.endToken = endToken;
        this.rightmostVisitedToken = rightmostVisitedToken;
        setAcceptanceRule(aRule);
        children = new ArrayList<>();
    }

    public static AnalysisTree emptyTree(Token endToken, Token rightmostVisitedToken){
        return new AnalysisTree(endToken, rightmostVisitedToken);
    }

    public Token startToken() {
        return startToken;
    }

    public Token endToken() {
        return endToken;
    }

    public ProductionRule acceptanceRule() {
        return acceptanceRule;
    }

    public String acceptanceRuleName() {
        return acceptanceRule.name();
    }

    private void setAcceptanceRule(ProductionRule acceptanceRule) {
        if(acceptanceRule == null)
            throw new IllegalArgumentException("Acceptance rule can not be null");
        this.acceptanceRule = acceptanceRule;
    }

    public List<AnalysisTree> children() {
        return children;
    }

    public void addChildren(List<AnalysisTree> children) {
        this.children = children;
    }

    public void addChild(AnalysisTree subsectionTree){
        if(subsectionTree.endTokenPosition() > endToken.position()
                || subsectionTree.startTokenPosition() < startToken.position()){
            throw new IllegalArgumentException("Child out of bound");
        }
        this.children.add(subsectionTree);
    }

    public int length(){
        if((endToken == null) || (startToken == null)) {
            return 0;
        }else {
            return endToken.position() - startToken.position() + 1;
        }
    }

    public List<Token> getTokens(){
        List<Token> tokenList = new ArrayList<>();
        Token t = startToken;
        int count = 0;
        while(count < length()){
            tokenList.add(t);
            t = t.nextToken();
            count++;
        }
        return tokenList;
    }

    public AnalysisTree rightmostChild(){
        AnalysisTree rightmostChild = children().get(children().size() -1);
        return rightmostChild;
    }

    public Token lastValidToken(){
        return endToken();
    }

    public int lastValidTokenPosition(){
        if(endToken() == null) return 0;
        return endTokenPosition();
    }

    public int endTokenPosition(){
        if(endToken == null) return 0;
        return endToken.position();
    }

    public int startTokenPosition(){
        if(startToken == null) return 0;
        return startToken.position();
    }

    public Token rightmostVisitedToken(){
        return this.rightmostVisitedToken;
    }

    public int rightmostVisitedTokenPosition(){
        if(rightmostVisitedToken == null) return goToEnd();
        return rightmostVisitedToken.position();
    }

    public int rightmostValidTokenPosition(){

        if(rightmostVisitedToken == null)
            return goToEnd();

        if (rightmostVisitedToken().previousToken() != null)
            return rightmostVisitedToken().previousToken().position();

        if(lastValidToken() == null)
            return 0;

        return rightmostVisitedTokenPosition();
    }

    public Token rightmostValidToken(){
        int position = rightmostValidTokenPosition();
        Token t = endToken();
        while(t != null){
            if(t.position() == position){
                break;
            }
            t = t.nextToken();
        }
        return t;
    }

    private int goToEnd(){
        Token tempToken = endToken;
        int i = 0;
        while(tempToken != null
                && endToken() != null){
            i = tempToken.position();
            tempToken = tempToken.nextToken();
        }
        return i;
    }

    public void print(){

    }
}
