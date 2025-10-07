package com.gestorventas.deposito.controllers;

import com.gestorventas.deposito.dto.out.PedidoResponseDto;
import com.gestorventas.deposito.services.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para gestionar pedidos de un cliente de un vendedor.
 *
 * Ruta base: /api/vendedor/{idVendedor}/cliente/{idCliente}/pedido
 *
 * @author Kevin William Olarte Braun
 */
@RestController
@RequestMapping("/api/vendedor/{idVendedor}/cliente/{idCliente}/pedido")
@AllArgsConstructor
public class PedidoController {
    private PedidoService pedidoService;

    /**
     * Crear un nuevo pedido para un cliente de un vendedor.
     */
    @PostMapping
    public ResponseEntity<PedidoResponseDto> add(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente) {
        return ResponseEntity.ok(pedidoService.add(idCliente, idVendedor));
    }

    /**
     * Obtener un pedido concreto por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> get(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long id) {
        PedidoResponseDto pedido = pedidoService.get(id,idCliente,idVendedor);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

    /**
     * Listar todos los pedidos de un cliente de un vendedor.
     */
    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> getAll(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente) {
        return ResponseEntity.ok(pedidoService.getAll(idVendedor, idCliente));
    }

    /**
     * Actualizar un pedido concreto.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> update(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        PedidoResponseDto pedido = pedidoService.update(id, idVendedor, idCliente, fecha);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

    /**
     * Eliminar un pedido concreto.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
