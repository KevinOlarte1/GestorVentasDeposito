package com.gestorventas.deposito.dto.out;

import com.gestorventas.deposito.models.Cliente;
import com.gestorventas.deposito.models.Pedido;

import java.util.List;

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
