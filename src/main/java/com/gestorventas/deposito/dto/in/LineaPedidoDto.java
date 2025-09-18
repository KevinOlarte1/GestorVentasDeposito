package com.gestorventas.deposito.dto.in;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineaPedidoDto {
    private Long idPedido;
    private Long idProducto;
    private Integer cantidad;
    private Double precio;
}
