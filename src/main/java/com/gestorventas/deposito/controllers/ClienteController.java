package com.gestorventas.deposito.controllers;

import com.gestorventas.deposito.dto.in.ClienteDto;
import com.gestorventas.deposito.dto.out.ClienteResponseDto;
import com.gestorventas.deposito.models.Cliente;
import com.gestorventas.deposito.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    @Operation(summary = "Crear un nuevo cliente", description = "Crea un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interlo", content = @Content) //TODO: CAMBIAR ESTO, FASE PRUIEBA
    })
    public ResponseEntity<ClienteResponseDto> add(
            @PathVariable Long idVendedor,
            @RequestBody ClienteDto clienteDto

    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.add(clienteDto.getNombre(),idVendedor));
    }


    /**
     * Listar todos los clientes de ese vendedor.
     * @param idVendedor identificador del Vendedor
     * @return lista de DTOs con todos los vendedores
     */
    @GetMapping
    @Operation(summary = "Listar todos los clientes de un vendedor", description = "Listar todos los clientes de un vendedor")
    @ApiResponse(responseCode = "200", description = "Lista de clientes encontrados")
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
    @Operation(summary = "Obtener un cliente por su ID", description = "Obtener un cliente por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado", content = @Content)
    })
    public ResponseEntity<ClienteResponseDto> get(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente) {

        ClienteResponseDto cliente = clienteService.get(idVendedor, idCliente);
        if (cliente == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cliente);
    }

    /**
     * Actualizar los datos de un cliente existente.
     *
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @param dto datos actualizados
     * @return DTO con los datos del vendedor actualizado
     */
    @Operation(summary = "Actualizar los datos de un cliente", description = "Actualiza los datos de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado/ datos incorrectos a actualizar", content = @Content)
    })
    @PutMapping("/{idCliente}")
    public ResponseEntity<ClienteResponseDto> update(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @RequestBody ClienteDto dto
    ) {
        ClienteResponseDto cliente = clienteService.update(idCliente, dto.getNombre(), idVendedor);
        if (cliente == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cliente);
    }


    /**
     * Eliminar un cliente por su ID.
     *
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @return código 204 si se eliminó correctamente
     */
    @Operation(summary = "Eliminar un cliente", description = "Elimina un cliente del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado")
    })
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> delete(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente
    ) {

        clienteService.delete(idCliente, idVendedor);
        return ResponseEntity.noContent().build();
    }
}

