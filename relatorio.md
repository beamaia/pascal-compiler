# 🚩 Checkpoint 1 - Analisador Léxico

Analisador léxico de Turbo Pascal feito por Beatriz Maia, Renan Moreira Gomes e Victor Aguiar Marques Aguiar.

## 📝 Descrição do Desenvolvimento

As regras e tokens estão presente no documento PASLexer.g dentro da pasta project. Foi utilizado como base o [Free Pascal](https://www.freepascal.org/docs-html/current/ref/ref.html) para desenvolvimento do projeto, em específico os seguintes capítulos da sua documentação:
- [Chapter 01: Pascal Tokens](https://www.freepascal.org/docs-html/current/ref/refch1.html#x8-70001)
- [Chapter 03: Types](https://www.freepascal.org/docs-html/current/ref/refch3.html#x24-230003)
- [Chapter 12: Expressions](https://www.freepascal.org/docs-html/current/ref/ref.html)
- [Chapter 13: Statements](https://www.freepascal.org/docs-html/current/ref/refch13.html#x159-18300013)
- [Chapter 14: Using functions and procedures](https://www.freepascal.org/docs-html/current/ref/refch14.html#x175-19900014)

### Tipos reconhecidos

O analisador léxico é capaz de reconhecer os seguintes tipos:
- integer
- real
- char
- boolean
- array

Além disso, consegue reconhecer os seguintes tipos de declarações:
- if...then, else if...then e else (identificável por os EBNF *if_stmt* e *else_stmt*).
- case (identificável por o EBNF *case_stmt*).
- for...to (identificável por o EBNF *for_stmt*).
- while...do (identificável por o EBNF *while_stmt*).
- function (identificável por o EBNF *fnc_and_procedures_sect*).
- procedure (identificável por o EBNF *fnc_and_procedures_sect*).

### ⚙️ Simplificações

Com base nas recomendações do professor, as seguintes funcionalidades não foram implementadas:

- Não estamos reconhecendo goto.
- Não estamos reconhecendo uses (imports de bibliotecas)
- Não estamos reconhecendo const (variáveis constantes)
- Não estamos reconhecendo concatenação de strings

## ▶️ Execução

Criamos uma imagem Docker contendo o Antlr (versão 4.10.1) rodando o openjdk na versão 11.

Para executar o analisador léxico e sintático devemos executar:
```
docker-compose up
```

Ao executar este comando, ele executa make e make runall. Isso gera as regras da gramática na pasta lexer dentro de projeto e roda todos os casos de testes.

### 🧪 Casos de Testes

Foram criados no total 28 exemplos, sendo 13 de erros sintáticos ou léxicos. 

## 🤔 Dificuldades

Tivemos dificuldades em compreender as diferentes versões de Pascal, sendo utilizado diferentes nomes como Turbo Pascal, Delphi, Object Pascal, entre outros. Optamos por implementar o que reconhecemos como Turbo Pascal, porém mesmo assim, havia funcionalidades que não havia distinção entre os tipos. Pode ser possível que algo de outros tipos de Pascal foi implementado devido a isso. 

No analisador léxico, tivemos um pouco de dificuldade com o reconhecimento de comentários. Conseguimos encontrar uma solução que funcionou bem para os casos testados.
