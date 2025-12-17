package br.com.ms_pedidos.gateway.impl;



import br.com.ms_pedidos.controller.mapper.FilaPedidosPreparacaoMapper;
import br.com.ms_pedidos.entity.FilaPedidosPreparacao;
import br.com.ms_pedidos.gateway.IFilaPedidosPreparacaoGateway;
import br.com.ms_pedidos.gateway.entity.FilaPedidosPreparacaoEntity;
import br.com.ms_pedidos.gateway.persistence.jpa.FilaPedidosPreparacaoRepository;

import java.util.Optional;

public class FilaPedidosPreparacaoGateway implements IFilaPedidosPreparacaoGateway {

    private final FilaPedidosPreparacaoRepository filaPedidosPreparacaoRepository;

    public FilaPedidosPreparacaoGateway(FilaPedidosPreparacaoRepository filaPedidosPreparacaoRepository) {
        this.filaPedidosPreparacaoRepository = filaPedidosPreparacaoRepository;
    }

    @Override
    public FilaPedidosPreparacao save(FilaPedidosPreparacao entity) {
        return filaPedidosPreparacaoRepository.save(FilaPedidosPreparacaoMapper.toEntityPersistence(entity)).toModel();
    }

    @Override
    public Optional<FilaPedidosPreparacao> findById(Long id) {
        return filaPedidosPreparacaoRepository.findById(id).map(FilaPedidosPreparacaoEntity::toModel);
    }

    @Override
    public Optional<FilaPedidosPreparacao> findByPedidocodigoId(Long id) {
        return filaPedidosPreparacaoRepository.findByPedidocodigoCodigo(id).map(FilaPedidosPreparacaoEntity::toModel);
    }

    @Override
    public void removerPedidoDaFila(FilaPedidosPreparacao entity) {
        filaPedidosPreparacaoRepository.delete(FilaPedidosPreparacaoMapper.toEntityPersistence(entity));
    }
}
