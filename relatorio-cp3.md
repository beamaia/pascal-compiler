# 🚩 Checkpoint 3 - Back end

Back end do compilador de Turbo Pascal para MIPS feito por Beatriz Maia, Renan Moreira Gomes e Victor Aguiar Marques Aguiar.

## 📝 Descrição do Desenvolvimento

Criação do pacote code contendo as classes relacionadas com a geração do código .asm baseado no laboratório 7 da disciplina com a adição das seguintes classes:

- Registers.java que serve para associar um registrador virtual/físico para uma variável;
- RegistersOrg.java que organiza os registradores em listas de tipos diferentes.

### ⚙️ Implementações

Como não conseguimos implementar algumas das funcionalidades necessárias, vamos listar o que foi implementado:

* Criação e associação dos registradores físicos/virtuais;
* Operações de soma, subtração, multiplicação e divisão para números inteiros;
* Atribuição de variável;
* Adição da tabela de strings para a parte .data do código .asm;
* Inclusão das instruções no .text do código .asm;
* O back end gera um código .asm, o arquivo out.asm.

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

## 🤔 Dificuldades

Tivemos bastante dificuldade para fazer a parte do CP3 principalmente por causa do mau gerenciamento do tempo. Além disso, os membros do grupo ficaram sobrecarregados por motivos diferentes, tanto acadêmicos quanto profissionais ou pessoais. Retirando-se os motivos "extra-disciplinares", a maior dificuldade no trabalho foi a manipulação das variáveis como registradores, tanto que temos uma lista de registradores para organizá-los e uma pilha para manusear os registradores. Tivemos dificuldades também, com a tradução das informações para instruções do MIPS, o que tomou um tempo considerável para que começássemos a fazer o back end funcionar de fato. 


Estávamos tentando implementar a tradução do bloco de código "if" e comparação de igualdade, mas este se encontra incompleto. A principal dificuldade foi novamente no manuseio dos registradores entre "visits" de nós diferentes.
