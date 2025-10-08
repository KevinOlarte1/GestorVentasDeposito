package com.gestorventas.deposito.controllers;

import com.gestorventas.deposito.dto.in.VendedorDto;
import com.gestorventas.deposito.dto.out.VendedorResponseDto;
import com.gestorventas.deposito.services.VendedorService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<VendedorResponseDto> add(@RequestBody VendedorDto dto) {
        return ResponseEntity.ok(vendedorService.add(dto.getNombre(), dto.getPassword(), dto.getEmail()));
    }

    /**
     * Obtener un vendedor por su ID.
     *
     * @param id identificador del vendedor
     * @return DTO con los datos del vendedor
     */
    @GetMapping("/{id}")
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
    public ResponseEntity<Void> delete(@PathVariable long id) {
        vendedorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

