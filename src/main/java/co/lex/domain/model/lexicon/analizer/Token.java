package co.lex.domain.model.lexicon.analizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jotauribe on 10/11/16.
 */
public class Token {

    private TokenType tokenType;

    private String word;

    public Token(TokenType aTokenType, String aAllowedWord){
        this.tokenType = aTokenType;
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
