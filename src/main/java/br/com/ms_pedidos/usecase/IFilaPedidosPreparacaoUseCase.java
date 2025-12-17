package br.com.ms_pedidos.usecase;

import br.com.ms_pedidos.entity.FilaPedidosPreparacao;

public interface IFilaPedidosPreparacaoUseCase {
    FilaPedidosPreparacao salvar(FilaPedidosPreparacao filaPedidosPreparacao);

    FilaPedidosPreparacao findByPedidoPorId(Long id);

    void removerPedidoDaFila(FilaPedidosPreparacao filaPedidosPreparacao);
}
