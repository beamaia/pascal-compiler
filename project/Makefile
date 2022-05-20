# Modifique as variaveis conforme o seu setup.

JAVA=java
JAVAC=javac

# Eu uso ROOT como o diretório raiz para os meus labs.
ROOT=/project

ANTLR_PATH=$(ROOT)/antlr-4.10.1-complete.jar
CLASS_PATH_OPTION=-cp .:$(ANTLR_PATH)

# Comandos como descritos na página do ANTLR.
ANTLR4=$(JAVA) -jar $(ANTLR_PATH)
GRUN=$(JAVA) $(CLASS_PATH_OPTION) org.antlr.v4.gui.TestRig 

# Diretório para aonde vão os arquivos gerados.
GEN_PATH=lexer

# Diretório para os casos de teste
IN=/in

all: antlr javac
	@echo "Done."

antlr: PASLexer.g
	$(ANTLR4) -o $(GEN_PATH) PASLexer.g

javac:
	$(JAVAC) $(CLASS_PATH_OPTION) $(GEN_PATH)/*.java

# Veja a explicação no README
run:
	cd $(GEN_PATH) && $(GRUN) PASLexer program $(FILE)

runall:
	-for FILE in $(IN)/*.pas; do \
	 	cd $(GEN_PATH) && \
	 	echo -e "\nRunning $${FILE}" && \
	 	$(GRUN) PASLexer program  $${FILE} && \
	 	cd .. ; \
	done;

clean:
	@rm -rf $(GEN_PATH)
