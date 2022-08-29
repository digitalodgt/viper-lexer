#
#   Viper Lexer
#   Programacion VIII - Compiladores
#

JFLAGS=
SUBDIR=$(PWD)/tree
CLASSPATH=$(PWD)/lib/jlex.jar

lexer: lexer.lex
	java -classpath $(CLASSPATH) JLex.Main lexer.lex
	mv lexer.lex.java Lexer.java
	javac Lexer.java
	$(RM) lexer
	echo '#!/bin/sh' >> lexer
	echo 'java Lexer $$*' >> lexer
	chmod a+x lexer

.PHONY: clean

clean:
	$(RM) lexer
	$(RM) Lexer.java
	$(RM) -r *.class
