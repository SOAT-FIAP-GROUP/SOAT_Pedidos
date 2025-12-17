package br.com.ms_pedidos.gateway.entity;

import br.com.ms_pedidos.entity.PedidoItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "pedidoitem")
@Data
@Builder
@Entity
@EqualsAndHashCode(of = "codigo")
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "pedidocodigo")
    @JsonIgnore
    private PedidoEntity pedido;

    private Long produtoCodigo;

    private int quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal precoTotal;


    public PedidoItem toModel() {
        return new PedidoItem(this.codigo, this.pedido.getCodigo(), this.produtoCodigo, this.quantidade, this.precoUnitario, this.precoTotal);
    }

}
