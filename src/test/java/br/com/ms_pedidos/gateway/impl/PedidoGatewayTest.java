package br.com.ms_pedidos.gateway.impl;

import br.com.ms_pedidos.controller.mapper.PedidoMapper;
import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;
import br.com.ms_pedidos.gateway.entity.PedidoEntity;
import br.com.ms_pedidos.gateway.persistence.jpa.PedidoRepository;
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

@DisplayName("Given the class PedidoGateway")
@ExtendWith(MockitoExtension.class)
public class PedidoGatewayTest {

    @Mock
    private PedidoRepository pedidoRepository;

    private PedidoGateway pedidoGateway;

    @BeforeEach
    void setup() {
        pedidoGateway = new PedidoGateway(pedidoRepository);
    }

    @Nested
    @DisplayName("When calling findById")
    class FindByIdTests {

        @Mock
        private PedidoEntity pedidoEntity;

        @Test
        @DisplayName("With a valid id, should return the Pedido")
        void testFindByIdWithValidId() {
            Long id = 1L;
            Pedido expectedPedido = new Pedido(id, 1L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.TEN, LocalDateTime.now(), Time.valueOf(LocalTime.NOON), List.of());

            when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedidoEntity));
            when(pedidoEntity.toModel()).thenReturn(expectedPedido);

            Optional<Pedido> result = pedidoGateway.findById(id);

            assertTrue(result.isPresent());
            assertEquals(expectedPedido, result.get());
            verify(pedidoRepository, times(1)).findById(id);
        }

        @Test
        @DisplayName("With an invalid id, should return empty Optional")
        void testFindByIdWithInvalidId() {
            Long id = 999L;

            when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

            Optional<Pedido> result = pedidoGateway.findById(id);

            assertTrue(result.isEmpty());
            verify(pedidoRepository, times(1)).findById(id);
        }
    }


    @Nested
    @DisplayName("When calling save")
    class SaveTests {

        @Mock
        private PedidoEntity pedidoEntity;

        @Test
        @DisplayName("With a valid Pedido, should save and return the model")
        void testSaveWithValidPedido() {
            Pedido pedido = new Pedido(null, 1L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.TEN, LocalDateTime.now(), Time.valueOf(LocalTime.NOON), List.of());
            Pedido expectedPedido = new Pedido(1L, 1L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.TEN, LocalDateTime.now(), Time.valueOf(LocalTime.NOON), List.of());

            when(pedidoRepository.save(any())).thenReturn(pedidoEntity);
            when(pedidoEntity.toModel()).thenReturn(expectedPedido);

            Pedido result = pedidoGateway.save(pedido);

            assertNotNull(result);
            assertEquals(expectedPedido, result);
            verify(pedidoRepository, times(1)).save(any());
        }
    }

    @Nested
    @DisplayName("When calling findAllByStatus")
    class FindAllByStatusTests {

        @Mock
        private PedidoEntity entity1;

        @Mock
        private PedidoEntity entity2;

        @Test
        @DisplayName("With a valid status, should return list of Pedidos")
        void testFindAllByStatusWithValidStatus() {
            StatusPedidoEnum status = StatusPedidoEnum.EM_PREPARACAO;
            Pedido pedido1 = new Pedido(1L, 1L, status, BigDecimal.TEN, LocalDateTime.now(), Time.valueOf(LocalTime.NOON), List.of());
            Pedido pedido2 = new Pedido(2L, 2L, status, BigDecimal.TEN, LocalDateTime.now(), Time.valueOf(LocalTime.NOON), List.of());

            when(pedidoRepository.findAllByStatus(status)).thenReturn(List.of(entity1, entity2));
            when(entity1.toModel()).thenReturn(pedido1);
            when(entity2.toModel()).thenReturn(pedido2);

            List<Pedido> result = pedidoGateway.findAllByStatus(status);

            assertEquals(2, result.size());
            assertEquals(List.of(pedido1, pedido2), result);
            verify(pedidoRepository, times(1)).findAllByStatus(status);
        }

        @Test
        @DisplayName("With no results, should return empty list")
        void testFindAllByStatusWithNoResults() {
            StatusPedidoEnum status = StatusPedidoEnum.FINALIZADO;

            when(pedidoRepository.findAllByStatus(status)).thenReturn(List.of());

            List<Pedido> result = pedidoGateway.findAllByStatus(status);

            assertTrue(result.isEmpty());
            verify(pedidoRepository, times(1)).findAllByStatus(status);
        }
    }

    @Nested
    @DisplayName("When calling findAllOrdenado")
    class FindAllOrdenadoTests {

        @Mock
        private PedidoEntity entity1;

        @Mock
        private PedidoEntity entity2;

        @Test
        @DisplayName("With results, should return ordered list of Pedidos")
        void testFindAllOrdenadoWithResults() {
            Pedido pedido1 = new Pedido(1L, 1L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.TEN, LocalDateTime.now(), Time.valueOf(LocalTime.NOON), List.of());
            Pedido pedido2 = new Pedido(2L, 2L, StatusPedidoEnum.EM_PREPARACAO, BigDecimal.TEN, LocalDateTime.now(), Time.valueOf(LocalTime.NOON), List.of());

            when(pedidoRepository.findAllOrdenado()).thenReturn(List.of(entity1, entity2));
            when(entity1.toModel()).thenReturn(pedido1);
            when(entity2.toModel()).thenReturn(pedido2);

            List<Pedido> result = pedidoGateway.findAllOrdenado();

            assertEquals(2, result.size());
            assertEquals(List.of(pedido1, pedido2), result);
            verify(pedidoRepository, times(1)).findAllOrdenado();
        }

        @Test
        @DisplayName("With no results, should return empty list")
        void testFindAllOrdenadoWithNoResults() {
            when(pedidoRepository.findAllOrdenado()).thenReturn(List.of());

            List<Pedido> result = pedidoGateway.findAllOrdenado();

            assertTrue(result.isEmpty());
            verify(pedidoRepository, times(1)).findAllOrdenado();
        }
}
}
