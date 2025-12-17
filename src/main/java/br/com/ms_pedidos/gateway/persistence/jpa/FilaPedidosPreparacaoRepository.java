package br.com.ms_pedidos.gateway.persistence.jpa;

import br.com.ms_pedidos.gateway.entity.FilaPedidosPreparacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilaPedidosPreparacaoRepository extends JpaRepository<FilaPedidosPreparacaoEntity,Long> {
    Optional<FilaPedidosPreparacaoEntity> findByPedidocodigoCodigo(Long id);
}
