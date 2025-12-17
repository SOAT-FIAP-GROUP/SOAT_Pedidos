package br.com.ms_pedidos.controller.mapper;

import br.com.ms_pedidos.entity.FilaPedidosPreparacao;
import br.com.ms_pedidos.gateway.entity.FilaPedidosPreparacaoEntity;

public class FilaPedidosPreparacaoMapper {
    public static FilaPedidosPreparacaoEntity toEntityPersistence(FilaPedidosPreparacao filaPedidosPreparacao){
        return new FilaPedidosPreparacaoEntity(filaPedidosPreparacao.id(),
                PedidoMapper.toEntityPersistence(filaPedidosPreparacao.pedido()));
    }
}
