package br.com.ms_pedidos;

import br.com.ms_pedidos.usecase.impl.FilaPedidosPreparacaoUseCaseTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsPedidosApplicationTests {

	@Nested
	class FilaPedidosPreparacaoTests extends FilaPedidosPreparacaoUseCaseTest {}
}
