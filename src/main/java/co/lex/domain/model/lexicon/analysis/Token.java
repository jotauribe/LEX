package co.lex.domain.model.lexicon.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jotauribe on 10/11/16.
 */
public class Token {

    private TokenType tokenType;

    private String word;

    private int position;

    private int normalizedPosition;

    private Token nextToken;

    private Token previousToken;

    public Token(TokenType aTokenType, String aAllowedWord){
        this.setTokenType(aTokenType);
        this.setWord(aAllowedWord);

    }

    private void setWord(String aAllowedWord){
        Matcher m = getMatcher(aAllowedWord);
        if( m.find() && (m.group(0).length() == aAllowedWord.length()) ){
            this.word = aAllowedWord;
        }
        else{
            throw new IllegalArgumentException("Not allowed word provided. Expected: "+tokenType.name()+". Provided: "+aAllowedWord);
        }
    }

    private void setTokenType(TokenType aTokenType){
        if(aTokenType == null){
            throw  new IllegalArgumentException("TokenType can not be null");
        }
        this.tokenType = aTokenType;
    }

    private Matcher getMatcher(String aWord){
        Pattern p = Pattern.compile(this.tokenType.patternString());
        return p.matcher(aWord);
    }

    public String word(){
        return this.word;
    }

    public String type(){
        return this.tokenType.name();
    }

    public TokenType tokenType(){
        return this.tokenType;
    }

    public Token nextToken(){
        return this.nextToken;
    }

    public int position(){
        return this.position;
    }

    public int normalizedPosition(){
        return this.normalizedPosition;
    }

    public Token previousToken(){
        return this.previousToken;
    }

    public void setNextToken(Token aToken){
        this.nextToken = aToken;
    }

    public void setPreviousToken(Token aToken){
        this.previousToken = aToken;
    }

    public void setPosition(int aPosition){
        if(this.previousToken != null){
            if(aPosition <= this.previousToken().position()){
                throw new IllegalArgumentException("An illegal position was provided. Position can not be smaller than previous Token position");
            }
        }
        this.position = aPosition;
    }

    public void setNormalizedPosition(int aPosition) {
        if (this.previousToken != null) {
            if (aPosition <= this.previousToken().normalizedPosition()) {
                throw new IllegalArgumentException("An illegal position was provided. Position can not be smaller than previous Token position");
            }
        }
        this.normalizedPosition = aPosition;
    }

    @Override
    public String toString(){
        return this.type()+"<"+this.word()+">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;

        Token token = (Token) o;

        if ( !(this.type().equals(token.tokenType().name())) ) return false;
        return this.word().equals(token.word());

    }

    @Override
    public int hashCode() {
        int result = tokenType.name().hashCode();
        result = 31 * result + word.hashCode();
        return result;
    }
}
