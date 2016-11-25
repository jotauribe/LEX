package co.lex.domain.model.lexicon.analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jotauribe on 10/11/16.
 */
public class Token {

    private TokenType tokenType;

    private Word word;

    private int position;

    private Token nextToken;

    private Token previousToken;

    public Token(TokenType aTokenType, Word aWord){
        this.setTokenType(aTokenType);
        this.setWord(aWord);

    }

    private void setWord(Word aWord){
        String wordString = aWord.toString();
        Matcher m = getMatcher(wordString);
        if( m.find() && (m.group(0).length() == wordString.length()) ){
            this.word = aWord;
        }
        else{
            throw new IllegalArgumentException("Not allowed word provided. Expected: "+tokenType.name()+". Provided: "+wordString);
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
        return word.toString();
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

    public Token previousToken(){
        return this.previousToken;
    }

    public int startLocation(){
        return word.start();
    }

    public int endLocation(){
        return word.end();
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
