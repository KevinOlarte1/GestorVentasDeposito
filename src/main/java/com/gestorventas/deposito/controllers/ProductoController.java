package com.gestorventas.deposito.controllers;

import com.gestorventas.deposito.dto.in.ProductoDto;
import com.gestorventas.deposito.dto.out.ProductoResponseDto;
import com.gestorventas.deposito.services.ProductoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controlador REST para gestionar Productos del sistema
 *
 * Ruta base: /api/productos
 *
 * @author Kevin William Olarte Braun
 */
@RestController
@RequestMapping("/api/producto")
@AllArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Crear un producto
     * @param productoDto datos de entrada para crear los productos.
     * @return Dto Response con la info de ese producto creado.
     */
    @PostMapping
    public ResponseEntity<ProductoResponseDto> add(
            @RequestBody ProductoDto productoDto) {
        return ResponseEntity.ok(productoService.add(productoDto.getDescripcion(), productoDto.getPrecio()));
    }

    /**
     * Listar todos los productos.
     *
     * @return lista de DTOs con todos los productos
     */
    @GetMapping
    public ResponseEntity<List<ProductoResponseDto>> getAll() {
        return ResponseEntity.ok(productoService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDto> get(
            @PathVariable Long id) {
        ProductoResponseDto responseDto = productoService.get(id);
        return responseDto != null ? ResponseEntity.ok(responseDto) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productoService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
