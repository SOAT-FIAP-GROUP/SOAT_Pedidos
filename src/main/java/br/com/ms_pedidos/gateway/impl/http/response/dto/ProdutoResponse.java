package br.com.ms_pedidos.gateway.impl.http.response.dto;

import java.math.BigDecimal;
import java.sql.Time;

public record ProdutoResponse(Long id, String nome, String descricao, Categoria categoria, BigDecimal preco, Time tempopreparo) {
}
