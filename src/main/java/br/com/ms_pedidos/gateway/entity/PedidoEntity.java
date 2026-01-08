package br.com.ms_pedidos.gateway.entity;

import br.com.ms_pedidos.entity.Pedido;
import br.com.ms_pedidos.entity.enums.StatusPedidoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "pedidos")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"codigo"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @JoinColumn(name = "usuariocodigo")
    private Long usuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedidoEnum status;

    @Column(name = "valortotal")
    private BigDecimal valorTotal;

    @Column(name = "datahorasolicitacao")
    private LocalDateTime dataHoraSolicitacao;

    @Column(name = "tempototalpreparo")
    private Time tempoTotalPreparo;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoItemEntity> itens;

    public PedidoEntity(Long codigo){
        this.codigo = codigo;
    }

    public Pedido toModel() {
        return new Pedido(
                codigo,
                usuario != null ? usuario : null,
                status,
                valorTotal,
                dataHoraSolicitacao,
                tempoTotalPreparo,
                itens != null
                        ? itens.stream().map(PedidoItemEntity::toModel).toList()
                        : List.of()
        );
    }
}
