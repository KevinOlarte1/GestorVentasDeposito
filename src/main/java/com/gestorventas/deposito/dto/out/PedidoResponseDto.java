package com.gestorventas.deposito.dto.out;

import com.gestorventas.deposito.models.LineaPedido;
import com.gestorventas.deposito.models.Pedido;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PedidoResponseDto {
    private Long id;
    private LocalDate fecha;
    private Long idCliente;
    private Long idVendedor;
    private List<Long> idLineaPedido;

    public PedidoResponseDto(Pedido pedido) {
        this.id = pedido.getId();
        this.fecha = pedido.getFecha();
        this.idCliente = pedido.getCliente().getId();
        this.idVendedor = pedido.getVendedor().getId();
        this.idLineaPedido = pedido.getLineas()
                .stream()
                .map(LineaPedido::getId)
                .toList();
    }
}
