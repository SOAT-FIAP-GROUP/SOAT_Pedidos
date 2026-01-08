package br.com.ms_pedidos.gateway.impl;

import br.com.ms_pedidos.controller.mapper.FilaPedidosPreparacaoMapper;
import br.com.ms_pedidos.entity.FilaPedidosPreparacao;
import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;
import br.com.ms_pedidos.gateway.entity.FilaPedidosPreparacaoEntity;
import br.com.ms_pedidos.gateway.persistence.jpa.FilaPedidosPreparacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Given the class FilaPedidosPreparacaoGateway")
@ExtendWith(MockitoExtension.class)
public class FilaPedidosPreparacaoGatewayTest {

    @Mock
    private FilaPedidosPreparacaoRepository filaPedidosPreparacaoRepository;

    private FilaPedidosPreparacaoGateway filaPedidosPreparacaoGateway;

    @BeforeEach
    void setup() {
        filaPedidosPreparacaoGateway = new FilaPedidosPreparacaoGateway(filaPedidosPreparacaoRepository);
    }

    private Pedido createPedido(Long codigo) {
        return new Pedido(codigo, 1L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.TEN, LocalDateTime.now(), Time.valueOf(LocalTime.NOON), List.of());
    }

    @Nested
    @DisplayName("When calling save")
    class SaveTests {

        @Mock
        private FilaPedidosPreparacaoEntity entity;

        @Test
        @DisplayName("With a valid FilaPedidosPreparacao, should save and return the model")
        void testSaveWithValidFilaPedidosPreparacao() {
            Pedido pedido = createPedido(1L);
            FilaPedidosPreparacao filaPedidosPreparacao = new FilaPedidosPreparacao(null, pedido);
            FilaPedidosPreparacao expectedResult = new FilaPedidosPreparacao(1L, pedido);

            when(filaPedidosPreparacaoRepository.save(any())).thenReturn(entity);
            when(entity.toModel()).thenReturn(expectedResult);

            FilaPedidosPreparacao result = filaPedidosPreparacaoGateway.save(filaPedidosPreparacao);

            assertNotNull(result);
            assertEquals(expectedResult, result);
            verify(filaPedidosPreparacaoRepository, times(1)).save(any());
        }
    }

    @Nested
    @DisplayName("When calling findById")
    class FindByIdTests {

        @Mock
        private FilaPedidosPreparacaoEntity entity;

        @Test
        @DisplayName("With a valid id, should return the FilaPedidosPreparacao")
        void testFindByIdWithValidId() {
            Long id = 1L;
            Pedido pedido = createPedido(1L);
            FilaPedidosPreparacao expectedResult = new FilaPedidosPreparacao(id, pedido);

            when(filaPedidosPreparacaoRepository.findById(id)).thenReturn(Optional.of(entity));
            when(entity.toModel()).thenReturn(expectedResult);

            Optional<FilaPedidosPreparacao> result = filaPedidosPreparacaoGateway.findById(id);

            assertTrue(result.isPresent());
            assertEquals(expectedResult, result.get());
            verify(filaPedidosPreparacaoRepository, times(1)).findById(id);
        }

        @Test
        @DisplayName("With an invalid id, should return empty Optional")
        void testFindByIdWithInvalidId() {
            Long id = 999L;

            when(filaPedidosPreparacaoRepository.findById(id)).thenReturn(Optional.empty());

            Optional<FilaPedidosPreparacao> result = filaPedidosPreparacaoGateway.findById(id);

            assertTrue(result.isEmpty());
            verify(filaPedidosPreparacaoRepository, times(1)).findById(id);
        }
    }

    @Nested
    @DisplayName("When calling findByPedidocodigoId")
    class FindByPedidocodigoIdTests {

        @Mock
        private FilaPedidosPreparacaoEntity entity;

        @Test
        @DisplayName("With a valid pedido id, should return the FilaPedidosPreparacao")
        void testFindByPedidocodigoIdWithValidId() {
            Long pedidoId = 1L;
            Pedido pedido = createPedido(pedidoId);
            FilaPedidosPreparacao expectedResult = new FilaPedidosPreparacao(1L, pedido);

            when(filaPedidosPreparacaoRepository.findByPedidocodigoCodigo(pedidoId)).thenReturn(Optional.of(entity));
            when(entity.toModel()).thenReturn(expectedResult);

            Optional<FilaPedidosPreparacao> result = filaPedidosPreparacaoGateway.findByPedidocodigoId(pedidoId);

            assertTrue(result.isPresent());
            assertEquals(expectedResult, result.get());
            verify(filaPedidosPreparacaoRepository, times(1)).findByPedidocodigoCodigo(pedidoId);
        }

        @Test
        @DisplayName("With an invalid pedido id, should return empty Optional")
        void testFindByPedidocodigoIdWithInvalidId() {
            Long pedidoId = 999L;

            when(filaPedidosPreparacaoRepository.findByPedidocodigoCodigo(pedidoId)).thenReturn(Optional.empty());

            Optional<FilaPedidosPreparacao> result = filaPedidosPreparacaoGateway.findByPedidocodigoId(pedidoId);

            assertTrue(result.isEmpty());
            verify(filaPedidosPreparacaoRepository, times(1)).findByPedidocodigoCodigo(pedidoId);
        }
    }

    @Nested
    @DisplayName("When calling removerPedidoDaFila")
    class RemoverPedidoDaFilaTests {

        @Test
        @DisplayName("With a valid FilaPedidosPreparacao, should remove from queue")
        void testRemoverPedidoDaFilaWithValidEntity() {
            Pedido pedido = createPedido(1L);
            FilaPedidosPreparacao filaPedidosPreparacao = new FilaPedidosPreparacao(1L, pedido);

            filaPedidosPreparacaoGateway.removerPedidoDaFila(filaPedidosPreparacao);

            verify(filaPedidosPreparacaoRepository, times(1)).delete(any());
        }
    }
}
