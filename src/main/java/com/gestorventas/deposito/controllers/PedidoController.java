package com.gestorventas.deposito.controllers;

import com.gestorventas.deposito.dto.out.PedidoResponseDto;
import com.gestorventas.deposito.models.Pedido;
import com.gestorventas.deposito.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
     * Crear un nuevo pedido.
     * @param idVendedor identificador del vendedor que va a realizar el pedido.
     * @param idCliente identificador del cliente que va a realizar el pedido.
     * @return DTO con los datos del pedido creado.
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido creado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno") //TODO: CAMBIAR ESTO, FASE PRUIEBA
    })
    public ResponseEntity<PedidoResponseDto> add(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.add(idCliente, idVendedor));
    }

    /**
     * Obtener un pedido concreto.
     * @param idVendedor identificador del vendedor que va a realizar el pedido.
     * @param idCliente identificador del cliente que va a realizar el pedido.
     * @param id identificador numerico que se usara para buscar
     * @return DTO con los datos guardados visibles.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un pedido por su ID", description = "Obtener un pedido por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    public ResponseEntity<PedidoResponseDto> get(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long id) {
        PedidoResponseDto pedido = pedidoService.get(id,idCliente,idVendedor);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

    /**
     * Obtener todos los pedidos de un cliente.
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @return lista de DTOs con todos los pedidos.
     */
    @GetMapping
    @Operation(summary = "Obtener todos los pedidos de un cliente", description = "Obtener todos los pedidos de un cliente")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos encontrados")
    public ResponseEntity<List<PedidoResponseDto>> getAll(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente) {
        return ResponseEntity.ok(pedidoService.getAll(idVendedor, idCliente));
    }

    /**
     * Actualizar los datos de un pedido concreto.
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @param id identificador numerico que se usara para buscar
     * @param fecha fecha que se realizo el pedido actualizado
     * @return DTO con los datos del pedido actualizado.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar los datos de un pedido", description = "Actualiza los datos de un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    public ResponseEntity<PedidoResponseDto> update(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        PedidoResponseDto pedido = pedidoService.update(id, idVendedor, idCliente, fecha);
        return pedido != null ? ResponseEntity.ok(pedido) : ResponseEntity.notFound().build();
    }

    /**
     * Eliminar un pedido por su ID.
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @param id identificador numerico que se usara para buscar
     * @return codigo 204.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pedido", description = "Elimina un pedido del sistema")
    @ApiResponse(responseCode = "204", description = "Pedido eliminado")
    public ResponseEntity<Void> delete(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Cerrar un pedido por su ID.
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @param id identificador numerico que se usara para buscar
     * @return DTO con los datos del pedido cerrado.
     */
    @PutMapping("/{id}/cerrar")
    @Operation(summary = "Cerrar un pedido", description = "Cerrar un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido cerrado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    public ResponseEntity<PedidoResponseDto> cerrar(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long id){
        return ResponseEntity.ok(pedidoService.cerrarPedido(idVendedor, idCliente, id));
    }

    /**
     * Generar un informe PDF de un pedido.
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @param idPedido identificador del pedido
     * @return byte[] con el informe PDF generado.
     */
    @GetMapping("/{idPedido}/pdf")
    @Operation(summary = "Generar un informe PDF de un pedido", description = "Genera un informe PDF de un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informe PDF generado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    public ResponseEntity<byte[]> getPedidoPdf(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long idPedido) {

        byte[] pdfBytes = pedidoService.generarInformePdf(idPedido, idCliente, idVendedor);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=pedido-" + idPedido + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
