package br.com.ms_pedidos.api.controller;

import br.com.ms_pedidos.controller.PedidoController;
import br.com.ms_pedidos.controller.mapper.dto.request.PedidoRequest;
import br.com.ms_pedidos.controller.mapper.dto.response.PedidoResponse;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedido")
public class PedidoAPIController {
    private final PedidoController pedidoController;

    public PedidoAPIController(PedidoController pedidoController) {
        this.pedidoController = pedidoController;
    }

    @Operation(summary = "Buscar pedido por codigo", description = "Retorna um pedido especifico cadastrado no codigo")
    @GetMapping("/buscar/{codigoPedido}")
    public ResponseEntity<PedidoResponse> buscarPedido(@PathVariable Long codigoPedido) throws Exception {
        var response = pedidoController.buscarPedido(codigoPedido);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Criar pedido", description = "Retorna um novo Pedido")
    @PostMapping
    public ResponseEntity<PedidoResponse> criarPedido(@RequestBody PedidoRequest pedidoRequest) throws Exception {
        var response = pedidoController.criarPedido(pedidoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar pedidos por status", description = "Lista um pedido a partir de um dos status pré definidos")
    public ResponseEntity<List<PedidoResponse>> listarPedidos(@RequestParam StatusPedidoEnum status) {
        var response = pedidoController.listarPedidos(status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/status/{codigo}")
    @Transactional
    @Operation(summary = "Alterar o status do pedido", description = "Altera o status de um pedido com base no código do pedido e status pré definidos")
    public ResponseEntity<?> alterarStatusPedido(@PathVariable Long codigo, @RequestParam StatusPedidoEnum status) {
        var response = pedidoController.alterarPedido(codigo, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("remover/fila/{codigoPedido}")
    @Transactional
    @Operation(summary = "Remove pedido da fila de preparo", description = "Remove pedido da fila de preparo com base no código do pedido")
    public ResponseEntity<Void> removerPedidoDaFilaDePreparo(@PathVariable Long codigoPedido) {
        pedidoController.removerPedidoDaFila(codigoPedido);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("listarPedidos")
    @Transactional
    @Operation(summary = "Lista pedidos de forma ordenada", description = "Lista pedidos de forma ordenada por Status e Tempo")
    public ResponseEntity<List<PedidoResponse>> listaPedidosOrdenadados(){
        pedidoController.listaPedidosOrd();
        return ResponseEntity.status(HttpStatus.OK).body(pedidoController.listaPedidosOrd());
    }
}
