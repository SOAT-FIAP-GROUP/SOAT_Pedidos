package br.com.ms_pedidos.controller.mapper.dto.request;

import java.util.List;

public record PedidoRequest(Long idUsuario, List<PedidoItemRequest> itens) {
}
