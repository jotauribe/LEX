package co.lex.domain.model.syntax.analysis;

import java.util.List;

/**
 * Created by jotauribe on 10/11/16.
 */
public class Grammar {

    private List<ProductionRule> productionRules;

    /**
     *
     * <TYPE>                       =   [RW-INTEGER] | [RW-FLOAT-NUMBER] | [RW-STRING] | [RW-BOOLEAN]
     * <VALUE>                      =   [STRING] | [INTEGER] | [FLOAT-NUMBER] | [BOOLEAN]
     * <VARIABLE>                   =   <VARIABLE> "[" [INTEGER] "]" | [IDENTIFIER]
     *
     * <STATEMENT>                  =   <BLOCK> <STATEMENT> | <STATEMENT> <BLOCK> | <BLOCK>
     * <BLOCK>                      =   <FIELD-DECLARATION> | <ASSIGNMENT> | <IF> | <SWITCH> | <FOR> | <WHILE> | <DO-WHILE>
     *
     * <EXPRESSION>                 =   <EXPRESSION> [MATH-OPERATOR] <EXPRESSION> | <EXPRESSION-GROUP> | <VARIABLE> | <VALUE>
     * <EXPRESSION-GROUP>           =   "(" <EXPRESSION> ")"
     *
     *
     * <FIELD-DECLARATION>          =   <TYPE> <VARIABLE-DECLARATOR-GROUP>
     * <VARIABLE-DECLARATOR-GROUP>  =   <VARIABLE-DECLARATOR> | <VARIABLE-DECLARATOR> "," <VARIABLE-DECLARATOR-GROUP>
     * <VARIABLE-DECLARATOR>        =   <VARIABLE-DECLARATOR-ID> | <VARIABLE-DECLARATOR-ID> "=" <INITIALIZER>
     * <VARIABLE-DECLARATOR-ID>     =   [IDENTIFIER] | <VARIABLE-DECLARATOR-ID> "[" "]"
     * <INITIALIZER>                =   <EXPRESSION> | <ARRAY-INITIALIZER>
     * <ARRAY-INITIALIZER>          =   [IDENTIFIER] | "{" <ARRAY-ELEMENT-GROUP> "}"
     * <ARRAY-ELEMENT-GROUP>        =   <ARRAY-ELEMENT> "," "{" <ARRAY-ELEMENT-GROUP> "}" | "{" <ARRAY-ELEMENT-GROUP> "}" "," <ARRAY-ELEMENT> | <ARRAY-ELEMENT>
     * <ARRAY-ELEMENT>              =   <VARIABLE> | [VALUE]
     *
     * <ASSIGNMENT>                 =   <VARIABLE> "=" <EXPRESSION>
     *
     *
     * <IF>                         =   "SI" "(" <CONDITION> ")" "{" <STATEMENT> "}" <IF-OPTIONAL-STRUCTURE> <IF-END>
     * <IF-OPTIONAL-STRUCTURE>      =   <ELSE> | [EMPTY]
     * <ELSE>                       =   "SINO" "{" <STATEMENT> "}"
     *
     *
     * <SWITCH>                     =   "SEGUN" [IDENTIFIER] ":" <SWITCH-BODY> [SWITCH-END]
     * <SWITCH-BODY>                =   <SWITCH-CASE-GROUP> <SWITCH-CASE-OPTIONAL-STRUCTURE> | <SWITCH-OPTIONAL-STRUCTURE> <SWITCH-CASE-GROUP>
     * <SWITCH-CASE-GROUP>          =   <SWITCH-CASE-GROUP> <SWITCH-CASE> | <SWITCH-CASE>
     * <SWITCH-CASE>                =   "CASO" <VALUE> ":" <STATEMENT>
     * <SWITCH-OPTIONAL-STRUCTURE>  =   "HACER-SIEMPRE" ":" <STATEMENT> | [EMPTY]
     *
     *
     * <FOR>                        =   "PARA" <FOR-DEFINITION> ":" <STATEMENT> <FOR-END>
     * <FOR-DEFINITION>             =   "(" <FOR-INIT> <FOR-TERMINATION> <FOR-INCREMENT> ")"
     * <FOR-INIT>                   =   <ASSIGNMENT> | [EMPTY]
     * <FOR-TERMINATION>            =   <CONDITION>
     * <FOR-INCREMENT>              =   <SIMPLIFIED-ASSIGNMENT-FOR-INCREMENTS> | [EMPTY]
     *
     *
     * <WHILE>                      =   "MIENTRAS" <CONDITION> ":" <STATEMENT> <WHILE-END>
     *
     *
     * <DO-WHILE>                   =   "HACER" <STATEMENT> "MIENTRAS" <CONDITION> <DO-WHILE-END>
     *
     *
     * <FUNCTION>                   =   "FUNCION" [IDENTIFIER] "(" <FUNCTION-PARAMETER-GROUP> ")" ":" <STATEMENT> <FUNCTION-END>
     * <FUNCTION-PARAMETER-GROUP>   =   <FUNCTION-PARAMETER> "," <FUNCTION-PARAMETER-GROUP> | <FUNCTION-PARAMETER>
     * <PARAMETER>                  =   <TYPE> [IDENTIFIER]
     *
     * <FUNCTION-CALL>              =   [IDENTIFIER] "(" <ARGUMENT-GROUP> ")"
     * <ARGUMENT-GROUP>             =   <ARGUMENT> <ARGUMENT-GROUP> | <ARGUMENT>
     * <ARGUMENT>                   =   [IDENTIFIER] | <VALUE>
     */

    public Grammar(){

    }

}
