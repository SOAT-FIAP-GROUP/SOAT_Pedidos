package br.com.ms_pedidos.gateway;

import br.com.ms_pedidos.gateway.impl.http.response.dto.ProdutoResponse;

import java.util.Set;

public interface IProdutoGateway {
    Set<ProdutoResponse> listaProdutosPedidosSet(Set<Long> produtoIdList);
}
