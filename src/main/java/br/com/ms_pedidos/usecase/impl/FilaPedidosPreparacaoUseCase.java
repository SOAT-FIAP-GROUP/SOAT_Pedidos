package br.com.ms_pedidos.usecase.impl;

import br.com.ms_pedidos.entity.FilaPedidosPreparacao;
import br.com.ms_pedidos.exception.EntityNotFoundException;
import br.com.ms_pedidos.gateway.IFilaPedidosPreparacaoGateway;
import br.com.ms_pedidos.usecase.IFilaPedidosPreparacaoUseCase;

public class FilaPedidosPreparacaoUseCase implements IFilaPedidosPreparacaoUseCase {

    private final IFilaPedidosPreparacaoGateway filaPedidosPreparacaoGateway;

    public FilaPedidosPreparacaoUseCase(IFilaPedidosPreparacaoGateway filaPedidosPreparacaoGateway) {
        this.filaPedidosPreparacaoGateway = filaPedidosPreparacaoGateway;
    }

    @Override
    public FilaPedidosPreparacao salvar(FilaPedidosPreparacao filaPedidosPreparacao) {
        return filaPedidosPreparacaoGateway.save(filaPedidosPreparacao);
    }

    @Override
    public FilaPedidosPreparacao findByPedidoPorId(Long id) {
        return filaPedidosPreparacaoGateway.findByPedidocodigoId(id).orElseThrow(() -> new EntityNotFoundException(FilaPedidosPreparacao.class, id));
    }

    @Override
    public void removerPedidoDaFila(FilaPedidosPreparacao filaPedidosPreparacao) {
        filaPedidosPreparacaoGateway.removerPedidoDaFila(filaPedidosPreparacao);
    }
}
