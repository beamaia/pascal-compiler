# Modifique as variaveis conforme o seu setup.

JAVA=java
JAVAC=javac

# Eu uso ROOT como o diretório raiz para os meus labs.
YEAR=$(shell pwd | grep -o '20..-.')
ROOT=/home/zambon/Teaching/$(YEAR)/CC/labs

ANTLR_PATH=$(ROOT)/tools/antlr-4.9.2-complete.jar
CLASS_PATH_OPTION=-cp .:$(ANTLR_PATH)

# Comandos como descritos na página do ANTLR.
ANTLR4=$(JAVA) -jar $(ANTLR_PATH)
GRUN=$(JAVA) $(CLASS_PATH_OPTION) org.antlr.v4.gui.TestRig

# Diretório para aonde vão os arquivos gerados pelo ANTLR.
GEN_PATH=parser

# Diretório para os arquivos .class
BIN_PATH=bin

# Diretório para os casos de teste
DATA=$(ROOT)/tests
IN=$(DATA)/in

all: antlr javac
	@echo "Done."

# Como dito no README, o ANTLR provê duas funcionalidades para se caminhar
# na parse tree: um listener e um visitor. Por padrão, o ANTLR gera um listener
# mas não gera um visitor. Como a gente quer o contrário, temos de passar
# as opções -no-listener -visitor.
antlr: EZLexer.g EZParser.g
	$(ANTLR4) -no-listener -visitor -o $(GEN_PATH) EZLexer.g EZParser.g

# Compila todos os subdiretórios e joga todos os .class em BIN_PATH pra organizar.
javac:
	rm -rf $(BIN_PATH)
	mkdir $(BIN_PATH)
	$(JAVAC) $(CLASS_PATH_OPTION) -d $(BIN_PATH) */*.java Main.java

run:
	$(JAVA) $(CLASS_PATH_OPTION):$(BIN_PATH) Main $(FILE)

tm: tm.c tables.c types.c
	gcc -Wall -Wconversion -o tmsim tm.c tables.c types.c

clean:
	@rm -rf $(GEN_PATH) $(BIN_PATH) out07 tmsim
