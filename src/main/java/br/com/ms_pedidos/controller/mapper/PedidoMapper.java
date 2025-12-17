package br.com.ms_pedidos.controller.mapper;

import br.com.ms_pedidos.controller.mapper.dto.request.PedidoRequest;
import br.com.ms_pedidos.controller.mapper.dto.response.PedidoResponse;
import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;
import br.com.ms_pedidos.gateway.entity.PedidoEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class PedidoMapper {

    public PedidoResponse toResponse(Pedido entity){
        return new PedidoResponse(entity.id(), entity.idUsuario(), entity.status(), entity.valorTotal(), entity.dataHoraSolicitacao(),
                entity.tempoTotalPreparo(),
                entity.itens().stream().map(PedidoItemMapper::toResponse).toList());
    }

    public static Pedido fromResponse(PedidoResponse pedido){
        return new Pedido(pedido.id(), pedido.idUsuario(), pedido.status(),pedido.valorTotal(),pedido.dataHoraSolicitacao(),pedido.tempoTotalPreparo(),
                pedido.itens().stream().map(PedidoItemMapper::fromResponse).toList());
    }

    public Pedido toEntity(PedidoRequest pedidoRequest) {
        return new Pedido(null, pedidoRequest.idUsuario(), StatusPedidoEnum.RECEBIDO, null, LocalDateTime.now(), null, pedidoRequest.itens().stream().map(PedidoItemMapper::toEntity).toList());
    }

    public static PedidoEntity toEntityPersistence(Pedido pedido) {
        return new PedidoEntity(pedido.id(), pedido.idUsuario(), pedido.status(), pedido.valorTotal(), pedido.dataHoraSolicitacao(),
                pedido.tempoTotalPreparo(), pedido.itens().stream().map(PedidoItemMapper::toEntityPersistence).collect(Collectors.toCollection(ArrayList::new)));
    }
}
