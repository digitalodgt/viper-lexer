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
    static int text = "prueba";

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



SEMI = ";"
WHITE = (" "|\t|\n|\r)

MINUSCULAS = [a-z]
MAYUSCULAS = [A-Z]
ALFANUM = [a-zA-Z0-9]
DIGIT = [0-9]



%%




<YYINITIAL>{SEMI}   { return new Token( Token.SEMI );   }

<YYINITIAL>{WHITE}  {  }

<YYINITIAL>.        {
                        System.out.println("Found: " + yytext());
                        return new Token(Token.ERROR);
                    }
