package com.gestorventas.deposito.controllers;

import com.gestorventas.deposito.dto.in.ClienteDto;
import com.gestorventas.deposito.dto.out.ClienteResponseDto;
import com.gestorventas.deposito.models.Cliente;
import com.gestorventas.deposito.services.ClienteService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


/**
 * Controlador REST encargado de exponer los endpoints relacionados con los Clientes,
 * anidados bajo el recurso Vendedor.
 *
 * @author Kevin William Olarte Braun
 */
@RestController
@RequestMapping("/api/vendedor/{idVendedor}/cliente")
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    /**
     * Crear un nuevo cliente.
     *
     * @param idVendedor identificador del vendedor al que pertenece el cliente
     * @return DTO con los datos del cliente creado
     */
    @PostMapping
    public ResponseEntity<ClienteResponseDto> add(
            @PathVariable Long idVendedor,
            @RequestBody ClienteDto clienteDto

    ) {
        return ResponseEntity.ok(clienteService.add(clienteDto.getNombre(),idVendedor));
    }


    /**
     * Listar todos los clientes de ese vendedor.
     * @param idVendedor identificador del Vendedor
     * @return lista de DTOs con todos los vendedores
     */
    @GetMapping
    public ResponseEntity<List<ClienteResponseDto>> getAll(
            @PathVariable Long idVendedor
    ) {
        return ResponseEntity.ok(clienteService.getAll(idVendedor));
    }

    /**
     * Obtener un cliente por su ID.
     *
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @return DTO con los datos del vendedor
     */
    @GetMapping("/{idCliente}")
    public ResponseEntity<ClienteResponseDto> get(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente
    ) {

        return ResponseEntity.ok(clienteService.get(idVendedor, idCliente));
    }

    /**
     * Actualizar los datos de un cliente existente.
     *
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @param dto datos actualizados
     * @return DTO con los datos del vendedor actualizado
     */
    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteResponseDto> update(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @RequestBody ClienteDto dto
    ) {
        Cliente cliente = clienteService.update(idCliente, dto.getNombre(), idVendedor);
        return ResponseEntity.ok(new ClienteResponseDto(cliente));
    }


    /**
     * Eliminar un cliente por su ID.
     *
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @return código 204 si se eliminó correctamente
     */
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> delete(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente
    ) {

        clienteService.delete(idCliente, idVendedor);
        return ResponseEntity.noContent().build();
    }
}

