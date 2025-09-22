package com.gestorventas.deposito.controllers;

/**
 * Controlador REST para gestionar pedidos de un cliente de un vendedor.
 *
 * Ruta base: /api/vendedor/{idVendedor}/cliente/{idCliente}/pedido
 *
 * Endpoints disponibles:
 *  - POST   /api/vendedor/{idVendedor}/cliente/{idCliente}/pedido        -> Crear pedido
 *  - GET    /api/vendedor/{idVendedor}/cliente/{idCliente}/pedido        -> Listar pedidos de ese cliente
 *  - GET    /api/vendedor/{idVendedor}/cliente/{idCliente}/pedido/{id}   -> Obtener un pedido concreto
 *  - PUT    /api/vendedor/{idVendedor}/cliente/{idCliente}/pedido/{id}   -> Actualizar pedido
 *  - DELETE /api/vendedor/{idVendedor}/cliente/{idCliente}/pedido/{id}   -> Eliminar pedido
 *
 * @author Kevin William Olarte Braun
 */
public class PedidoController {
}
