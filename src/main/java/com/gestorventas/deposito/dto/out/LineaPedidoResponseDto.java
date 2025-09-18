package com.gestorventas.deposito.dto.out;

import com.gestorventas.deposito.models.LineaPedido;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de salida para representar los datos públicos de una Linea de pedido del sistema.
 * <p>
 * Esta clase se utiliza para devolver información básica de una linea de pedido en las respuestas
 * de la API, sin incluir detalles.
 * </p>
 *
 * @author Kevin William Olarte Braun
 */
@Getter
@Setter
public class LineaPedidoResponseDto {
    private Long id;
    private Long idPedido;
    private Long idProducto;
    private Integer cantidad;
    private Double precio;

    public LineaPedidoResponseDto(LineaPedido lineaPedido) {
        this.id = lineaPedido.getId();
        this.idPedido = lineaPedido.getPedido().getId();
        this.idProducto = lineaPedido.getProducto().getId();
        this.cantidad = lineaPedido.getCantidad();
        this.precio = lineaPedido.getPrecio();
    }
}
