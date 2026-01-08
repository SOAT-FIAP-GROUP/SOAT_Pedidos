package br.com.ms_pedidos.usecase.impl;

import br.com.ms_pedidos.entity.FilaPedidosPreparacao;
import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.exception.EntityNotFoundException;
import br.com.ms_pedidos.gateway.IFilaPedidosPreparacaoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given the class FilaPedidosPreparacaoUseCase")
public class FilaPedidosPreparacaoUseCaseTest {

    @Mock
    private IFilaPedidosPreparacaoGateway gateway;

    @Mock
    private Pedido pedido;

    private FilaPedidosPreparacaoUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new FilaPedidosPreparacaoUseCase(gateway);
    }

    @Nested
    @DisplayName("Salvar method")
    class SalvarTest {

        @Test
        @DisplayName("With a valid FilaPedidosPreparacao, should save and return it")
        void shouldSaveAndReturnFilaPedidosPreparacao() {
            var filaPedidos = new FilaPedidosPreparacao(1L, pedido);
            when(gateway.save(any())).thenReturn(filaPedidos);

            var result = useCase.salvar(filaPedidos);

            assertNotNull(result);
            verify(gateway, times(1)).save(filaPedidos);
        }
    }

    @Nested
    @DisplayName("FindByPedidoPorId method")
    class FindByPedidoPorIdTest {

        @Test
        @DisplayName("With an existing id, should return FilaPedidosPreparacao")
        void shouldReturnFilaPedidosPreparacaoWhenIdExists() {
            var id = 1L;
            var filaPedidos = new FilaPedidosPreparacao(id, pedido);
            when(gateway.findByPedidocodigoId(id)).thenReturn(Optional.of(filaPedidos));

            var result = useCase.findByPedidoPorId(id);

            assertNotNull(result);
            verify(gateway, times(1)).findByPedidocodigoId(id);
        }

        @Test
        @DisplayName("With a non-existing id, should throw EntityNotFoundException")
        void shouldThrowEntityNotFoundExceptionWhenIdDoesNotExist() {
            var id = 999L;
            when(gateway.findByPedidocodigoId(id)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () -> useCase.findByPedidoPorId(id));
            verify(gateway, times(1)).findByPedidocodigoId(id);
        }
    }

    @Nested
    @DisplayName("RemoverPedidoDaFila method")
    class RemoverPedidoDaFilaTest {

        @Test
        @DisplayName("With a valid FilaPedidosPreparacao, should remove from queue")
        void shouldRemovePedidoFromQueue() {
            var filaPedidos = new FilaPedidosPreparacao(1L, pedido);

            useCase.removerPedidoDaFila(filaPedidos);

            verify(gateway, times(1)).removerPedidoDaFila(filaPedidos);
        }
    }
}
