package br.com.ms_pedidos;

import br.com.ms_pedidos.usecase.impl.FilaPedidosPreparacaoUseCaseTest;
import br.com.ms_pedidos.usecase.impl.PedidoUseCaseTest;
import org.junit.jupiter.api.Nested;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsPedidosApplicationTests {

	@Nested
	class FilaPedidosPreparacaoTests extends FilaPedidosPreparacaoUseCaseTest {}

	@Nested
	class PedidoUseCaseTests extends PedidoUseCaseTest {}
}
