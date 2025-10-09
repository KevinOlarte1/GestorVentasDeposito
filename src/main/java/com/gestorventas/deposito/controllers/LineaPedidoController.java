package com.gestorventas.deposito.controllers;

import com.gestorventas.deposito.dto.in.LineaPedidoDto;
import com.gestorventas.deposito.dto.out.LineaPedidoResponseDto;
import com.gestorventas.deposito.services.LineaPedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendedor/{idVendedor}/cliente/{idCliente}/pedido/{idPedido}/linea")
@AllArgsConstructor
public class LineaPedidoController {

    private final LineaPedidoService lineaPedidoService;

    /**
     * Crear una nueva linea de pedido.
     * @param idVendedor identificador del vendedor que va a realizar el pedido.
     * @param idCliente identificador del cliente que va a realizar el pedido.
     * @param idPedido identificador del pedido que va a realizar el pedido.
     * @param lineaDto datos de la linea de pedido.
     * @return DTO con los datos guardados visibles.
     */
    @PostMapping
    @Operation(summary = "Crear una nueva linea de pedido", description = "Crea una nueva linea de pedido en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Linea de pedido creada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content) //TODO: CAMBIAR ESTO, FASE PRUIEBA
    })
    public ResponseEntity<LineaPedidoResponseDto> addLinea(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long idPedido,
            @RequestBody LineaPedidoDto lineaDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(lineaPedidoService.add(idVendedor, idCliente, idPedido, lineaDto.getIdProducto(), lineaDto.getCantidad(), lineaDto.getPrecio()));
    }

    /**
     * Obtener una linea de pedido por su id.
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @param idPedido identificador del pedido
     * @param idLinea identificador de la linea de pedido
     * @return DTO con los datos guardados visibles.
     */
    @GetMapping("/{idLinea}")
    @Operation(summary = "Obtener una linea de pedido por su id", description = "Obtener una linea de pedido por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Linea de pedido encontrada"),
            @ApiResponse(responseCode = "404", description = "Linea de pedido no encontrada", content = @Content)
    })
    public ResponseEntity<LineaPedidoResponseDto> getLinea(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long idPedido,
            @PathVariable Long idLinea){
        List<LineaPedidoResponseDto>  list= lineaPedidoService.get(idLinea, idPedido, idVendedor, idCliente);
        if(list.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list.get(0));
    }

    /**
     * Obtener todas las lineas de un pedido.
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @param idPedido identificador del pedido
     * @return Listado DTO con todos los clientes.
     */
    @GetMapping
    @Operation(summary = "Obtener todas las lineas de un pedido", description = "Obtener todas las lineas de un pedido")
    @ApiResponse(responseCode = "200", description = "Lista de lineas encontradas")
    public ResponseEntity<List<LineaPedidoResponseDto>> getAllLineas(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long idPedido){
        return ResponseEntity.ok(lineaPedidoService.get(null,idPedido,idVendedor,idCliente));
    }

    /**
     * Eliminar una linea de un pedido.
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @param idPedido identificador del pedido
     * @param idLinea identificador de la linea de pedido
     * @return DTO con los datos guardados visibles.
     */

    @DeleteMapping("/{idLinea}")
    @Operation(summary = "Eliminar una linea de un pedido", description = "Elimina una linea de un pedido")
    @ApiResponse(responseCode = "204", description = "Linea de pedido eliminada", content = @Content)
    public ResponseEntity<Void> deleteAllLineas(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long idPedido,
            @PathVariable Long idLinea){

        lineaPedidoService.delete(idVendedor, idCliente, idPedido, idLinea);
        return ResponseEntity.noContent().build();
    }





}
