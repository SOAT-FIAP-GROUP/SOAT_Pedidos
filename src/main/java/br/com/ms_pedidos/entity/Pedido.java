package br.com.ms_pedidos.entity;

import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record Pedido(Long id, Long idUsuario, StatusPedidoEnum status, BigDecimal valorTotal, LocalDateTime dataHoraSolicitacao, Time tempoTotalPreparo, List<PedidoItem> itens) {

    public Pedido withStatus(StatusPedidoEnum novoStatus){
        return new Pedido(id,
                idUsuario,
                novoStatus,
                valorTotal,
                dataHoraSolicitacao,
                tempoTotalPreparo,
                itens);
    }

    public Pedido preSalvar(Long idUsuario, StatusPedidoEnum status, LocalDateTime dataHoraSolicitacao){
        return new Pedido(null, idUsuario, status,null,dataHoraSolicitacao, null, new ArrayList<>());
    }
}
