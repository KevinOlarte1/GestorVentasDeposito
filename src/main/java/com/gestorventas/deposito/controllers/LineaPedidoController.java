package com.gestorventas.deposito.controllers;

import com.gestorventas.deposito.dto.in.LineaPedidoDto;
import com.gestorventas.deposito.dto.out.LineaPedidoResponseDto;
import com.gestorventas.deposito.services.LineaPedidoService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<LineaPedidoResponseDto> addLinea(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long idPedido,
            @RequestBody LineaPedidoDto lineaDto){

        return ResponseEntity.ok(lineaPedidoService.add(idVendedor, idCliente, idPedido, lineaDto.getIdProducto(), lineaDto.getCantidad(), lineaDto.getPrecio()));
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
    public ResponseEntity<LineaPedidoResponseDto> getLinea(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long idPedido,
            @PathVariable Long idLinea){
        return ResponseEntity.ok(lineaPedidoService.get(idLinea, idPedido, idVendedor, idCliente).get(0));
    }

    /**
     * Obtener todas las lineas de un pedido.
     * @param idVendedor identificador del vendedor
     * @param idCliente identificador del cliente
     * @param idPedido identificador del pedido
     * @return Listado DTO con todos los clientes.
     */
    @GetMapping
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
    public ResponseEntity<Void> deleteAllLineas(
            @PathVariable Long idVendedor,
            @PathVariable Long idCliente,
            @PathVariable Long idPedido,
            @PathVariable Long idLinea){

        lineaPedidoService.delete(idVendedor, idCliente, idPedido, idLinea);
        return ResponseEntity.noContent().build();
    }





}
