package br.com.ms_pedidos.gateway;

import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;

import java.util.List;
import java.util.Optional;

public interface IPedidoGateway {
    Optional<Pedido> findById(Long id);

    Pedido save (Pedido entity);

    List<Pedido> findAllByStatus(StatusPedidoEnum status);

    List<Pedido> findAllOrdenado();
}
