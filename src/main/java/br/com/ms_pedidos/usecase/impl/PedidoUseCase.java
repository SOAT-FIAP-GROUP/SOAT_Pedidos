package br.com.ms_pedidos.usecase.impl;

import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.entity.PedidoItem;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;
import br.com.ms_pedidos.exception.EntityNotFoundException;
import br.com.ms_pedidos.gateway.IPedidoGateway;
import br.com.ms_pedidos.gateway.IProdutoGateway;
import br.com.ms_pedidos.gateway.impl.http.response.dto.ProdutoResponse;
import br.com.ms_pedidos.usecase.IPedidoUseCase;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class PedidoUseCase implements IPedidoUseCase {
    private final IPedidoGateway pedidoGateway;
    private final IProdutoGateway produtoGateway;

    public PedidoUseCase(IPedidoGateway pedidoGateway, IProdutoGateway produtoGateway) {
        this.pedidoGateway = pedidoGateway;
        this.produtoGateway = produtoGateway;
    }

    @Override
    public Pedido buscarPedido(Long id) {
        return pedidoGateway.findById(id).orElseThrow(() -> new EntityNotFoundException(Pedido.class, id));
    }

    @Transactional
    @Override
    public Pedido criarPedido(Pedido pedido) {

        Pedido pedidoSalvar = pedido.preSalvar(pedido.idUsuario(), pedido.status(),pedido.dataHoraSolicitacao());

        Set<ProdutoResponse> listProdutos = produtoGateway.listaProdutosPedidosSet(pedido.itens().stream().map(PedidoItem::id).collect(Collectors.toSet()));

        Pedido pedidoSalvo = pedidoGateway.save(pedidoSalvar);

        AtomicReference<Duration> totalPreparo = new AtomicReference<>(Duration.ZERO);

        List<PedidoItem> itens = pedido.itens().stream()
                .map(item -> {
                    ProdutoResponse produtoResponse = listProdutos.stream()
                            .filter(p ->item.id().equals(p.id()))
                            .findFirst()
                            .orElse(null);

//                    Duration tempoItem = Duration.between(LocalTime.MIDNIGHT, produtoResponse != null ? produtoResponse.tempopreparo().toLocalTime() : null)
//                            .multipliedBy(item.quantidade());

                    Duration tempoItem;
                    if(produtoResponse != null){
                        tempoItem = Duration.between(LocalTime.MIDNIGHT, produtoResponse.tempopreparo().toLocalTime()).multipliedBy(item.quantidade());
                    }
                    else tempoItem = null;

                    totalPreparo.updateAndGet(tp -> tp.plus(tempoItem));
                    return new PedidoItem(null, pedidoSalvo.id(), produtoResponse.id(), item.quantidade(), produtoResponse.preco(),
                            produtoResponse.preco().multiply(BigDecimal.valueOf(item.quantidade())));
                }).toList();

        if (itens.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "O pedido deve conter ao menos um item.");
        }

        BigDecimal totalAPagar = itens.stream()
                .map(PedidoItem::precoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Duration duracao = totalPreparo.get();
        LocalTime tempoTotal = LocalTime.of(
                duracao.toHoursPart(),
                duracao.toMinutesPart(),
                duracao.toSecondsPart()
        );
        Time tempoTotalSql = Time.valueOf(tempoTotal);

        Pedido pedidoAtualizar = new Pedido(
                pedidoSalvo.id(),
                pedido.idUsuario(),
                pedido.status(),
                totalAPagar,
                pedido.dataHoraSolicitacao(),
                tempoTotalSql,
                itens
        );
        return pedidoGateway.save(pedidoAtualizar);
    }

    @Override
    public List<Pedido> listarPedidos(StatusPedidoEnum status) {
        return pedidoGateway.findAllByStatus(status);
    }

    @Override
    public Pedido alterarPedido(Long id, StatusPedidoEnum status) {
        Pedido pedido = this.buscarPedido(id);
        Pedido pedidoAtualizar = pedido.withStatus(status);
        return pedidoGateway.save(pedidoAtualizar);
    }

    @Override
    public List<Pedido> listaPedidosOrd() {
        return pedidoGateway.findAllOrdenado();
    }

}
