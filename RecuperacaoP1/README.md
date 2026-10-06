# Recuperação P1 — Sistema de Análise de Pedidos

Projeto adaptado a partir da estrutura original do IntelliJ:

```text
src/
└── br/
    └── edu/
        └── fatecpg/
            └── stream/
                ├── model/
                │   ├── Carrinho.java
                │   ├── Cliente.java
                │   ├── ProdutoCarrinho.java
                │   └── RespostaCarrinhos.java
                └── view/
                    └── Main.java
```

A aplicação consome a API pública DummyJSON, desserializa o JSON com Jackson e gera um relatório usando Streams e Lambdas.

## Requisitos da tarefa implementados

- `HttpClient` do pacote `java.net.http`;
- Jackson para desserialização JSON;
- Classes `RespostaCarrinhos`, `Carrinho` e `ProdutoCarrinho` com construtor vazio, getters e `toString`;
- `Carrinho.getEconomia()`;
- Tratamento de falha de conexão, status HTTP diferente de 200 e JSON inválido;
- `filter` para carrinhos acima de US$ 1.000;
- `flatMap` e `map` para produtos com desconto acima de 15%;
- `sorted` para ordenar pela economia;
- `reduce` para somar os valores com desconto;
- `groupingBy` para agrupar pelo número de produtos;
- `max` e `Optional` para encontrar o maior carrinho;
- Lambda para formatar a saída;
- Nenhum `for` ou `while` no código da tarefa.

## Como abrir no IntelliJ IDEA

1. Extraia o projeto e abra a pasta `RecuperacaoP1` no IntelliJ IDEA;
2. Abra o arquivo `pom.xml` e selecione **Load Maven Changes**, se o IntelliJ solicitar;
3. Aguarde o download da dependência Jackson;
4. Execute `src/br/edu/fatecpg/stream/view/Main.java` pelo botão verde ao lado do método `main`.

O projeto usa Java 17, igual à configuração original do arquivo `.idea/misc.xml`.

## Como executar no terminal

Na pasta raiz `RecuperacaoP1`:

```bash
mvn clean compile
mvn exec:java
```

## GitHub

Na pasta raiz do projeto:

```bash
git init
git add .
git commit -m "Implementa analise de pedidos com streams"
git branch -M main
git remote add origin https://github.com/SEU-USUARIO/recuperacao-p1.git
git push -u origin main
```

Substitua `SEU-USUARIO` pela sua conta do GitHub.
