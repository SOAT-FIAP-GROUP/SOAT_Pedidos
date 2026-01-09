package br.com.ms_pedidos.usecase;

import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;
import br.com.ms_pedidos.exception.EntityNotFoundException;
import br.com.ms_pedidos.gateway.IPedidoGateway;
import br.com.ms_pedidos.gateway.IProdutoGateway;
import br.com.ms_pedidos.gateway.impl.http.response.dto.ProdutoResponse;
import br.com.ms_pedidos.mocks.MockGenerator;
import br.com.ms_pedidos.usecase.impl.PedidoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PedidoUseCaseTest {
    private PedidoUseCase pedidoUseCase;
    private IPedidoGateway pedidoGateway;
    private IProdutoGateway produtoGateway;

    private static final Long ID = 1L;

    @BeforeEach
    void setUp() {
        pedidoGateway = mock(IPedidoGateway.class);
        produtoGateway = mock(IProdutoGateway.class);
        pedidoUseCase = new PedidoUseCase(pedidoGateway, produtoGateway);
    }

    @Test
    void deveBuscarPedidoComSucesso() {
        Pedido pedido = MockGenerator.generatePedidoMock();
        when(pedidoGateway.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido result = pedidoUseCase.buscarPedido(1L);

        assertEquals(pedido, result);
    }

    @Test
    void deveLancarExcecaoQuandoPedidoNaoEncontrado() {
        when(pedidoGateway.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pedidoUseCase.buscarPedido(999L));
    }

    @Test
    void deveCriarPedidoComSucesso() {
        Pedido pedido = MockGenerator.generatePedidoMockSemId();
        Set<ProdutoResponse> produtoResponseSet = MockGenerator.generatedProdutoMock();

        Pedido pedidoSalvo = new Pedido(1L, pedido.idUsuario(), pedido.status(), null, pedido.dataHoraSolicitacao(), Time.valueOf("00:10:00"), List.of());
        when(produtoGateway.listaProdutosPedidosSet(anySet())).thenReturn(produtoResponseSet);
        when(pedidoGateway.save(any())).thenReturn(pedidoSalvo);

        Pedido result = pedidoUseCase.criarPedido(pedido);

        assertNotNull(result);
        assertEquals(pedidoSalvo.id(), result.id());
        verify(pedidoGateway, times(2)).save(any()); // uma para salvar inicial e outra para salvar final
    }

    @Test
    void deveGerarExcecaoProdutoNulo() {
        Pedido pedido = MockGenerator.generatePedidoMockSemId();
        Set<ProdutoResponse> produtoResponseSet = new HashSet<>();

        Pedido pedidoSalvo = new Pedido(1L, pedido.idUsuario(), pedido.status(), null, pedido.dataHoraSolicitacao(), Time.valueOf("00:10:00"), List.of());
        when(produtoGateway.listaProdutosPedidosSet(anySet())).thenReturn(produtoResponseSet);
        when(pedidoGateway.save(any())).thenReturn(pedidoSalvo);

        assertThrows(EntityNotFoundException.class, () -> {
            pedidoUseCase.criarPedido(pedido);
        });
    }

    @Test
    void deveLancarExcecaoAoCriarPedidoSemItens() {
        Pedido pedido = MockGenerator.generatePedidoMockComItensVazios();

        when(pedidoGateway.save(any())).thenReturn(pedido);

        assertThrows(ResponseStatusException.class, () -> pedidoUseCase.criarPedido(pedido));
    }

    @Test
    void deveListarPedidosPorStatus() {
        StatusPedidoEnum status = StatusPedidoEnum.RECEBIDO;
        Pedido pedido = MockGenerator.generatePedidoMock();
        when(pedidoGateway.findAllByStatus(status)).thenReturn(List.of(pedido));

        List<Pedido> result = pedidoUseCase.listarPedidos(status);

        assertEquals(1, result.size());
    }

    @Test
    void deveAlterarStatusDoPedido() {
        Pedido pedido = MockGenerator.generatePedidoMock();
        Pedido pedidoAtualizado = pedido.withStatus(StatusPedidoEnum.PRONTO);

        when(pedidoGateway.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoGateway.save(any())).thenReturn(pedidoAtualizado);

        Pedido result = pedidoUseCase.alterarPedido(1L, StatusPedidoEnum.PRONTO);

        assertEquals(StatusPedidoEnum.PRONTO, result.status());
    }

    @Test
    void deveListarPedidosOrdenados() {
        Pedido pedido = MockGenerator.generatePedidoMock();
        when(pedidoGateway.findAllOrdenado()).thenReturn(List.of(pedido));

        List<Pedido> pedidos = pedidoUseCase.listaPedidosOrd();

        assertEquals(1, pedidos.size());
        assertEquals(pedido, pedidos.get(0));
    }

}
