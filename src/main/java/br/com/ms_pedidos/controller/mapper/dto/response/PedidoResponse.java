package br.com.ms_pedidos.controller.mapper.dto.response;

import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(Long id, Long idUsuario, StatusPedidoEnum status, BigDecimal valorTotal, LocalDateTime dataHoraSolicitacao, Time tempoTotalPreparo, List<PedidoItemResponse> itens) {
}
