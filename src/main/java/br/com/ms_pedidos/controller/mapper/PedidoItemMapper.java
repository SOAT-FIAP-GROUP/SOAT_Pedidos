package br.com.ms_pedidos.controller.mapper;


import br.com.ms_pedidos.controller.mapper.dto.request.PedidoItemRequest;
import br.com.ms_pedidos.controller.mapper.dto.response.PedidoItemResponse;
import br.com.ms_pedidos.entity.PedidoItem;
import br.com.ms_pedidos.gateway.entity.PedidoEntity;
import br.com.ms_pedidos.gateway.entity.PedidoItemEntity;

public class PedidoItemMapper {

    public static PedidoItemResponse toResponse(PedidoItem entity){
        return new PedidoItemResponse(entity.id(), entity.pedidoId(), entity.produtoId(), entity.quantidade(), entity.precoUnitario(), entity.precoTotal());
    }

    public static PedidoItem fromResponse(PedidoItemResponse pedidoItem){
        return new PedidoItem(pedidoItem.id(), pedidoItem.pedidoId(), pedidoItem.produtoId(), pedidoItem.quantidade(), pedidoItem.precoUnitario(), pedidoItem.precoTotal());
    }

    public static PedidoItem toEntity(PedidoItemRequest pedidoItemRequest) {
        return new PedidoItem(pedidoItemRequest.produtoId(), null, null, pedidoItemRequest.quantidade(), null, null);
    }

    public static PedidoItemEntity toEntityPersistence(PedidoItem pedidoItem) {
        return new PedidoItemEntity(pedidoItem.id(), new PedidoEntity(pedidoItem.pedidoId()), pedidoItem.produtoId(), pedidoItem.quantidade(), pedidoItem.precoUnitario(), pedidoItem.precoTotal());
    }
}
