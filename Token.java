/*
    Viper Lexer
    Programacion VIII - Compiladores
*/

public final class Token {
    // Tokens
    public static final int SEMI   = 0;  // ;
    public static final int PLUS   = 1;  // +
    public static final int MINUS  = 2;  // -
    public static final int MULT   = 3;  // *
    public static final int DIV    = 4;  // /
    public static final int MOD    = 5;  // %
    public static final int EQ     = 6;  // ^
    public static final int LPAREN = 7;  // (
    public static final int RPAREN = 8;  // )
    public static final int COLON  = 9;  // :
    public static final int LBRACE = 10; // {
    public static final int RBRACE = 11; // }
    public static final int COMP   = 12; // =
    public static final int LT     = 13; // <
    public static final int LE     = 14; // <=
    public static final int ID     = 15; // nombre de variable o subrutina
    public static final int STR_CONST  = 16; // string
    public static final int BOOL_CONST = 17; // true o false
    public static final int INT_CONST  = 18; // entero
    public static final int INT    = 19; // keyword int
    public static final int BOOL   = 20; // keyword bool
    public static final int STR    = 21; // keyword str
    public static final int DEF    = 22; // keyword def
    public static final int IF     = 23; // keyword def
    public static final int ELSE   = 24; // keyword else
    public static final int WHILE  = 25; // keyword while
    public static final int RETURN = 26; // keyword return
    public static final int ERROR  = 27; // error de lexer
    public static final int error  = 28; // error de parser


    // Esto puede ser bastante util
    private static final String[] tokens = {
        ";",
        "+",
        "-",
        "*",
        "/",
        "%",
        "=",
        "(",
        ")",
        ":",
        "{",
        "}",
        "==",
        "<",
        "<=",
        "id",
        "str const",
        "bool const",
        "int const",
        "int",
        "bool",
        "str",
        "def",
        "if",
        "else",
        "while",
        "return",
        "lexer error",
        "parser error"
    };

    private int id;
    private String val;

    // Constructor para tokens con lexema
    public Token(int id, String val) {
        this.id = id;
        this.val = val;
    }

    // Constructor para tokens sin lexema
    public Token(int id) {
        this.id = id;
        this.val = null;
    }

    // Representar token como string
    public String toString() {
        if(this.id == Token.STR_CONST || this.id == Token.INT_CONST || this.id == Token.BOOL_CONST) {
            if (this.val != null) {
                return Token.tokens[this.id] + " : " + this.val;
            }
        }
        return Token.tokens[this.id];
    }
}
