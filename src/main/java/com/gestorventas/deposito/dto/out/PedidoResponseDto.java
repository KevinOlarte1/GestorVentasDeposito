package com.gestorventas.deposito.dto.out;

import com.gestorventas.deposito.models.LineaPedido;
import com.gestorventas.deposito.models.Pedido;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO de salida para representar los datos públicos de un pedido del sistema.
 * <p>
 * Esta clase se utiliza para devolver información básica del pedido en las respuestas
 * de la API, sin incluir detalles.
 * </p>
 *
 * @author Kevin William Olarte Braun
 */
@Getter
@Setter
public class PedidoResponseDto {
    private Long id;
    private LocalDate fecha;
    private Long idCliente;
    private List<Long> idLineaPedido;
    private boolean cerrado;

    public PedidoResponseDto(Pedido pedido) {
        this.id = pedido.getId();
        this.fecha = pedido.getFecha();
        this.idCliente = pedido.getCliente().getId();
        this.idLineaPedido = pedido.getLineas()
                .stream()
                .map(LineaPedido::getId)
                .toList();
        this.cerrado = pedido.isFinalizado();
    }
}
