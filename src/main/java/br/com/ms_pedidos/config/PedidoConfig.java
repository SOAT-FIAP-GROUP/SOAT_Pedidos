package br.com.ms_pedidos.config;

import br.com.ms_pedidos.api.controller.PedidoAPIController;
import br.com.ms_pedidos.controller.PedidoController;
import br.com.ms_pedidos.controller.mapper.PedidoItemMapper;
import br.com.ms_pedidos.controller.mapper.PedidoMapper;
import br.com.ms_pedidos.gateway.IPedidoGateway;
import br.com.ms_pedidos.gateway.IProdutoGateway;
import br.com.ms_pedidos.gateway.impl.PedidoGateway;
import br.com.ms_pedidos.gateway.persistence.jpa.PedidoRepository;
import br.com.ms_pedidos.usecase.IPedidoUseCase;
import br.com.ms_pedidos.usecase.impl.PedidoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PedidoConfig {

    @Bean
    PedidoAPIController pedidoAPIController(PedidoController pedidoController) {
        return new PedidoAPIController(pedidoController);
    }

    @Bean
    PedidoController pedidoController(IPedidoUseCase pedidoUseCase, PedidoMapper pedidoMapper) {
        return new PedidoController(pedidoUseCase, pedidoMapper);
    }

    @Bean
    PedidoMapper pedidoMapper() {
        return new PedidoMapper();
    }

    @Bean
    PedidoItemMapper pedidoItemMapper() {
        return new PedidoItemMapper();
    }

    @Bean
    PedidoUseCase pedidoUseCase(IPedidoGateway pedidoGateway, IProdutoGateway produtoGateway) {
        return new PedidoUseCase(pedidoGateway, produtoGateway);
    }

    @Bean
    PedidoGateway pedidoGateway(PedidoRepository pedidoRepository) {
        return new PedidoGateway(pedidoRepository);
    }

}