package co.lex.domain.model.lexicon.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jotauribe on 10/11/16.
 */
public class Analyzer {

    private List<TokenType> tokenTypes;

    private List<Token> latestGeneratedTokens;

    public Analyzer(){

        this.tokenTypes = BreakerCriteriaProvider.tokenTypes();
        this.latestGeneratedTokens = new ArrayList<>();
    }

    /**
     *
     * @param aString the analyzed String
     * @return a token list
     */
    public List<Token> tokenize(String aString){

        latestGeneratedTokens.clear();

        String tempString = aString;
        int matcherStart = 0;
        while (tempString.length()>0){
            tempString = nextMatch(tempString);
        }

        this.linkTokens();

        return latestGeneratedTokens;
    }

    /**
     * Adds the next valid token in the analyzed String to the latestGeneratedTokens list
     * and return the remaining analyzed string after subtract the matched valid word
     */
    private String nextMatch(String aString){

        String tempString = aString;
        String matchedString = "";
        TokenType matchedTokenType = this.tokenTypes.get(0);
        Boolean errorFlag = true; // For Error Handling

        for(TokenType currentTokenType: tokenTypes){
            Pattern currentPattern = currentTokenType.compiledPattern();
            Matcher m = currentPattern.matcher(tempString);
            if(m.lookingAt()){
                errorFlag = false;
                String tempMatchedString = m.group();
                if( tempMatchedString.length() >= matchedString.length()
                        && currentTokenType.priorityIndex() >= matchedTokenType.priorityIndex()){
                    matchedString = tempMatchedString;
                    matchedTokenType = currentTokenType;
                }
            }
        }

        latestGeneratedTokens.add( new Token(matchedTokenType, matchedString) );

        return aString.substring(matchedString.length());
    }

    private void linkTokens(){
        latestGeneratedTokens = getNormalizedTokenList(latestGeneratedTokens);
        for(int i = 0; i < this.latestGeneratedTokens.size()-1; i++){
            if(this.latestGeneratedTokens.get(i+1) != null){
                latestGeneratedTokens.get(i).setNextToken(latestGeneratedTokens.get(i+1));
                this.latestGeneratedTokens.get(i+1).setPreviousToken(this.latestGeneratedTokens.get(i));// TODO

            }

        }

        for (int i = 0; i < latestGeneratedTokens.size(); i++){
            this.latestGeneratedTokens.get(i).setPosition(i);
            ;
        }
    }


    //TODO implement method
    private List<Token> getNormalizedTokenList(List<Token> aLinkedTokenList){
        List<Token> normalizedTokenList = new ArrayList<>();
        for(int i = 0; i < latestGeneratedTokens.size(); i++){
            if(latestGeneratedTokens.get(i).tokenType() != TokenType.WHITESPACE){
                normalizedTokenList.add(latestGeneratedTokens.get(i));
            }
        }

        return normalizedTokenList;
    }

}
