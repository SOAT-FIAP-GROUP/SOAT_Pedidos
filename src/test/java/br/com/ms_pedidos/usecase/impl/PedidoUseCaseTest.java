package br.com.ms_pedidos.usecase.impl;

import br.com.ms_pedidos.entity.FilaPedidosPreparacao;
import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.entity.PedidoItem;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;
import br.com.ms_pedidos.exception.EntityNotFoundException;
import br.com.ms_pedidos.gateway.IPedidoGateway;
import br.com.ms_pedidos.gateway.IProdutoGateway;
import br.com.ms_pedidos.gateway.impl.http.response.dto.Categoria;
import br.com.ms_pedidos.gateway.impl.http.response.dto.ProdutoResponse;
import br.com.ms_pedidos.usecase.IFilaPedidosPreparacaoUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given the class PedidoUseCase")
public class PedidoUseCaseTest {

    @Mock
    private IPedidoGateway pedidoGateway;

    @Mock
    private IProdutoGateway produtoGateway;

    @Mock
    private IFilaPedidosPreparacaoUseCase filaPedidosPreparacaoUseCase;

    private PedidoUseCase useCase;

    private long timeValue = LocalDateTime.now().toEpochSecond(ZoneOffset.of("-03:00")) + 3600;
    private LocalDateTime nowDateTime = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        useCase = new PedidoUseCase(pedidoGateway, produtoGateway, filaPedidosPreparacaoUseCase);
    }

    @Nested
    @DisplayName("buscarPedido method")
    class BuscarPedidoTest {

        @Test
        @DisplayName("With an existing id, should return the Pedido")
        void shouldReturnPedidoWhenIdExists() {
            var id = 1L;
            var pedido = new Pedido(id, 1L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.TEN, nowDateTime, Time.valueOf(LocalTime.NOON), List.of());
            when(pedidoGateway.findById(id)).thenReturn(Optional.of(pedido));

            var result = useCase.buscarPedido(id);

            assertNotNull(result);
            assertEquals(id, result.id());
            verify(pedidoGateway, times(1)).findById(id);
        }

        @Test
        @DisplayName("With a non-existing id, should throw EntityNotFoundException")
        void shouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
            var id = 999L;
            when(pedidoGateway.findById(id)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> useCase.buscarPedido(id));
            verify(pedidoGateway, times(1)).findById(id);
        }
    }

    @Nested
    @DisplayName("criarPedido method")
    class CriarPedidoTest {

        @Test
        @DisplayName("With a valid Pedido containing items, should create and save it")
        void shouldCreateAndSavePedidoWithValidItems() {
            var pedidoItem = new PedidoItem(1L, null, 1L, 2, BigDecimal.valueOf(50), BigDecimal.valueOf(100));
            var pedidoInput = new Pedido(null, 1L, StatusPedidoEnum.EM_PREPARACAO, null, nowDateTime, null, List.of(pedidoItem));
            var pedidoSalvo = new Pedido(1L, 1L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.ZERO, nowDateTime, null, List.of());

            var categoria = new Categoria(1L, "Categoria de teste");

            var produtoResponse = new ProdutoResponse(
                    1L,
                    "Produto em teste",
                    "produto de testes",
                    categoria,
                    BigDecimal.valueOf(10),
                    Time.valueOf("00:15:00")
            );

            when(pedidoGateway.save(any())).thenReturn(pedidoSalvo);
            when(produtoGateway.listaProdutosPedidosSet(any())).thenReturn(Set.of(produtoResponse));

            var result = useCase.criarPedido(pedidoInput);

            assertNotNull(result);
            verify(pedidoGateway, times(2)).save(any());
            verify(produtoGateway, times(1)).listaProdutosPedidosSet(any());
        }

        @Test
        @DisplayName("With a Pedido containing no items, should throw ResponseStatusException")
        void shouldThrowResponseStatusExceptionWhenPedidoHasNoItems() {
            var pedidoInput = new Pedido(null, 1L, StatusPedidoEnum.EM_PREPARACAO, null, nowDateTime, null, List.of());
            var pedidoSalvo = new Pedido(1L, 1L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.ZERO, nowDateTime, null, List.of());

            when(pedidoGateway.save(any())).thenReturn(pedidoSalvo);
            when(produtoGateway.listaProdutosPedidosSet(any())).thenReturn(Set.of());

            assertThrows(ResponseStatusException.class, () -> useCase.criarPedido(pedidoInput));
        }
    }

    @Nested
    @DisplayName("listarPedidos method")
    class ListarPedidosTest {

        @Test
        @DisplayName("With a valid status, should return list of Pedidos")
        void shouldReturnListOfPedidosByStatus() {
            var status = StatusPedidoEnum.EM_PREPARACAO;
            var pedidos = List.of(
                    new Pedido(1L, 1L, status, BigDecimal.TEN, nowDateTime, Time.valueOf(LocalTime.NOON), List.of()),
                    new Pedido(2L, 2L, status, BigDecimal.ONE, nowDateTime, Time.valueOf(LocalTime.NOON), List.of())
            );
            when(pedidoGateway.findAllByStatus(status)).thenReturn(pedidos);

            var result = useCase.listarPedidos(status);

            assertNotNull(result);
            assertEquals(2, result.size());
            verify(pedidoGateway, times(1)).findAllByStatus(status);
        }

        @Test
        @DisplayName("With a status having no Pedidos, should return empty list")
        void shouldReturnEmptyListWhenStatusHasNoPedidos() {
            var status = StatusPedidoEnum.FINALIZADO;
            when(pedidoGateway.findAllByStatus(status)).thenReturn(List.of());

            var result = useCase.listarPedidos(status);

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(pedidoGateway, times(1)).findAllByStatus(status);
        }
    }

    @Nested
    @DisplayName("alterarPedido method")
    class AlterarPedidoTest {

        @Test
        @DisplayName("With a valid id and new status, should update and return Pedido")
        void shouldUpdateAndReturnPedidoWithNewStatus() {
            var id = 1L;
            var newStatus = StatusPedidoEnum.RECEBIDO;
            var pedidoExistente = new Pedido(id, 1L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.TEN, nowDateTime, Time.valueOf(LocalTime.NOON), List.of());
            var pedidoAtualizado = new Pedido(id, 1L, newStatus, BigDecimal.TEN, nowDateTime, Time.valueOf(LocalTime.NOON), List.of());

            when(pedidoGateway.findById(id)).thenReturn(Optional.of(pedidoExistente));
            when(pedidoGateway.save(any())).thenReturn(pedidoAtualizado);

            var result = useCase.alterarPedido(id, newStatus);

            assertNotNull(result);
            assertEquals(newStatus, result.status());
            verify(pedidoGateway, times(1)).findById(id);
            verify(pedidoGateway, times(1)).save(any());
        }

        @Test
        @DisplayName("With a non-existing id, should throw EntityNotFoundException")
        void shouldThrowEntityNotFoundExceptionWhenAlteringNonExistingPedido() {
            var id = 999L;
            var newStatus = StatusPedidoEnum.FINALIZADO;
            when(pedidoGateway.findById(id)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> useCase.alterarPedido(id, newStatus));
        }
    }

    @Nested
    @DisplayName("adicionarPedidoNaFila method")
    class AdicionarPedidoNaFilaTest {

        @Test
        @DisplayName("With a valid Pedido id, should add to queue and return FilaPedidosPreparacao")
        void shouldAddPedidoToQueueAndReturnFila() {
            var id = 1L;
            var pedido = new Pedido(id, 1L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.TEN, nowDateTime, Time.valueOf(LocalTime.NOON), List.of());
            var fila = new FilaPedidosPreparacao(1L, pedido);

            when(pedidoGateway.findById(id)).thenReturn(Optional.of(pedido));
            when(filaPedidosPreparacaoUseCase.salvar(any())).thenReturn(fila);

            var result = useCase.adicionarPedidoNaFila(id);

            assertNotNull(result);
            assertEquals(pedido.id(), result.pedido().id());
            verify(pedidoGateway, times(1)).findById(id);
            verify(filaPedidosPreparacaoUseCase, times(1)).salvar(any());
        }

        @Test
        @DisplayName("With a non-existing Pedido id, should throw EntityNotFoundException")
        void shouldThrowEntityNotFoundExceptionWhenPedidoDoesNotExist() {
            var id = 999L;
            when(pedidoGateway.findById(id)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> useCase.adicionarPedidoNaFila(id));
        }
    }

    @Nested
    @DisplayName("removerPedidoDaFila method")
    class RemoverPedidoDaFilaTest {

        @Test
        @DisplayName("With a valid Pedido id, should remove from queue")
        void shouldRemovePedidoFromQueue() {
            var id = 1L;
            var pedido = new Pedido(id, 1L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.TEN, nowDateTime, Time.valueOf(LocalTime.NOON), List.of());
            var fila = new FilaPedidosPreparacao(1L, pedido);

            when(filaPedidosPreparacaoUseCase.findByPedidoPorId(id)).thenReturn(fila);

            useCase.removerPedidoDaFila(id);

            verify(filaPedidosPreparacaoUseCase, times(1)).findByPedidoPorId(id);
            verify(filaPedidosPreparacaoUseCase, times(1)).removerPedidoDaFila(fila);
        }

        @Test
        @DisplayName("With a non-existing Pedido id, should throw EntityNotFoundException")
        void shouldThrowEntityNotFoundExceptionWhenPedidoNotInQueue() {
            var id = 999L;
            when(filaPedidosPreparacaoUseCase.findByPedidoPorId(id)).thenThrow(EntityNotFoundException.class);

            assertThrows(EntityNotFoundException.class, () -> useCase.removerPedidoDaFila(id));
        }
    }

    @Nested
    @DisplayName("listaPedidosOrd method")
    class ListaPedidosOrdTest {

        @Test
        @DisplayName("Should return list of ordered Pedidos")
        void shouldReturnOrderedListOfPedidos() {
            var pedidos = List.of(
                    new Pedido(1L, 1L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.TEN, nowDateTime, Time.valueOf(LocalTime.NOON), List.of()),
                    new Pedido(2L, 2L, StatusPedidoEnum.RECEBIDO, BigDecimal.TEN, nowDateTime, Time.valueOf(LocalTime.NOON), List.of())
            );
            when(pedidoGateway.findAllOrdenado()).thenReturn(pedidos);

            var result = useCase.listaPedidosOrd();

            assertNotNull(result);
            assertEquals(2, result.size());
            verify(pedidoGateway, times(1)).findAllOrdenado();
        }

        @Test
        @DisplayName("When no ordered Pedidos exist, should return empty list")
        void shouldReturnEmptyListWhenNoOrderedPedidosExist() {
            when(pedidoGateway.findAllOrdenado()).thenReturn(List.of());

            var result = useCase.listaPedidosOrd();

            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(pedidoGateway, times(1)).findAllOrdenado();
        }
    }
}
