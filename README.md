# Recuperação P1 - Sistema de Análise de Pedidos

Projeto desenvolvido para a recuperação da P1 de Java. O sistema consome a API pública DummyJSON, realiza a desserialização dos dados e gera relatórios utilizando apenas Streams e Lambdas.

## O que foi implementado

- Consumo da API utilizando a classe nativa `HttpClient`.
- Desserialização do JSON utilizando a biblioteca Jackson.
- Classes de modelo com construtor vazio, getters e toString.
- Tratamento de exceções (HTTP diferente de 200, falha de conexão e JSON inválido) sem quebrar a execução.
- Consultas com Streams: `filter`, `map`, `flatMap`, `sorted`, `reduce` e `groupingBy`.
- Desafios extras: formatação de saída feita com Lambda e uso de `max` com `Optional`.
- Ausência total de laços de repetição tradicionais (`for` ou `while`).

## Como executar o projeto

Certifique-se de ter o Java 17 e o Maven configurados em sua máquina.

1. Abra o terminal na pasta raiz do projeto.
2. Baixe as dependências e compile o código executando:
   mvn clean compile

3. Para rodar a aplicação, execute:
   mvn exec:java
