package br.com.ms_pedidos.mocks;

import br.com.ms_pedidos.controller.mapper.PedidoMapper;
import br.com.ms_pedidos.controller.mapper.dto.request.PedidoItemRequest;
import br.com.ms_pedidos.controller.mapper.dto.request.PedidoRequest;
import br.com.ms_pedidos.controller.mapper.dto.response.PedidoResponse;
import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.entity.PedidoItem;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;
import br.com.ms_pedidos.gateway.impl.http.response.dto.Categoria;
import br.com.ms_pedidos.gateway.impl.http.response.dto.ProdutoResponse;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MockGenerator {

    private static final Long ID = 1L;

    public static Pedido generatePedidoMock() {
        return new Pedido(ID, ID, StatusPedidoEnum.PRONTO,
                BigDecimal.valueOf(50), LocalDateTime.now(),
                Time.valueOf("00:10:00"), List.of());
    }

    public static PedidoResponse generatePedidoResponseMock() {
        PedidoMapper pedidoMapper = new PedidoMapper();
        return pedidoMapper.toResponse(generatePedidoMock());
    }

    public static PedidoRequest generatePedidoRequestMock() {
        PedidoItemRequest item1 = new PedidoItemRequest(ID, 2); // produtoId = 1, quantidade = 2
        PedidoItemRequest item2 = new PedidoItemRequest(2L, 1); // produtoId = 2, quantidade = 1

        return new PedidoRequest(
                ID,
                List.of(item1, item2)
        );
    }

    public static Pedido generatePedidoMockComItensVazios() {
        return new Pedido(
                null,
                ID,
                StatusPedidoEnum.RECEBIDO,
                null,
                LocalDateTime.now(),
                null,
                List.of()
        );
    }

    public static Pedido generatePedidoMockSemId() {
        PedidoItem item1 = new PedidoItem(1L, null, ID, 2, new BigDecimal("10.00"), new BigDecimal("20.00")
        );

        PedidoItem item2 = new PedidoItem(2L, null, 2L, 1, new BigDecimal("15.00"), new BigDecimal("15.00"));

        return new Pedido(
                null,
                ID,
                StatusPedidoEnum.RECEBIDO,
                new BigDecimal("35.00"),
                LocalDateTime.now(),
                null,
                List.of(item1, item2)
        );
    }

    public static Set<ProdutoResponse> generatedProdutoMock() {
        ProdutoResponse p1 = new ProdutoResponse(1L,
                "lanche",
                "teste",
                new Categoria(ID, null),
                BigDecimal.valueOf(30.0),
                Time.valueOf("00:10:00")
        );

        ProdutoResponse p2 = new ProdutoResponse(2L,
                "lanche",
                "teste",
                new Categoria(ID, null),
                BigDecimal.valueOf(30.0),
                Time.valueOf("00:10:00")
        );
        Set<ProdutoResponse> produtoResponseSet = new HashSet<>();
        produtoResponseSet.add(p1);
        produtoResponseSet.add(p2);

        return produtoResponseSet;
    }
}



