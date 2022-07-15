package checker;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import parser.PASLexer;
import parser.PASParser;



public class Main {

	/*
	 *  Programa principal para funcionamento de compilador.
	 *  
	 *  Esta função espera um único argumento: o nome do
	 *  programa a ser compilado. Em um código real certamente
	 *  deveria haver alguma verificação de erro mas ela foi
	 *  omitida aqui para simplificar o código e facilitar a leitura.
	 */
	public static void main(String[] args) throws IOException {
		CharStream input = CharStreams.fromFileName(args[0]);
		
		// Cria um lexer que consome a entrada do CharStream.
		PASLexer lexer = new PASLexer(input);
		
		// Cria um buffer de tokens vindos do lexer.
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		
		// Cria um parser que consome os tokens do buffer.
		PASParser parser = new PASParser(tokens);
		
		// Começa o processo de parsing na regra 'program'.
		ParseTree tree = parser.program(); 
		
		if (parser.getNumberOfSyntaxErrors() != 0) {
			// Houve algum erro sintático. Termina a compilação aqui.
			System.out.println("Erro sintático");
			return;
		}

		/// Cria o analisador semântico e visita a ParseTree para
		// fazer a análise.
		SemanticChecker checker = new SemanticChecker();
		checker.visit(tree);
		
		
		// Saída final.
		System.out.println("PARSE SUCCESSFUL!");

		boolean show = false;

		if (show) {
			checker.showTables();
		}
	}

}