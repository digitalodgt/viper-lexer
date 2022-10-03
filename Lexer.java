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


public class Lexer {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 128;
	private final int YY_EOF = 129;

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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public Lexer (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public Lexer (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private Lexer () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;
	}

	private boolean yy_eof_done = false;
	private final int STRING = 2;
	private final int YYINITIAL = 0;
	private final int COMMENT = 1;
	private final int STRINGQ = 3;
	private final int yy_state_dtrans[] = {
		0,
		40,
		42,
		44
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NOT_ACCEPT,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NOT_ACCEPT,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NOT_ACCEPT,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,130,
"33:9,2,3,33:2,4,33:18,2,31,32,39,33,9,33,34,11,12,7,6,33,5,33,8,35:10,13,1," +
"16,10,33:3,38:26,33:6,36,21,37,26,27,18,37,29,17,37:2,23,37,19,22,37:2,25,2" +
"4,20,30,37,28,37:3,14,33,15,33:2,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,70,
"0,1:8,2,1:5,3,4,1:4,5,1:3,6:9,1:2,7,1,8,1,9,10,11,12,13,14,15,16,17,18,19,2" +
"0,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,6,36,37")[0];

	private int yy_nxt[][] = unpackFromString(38,40,
"1,2,3:3,4,5,6,7,8,9,10,11,12,13,14,15,16,41,67,60,61,67:2,52,68,53,62,69,67" +
":2,17,18,19,20,21,67:2,19,22,-1:50,23,-1:39,24,-1:46,67,25,43,67:11,-1:4,67" +
":4,-1:36,21,-1:21,67:14,-1:4,67:4,-1:2,36:33,-1,36:5,-1,38:31,-1,38:7,1,34:" +
"2,35,-1,34:35,-1:17,67:14,-1:4,67,63,67:2,-1,1,36:33,37,36:5,-1:17,67:3,26," +
"67:10,-1:4,67:4,-1,1,38:31,39,38:7,-1:17,67:8,27,67:5,-1:4,67:4,-1:18,67,28" +
",67:12,-1:4,67:4,-1:18,67:10,29,67:3,-1:4,67:4,-1:18,67:6,30,67:7,-1:4,67:4" +
",-1:18,67:10,31,67:3,-1:4,67:4,-1:18,67:10,32,67:3,-1:4,67:4,-1:18,67:2,33," +
"67:11,-1:4,67:4,-1:18,67:3,45,67:10,-1:4,67:4,-1:18,67:10,46,67:3,-1:4,67:4" +
",-1:18,67:13,47,-1:4,67:4,-1:18,67:5,48,67:8,-1:4,67:4,-1:18,67:7,49,67:6,-" +
"1:4,67:4,-1:18,67:7,47,67:6,-1:4,67:4,-1:18,67:6,50,67:7,-1:4,67:4,-1:18,67" +
":8,51,67:5,-1:4,67:4,-1:18,67:8,54,67:5,-1:4,67:4,-1:18,67:5,55,67:8,-1:4,6" +
"7:4,-1:18,67:6,56,67:7,-1:4,67:4,-1:18,67:6,57,67:7,-1:4,67:4,-1:18,67:3,66" +
",67:10,-1:4,67:4,-1:18,58,67:13,-1:4,67:4,-1:18,67:13,59,-1:4,67:4,-1:18,67" +
":10,64,67:3,-1:4,67:4,-1:18,67:12,65,67,-1:4,67:4,-1");

	public Token nextToken ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {
				return null;
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return new Token( Token.SEMI );   }
					case -3:
						break;
					case 3:
						{  }
					case -4:
						break;
					case 4:
						{ return new Token( Token.MINUS );	}
					case -5:
						break;
					case 5:
						{ return new Token( Token.PLUS );	}
					case -6:
						break;
					case 6:
						{ return new Token( Token.MULT );	}
					case -7:
						break;
					case 7:
						{ return new Token( Token.DIV );	}
					case -8:
						break;
					case 8:
						{ return new Token( Token.MOD );	}
					case -9:
						break;
					case 9:
						{ return new Token( Token.EQ );		}
					case -10:
						break;
					case 10:
						{ return new Token( Token.LPAREN );	}
					case -11:
						break;
					case 11:
						{ return new Token( Token.RPAREN );	}
					case -12:
						break;
					case 12:
						{ return new Token( Token.COLON );	}
					case -13:
						break;
					case 13:
						{ return new Token( Token.LBRACE );	}
					case -14:
						break;
					case 14:
						{ return new Token( Token.RBRACE );	}
					case -15:
						break;
					case 15:
						{ return new Token( Token.LT );		}
					case -16:
						break;
					case 16:
						{return new Token( Token.ID, yytext() );	}
					case -17:
						break;
					case 17:
						{ return new Token( Token.NEG );	}
					case -18:
						break;
					case 18:
						{ yybegin( STRINGQ );	}
					case -19:
						break;
					case 19:
						{
                        System.out.println("Found: " + yytext());
                        return new Token(Token.ERROR);
                    }
					case -20:
						break;
					case 20:
						{ yybegin( STRING );	}
					case -21:
						break;
					case 21:
						{ return new Token( Token.INT_CONST, yytext() );	}
					case -22:
						break;
					case 22:
						{ yybegin( COMMENT );			}
					case -23:
						break;
					case 23:
						{ return new Token( Token.COMP );	}
					case -24:
						break;
					case 24:
						{ return new Token( Token.LE );		}
					case -25:
						break;
					case 25:
						{ return new Token( Token.IF );		}
					case -26:
						break;
					case 26:
						{ return new Token( Token.INT );	}
					case -27:
						break;
					case 27:
						{ return new Token( Token.STR );	}
					case -28:
						break;
					case 28:
						{ return new Token( Token.DEF );	}
					case -29:
						break;
					case 29:
						{return new Token( Token.BOOL_CONST, yytext() );		}
					case -30:
						break;
					case 30:
						{ return new Token( Token.BOOL );	}
					case -31:
						break;
					case 31:
						{ return new Token( Token.ELSE );	}
					case -32:
						break;
					case 32:
						{ return new Token( Token.WHILE );	}
					case -33:
						break;
					case 33:
						{ return new Token( Token.RETURN );	}
					case -34:
						break;
					case 34:
						{ }
					case -35:
						break;
					case 35:
						{ yybegin( YYINITIAL );			}
					case -36:
						break;
					case 36:
						{ return new Token( Token.STR_CONST, yytext() );		}
					case -37:
						break;
					case 37:
						{ yybegin( YYINITIAL);	}
					case -38:
						break;
					case 38:
						{ return new Token( Token.STR_CONST, yytext() );		}
					case -39:
						break;
					case 39:
						{ yybegin( YYINITIAL);	}
					case -40:
						break;
					case 41:
						{return new Token( Token.ID, yytext() );	}
					case -41:
						break;
					case 43:
						{return new Token( Token.ID, yytext() );	}
					case -42:
						break;
					case 45:
						{return new Token( Token.ID, yytext() );	}
					case -43:
						break;
					case 46:
						{return new Token( Token.ID, yytext() );	}
					case -44:
						break;
					case 47:
						{return new Token( Token.ID, yytext() );	}
					case -45:
						break;
					case 48:
						{return new Token( Token.ID, yytext() );	}
					case -46:
						break;
					case 49:
						{return new Token( Token.ID, yytext() );	}
					case -47:
						break;
					case 50:
						{return new Token( Token.ID, yytext() );	}
					case -48:
						break;
					case 51:
						{return new Token( Token.ID, yytext() );	}
					case -49:
						break;
					case 52:
						{return new Token( Token.ID, yytext() );	}
					case -50:
						break;
					case 53:
						{return new Token( Token.ID, yytext() );	}
					case -51:
						break;
					case 54:
						{return new Token( Token.ID, yytext() );	}
					case -52:
						break;
					case 55:
						{return new Token( Token.ID, yytext() );	}
					case -53:
						break;
					case 56:
						{return new Token( Token.ID, yytext() );	}
					case -54:
						break;
					case 57:
						{return new Token( Token.ID, yytext() );	}
					case -55:
						break;
					case 58:
						{return new Token( Token.ID, yytext() );	}
					case -56:
						break;
					case 59:
						{return new Token( Token.ID, yytext() );	}
					case -57:
						break;
					case 60:
						{return new Token( Token.ID, yytext() );	}
					case -58:
						break;
					case 61:
						{return new Token( Token.ID, yytext() );	}
					case -59:
						break;
					case 62:
						{return new Token( Token.ID, yytext() );	}
					case -60:
						break;
					case 63:
						{return new Token( Token.ID, yytext() );	}
					case -61:
						break;
					case 64:
						{return new Token( Token.ID, yytext() );	}
					case -62:
						break;
					case 65:
						{return new Token( Token.ID, yytext() );	}
					case -63:
						break;
					case 66:
						{return new Token( Token.ID, yytext() );	}
					case -64:
						break;
					case 67:
						{return new Token( Token.ID, yytext() );	}
					case -65:
						break;
					case 68:
						{return new Token( Token.ID, yytext() );	}
					case -66:
						break;
					case 69:
						{return new Token( Token.ID, yytext() );	}
					case -67:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
