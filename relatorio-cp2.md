O que voce deve entregar:
– O codigo desenvolvido, juntamente com as instrucoes de compilacao.
– Os casos de testes que o grupo criou.
– Um breve relatorio (em formato .pdf ou .txt) apresentando o andamento do projeto ate o momento.
– Envie um arquivo compactado com todo o material.

Simplificacoes: Nesse ponto, ́e bastante provavel que voce tenha de simplificar alguns
aspectos da linguagem fonte, visto que praticamente todas as linguagens de programacao
reais possuem uma quantidade muito grande de funcionalidades. Segue abaixo uma lista
mınima de elementos que o seu compilador deve tratar corretamente:

– Operacoes aritmeticas e de comparacao basicas (+, ∗, <, ==, etc, etc).
– Comandos de atribuicao.
– Execucao de blocos sequenciais de codigo.
– Pelo menos uma estrutura de escolha (if-then-else) e uma de repeticao (while, for, etc).
– Declaracao e manipulacao de tipos basicos como int, real, string e bool (quando aplicavel a LP).
– Declaracao e manipulacao de pelo menos um tipo composto (vetores, listas em Python, etc).
– Declaracao e execucao correta de chamadas de funcao com numero de parametros fixos (nao precisa ser varargs).
– Sistema de tipos que trata adequadamente todos os tipos permitidos.
– Operacoes de IO basicas sobre stdin e stdout para permitir testes.

Fonte
http://ctp.mkprog.com/en/pascal/menu/operators/

Consideramos a seguintes possiveis operacoes aritmeticas:

- +,-,/,*: Apenas inteiro com inteiro, inteiro com real e real com real
- div: Apenas inteiro com inteiro
- op logicos:

Retiramos o switch case
Retiramos o type
Simplificamos para aceitar apenas arrays (n pode matrizes de dimensoes maiores que 1)
Não consideramos arrays como tipo de retorno de função, nem assign de arrays, por exemplo, A e B são arrays, B := A (teria que verificar se são arrays e se tem o mesmo tamanho).

Não consideramos precedência de operadores (ainda)
