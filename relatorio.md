# 🚩 Checkpoint 1 - Analisador Léxico

Analisador léxico de Turbo Pascal feito por Beatriz Maia, Renan Moreira Gomes e Victor Aguiar Marques Aguiar.

## 📝 Descrição do Desenvolvimento

As regras e tokens estão presente no documento PASLexer.g dentro da pasta project.

### ⚙️ Simplificações

Com base nas recomendações do professor, as seguintes funcionalidades não foram implementadas:

- Goto
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
