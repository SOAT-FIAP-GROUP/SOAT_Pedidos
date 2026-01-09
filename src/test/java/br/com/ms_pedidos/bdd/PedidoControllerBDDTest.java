package br.com.ms_pedidos.bdd;

import br.com.ms_pedidos.controller.PedidoController;
import br.com.ms_pedidos.controller.mapper.PedidoMapper;
import br.com.ms_pedidos.controller.mapper.dto.response.PedidoResponse;
import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.mocks.MockGenerator;
import br.com.ms_pedidos.usecase.IPedidoUseCase;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PedidoControllerBDDTest {
    private static final Long ID = 1L;
    private PedidoController pedidoController;
    private IPedidoUseCase pedidoUseCase;
    private PedidoMapper pedidoMapper;

    private Pedido pedido;
    private PedidoResponse response;
    private PedidoResponse resultado;

    @Before
    public void setUp() {
        pedidoUseCase = mock(IPedidoUseCase.class);
        pedidoMapper = mock(PedidoMapper.class);
        pedidoController = new PedidoController(pedidoUseCase, pedidoMapper);
    }

    @Given("que foi cadastrado um pedido no sistema")
    public void que_foi_cadastrado_um_pedido_no_sistema() {
        this.pedido = MockGenerator.generatePedidoMock();
        this.response = MockGenerator.generatePedidoResponseMock();
    }

    @When("o servico invocar a funcao de buscar pedido")
    public void o_servico_invocar_a_funcao_de_buscar_pedido() {
        when(pedidoUseCase.buscarPedido(anyLong())).thenReturn(pedido);
        when(pedidoMapper.toResponse(pedido)).thenReturn(response);
        this.resultado = pedidoController.buscarPedido(ID);
    }

    @Then("a funcao deve ser executada com sucesso")
    public void a_funcao_deve_ser_executada_com_sucesso() {
        assertEquals(this.response, this.resultado);
        verify(pedidoUseCase).buscarPedido(ID);
        verify(pedidoMapper).toResponse(pedido);
    }
}

