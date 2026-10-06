package br.edu.fatecpg.stream.view;

import br.edu.fatecpg.stream.model.Carrinho;
import br.edu.fatecpg.stream.model.ProdutoCarrinho;
import br.edu.fatecpg.stream.model.RespostaCarrinhos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Main {
    private static final String API_URL = "https://dummyjson.com/carts?limit=0";

    public static void main(String[] args) {
        try {
            RespostaCarrinhos resposta = consultarApi();
            List<Carrinho> carrinhos = resposta.getCarts();

            if (carrinhos == null || carrinhos.isEmpty()) {
                System.out.println("A API não retornou carrinhos para análise.");
                return;
            }

            exibirRelatorio(carrinhos);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.out.println("Erro: a requisição foi interrompida.");
        } catch (Exception exception) {
            System.out.println("Erro ao processar os carrinhos: " + exception.getMessage());
        }
    }

    private static RespostaCarrinhos consultarApi() throws Exception {
        HttpClient cliente = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();

        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .timeout(Duration.ofSeconds(30))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> resposta = cliente.send(
                requisicao,
                HttpResponse.BodyHandlers.ofString()
        );

        if (resposta.statusCode() != 200) {
            throw new IllegalStateException(
                    "a API retornou o status HTTP " + resposta.statusCode() + "."
            );
        }

        try {
            return new ObjectMapper().readValue(resposta.body(), RespostaCarrinhos.class);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("o JSON retornado pela API é inválido.", exception);
        }
    }

    private static void exibirRelatorio(List<Carrinho> carrinhos) {
        Consumer<Carrinho> formatarCarrinho = carrinho -> System.out.printf(
                "Carrinho #%d | Usuário: %d | Itens: %d | Total: US$ %.2f%n",
                carrinho.getId(),
                carrinho.getUserId(),
                carrinho.getTotalQuantity(),
                carrinho.getTotal()
        );

        System.out.println("=== SISTEMA DE ANÁLISE DE PEDIDOS ===");
        System.out.println("Carrinhos recebidos: " + carrinhos.size());

        System.out.println("\n1. Carrinhos com total acima de US$ 1.000:");
        carrinhos.stream()
                .filter(carrinho -> carrinho.getTotal() > 1000)
                .forEach(formatarCarrinho);

        System.out.println("\n2. Produtos com desconto acima de 15%:");
        carrinhos.stream()
                .flatMap(carrinho -> carrinho.getProducts().stream())
                .filter(produto -> produto.getDiscountPercentage() > 15)
                .map(ProdutoCarrinho::getTitle)
                .forEach(System.out::println);

        System.out.println("\n3. Carrinhos ordenados pela economia (maior para menor):");
        carrinhos.stream()
                .sorted(Comparator.comparingDouble(Carrinho::getEconomia).reversed())
                .forEach(carrinho -> System.out.printf(
                        "Carrinho #%d | Economia: US$ %.2f%n",
                        carrinho.getId(),
                        carrinho.getEconomia()
                ));

        double somaDescontados = carrinhos.stream()
                .map(Carrinho::getDiscountedTotal)
                .reduce(0.0, Double::sum);
        System.out.printf("\n4. Soma dos discountedTotal: US$ %.2f%n", somaDescontados);

        System.out.println("\n5. Quantidade de carrinhos por número de produtos:");
        Map<Integer, Long> carrinhosPorProdutos = carrinhos.stream()
                .collect(Collectors.groupingBy(
                        Carrinho::getTotalProducts,
                        Collectors.counting()
                ));
        carrinhosPorProdutos.forEach((quantidade, total) ->
                System.out.println(
                        quantidade + " produto(s): " + total + " carrinho(s)"
                ));

        Optional<Carrinho> maiorCarrinho = carrinhos.stream()
                .max(Comparator.comparingDouble(Carrinho::getTotal));
        System.out.println("\n6. Carrinho de maior valor:");
        maiorCarrinho.ifPresent(formatarCarrinho);
    }
}
