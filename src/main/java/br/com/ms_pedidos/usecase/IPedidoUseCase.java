package br.com.ms_pedidos.usecase;

import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;

import java.util.List;

public interface IPedidoUseCase {

    Pedido buscarPedido(Long id);

    Pedido criarPedido(Pedido pedido);

    List<Pedido> listarPedidos(StatusPedidoEnum status);

    Pedido alterarPedido(Long id, StatusPedidoEnum status);

    List<Pedido> listaPedidosOrd();
}
