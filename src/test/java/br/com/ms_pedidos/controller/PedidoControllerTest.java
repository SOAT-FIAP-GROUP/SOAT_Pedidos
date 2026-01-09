package br.com.ms_pedidos.controller;

import br.com.ms_pedidos.controller.mapper.PedidoMapper;
import br.com.ms_pedidos.controller.mapper.dto.request.PedidoRequest;
import br.com.ms_pedidos.controller.mapper.dto.response.PedidoResponse;
import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;
import br.com.ms_pedidos.mocks.MockGenerator;
import br.com.ms_pedidos.usecase.IPedidoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PedidoControllerTest {
    private PedidoController pedidoController;
    private IPedidoUseCase pedidoUseCase;
    private PedidoMapper pedidoMapper;

    private static final Long ID = 1L;

    @BeforeEach
    void setUp() {
        pedidoUseCase = mock(IPedidoUseCase.class);
        pedidoMapper = mock(PedidoMapper.class);

        pedidoController = new PedidoController(pedidoUseCase, pedidoMapper);
    }

    @Test
    void deveBuscarPedidoComSucesso() {
        Pedido pedido = MockGenerator.generatePedidoMock();
        PedidoResponse response = MockGenerator.generatePedidoResponseMock();

        when(pedidoUseCase.buscarPedido(anyLong())).thenReturn(pedido);
        when(pedidoMapper.toResponse(pedido)).thenReturn(response);

        PedidoResponse resultado = pedidoController.buscarPedido(ID);

        assertEquals(response, resultado);
        verify(pedidoUseCase).buscarPedido(ID);
        verify(pedidoMapper).toResponse(pedido);
    }

    @Test
    void deveCriarPedidoComSucesso() {
        PedidoRequest request = MockGenerator.generatePedidoRequestMock();
        Pedido pedido = MockGenerator.generatePedidoMock();
        PedidoResponse response = MockGenerator.generatePedidoResponseMock();

        when(pedidoMapper.toEntity(request)).thenReturn(pedido);
        when(pedidoUseCase.criarPedido(any(Pedido.class))).thenReturn(pedido);
        when(pedidoMapper.toResponse(any(Pedido.class))).thenReturn(response);

        PedidoResponse resultado = pedidoController.criarPedido(request);

        assertEquals(response, resultado);
        verify(pedidoMapper).toEntity(request);
        verify(pedidoUseCase).criarPedido(pedido);
        verify(pedidoMapper).toResponse(pedido);
    }

    @Test
    void testListarPedidos() {
        StatusPedidoEnum status = StatusPedidoEnum.EM_PREPARACAO;
        Pedido pedido = MockGenerator.generatePedidoMock(); // objeto de domínio
        PedidoResponse response = MockGenerator.generatePedidoResponseMock(); // DTO de resposta

        when(pedidoUseCase.listarPedidos(status)).thenReturn(List.of(pedido));
        when(pedidoMapper.toResponse(pedido)).thenReturn(response);

        List<PedidoResponse> result = pedidoController.listarPedidos(status);

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
        verify(pedidoUseCase).listarPedidos(status);
        verify(pedidoMapper).toResponse(pedido);
    }

    @Test
    void testAlterarPedido() {
        Long codigo = 1L;
        StatusPedidoEnum status = StatusPedidoEnum.EM_PREPARACAO;
        Pedido pedido = MockGenerator.generatePedidoMock();
        PedidoResponse response = MockGenerator.generatePedidoResponseMock();

        when(pedidoUseCase.alterarPedido(codigo, status)).thenReturn(pedido);
        when(pedidoMapper.toResponse(pedido)).thenReturn(response);

        PedidoResponse result = pedidoController.alterarPedido(codigo, status);

        assertEquals(response, result);
        verify(pedidoUseCase).alterarPedido(codigo, status);
        verify(pedidoMapper).toResponse(pedido);
    }


    @Test
    void testListaPedidosOrd() {
        Pedido pedido = MockGenerator.generatePedidoMock();
        PedidoResponse response = MockGenerator.generatePedidoResponseMock();

        when(pedidoUseCase.listaPedidosOrd()).thenReturn(List.of(pedido));
        when(pedidoMapper.toResponse(pedido)).thenReturn(response);

        List<PedidoResponse> result = pedidoController.listaPedidosOrd();

        assertEquals(1, result.size());
        assertEquals(response, result.get(0));
        verify(pedidoUseCase).listaPedidosOrd();
        verify(pedidoMapper).toResponse(pedido);
    }
}

