package br.com.ms_pedidos.gateway.impl;

import br.com.ms_pedidos.controller.mapper.PedidoMapper;
import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;
import br.com.ms_pedidos.gateway.IPedidoGateway;
import br.com.ms_pedidos.gateway.entity.PedidoEntity;
import br.com.ms_pedidos.gateway.persistence.jpa.PedidoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PedidoGateway implements IPedidoGateway {

    private final PedidoRepository pedidoRepository;

    public PedidoGateway(PedidoRepository pedidoRepository) {

        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id).map(PedidoEntity::toModel);
    }

    @Override
    public Pedido save(Pedido entity) {
        return pedidoRepository.save(PedidoMapper.toEntityPersistence(entity)).toModel();
    }

    @Override
    public List<Pedido> findAllByStatus(StatusPedidoEnum status) {
        return pedidoRepository.findAllByStatus(status).stream()
                .map(PedidoEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Pedido> findAllOrdenado() {
        return pedidoRepository.findAllOrdenado().stream()
                .map(PedidoEntity::toModel)
                .collect(Collectors.toList());
    }
}
