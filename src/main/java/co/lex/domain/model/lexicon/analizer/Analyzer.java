package co.lex.domain.model.lexicon.analizer;

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
    public List<Token> parse(String aString){

        latestGeneratedTokens.clear();

        String tempString = aString;
        int matcherStart = 0;
        while (tempString.length()>0){
            tempString = nextMatch(tempString);
        }

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
                        //TEST STATE FIX PROVIDED AND TESTED This depend on the order of the TokenTypes HAVE TO FIX
                        && currentTokenType.priorityIndex() >= matchedTokenType.priorityIndex()){
                    matchedString = tempMatchedString;
                    matchedTokenType = currentTokenType;
                }
            }
        }

        latestGeneratedTokens.add( new Token(matchedTokenType, matchedString) );

        return aString.substring(matchedString.length());
    }

}
