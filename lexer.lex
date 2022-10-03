/*
    Viper Lexer
    Programacion VIII - Compiladores
*/

/*
    Importar
*/

import java.io.StringReader;
import java.io.IOException;
import java.io.FileInputStream;

%%

%{
    /*
        Codigo a inyectarse
    */

    // Si necesito crear variables puedo hacerlo aqui
    // Puede servirme como ayuda al trabajar los STR_CONST
    static int num = 123;
    static String text = "prueba";

    public static void main(String[] args) throws IOException {

        FileInputStream fis = new FileInputStream(args[0]);

        byte[] rarray = new byte[fis.available()];
        fis.read(rarray);

        String input = new String(rarray);

        Lexer lexer = new Lexer(new StringReader(input));
        Token token;
        while((token = lexer.nextToken()) != null) {
            System.out.println(token);
        }
    }



    /*
        - %public es para que la clase sea publica y se pueda utilizar en otros paquetes
        - %class Lexer es para que la clase generada se llame "Lexer"
        - %function nextToken el lexer generado tendra una funcion nextToken() para obtener
            el siguiente token del input
        - %type Token es para que la clase tome en cuenta que vamos a devolver un objeto Token
    */
%}

%public
%class Lexer
%function nextToken
%type Token

%state COMMENT
%state STRING
%state STRINGQ



SEMI = ";" 
WHITE = (" "|\t|\n|\r)

MINUSCULAS = [a-z]
MAYUSCULAS = [A-Z]
ALFANUM = [a-zA-Z0-9]
DIGIT = [0-9]
BOOL = ("true"|"false")
QUOT = "\""
CHARS = [^{QUOT}]+
COMI = "'"
CHARSCOM = [^{COMI}]+

%%


<YYINITIAL>{SEMI}   	{ return new Token( Token.SEMI );   }

<YYINITIAL>{WHITE}  	{  }

<YYINITIAL>"-"		{ return new Token( Token.MINUS );	}

<YYINITIAL>"+"		{ return new Token( Token.PLUS );	}

<YYINITIAL>"*"		{ return new Token( Token.MULT );	}

<YYINITIAL>"/"		{ return new Token( Token.DIV );	}

<YYINITIAL>"%"		{ return new Token( Token.MOD );	}

<YYINITIAL>"="		{ return new Token( Token.EQ );		}

<YYINITIAL>"("		{ return new Token( Token.LPAREN );	}

<YYINITIAL>")"		{ return new Token( Token.RPAREN );	}

<YYINITIAL>":"		{ return new Token( Token.COLON );	}

<YYINITIAL>"{"		{ return new Token( Token.LBRACE );	}

<YYINITIAL>"}"		{ return new Token( Token.RBRACE );	}

<YYINITIAL>"=="		{ return new Token( Token.COMP );	}

<YYINITIAL>"<"		{ return new Token( Token.LT );		}

<YYINITIAL>"<="		{ return new Token( Token.LE );		}

<YYINITIAL>"if"		{ return new Token( Token.IF );		}

<YYINITIAL>"int"	{ return new Token( Token.INT );	}

<YYINITIAL>"bool"	{ return new Token( Token.BOOL );	}

<YYINITIAL>"str"	{ return new Token( Token.STR );	}

<YYINITIAL>"def"	{ return new Token( Token.DEF );	}

<YYINITIAL>"else"	{ return new Token( Token.ELSE );	}

<YYINITIAL>"while"	{ return new Token( Token.WHILE );	}

<YYINITIAL>"return"	{ return new Token( Token.RETURN );	}

<YYINITIAL>"!"		{ return new Token( Token.NEG );	}

<YYINITIAL>{QUOT}	{ yybegin( STRINGQ );	}

<STRINGQ>{CHARS}	{ return new Token( Token.STR_CONST, yytext() );		}

<STRINGQ>{QUOT}		{ yybegin( YYINITIAL);	}

<YYINITIAL>{COMI}	{ yybegin( STRING );	}

<STRING>{CHARSCOM}	{ return new Token( Token.STR_CONST, yytext() );		}

<STRING>{COMI}		{ yybegin( YYINITIAL);	}

<YYINITIAL>{DIGIT}{DIGIT}*	{ return new Token( Token.INT_CONST, yytext() );	}

<YYINITIAL>{BOOL}	{return new Token( Token.BOOL_CONST, yytext() );		}

<YYINITIAL>{MINUSCULAS}+{ALFANUM}*	{return new Token( Token.ID, yytext() );	}

<YYINITIAL>"#"		{ yybegin( COMMENT );			}

<COMMENT>\n		{ yybegin( YYINITIAL );			}

<COMMENT>.		{ }

<YYINITIAL>.        {
                        System.out.println("Found: " + yytext());
                        return new Token(Token.ERROR);
                    }
