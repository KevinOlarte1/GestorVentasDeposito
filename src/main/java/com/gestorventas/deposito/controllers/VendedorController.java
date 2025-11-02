package com.gestorventas.deposito.controllers;

import com.gestorventas.deposito.dto.in.VendedorDto;
import com.gestorventas.deposito.dto.out.VendedorResponseDto;
import com.gestorventas.deposito.enums.Role;
import com.gestorventas.deposito.services.VendedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST encargado de exponer los endpoints relacionados con los Vendedores.
 * <p>
 * Permite crear, consultar, actualizar y eliminar vendedores en el sistema.
 * </p>
 *
 * @author Kevin William Olarte Braun
 */
@RestController
@RequestMapping("/api/vendedor")
@AllArgsConstructor
public class VendedorController {

    private final VendedorService vendedorService;

    /**
     * Crear un nuevo vendedor.
     *
     * @param dto datos de entrada para crear el vendedor
     * @return DTO con los datos del vendedor creado
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo vendedor", description = "Crea un nuevo vendedor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vendedor creado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    public ResponseEntity<VendedorResponseDto> add(@RequestBody VendedorDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendedorService.add(dto.getNombre(), dto.getPassword(), dto.getEmail(), Role.USER));
    }

    /**
     * Obtener un vendedor por su ID.
     *
     * @param id identificador del vendedor
     * @return DTO con los datos del vendedor
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un vendedor por su ID", description = "Obtener un vendedor por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor encontrado"),
            @ApiResponse(responseCode = "404", description = "Vendedor no encontrado", content = @Content)
    })
    public ResponseEntity<VendedorResponseDto> get(@PathVariable long id) {
        VendedorResponseDto vendedor = vendedorService.get(id);
        if (vendedor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vendedor);
    }

    /**
     * Listar todos los vendedores.
     *
     * @return lista de DTOs con todos los vendedores
     */
    @GetMapping
    @Operation(summary = "Listar todos los vendedores", description = "Listar todos los vendedores")
    @ApiResponse(responseCode = "200", description = "Lista de vendedores encontrados")
    public ResponseEntity<List<VendedorResponseDto>> getAll() {
        return ResponseEntity.ok(vendedorService.getAll());
    }

    /**
     * Actualizar los datos de un vendedor existente.
     *
     * @param id  identificador del vendedor
     * @param dto datos actualizados
     * @return DTO con los datos del vendedor actualizado
     */
    @Operation(summary = "Actualizar los datos de un vendedor", description = "Actualiza los datos de un vendedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vendedor actualizado"),
            @ApiResponse(responseCode = "404", description = "Vendedor no encontrado/ datos incorrectos a actualizar", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<VendedorResponseDto> update(
            @PathVariable long id,
            @RequestBody VendedorDto dto
    ) {
        VendedorResponseDto vendedor =  vendedorService.update(id, dto.getNombre(), dto.getPassword(), dto.getEmail());
        if (vendedor == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(vendedor);
    }

    /**
     * Eliminar un vendedor por su ID.
     *
     * @param id identificador del vendedor
     * @return código 204 si se eliminó correctamente
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un vendedor por su ID", description = "Elimina un vendedor por su ID")
    @ApiResponse(responseCode = "204", description = "Vendedor eliminado correctamente")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        vendedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

