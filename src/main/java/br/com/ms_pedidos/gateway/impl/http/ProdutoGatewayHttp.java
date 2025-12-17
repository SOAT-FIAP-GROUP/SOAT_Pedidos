package br.com.ms_pedidos.gateway.impl.http;

import br.com.ms_pedidos.gateway.IProdutoGateway;
import br.com.ms_pedidos.gateway.impl.http.client.ProdutoClient;
import br.com.ms_pedidos.gateway.impl.http.response.dto.ProdutoResponse;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProdutoGatewayHttp implements IProdutoGateway {

    private final ProdutoClient produtoClient;

    public ProdutoGatewayHttp(ProdutoClient produtoClient) {
        this.produtoClient = produtoClient;
    }

    @Override
    public Set<ProdutoResponse> listaProdutosPedidosSet(Set<Long> produtoIdList) {
        return produtoClient.buscarProdutosPorIds(produtoIdList);
    }
}

