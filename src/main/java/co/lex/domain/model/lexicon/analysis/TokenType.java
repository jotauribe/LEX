package co.lex.domain.model.lexicon.analysis;

import java.util.regex.Pattern;

/**
 * Created by jotauribe on 9/11/16.
 */
public enum TokenType {

    //ASCENDANTLY ORDERED BY PRIORITY

    WHITESPACE("[ \t\f\r\n]+", TokenType.DEFAULT_PRIORITY_INDEX),

    END_OF_STATEMENT(";", TokenType.DEFAULT_PRIORITY_INDEX),

    COLON(":", TokenType.DEFAULT_PRIORITY_INDEX),

    IDENTIFIER("[a-zA-Zñ]([\\w]|[ñ])+", TokenType.DEFAULT_PRIORITY_INDEX),

    VALUE_INTEGER("([\\d])+", TokenType.DEFAULT_PRIORITY_INDEX), //PREVIOUS PATTERN "-?([\\d])+"

    VALUE_REAL("(\\d+\\.\\d+)+", TokenType.DEFAULT_PRIORITY_INDEX), //PREVIOUS PATTERN "(\\d+\\.\\d+)+"

    VALUE_STRING("[\"]([\\w]|[ñ]|\\s)+[\"]", TokenType.DEFAULT_PRIORITY_INDEX),

    VALUE_BOOLEAN_TRUE("cierto", TokenType.MAX_PRIORITY_INDEX),

    VALUE_BOOLEAN_FALSE("falso", TokenType.MAX_PRIORITY_INDEX),

    MATH_OPERATOR("(\\+|-|\\*|\\\\)", TokenType.DEFAULT_PRIORITY_INDEX),

    ASSIGNMENT_OPERATOR("=", TokenType.DEFAULT_PRIORITY_INDEX),

    LOGICAL_OPERATOR("(\\||&|==|!=)", TokenType.DEFAULT_PRIORITY_INDEX),

    GROUPER_BRACKET_LEFT("\\[", TokenType.DEFAULT_PRIORITY_INDEX),

    GROUPER_BRACKET_RIGHT("\\]", TokenType.DEFAULT_PRIORITY_INDEX),

    GROUPER_BRACE_LEFT("\\{", TokenType.DEFAULT_PRIORITY_INDEX),

    GROUPER_BRACE_RIGTH("\\}", TokenType.DEFAULT_PRIORITY_INDEX),

    GROUPER_PARENTHESES_LEFT("\\(", TokenType.DEFAULT_PRIORITY_INDEX),

    GROUPER_PARENTHESES_RIGHT("\\)", TokenType.DEFAULT_PRIORITY_INDEX),

    //DATA TYPE TOKEN TYPES

    RW_DATATYPE_INTEGER("entero", TokenType.MAX_PRIORITY_INDEX),

    RW_DATATYPE_STRING("cadena", TokenType.MAX_PRIORITY_INDEX),

    RW_DATATYPE_REAL("real", TokenType.MAX_PRIORITY_INDEX),

    RW_DATATYPE_BOOLEAN("logico", TokenType.MAX_PRIORITY_INDEX),

    //CONTROL STRUCTURES TOKEN TYPES

    RW_CONTROLSTRUCTURE_CONDITIONAL_IF("si", TokenType.MAX_PRIORITY_INDEX),

    RW_CONTROLSTRUCTURE_CONDITIONAL_ELSE("sino", TokenType.MAX_PRIORITY_INDEX),

    RW_CONTROLSTRUCTURE_CONDITIONAL_SWITCH_START("segun", TokenType.MAX_PRIORITY_INDEX),

    RW_CONTROLSTRUCTURE_CONDITIONAL_SWITCH_END("fin_segun", TokenType.MAX_PRIORITY_INDEX),

    RW_CONTROLSTRUCTURE_ITERATIVE_FOR("para", TokenType.MAX_PRIORITY_INDEX),

    RW_CONTROLSTRUCTURE_ITERATIVE_WHILE("mientras", TokenType.MAX_PRIORITY_INDEX),

    RW_CONTROLSTRUCTURE_ITERATIVE_DO("hacer", TokenType.MAX_PRIORITY_INDEX);

    public final static int DEFAULT_PRIORITY_INDEX = 50;

    public final static int MAX_PRIORITY_INDEX = 100;

    private String pattern;

    private int priority;

    private TokenType(String aPattern, int priorityIndex){
        this.pattern = aPattern;
        this.priority = priorityIndex;
    }

    /**
     * Returns the TokenType's regular expression in string format
     *
     * @return the TokenType's regular expression in string format
     */
    public String patternString(){
        return this.pattern;
    }

    /**
     * Returns the TokenType's compile regular expression (Pattern object)
     *
     * @return the TokenType's compile regular expression (Pattern object)
     */
    public Pattern compiledPattern(){
        return Pattern.compile(this.patternString());
    }

    public int priorityIndex(){
        return this.priority;
    }

}
