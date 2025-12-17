package br.com.ms_pedidos.gateway;

import br.com.ms_pedidos.entity.FilaPedidosPreparacao;

import java.util.Optional;

public interface IFilaPedidosPreparacaoGateway {

    FilaPedidosPreparacao save(FilaPedidosPreparacao entity);

    Optional<FilaPedidosPreparacao> findById(Long id);

    Optional<FilaPedidosPreparacao> findByPedidocodigoId(Long id);

    void removerPedidoDaFila(FilaPedidosPreparacao entity);
}
