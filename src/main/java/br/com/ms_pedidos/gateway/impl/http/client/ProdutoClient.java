package br.com.ms_pedidos.gateway.impl.http.client;

import br.com.ms_pedidos.gateway.impl.http.response.dto.ProdutoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProdutoClient {

    private final WebClient webClient;

    public ProdutoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public Set<ProdutoResponse> buscarProdutosPorIds(Set<Long> ids) {
        String codigos = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/produtos/buscar/lista")
                        .queryParam("listCodigoProdutos", codigos)
                        .build()
                )
                .retrieve()
                .bodyToFlux(ProdutoResponse.class)
                .collect(Collectors.toSet())
                .block();
    }
}

