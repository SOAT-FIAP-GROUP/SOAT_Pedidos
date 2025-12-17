package br.com.ms_pedidos.controller.mapper.dto.response;

import java.math.BigDecimal;

public record PedidoItemResponse(Long id, Long pedidoId, Long produtoId, int quantidade, BigDecimal precoUnitario, BigDecimal precoTotal) {
}
