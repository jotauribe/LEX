package co.lex.domain.model.syntax.analysis;

import co.lex.domain.model.lexicon.analysis.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jotauribe on 10/11/16.
 */
public class Grammar {

    private List<RuleGroup> productionRules;

    /**
     * <START>                      =   <STATEMENT>
     *
     *
     * <TYPE>                       =   [RW-INTEGER] | [RW-FLOAT-NUMBER] | [RW-STRING] | [RW-BOOLEAN]
     * <VALUE>                      =   [STRING] | [INTEGER] | [FLOAT-NUMBER] | [BOOLEAN]
     * <VARIABLE>                   =   <VARIABLE> "[" [INTEGER] "]" | [IDENTIFIER]
     *
     * <STATEMENT>                  =   <BLOCK> <STATEMENT> | <BLOCK>
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
        this.productionRules = loadRules();
    }

    public Grammar(List<RuleGroup> productionRules){
        this.productionRules = productionRules;
    }

    public List<RuleGroup> rules(){
        return this.productionRules;
    }

    private List<RuleGroup> loadRules(){
        String[] names = {"ASSIGNMENT", "EXPRESSION", "VALUE", "VARIABLE", "DIMENSION",
                          "FIELD-DECLARATION", "VARIABLE-DECLARATOR-GROUP", "VARIABLE-DECLARATOR", "VARIABLE-DECLARATOR-ID", "DIMENSION-DECLARATOR",
                          "INITIALIZER", "ARRAY-INITIALIZER", "ARRAY-ELEMENT-GROUP", "ARRAY-ELEMENT", "TYPE",
                          "CONDITION", "IF", "WHILE", "STATEMENT", "BLOCK",
                          "SWITCH", "SWITCH-BODY", "SWITCH-CASE-GROUP", "SWITCH-CASE", "SWITCH-CASE-OPTIONAL-STRUCTURE",
                          "FOR", "FOR-DEFINITION", "FOR-INIT", "FOR-TERMINATION", "FOR-UPDATE",
                          "FUNCTION", "FUNCTION-PARAMETERS-GROUP", "FUNCTION-PARAMETER", "FUNCTION-BODY", "FUNCTION-CALL",
                          "FUNCTION-CALL-ARGUMENT", "WRITE", "READ", "DO-WHILE"};
        List<RuleGroup> rules = new ArrayList<>();
        for(String s : names){
            rules.add(new RuleGroup(s));
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                      RG1
        //                                           GRUPO DE REGLAS: ASSIGMENT
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                   <ASSIGNMENT> = <VARIABLE> "=" <EXPRESSION>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R11
        List<Symbol> S11 = new ArrayList<>();
        S11.add(new NonTerminalSymbol(rules.get(3))); //VARIABLE
        S11.add(new TerminalSymbol(TokenType.ASSIGNMENT_OPERATOR)); // "="
        S11.add(new NonTerminalSymbol(rules.get(1))); //EXPRESSION
        Rule R11 = new Rule(S11);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR1 = new ArrayList<>();
        LR1.add(R11);
        RuleGroup RG1 = rules.get(0);
        RG1.setRules(LR1);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                      RG2
        //                                         GRUPO DE REGLAS: EXPRESSION
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //     <EXPRESSION> = <EXPRESSION> [MATH-OPERATOR] <EXPRESSION> | <EXPRESSION-GROUP> | <VARIABLE> | <VALUE>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R21
        List<Symbol> S21 = new ArrayList<>();
        S21.add(new NonTerminalSymbol(rules.get(3))); //VARIABLE
        S21.add(new TerminalSymbol(TokenType.MATH_OPERATOR));
        S21.add(new NonTerminalSymbol(rules.get(1))); //EXPRESSION
        Rule R21 = new Rule(S21);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R22
        List<Symbol> S22 = new ArrayList<>();
        S22.add(new NonTerminalSymbol(rules.get(2))); //VALUE
        Rule R22 = new Rule(S22);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R23
        List<Symbol> S23 = new ArrayList<>();
        S23.add(new NonTerminalSymbol(rules.get(3)));
        Rule R23 = new Rule(S23);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R24
        List<Symbol> S24 = new ArrayList<>();
        S24.add(new NonTerminalSymbol(rules.get(2)));
        S24.add(new TerminalSymbol(TokenType.MATH_OPERATOR));
        S24.add(new NonTerminalSymbol(rules.get(1)));
        Rule R24 = new Rule(S24);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R25
        List<Symbol> S25 = new ArrayList<>();
        S25.add(new TerminalSymbol(TokenType.IDENTIFIER));
        Rule R25 = new Rule(S25);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR2 = new ArrayList<>();
        LR2.add(R21);
        LR2.add(R22);
        LR2.add(R23);
        LR2.add(R24);
        LR2.add(R25);
        RuleGroup RG2 = rules.get(1);
        RG2.setRules(LR2);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                      RG3
        //                                            GRUPO DE REGLAS: VALUE
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                          <VALUE> = [STRING] | [INTEGER] | [FLOAT-NUMBER] | [BOOLEAN]
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R31
        List<Symbol> S31 = new ArrayList<>();
        S31.add(new TerminalSymbol(TokenType.VALUE_INTEGER));
        Rule R31 = new Rule(S31);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R32
        List<Symbol> S32 = new ArrayList<>();
        S32.add(new TerminalSymbol(TokenType.VALUE_REAL));
        Rule R32 = new Rule(S32);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R33
        List<Symbol> S33 = new ArrayList<>();
        S33.add(new TerminalSymbol(TokenType.VALUE_STRING));
        Rule R33 = new Rule(S33);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR3 = new ArrayList<>();
        LR3.add(R31);
        LR3.add(R32);
        LR3.add(R33);
        RuleGroup RG3 = rules.get(2);
        RG3.setRules(LR3);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                      RG4
        //                                            GRUPO DE REGLAS: VARIABLE
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                      R41 : <VARIABLE> = [IDENTIFIER]
        //                      R42 : <VARIABLE> = [IDENTIFIER] <DIMENSION>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R41
        List<Symbol> S41 = new ArrayList<>();
        S41.add(new TerminalSymbol(TokenType.IDENTIFIER));
        Rule R41 = new Rule(S41);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R42
        List<Symbol> S42 = new ArrayList<>();
        S42.add(new TerminalSymbol(TokenType.IDENTIFIER));
        S42.add(new NonTerminalSymbol(rules.get(4)));
        Rule R42 = new Rule(S42);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR4 = new ArrayList<>();
        LR4.add(R41);
        LR4.add(R42);
        RuleGroup RG4 = rules.get(3);
        RG4.setRules(LR4);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                      RG5
        //                                          GRUPO DE REGLAS: DIMENSION
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                    R51 : <DIMENSION>= "[" [INTEGER] "]"
        //                              R52 : <DIMENSION>= "[" [INTEGER] "]" <DIMENSION>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R51
        List<Symbol> S51 = new ArrayList<>();
        S51.add(new TerminalSymbol(TokenType.GROUPER_BRACKET_LEFT));
        S51.add(new TerminalSymbol(TokenType.VALUE_INTEGER));
        S51.add(new TerminalSymbol(TokenType.GROUPER_BRACKET_RIGHT));
        Rule R51 = new Rule(S51);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R52
        List<Symbol> S52 = new ArrayList<>();
        S52.add(new TerminalSymbol(TokenType.GROUPER_BRACKET_LEFT));
        S52.add(new TerminalSymbol(TokenType.VALUE_INTEGER));
        S52.add(new TerminalSymbol(TokenType.GROUPER_BRACKET_RIGHT));
        S52.add(new NonTerminalSymbol(rules.get(4)));
        Rule R52 = new Rule(S52);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR5 = new ArrayList<>();
        LR5.add(R51);
        LR5.add(R52);
        RuleGroup RG5 = rules.get(4);
        RG5.setRules(LR5);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                      RG6
        //                                    GRUPO DE REGLAS: FIELD-DECLARATION
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                         <FIELD-DECLARATION> = <TYPE> <VARIABLE-DECLARATOR-GROUP>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R61
        List<Symbol> S61 = new ArrayList<>();
        S61.add(new NonTerminalSymbol(rules.get(14))); // TYPE
        //S61.add(new NonTerminalSymbol(rules.get(6)));  //VARIABLE-DECLARATOR-GROUP
        S61.add(new NonTerminalSymbol(rules.get(6)));  //VARIABLE-DECLARATOR-GROUP
        Rule R61 = new Rule(S61);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR6 = new ArrayList<>();
        LR6.add(R61);
        RuleGroup RG6 = rules.get(5);
        RG6.setRules(LR6);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                      RG7
        //                                    GRUPO DE REGLAS: VARIABLE-DECLARATOR-GROUP
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                 <VARIABLE-DECLARATOR-GROUP> = <VARIABLE-DECLARATOR>
        //                 <VARIABLE-DECLARATOR-GROUP> = <VARIABLE-DECLARATOR> "," <VARIABLE-DECLARATOR-GROUP>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R71
        List<Symbol> S71 = new ArrayList<>();
        S71.add(new NonTerminalSymbol(rules.get(7))); // VARIABLE-DECLARATOR
        Rule R71 = new Rule(S71);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R72
        List<Symbol> S72 = new ArrayList<>();
        S72.add(new NonTerminalSymbol(rules.get(7))); // VARIABLE-DECLARATOR
        S72.add(new TerminalSymbol(TokenType.COMMA)); // COMMA
        S72.add(new NonTerminalSymbol(rules.get(6)));  //VARIABLE-DECLARATOR-GROUP
        Rule R72 = new Rule(S72);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR7 = new ArrayList<>();
        LR7.add(R71);
        LR7.add(R72);
        RuleGroup RG7 = rules.get(6);
        RG7.setRules(LR7);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                      RG8
        //                                    GRUPO DE REGLAS: VARIABLE-DECLARATOR
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                 <VARIABLE-DECLARATOR> = <VARIABLE-DECLARATOR-ID>
        //                 <VARIABLE-DECLARATOR> = <VARIABLE-DECLARATOR-ID> "=" <INITIALIZER>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R81
        List<Symbol> S81 = new ArrayList<>();
        S81.add(new NonTerminalSymbol(rules.get(8))); // VARIABLE-DECLARATOR-ID
        Rule R81 = new Rule(S81);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R82
        List<Symbol> S82 = new ArrayList<>();
        S82.add(new NonTerminalSymbol(rules.get(8))); // VARIABLE-DECLARATOR-ID
        S82.add(new TerminalSymbol(TokenType.ASSIGNMENT_OPERATOR)); // "="
        S82.add(new NonTerminalSymbol(rules.get(10)));  //INITIALIZER
        Rule R82 = new Rule(S82);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR8 = new ArrayList<>();
        LR8.add(R81);
        LR8.add(R82);
        RuleGroup RG8 = rules.get(7);
        RG8.setRules(LR8);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                      RG9
        //                                    GRUPO DE REGLAS: VARIABLE-DECLARATOR-ID
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                 <VARIABLE-DECLARATOR-ID> = [IDENTIFIER]
        //                 <VARIABLE-DECLARATOR-ID> = [IDENTIFIER] <DIMENSION-DECLARATOR>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R91
        List<Symbol> S91 = new ArrayList<>();
        S91.add(new TerminalSymbol(TokenType.IDENTIFIER)); // IDENTIFIER
        Rule R91 = new Rule(S91);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R92
        List<Symbol> S92 = new ArrayList<>();
        S92.add(new TerminalSymbol(TokenType.IDENTIFIER));
        S92.add(new NonTerminalSymbol(rules.get(9))); // DIMENSION-DECLARATOR
        Rule R92 = new Rule(S92);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        List<Rule> LR9 = new ArrayList<>();
        LR9.add(R91);
        LR9.add(R92);
        RuleGroup RG9 = rules.get(8);
        RG9.setRules(LR9);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                      RG10
        //                                    GRUPO DE REGLAS: DIMENSION-DECLARATOR
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                      R101 : <DIMENSION-DECLARATOR> = "[" "]"
        //                      R102 : <DIMENSION-DECLARATOR> = "[" "]" <DIMENSION-DECLARATOR>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R101
        List<Symbol> S101 = new ArrayList<>();
        S101.add(new TerminalSymbol(TokenType.GROUPER_BRACKET_LEFT)); // "["
        S101.add(new TerminalSymbol(TokenType.GROUPER_BRACKET_RIGHT));// "]"
        Rule R101 = new Rule(S101);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R102
        List<Symbol> S102 = new ArrayList<>();
        S102.add(new TerminalSymbol(TokenType.GROUPER_BRACKET_LEFT));
        S102.add(new TerminalSymbol(TokenType.GROUPER_BRACKET_RIGHT));
        S102.add(new NonTerminalSymbol(rules.get(9))); // DIMENSION-DECLARATOR
        Rule R102 = new Rule(S102);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR10 = new ArrayList<>();
        LR10.add(R101);
        LR10.add(R102);
        RuleGroup RG10 = rules.get(9);
        RG10.setRules(LR10);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG11
        //                                      GRUPO DE REGLAS: INITIALIZER
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                              R111 : <INITIALIZER> = <EXPRESSION>
        //                              R112 : <INITIALIZER> = <ARRAY-INITIALIZER>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R111
        List<Symbol> S111 = new ArrayList<>();
        S111.add(new NonTerminalSymbol(rules.get(1))); // EXPRESSION
        Rule R111 = new Rule(S111);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R112
        List<Symbol> S112 = new ArrayList<>();
        S112.add(new NonTerminalSymbol(rules.get(11))); // ARRAY-INITIALIZER
        Rule R112 = new Rule(S112);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR11 = new ArrayList<>();
        LR11.add(R111);
        LR11.add(R112);
        RuleGroup RG11 = rules.get(10);
        RG11.setRules(LR11);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG12
        //                                      GRUPO DE REGLAS: ARRAY-INITIALIZER
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                      <ARRAY-INITIALIZER> = [IDENTIFIER]
        //                              <ARRAY-INITIALIZER> = "{" <ARRAY-ELEMENT-GROUP> "}"
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R121
        List<Symbol> S121 = new ArrayList<>();
        S121.add(new TerminalSymbol(TokenType.IDENTIFIER));
        Rule R121 = new Rule(S121);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R122
        List<Symbol> S122 = new ArrayList<>();
        S122.add(new TerminalSymbol(TokenType.GROUPER_BRACE_LEFT));
        S122.add(new NonTerminalSymbol(rules.get(12))); // ARRAY-ELEMENT-GROUP
        S122.add(new TerminalSymbol(TokenType.GROUPER_BRACE_RIGTH));
        Rule R122 = new Rule(S122);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR12 = new ArrayList<>();
        LR12.add(R121);
        LR12.add(R122);
        RuleGroup RG12 = rules.get(11);
        RG12.setRules(LR12);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG13
        //                                      GRUPO DE REGLAS: ARRAY-ELEMENT-GROUP
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                 <ARRAY-ELEMENT-GROUP> = <ARRAY-ELEMENT> "," "{" <ARRAY-ELEMENT-GROUP> "}"
        //                 <ARRAY-ELEMENT-GROUP> = "{" <ARRAY-ELEMENT-GROUP> "}" "," <ARRAY-ELEMENT>
        //                 <ARRAY-ELEMENT-GROUP> = <ARRAY-ELEMENT>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R131
        List<Symbol> S131 = new ArrayList<>();
        S131.add(new NonTerminalSymbol(rules.get(13))); // ARRAY-ELEMENT
        S131.add(new TerminalSymbol(TokenType.COMMA)); // ","
        S131.add(new TerminalSymbol(TokenType.GROUPER_BRACE_LEFT)); // "{"
        S131.add(new NonTerminalSymbol(rules.get(12)));// ARRAY-ELEMENT-GROUP
        S131.add(new TerminalSymbol(TokenType.GROUPER_BRACE_RIGTH)); // "}"
        Rule R131 = new Rule(S131);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R132
        List<Symbol> S132 = new ArrayList<>();
        S132.add(new TerminalSymbol(TokenType.GROUPER_BRACE_LEFT)); // "{"
        S132.add(new NonTerminalSymbol(rules.get(12))); // ARRAY-ELEMENT-GROUP
        S132.add(new TerminalSymbol(TokenType.GROUPER_BRACE_RIGTH)); // "}"
        S132.add(new TerminalSymbol(TokenType.COMMA)); // ","
        S132.add(new NonTerminalSymbol(rules.get(13))); // ARRAY-ELEMENT
        Rule R132 = new Rule(S132);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R133
        List<Symbol> S133 = new ArrayList<>();
        S133.add(new NonTerminalSymbol(rules.get(13))); // ARRAY-ELEMENT
        Rule R133 = new Rule(S133);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR13 = new ArrayList<>();
        LR13.add(R131);
        LR13.add(R132);
        LR13.add(R133);
        RuleGroup RG13 = rules.get(12);
        RG13.setRules(LR13);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG14
        //                                      GRUPO DE REGLAS: ARRAY-ELEMENT
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                      <ARRAY-ELEMENT> = <VARIABLE>
        //                                      <ARRAY-ELEMENT> = [VALUE]
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R141
        List<Symbol> S141 = new ArrayList<>();
        S141.add(new NonTerminalSymbol(rules.get(3))); // VARIABLE
        Rule R141 = new Rule(S141);
        //--------------------------------------------------------------------------------------------------------------
        List<Symbol> S142 = new ArrayList<>();
        S142.add(new NonTerminalSymbol(rules.get(2))); // VALUE
        Rule R142 = new Rule(S142);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR14 = new ArrayList<>();
        LR14.add(R141);
        LR14.add(R142);
        RuleGroup RG14 = rules.get(13);
        RG14.setRules(LR14);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG15
        //                                            GRUPO DE REGLAS: TYPE
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                      <TYPE> = [RW-DATATYPE-INTEGER]
        //                                      <TYPE> = [RW-DATATYPE-REAL]
        //                                      <TYPE> = [RW-DATATYPE-STRING]
        //                                      <TYPE> = [RW-DATATYPE-BOOLEAN]
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R151
        List<Symbol> S151 = new ArrayList<>();
        S151.add(new TerminalSymbol(TokenType.RW_DATATYPE_INTEGER));
        Rule R151 = new Rule(S151);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R152
        List<Symbol> S152 = new ArrayList<>();
        S152.add(new TerminalSymbol(TokenType.RW_DATATYPE_REAL));
        Rule R152 = new Rule(S152);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R153
        List<Symbol> S153 = new ArrayList<>();
        S153.add(new TerminalSymbol(TokenType.RW_DATATYPE_STRING));
        Rule R153 = new Rule(S153);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR15 = new ArrayList<>();
        LR15.add(R151);
        LR15.add(R152);
        LR15.add(R153);
        RuleGroup RG15 = rules.get(14);
        RG15.setRules(LR15);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                                     RG16
        //                                          GRUPO DE REGLAS: CONDITION
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                         R161 : <CONDITION> = [VARIABLE] [LOGICAL-OPERATOR] [VARIABLE]
        //                         R162 : <CONDITION> = [VALUE] [LOGICAL-OPERATOR] [VARIABLE]
        //                         R162 : <CONDITION> = [VARIABLE] [LOGICAL-OPERATOR] [VALUE]
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R161
        List<Symbol> S161 = new ArrayList<>();
        S161.add(new NonTerminalSymbol(rules.get(1))); //EXPRESSION
        S161.add(new TerminalSymbol(TokenType.LOGICAL_OPERATOR));
        S161.add(new NonTerminalSymbol(rules.get(1))); //EXPRESSION
        Rule R161 = new Rule(S161);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R162
        List<Symbol> S162 = new ArrayList<>();
        S162.add(new NonTerminalSymbol(rules.get(3))); //VARIABLE
        S162.add(new TerminalSymbol(TokenType.LOGICAL_OPERATOR));
        S162.add(new NonTerminalSymbol(rules.get(2))); //VALUE
        Rule R162 = new Rule(S162);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR16 = new ArrayList<>();
        LR16.add(R161);
        //LR16.add(R162);
        RuleGroup RG16 = rules.get(15);
        RG16.setRules(LR16);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG17
        //                                              GRUPO DE REGLAS: IF
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //        R171 : <IF> =   "SI" "(" <CONDITION> ")" "{" <STATEMENT> "}" <IF-OPTIONAL-STRUCTURE> <IF-END>
        //              <IF-OPTIONAL-STRUCTURE> = <ELSE> | [EMPTY]
        //              <ELSE> = "SINO" "{" <STATEMENT> "}"
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R171
        List<Symbol> S171 = new ArrayList<>();
        S171.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_CONDITIONAL_IF_START));
        S171.add(new TerminalSymbol(TokenType.GROUPER_PARENTHESES_LEFT));
        S171.add(new NonTerminalSymbol(rules.get(15))); //CONDITION
        S171.add(new TerminalSymbol(TokenType.GROUPER_PARENTHESES_RIGHT));
        S171.add(new TerminalSymbol(TokenType.COLON));
        S171.add(new NonTerminalSymbol(rules.get(18))); //STATEMENT
        S171.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_CONDITIONAL_IF_END));
        Rule R171 = new Rule(S171);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R171
        List<Symbol> S172 = new ArrayList<>();
        S172.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_CONDITIONAL_IF_START));
        S172.add(new TerminalSymbol(TokenType.GROUPER_PARENTHESES_LEFT));
        S172.add(new NonTerminalSymbol(rules.get(15))); //CONDITION
        S172.add(new TerminalSymbol(TokenType.GROUPER_PARENTHESES_RIGHT));
        S172.add(new TerminalSymbol(TokenType.COLON));
        S172.add(new NonTerminalSymbol(rules.get(0))); //STATEMENT TODO CORREGIR
        S172.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_CONDITIONAL_ELSE));
        S172.add(new NonTerminalSymbol(rules.get(18))); //STATEMENT TODO CORREGIR
        S172.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_CONDITIONAL_IF_END));
        Rule R172 = new Rule(S172);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR17 = new ArrayList<>();
        LR17.add(R171);
        LR17.add(R172);
        RuleGroup RG17 = rules.get(16);
        RG17.setRules(LR17);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG18
        //                                            GRUPO DE REGLAS: WHILE
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                      R181 : <WHILE> = "MIENTRAS" <CONDITION> ":" <STATEMENT> <WHILE-END>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R181
        List<Symbol> S181 = new ArrayList<>();
        S181.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_ITERATIVE_WHILE_START));
        S181.add(new NonTerminalSymbol(rules.get(15))); //CONDITION
        S181.add(new TerminalSymbol(TokenType.COLON)); //
        S181.add(new NonTerminalSymbol(rules.get(18))); //STATEMENT
        S181.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_ITERATIVE_WHILE_END));
        Rule R181 = new Rule(S181);
        //--------------------------------------------------------------------------------------------------------------
        //TODO                                                  R182
        List<Symbol> S182 = new ArrayList<>();
        S182.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_ITERATIVE_DO_WHILE_START));
        S182.add(new TerminalSymbol(TokenType.COLON)); //
        S182.add(new NonTerminalSymbol(rules.get(18))); //STATEMENT
        S182.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_ITERATIVE_DO_WHILE_CONDITION));
        S182.add(new NonTerminalSymbol(rules.get(15))); //CONDITION
        Rule R182 = new Rule(S182);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR18 = new ArrayList<>();
        LR18.add(R181);
        LR18.add(R181);
        RuleGroup RG18 = rules.get(17);
        RG18.setRules(LR18);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG19
        //                                          GRUPO DE REGLAS: STATEMENT
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                 R191: <STATEMENT> = <BLOCK> <STATEMENT>
        //TODO                             R192: <STATEMENT> = <STATEMENT> <BLOCK>
        //                                 R193: <STATEMENT> = <BLOCK>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R191
        List<Symbol> S191 = new ArrayList<>();
        S191.add(new NonTerminalSymbol(rules.get(19))); //BLOCK
        S191.add(new NonTerminalSymbol(rules.get(18))); //STATEMENT
        Rule R191 = new Rule(S191);
        //--------------------------------------------------------------------------------------------------------------
        //TODO REVISAR VIABILIDAD                              R192
        List<Symbol> S192 = new ArrayList<>();
        S192.add(new NonTerminalSymbol(rules.get(18))); //STATEMENT
        S192.add(new NonTerminalSymbol(rules.get(19))); //BLOC
        Rule R192 = new Rule(S192);
        //--------------------------------------------------------------------------------------------------------------
        //                                                      R193
        List<Symbol> S193 = new ArrayList<>();
        S193.add(new NonTerminalSymbol(rules.get(19))); //BLOC
        Rule R193 = new Rule(S193);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR19 = new ArrayList<>();
        LR19.add(R191);
        //LR19.add(R192); //TODO CORREGIR
        LR19.add(R193);
        RuleGroup RG19 = rules.get(18);
        RG19.setRules(LR19);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG20
        //                                             GRUPO DE REGLAS: BLOCK
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                      R201: <BLOCK> = <FIELD-DECLARATION>
        //                                      R202: <BLOCK> = <ASSIGNMENT>
        //                                      R203: <BLOCK> = <IF>
        //                                      R204: <BLOCK> = <SWITCH>
        //                                      R205: <BLOCK> = <FOR>
        //                                      R206: <BLOCK> = <WHILE>
        //TODO                                  R207: <BLOCK> = <DO-WHILE>
        //                                      R208: <BLOCK> = <WRITE>
        //                                      R209: <BLOCK> = <READ>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R201
        List<Symbol> S201 = new ArrayList<>();
        S201.add(new NonTerminalSymbol(rules.get(5))); //FIELD-DECLARATION
        Rule R201 = new Rule(S201);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R202
        List<Symbol> S202 = new ArrayList<>();
        S202.add(new NonTerminalSymbol(rules.get(0))); //ASSIGNMENT
        Rule R202 = new Rule(S202);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R203
        List<Symbol> S203 = new ArrayList<>();
        S203.add(new NonTerminalSymbol(rules.get(16))); //IF
        Rule R203 = new Rule(S203);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R204
        List<Symbol> S204 = new ArrayList<>();
        S204.add(new NonTerminalSymbol(rules.get(20))); //SWITCH
        Rule R204 = new Rule(S204);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R205
        List<Symbol> S205 = new ArrayList<>();
        S205.add(new NonTerminalSymbol(rules.get(25))); //FOR
        Rule R205 = new Rule(S205);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R206
        List<Symbol> S206 = new ArrayList<>();
        S206.add(new NonTerminalSymbol(rules.get(17))); //WHILE
        Rule R206 = new Rule(S206);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R207
        List<Symbol> S207 = new ArrayList<>();
        S207.add(new NonTerminalSymbol(rules.get(30))); //DO-WHILE TODO ACTUALIZAR ESTO
        Rule R207 = new Rule(S207);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R208
        List<Symbol> S208 = new ArrayList<>();
        S208.add(new NonTerminalSymbol(rules.get(36))); //WRITE
        Rule R208 = new Rule(S208);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R209
        List<Symbol> S209 = new ArrayList<>();
        S209.add(new NonTerminalSymbol(rules.get(37))); //READ
        Rule R209 = new Rule(S209);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR20 = new ArrayList<>();
        LR20.add(R201);
        LR20.add(R202);
        LR20.add(R203);
        LR20.add(R204);
        LR20.add(R205);
        LR20.add(R206);
        LR20.add(R207);
        LR20.add(R208);
        LR20.add(R209);
        RuleGroup RG20 = rules.get(19);
        RG20.setRules(LR20);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG21
        //                                           GRUPO DE REGLAS: SWITCH
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                  R211: <SWITCH> = "SEGUN" [IDENTIFIER] ":" <SWITCH-BODY> [SWITCH-END]
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R211
        List<Symbol> S211 = new ArrayList<>();
        S211.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_CONDITIONAL_SWITCH_START));
        S211.add(new TerminalSymbol(TokenType.IDENTIFIER));
        S211.add(new TerminalSymbol(TokenType.COLON));
        S211.add(new NonTerminalSymbol(rules.get(21))); //SWITCH-BODY
        S211.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_CONDITIONAL_SWITCH_END));
        Rule R211 = new Rule(S211);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR21 = new ArrayList<>();
        LR21.add(R211);
        RuleGroup RG21 = rules.get(20);
        RG21.setRules(LR21);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG22
        //                                         GRUPO DE REGLAS: SWITCH-BODY
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                  R220: <SWITCH-BODY> = <SWITCH-CASE-GROUP>
        //                  R221: <SWITCH-BODY> = <SWITCH-CASE-GROUP> <SWITCH-OPTIONAL-STRUCTURE>
        //                  R222: <SWITCH-BODY> = <SWITCH-OPTIONAL-STRUCTURE> <SWITCH-CASE-GROUP>
        //                  R222: <SWITCH-BODY> = <SWITCH-CASE-GROUP> <SWITCH-OPTIONAL-STRUCTURE> <SWITCH-CASE-GROUP>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R220
        List<Symbol> S220 = new ArrayList<>();
        S220.add(new NonTerminalSymbol(rules.get(22))); //SWITCH-CASE-GROUP
        Rule R220 = new Rule(S220);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R221
        List<Symbol> S221 = new ArrayList<>();
        S221.add(new NonTerminalSymbol(rules.get(22))); //SWITCH-CASE-GROUP
        S221.add(new NonTerminalSymbol(rules.get(24))); //SWITCH-OPTIONAL-STRUCTURE
        Rule R221 = new Rule(S221);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R222
        List<Symbol> S222 = new ArrayList<>();
        S222.add(new NonTerminalSymbol(rules.get(24))); ///SWITCH-OPTIONAL-STRUCTURE
        S222.add(new NonTerminalSymbol(rules.get(22))); //SWITCH-CASE-GROUP
        Rule R222 = new Rule(S222);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R223
        List<Symbol> S223 = new ArrayList<>();
        S223.add(new NonTerminalSymbol(rules.get(22))); //SWITCH-CASE-GROUP
        S223.add(new NonTerminalSymbol(rules.get(24))); //SWITCH-OPTIONAL-STRUCTURE
        S223.add(new NonTerminalSymbol(rules.get(22))); //SWITCH-CASE-GROUP
        Rule R223 = new Rule(S223);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR22 = new ArrayList<>();
        LR22.add(R220);
        LR22.add(R221);
        LR22.add(R222);
        LR22.add(R223);
        RuleGroup RG22 = rules.get(21);
        RG22.setRules(LR22);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG23
        //                                     GRUPO DE REGLAS: SWITCH-CASE-GROUP
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                 R231: <SWITCH-CASE-GROUP> = <SWITCH-CASE>
        //                                 R232: <SWITCH-CASE-GROUP> = <SWITCH-CASE> <SWITCH-CASE-GROUP>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R231
        List<Symbol> S231 = new ArrayList<>();
        S231.add(new NonTerminalSymbol(rules.get(23))); //SWITCH-CASE
        Rule R231 = new Rule(S231);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R232
        List<Symbol> S232 = new ArrayList<>();
        S232.add(new NonTerminalSymbol(rules.get(23))); //SWITCH-CASE
        S232.add(new NonTerminalSymbol(rules.get(22))); //SWITCH-CASE-GROUP
        Rule R232 = new Rule(S232);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR23 = new ArrayList<>();
        LR23.add(R231);
        LR23.add(R232);
        RuleGroup RG23 = rules.get(22);
        RG23.setRules(LR23);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG24
        //                                         GRUPO DE REGLAS: SWITCH-CASE
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                              <SWITCH-CASE> = "CASO" <VALUE> ":" <STATEMENT>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R241
        List<Symbol> S241 = new ArrayList<>();
        S241.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_CONDITIONAL_SWITCH_CASE));
        S241.add(new NonTerminalSymbol(rules.get(2))); //VALUE
        S241.add(new TerminalSymbol(TokenType.COLON));
        S241.add(new NonTerminalSymbol(rules.get(18)));//STATEMENT
        Rule R241 = new Rule(S241);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR24 = new ArrayList<>();
        LR24.add(R241);
        RuleGroup RG24 = rules.get(23);
        RG24.setRules(LR24);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG25
        //                                  GRUPO DE REGLAS: SWITCH-OPTIONAL-STRUCTURE
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                         <SWITCH-OPTIONAL-STRUCTURE> = "HACER-SIEMPRE" ":" <STATEMENT>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R251
        List<Symbol> S251 = new ArrayList<>();
        S251.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_CONDITIONAL_SWITCH_DEFAULT));
        S251.add(new TerminalSymbol(TokenType.COLON));
        S251.add(new NonTerminalSymbol(rules.get(18)));//STATEMENT
        Rule R251 = new Rule(S251);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR25 = new ArrayList<>();
        LR25.add(R251);
        RuleGroup RG25 = rules.get(24);
        RG25.setRules(LR25);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG26
        //                                             GRUPO DE REGLAS: FOR
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                         <FOR> = "PARA" <FOR-DEFINITION> ":" <STATEMENT> <FOR-END>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R261
        List<Symbol> S261 = new ArrayList<>();
        S261.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_ITERATIVE_FOR_START));
        S261.add(new NonTerminalSymbol(rules.get(26)));//FOR-DEFINITION
        S261.add(new TerminalSymbol(TokenType.COLON));
        S261.add(new NonTerminalSymbol(rules.get(18)));//STATEMENT
        S261.add(new TerminalSymbol(TokenType.RW_CONTROLSTRUCTURE_ITERATIVE_FOR_END));
        Rule R261 = new Rule(S261);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR26 = new ArrayList<>();
        LR26.add(R261);
        RuleGroup RG26 = rules.get(25);
        RG26.setRules(LR26);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG27
        //                                        GRUPO DE REGLAS: FOR-DEFINITION
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //              <FOR-DEFINITION> =   "(" <FOR-INIT> <FOR-TERMINATION> <FOR-UPDATE> ")"
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R271
        List<Symbol> S271 = new ArrayList<>();
        S271.add(new TerminalSymbol(TokenType.GROUPER_PARENTHESES_LEFT));
        S271.add(new NonTerminalSymbol(rules.get(27)));//INIT
        S271.add(new NonTerminalSymbol(rules.get(28)));//TERMINATION
        S271.add(new NonTerminalSymbol(rules.get(29)));//UPDATE
        S271.add(new TerminalSymbol(TokenType.GROUPER_PARENTHESES_RIGHT));
        Rule R271 = new Rule(S271);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR27 = new ArrayList<>();
        LR27.add(R271);
        RuleGroup RG27 = rules.get(26);
        RG27.setRules(LR27);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG28
        //                                           GRUPO DE REGLAS: FOR-INIT
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                     <FOR-INIT> = <ASSIGNMENT> | [EMPTY]
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R281
        List<Symbol> S281 = new ArrayList<>();
        S281.add(new NonTerminalSymbol(rules.get(0)));//ASSIGNMENT
        Rule R281 = new Rule(S281);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R281
        List<Symbol> S282 = new ArrayList<>();
        S282.add(new NonTerminalSymbol(rules.get(14)));//TYPE
        S282.add(new NonTerminalSymbol(rules.get(0)));//ASSIGNMENT
        Rule R282 = new Rule(S282);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR28 = new ArrayList<>();
        LR28.add(R281);
        LR28.add(R282);
        RuleGroup RG28 = rules.get(27);
        RG28.setRules(LR28);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG29
        //                                      GRUPO DE REGLAS: FOR-TERMINATION
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                      <FOR-TERMINATION> = <CONDITION>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R291
        List<Symbol> S291 = new ArrayList<>();
        S291.add(new NonTerminalSymbol(rules.get(15)));//CONDITION
        Rule R291 = new Rule(S291);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR29 = new ArrayList<>();
        LR29.add(R291);
        RuleGroup RG29 = rules.get(28);
        RG29.setRules(LR29);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG30
        //                                          GRUPO DE REGLAS: FOR-UPDATE
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                      <FOR-UPDATE> = <SIMPLIFIED-ASSIGNMENT-FOR-INCREMENTS> | [EMPTY]
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R301
        List<Symbol> S301 = new ArrayList<>();
        S301.add(new NonTerminalSymbol(rules.get(0)));//ASSIGNMENT
        Rule R301 = new Rule(S301);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR30 = new ArrayList<>();
        LR30.add(R301);
        RuleGroup RG30 = rules.get(29);
        RG30.setRules(LR30);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG31
        //                                          GRUPO DE REGLAS: FUNCTION
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //          R311:  <FUNCTION> = "FUNCION" <TYPE> [IDENTIFIER] "(" ")" <FUNCTION-BODY> [FUNCTION-END]
        //R312:<FUNCTION> = "FUNCION" <TYPE> [IDENTIFIER] "(" <FUNCTION-PARAMETER-GROUP>")"<FUNCTION-BODY>[FUNCTION-END]
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R311
        List<Symbol> S311 = new ArrayList<>();
        S311.add(new TerminalSymbol(TokenType.RW_FUNCTION_START));
        S311.add(new NonTerminalSymbol(rules.get(14)));  //TYPE
        S311.add(new TerminalSymbol(TokenType.IDENTIFIER));
        S311.add(new TerminalSymbol(TokenType.GROUPER_PARENTHESES_LEFT));
        S311.add(new TerminalSymbol(TokenType.GROUPER_PARENTHESES_RIGHT));
        S311.add(new NonTerminalSymbol(rules.get(18)));  //STATEMENT
        S311.add(new TerminalSymbol(TokenType.RW_FUNCTION_END));
        Rule R311 = new Rule(S311);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R312
        List<Symbol> S312 = new ArrayList<>();
        S312.add(new TerminalSymbol(TokenType.RW_FUNCTION_START));
        S312.add(new NonTerminalSymbol(rules.get(14)));  //TYPE
        S312.add(new TerminalSymbol(TokenType.IDENTIFIER));
        S312.add(new TerminalSymbol(TokenType.GROUPER_PARENTHESES_LEFT));
        S312.add(new NonTerminalSymbol(rules.get(31)));  //FUNCTION-PARAMETER-GROUP
        S312.add(new TerminalSymbol(TokenType.GROUPER_PARENTHESES_RIGHT));
        S312.add(new NonTerminalSymbol(rules.get(18)));  //STATEMENT
        S312.add(new TerminalSymbol(TokenType.RW_FUNCTION_END));
        Rule R312 = new Rule(S312);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR31 = new ArrayList<>();
        LR31.add(R311);
        LR31.add(R312);
        RuleGroup RG31 = rules.get(30);
        RG31.setRules(LR31);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG32
        //                                  GRUPO DE REGLAS: FUNCTION-PARAMETER-GROUP
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                          R321: <FUNCTION-PARAMETER-GROUP> = <FUNCTION-PARAMETER>
        //            R322: <FUNCTION-PARAMETER-GROUP> = <FUNCTION-PARAMETER> "," <FUNCTION-PARAMETER-GROUP>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R321
        List<Symbol> S321 = new ArrayList<>();
        S321.add(new NonTerminalSymbol(rules.get(32)));  //FUNCTION-PARAMETER
        Rule R321 = new Rule(S321);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R322
        List<Symbol> S322 = new ArrayList<>();
        S322.add(new NonTerminalSymbol(rules.get(32)));  //FUNCTION-PARAMETER
        S322.add(new TerminalSymbol(TokenType.COMMA));
        S322.add(new NonTerminalSymbol(rules.get(31)));  //FUNCTION-PARAMETER-GROUP
        Rule R322 = new Rule(S322);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR32 = new ArrayList<>();
        LR32.add(R321);
        LR32.add(R322);
        RuleGroup RG32 = rules.get(31);
        RG32.setRules(LR32);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG33
        //                                      GRUPO DE REGLAS: FUNCTION-PARAMETER
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                <FOR-UPDATE> = <TYPE> <VARIABLE-DECLARATOR-ID>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R331
        List<Symbol> S331 = new ArrayList<>();
        S331.add(new NonTerminalSymbol(rules.get(14)));  //TYPE
        S331.add(new NonTerminalSymbol(rules.get(8)));  //VARIABLE-DECLARATOR-ID
        Rule R331 = new Rule(S331);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR33 = new ArrayList<>();
        LR33.add(R331);
        RuleGroup RG33 = rules.get(32);
        RG33.setRules(LR33);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG34
        //                                       GRUPO DE REGLAS: FUNCTION-RETURN
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                                  <FUNCTION-RETURN> = "RETORNAR" <EXPRESSION>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R341
        List<Symbol> S341 = new ArrayList<>();
        S341.add(new TerminalSymbol(TokenType.RW_FUNCTION_RETURN));
        S341.add(new NonTerminalSymbol(rules.get(1)));  //EXPRESSION
        Rule R341 = new Rule(S341);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR34 = new ArrayList<>();
        LR34.add(R341);
        RuleGroup RG34 = rules.get(33);
        RG34.setRules(LR34);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG35
        //                                       GRUPO DE REGLAS: FUNCTION-CALL
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //                    <FUNCTION-CALL> = [IDENTIFIER] "(" <FUNCTION-CALL-ARGUMENT-GROUP> ")"
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R351
        List<Symbol> S351 = new ArrayList<>();
        S351.add(new TerminalSymbol(TokenType.IDENTIFIER));
        S351.add(new TerminalSymbol(TokenType.GROUPER_PARENTHESES_LEFT));
        S351.add(new NonTerminalSymbol(rules.get(35)));  //FUNCTION-CALL-ARGUMENT-GROUP
        S351.add(new TerminalSymbol(TokenType.GROUPER_PARENTHESES_RIGHT));
        Rule R351 = new Rule(S351);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR35 = new ArrayList<>();
        LR35.add(R351);
        RuleGroup RG35 = rules.get(34);
        RG35.setRules(LR35);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG36
        //                                 GRUPO DE REGLAS: FUNCTION-CALL-ARGUMENT-GROUP
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //        R361: <FUNCTION-CALL-ARGUMENT-GROUP> = <EXPRESSION>
        //        R362: <FUNCTION-CALL-ARGUMENT-GROUP> = <EXPRESSION> "," <FUNCTION-CALL-ARGUMENT-GROUP>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R361
        List<Symbol> S361 = new ArrayList<>();
        S361.add(new NonTerminalSymbol(rules.get(1)));  //EXPRESSION
        Rule R361 = new Rule(S361);
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R362
        List<Symbol> S362 = new ArrayList<>();
        S362.add(new NonTerminalSymbol(rules.get(1)));  //EXPRESSION
        S362.add(new TerminalSymbol(TokenType.COMMA));
        S362.add(new NonTerminalSymbol(rules.get(35)));  //FUNCTION-CALL-ARGUMENT-GROUP
        Rule R362 = new Rule(S362);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR36 = new ArrayList<>();
        LR36.add(R361);
        LR36.add(R362);
        RuleGroup RG36 = rules.get(35);
        RG36.setRules(LR36);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG37
        //                                            GRUPO DE REGLAS: WRITE
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //
        //                                       <WRITE>  = "ESCRIBIR" <EXPRESSION>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R371
        List<Symbol> S371 = new ArrayList<>();
        S371.add(new TerminalSymbol(TokenType.RW_WRITE));
        S371.add(new NonTerminalSymbol(rules.get(1)));  //EXPRESSION
        Rule R371 = new Rule(S371);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR37 = new ArrayList<>();
        LR37.add(R371);
        RuleGroup RG37 = rules.get(36);
        RG37.setRules(LR37);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                                     RG38
        //                                            GRUPO DE REGLAS: READ
        //
        //--------------------------------------------------------------------------------------------------------------
        //
        //
        //                                      R381: <READ> = "LEER" <VARIABLE>
        //
        //--------------------------------------------------------------------------------------------------------------
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //--------------------------------------------------------------------------------------------------------------
        //                                                    R381
        List<Symbol> S381 = new ArrayList<>();
        S381.add(new TerminalSymbol(TokenType.RW_READ));
        S381.add(new NonTerminalSymbol(rules.get(3)));  //VARIABLE
        Rule R381 = new Rule(S381);
        //--------------------------------------------------------------------------------------------------------------
        //CREANDO GRUPO DE REGLAS
        //
        List<Rule> LR38 = new ArrayList<>();
        LR38.add(R381);
        RuleGroup RG38 = rules.get(37);
        RG38.setRules(LR38);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //                                        CREACION DE LISTA DE REGLAS
        //
        //--------------------------------------------------------------------------------------------------------------
        List<RuleGroup> RULEGROUPS = new ArrayList<>();
        RULEGROUPS.add(RG1); //ASSIGNMENT
        RULEGROUPS.add(RG2); //
        RULEGROUPS.add(RG3);
        RULEGROUPS.add(RG4);
        RULEGROUPS.add(RG5);
        RULEGROUPS.add(RG6);
        RULEGROUPS.add(RG7);
        RULEGROUPS.add(RG8);
        RULEGROUPS.add(RG9);
        RULEGROUPS.add(RG10);
        RULEGROUPS.add(RG11);
        RULEGROUPS.add(RG12);
        RULEGROUPS.add(RG13);
        RULEGROUPS.add(RG14);
        RULEGROUPS.add(RG15);
        RULEGROUPS.add(RG16);
        RULEGROUPS.add(RG17);
        RULEGROUPS.add(RG18);
        RULEGROUPS.add(RG19);
        RULEGROUPS.add(RG20);
        RULEGROUPS.add(RG21);
        RULEGROUPS.add(RG22);
        RULEGROUPS.add(RG23);
        RULEGROUPS.add(RG24);
        RULEGROUPS.add(RG25);
        RULEGROUPS.add(RG26);
        RULEGROUPS.add(RG27);
        RULEGROUPS.add(RG28);
        RULEGROUPS.add(RG29);
        RULEGROUPS.add(RG30);
        RULEGROUPS.add(RG31);
        RULEGROUPS.add(RG32);
        RULEGROUPS.add(RG33);
        RULEGROUPS.add(RG34);
        RULEGROUPS.add(RG35);
        RULEGROUPS.add(RG36);
        RULEGROUPS.add(RG37);
        RULEGROUPS.add(RG38);
        //
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        return RULEGROUPS;
    }

}