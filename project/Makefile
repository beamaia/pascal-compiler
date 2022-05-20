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

antlr: EZLexer.g
	$(ANTLR4) -o $(GEN_PATH) EZLexer.g

javac:
	$(JAVAC) $(CLASS_PATH_OPTION) $(GEN_PATH)/*.java

# Veja a explicação no README
run:
	cd $(GEN_PATH) && $(GRUN) EZLexer program $(FILE) -gui

runall:
	-for FILE in $(IN)/*.ezl; do \
	 	cd $(GEN_PATH) && \
	 	echo -e "\nRunning $${FILE}" && \
	 	$(GRUN) EZLexer program $${FILE} -gui && \
	 	cd .. ; \
	done;

clean:
	@rm -rf $(GEN_PATH)
