package br.com.ms_pedidos.config;

import br.com.ms_pedidos.gateway.IFilaPedidosPreparacaoGateway;
import br.com.ms_pedidos.gateway.impl.FilaPedidosPreparacaoGateway;
import br.com.ms_pedidos.gateway.persistence.jpa.FilaPedidosPreparacaoRepository;
import br.com.ms_pedidos.usecase.IFilaPedidosPreparacaoUseCase;
import br.com.ms_pedidos.usecase.impl.FilaPedidosPreparacaoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilaPedidoConfig {

    @Bean
    IFilaPedidosPreparacaoGateway filaPedidoGateway(FilaPedidosPreparacaoRepository filaPedidosPreparacaoRepository) {
        return new FilaPedidosPreparacaoGateway(filaPedidosPreparacaoRepository);
    }

    @Bean
    FilaPedidosPreparacaoUseCase filaPedidoUserCase(IFilaPedidosPreparacaoGateway filaPedidosPreparacaoGateway){
        return new FilaPedidosPreparacaoUseCase(filaPedidosPreparacaoGateway);
    }
}
