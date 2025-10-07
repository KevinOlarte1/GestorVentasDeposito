package com.gestorventas.deposito.dto.out;

import com.gestorventas.deposito.models.Cliente;
import com.gestorventas.deposito.models.Pedido;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO de salida para representar los datos públicos de un cliente del sistema.
 * <p>
 * Esta clase se utiliza para devolver información básica del pedido en las respuestas
 * de la API, sin incluir detalles.
 * </p>
 *
 * @author Kevin William Olarte Braun
 */
@Getter
@Setter
public class ClienteResponseDto {
    private Long id;
    private String nombre;
    private Long idVendedor;
    private List<Long> idPedidos;

    public ClienteResponseDto(Cliente cliente) {
        this.id = cliente.getId();
        this.nombre = cliente.getNombre();
        this.idVendedor = cliente.getVendedor().getId();
        this.idPedidos = cliente.getPedidos()
                .stream()
                .map(Pedido::getId)
                .toList();
    }
}
