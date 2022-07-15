# 🚩 Checkpoint 2 - Representação intermediária

Representação intermediária de Turbo Pascal feito por Beatriz Maia, Renan Moreira Gomes e Victor Aguiar Marques Aguiar.

## 📝 Descrição do Desenvolvimento

Para verificar alguns erros semanticos que poderiam ocorrer, foram utilizados:
- [Pascal opertors](http://ctp.mkprog.com/en/pascal/menu/operators/)
- [Compilador de pascal online](https://www.onlinegdb.com/online_pascal_compiler)

As classes foram divididas nos seguintes pacotes:
- AST: em que encontra a AST e os tipos de nós possíveis (classe NodeKind)
- checker: em que encontra a main, o SemanticChecker e a classe de escopo
- tables: em que encontra principais classes de tabelas de variáveis, funçoes e string, além do tipo de entrada de cada tabela
- types: em que encontra a classe Type de tipos e Conv para conversão de tipos.

### ⚙️ Simplificações

Com base nas recomendações do professor, as seguintes funcionalidades não foram implementadas:

- Não estamos mais reconhecendo o switch case.
- Não estamos reconhecendo uses (imports de bibliotecas)
- Não estamos reconhecendo const (variáveis constantes)
- Não estamos reconhecendo concatenação de strings

Foram consideradas apenas as seguintes operações:
- +,-,/,*: Apenas inteiro com inteiro, inteiro com real e real com real
- div, mod: Apenas inteiro com inteiro
- operadores lógicos só podem ser utilizado com expressões do tipo boolean

A declaração de array foi simplificada. No trabalho anterior tinha uma seção Type em que era possível declarar tipos diferentes de arrays e depois utilizar eles no local de tipos primitivos. Isso demandaria que a verificação de tipo fosse semântico em vez de sintático. Como isso aumentaria a complexidade, decidimos retirar. Outra modificação feita é a permissão de apenas matrizes 1D, em vez de n tamanho. Não consideramos arrays como tipo de retorno de função, nem assign de arrays, por exemplo, A e B são arrays, B := A.

As estruturas implementadas foram:
- while
- if
- function
- procedure

Consideramos que ocorre *widening* apenas de int para real. 

As funções writeln e readln foram adicionadas a tabela de funções no início de cada programa, sendo considerada como um Entry do tipo IO_TYPE. 

## ▶️ Execução

Criamos uma imagem Docker contendo o Antlr (versão 4.10.1) rodando o openjdk na versão 11.

Para executar o analisador léxico e sintático devemos executar:
```
docker-compose up
```

Ao executar este comando, ele executa make e make runall. Isso gera as regras da gramática na pasta lexer dentro de projeto e roda todos os casos de testes. Para pode rodar um arquivo em específico, basta executar:
```
make run FILE=nome_arquivo
```
Ou alterar a variávei FILE do makefile.

Na main há um trecho da seguinte forma:
```java
    boolean show = false;

    if (show) {
        checker.showTables();
    }
```
Ele foi feito para não ter poluição visual no terminalm, uma vez que o método showTables apresenta a tabela .dot de cada programa. 

### 🧪 Casos de Testes

Foram criados no total 38 exemplos, sendo 11 de erros semânticos e 13 de erros sintáticos ou léxicos. Eles possuem o seguinte formato:
```
ex{número do exercício}-{descrição sucinta do problema}.pas para casos sem erro
semerr-ex{número do exercício}-{descrição sucinta do problema}.pas para casos com erros semânticos
synerr-ex{número do exercício}-{descrição sucinta do problema}.pas para casos com erros sintáticos
```

## 🤔 Dificuldades

Tivemos bastante dificuldade com a implementação da AST, ficando de início preso em como adicionar as variáveis ao bloco de declaração de variáveis. Isso era devido ao fato que cada variável era irmão de outra e a forma que implementamos dificultava tratar isso, de forma que era mais facíl adicionar cada variável como filha de outra. Esse tipo de lógico se apresentou diversas vezes, como na declaração de funções e declaração de statements.
