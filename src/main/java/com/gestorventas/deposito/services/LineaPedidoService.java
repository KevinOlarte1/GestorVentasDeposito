package com.gestorventas.deposito.services;

import com.gestorventas.deposito.dto.out.LineaPedidoResponseDto;
import com.gestorventas.deposito.models.*;
import com.gestorventas.deposito.repositories.*;
import com.gestorventas.deposito.specifications.LineaPedidoSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de gestionar la logica del negocio relacionado con las lineas de pedidos.
 * <p>
 *     Permite registrar, consultar, actualizar y eliminar lineas de pedidos.
 * </p>
 * @author Kevin William Olarte Braun
 */
@Service
@AllArgsConstructor
public class LineaPedidoService {

    private LineaPedidoRepository lineaPedidoRepository;
    private VendedorRepository vendedorRepository;
    private ClienteRepository clienteRepository;
    private PedidoRepository pedidoRepository;
    private ProductoRepository productoRepository;


    /**
     * Guardar una nueva linea de pedido en el sistema.
     * @param idVendedor identificador del vendedor que va a realizar el pedido.
     * @param idCliente identificador del cliente que va a realizar el pedido.
     * @param idPedido identificador del pedido que va a realizar el pedido.
     * @param idProducto identificador del producto que va a agregar al pedido.
     * @param cantidad cantidad a agregar al pedido.
     * @param precio precio total del producto.
     * @return DTO con los datos guardados visibles.
     * @throws RuntimeException entidades inexistentes.
     * @throws IllegalArgumentException datos erroneos.
     */
    public LineaPedidoResponseDto add(long idVendedor, long idCliente, long idPedido, long idProducto, int cantidad, Double precio){
        Vendedor vendedor = vendedorRepository.findById(idVendedor);
        if (vendedor == null)
            throw new RuntimeException("Vendedor inexistente");
        Cliente cliente = vendedor.getClientes().stream().filter(c -> c.getId()==idCliente).findFirst().orElse(null);
        if (cliente == null)
            throw new RuntimeException("Cliente inexistente");
        Pedido pedido = cliente.getPedidos().stream().filter(p -> p.getId()==idPedido).findFirst().orElse(null);
        if (pedido == null || pedido.isFinalizado())
            throw new RuntimeException("Pedido inexistente");
        Producto producto = productoRepository.findById(idProducto);
        if (producto == null)
            throw new RuntimeException("Producto inexistente");
        if (cantidad <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        if (precio == null)
            precio = producto.getPrecio() * cantidad;
        else if (precio < 0)
            throw new IllegalArgumentException("El precio debe ser mayor o igual 0");


        LineaPedido pedidoLinea = new LineaPedido();
        pedidoLinea.setPedido(pedido);
        pedidoLinea.setProducto(producto);
        pedidoLinea.setCantidad(cantidad);
        pedidoLinea.setPrecio(precio);
        return new  LineaPedidoResponseDto(lineaPedidoRepository.save(pedidoLinea));
    }

    /**
     * Obtener una linea de pedudo por su id.
     * @param id id que representa el identificador unico
     * @return DTO con los datos guardados visibles.
     */
    public LineaPedidoResponseDto get(long id) {
        LineaPedido linea = lineaPedidoRepository.findById(id);
        if(linea==null)
            return null;
        return new LineaPedidoResponseDto(linea);
    }
    /**
     * Obtener listado de todos los lineasdePedido registrados en el sistema.
     * se le puede añadir los siguentes filtrados, id, idPedido, idVendedor, idCliente
     * @param id filtrado opcional sacar por id.
     * @param idPedido filtrado opcional scar por pedido.
     * @param idVendedor filtrado opcionar sacar por vendedor.
     * @param idCliente filtrado opcional sacar por cliente.
     * @return Listado DTO con todos los pedidos.
     */
    public List<LineaPedidoResponseDto> get(Long id,Long idPedido, Long idVendedor, Long idCliente) {
        return lineaPedidoRepository.findAll(LineaPedidoSpecifications.filter(id, idPedido, idVendedor,idCliente)).stream()
                .map(LineaPedidoResponseDto::new)
                .toList();
    }


    /**
     * Actualizar los datos de una liena de pedido segun el identificador id
     * @param id id de la linea a modificar
     * @param cantidad cantidad nueva deseada
     * @param precio precio total
     * @return peiddo actualizado.
     * @throws RuntimeException linea no existe
     */
    public LineaPedidoResponseDto update(long id, Integer cantidad, Double precio){
        LineaPedido linea = lineaPedidoRepository.findById(id);
        if(linea==null){
            throw new RuntimeException("Linea no encontrada");
        }
        if(cantidad != null && cantidad > 0)
            linea.setCantidad(cantidad);
        if (precio != null && precio > 0)
            linea.setPrecio(precio);
        return new  LineaPedidoResponseDto(lineaPedidoRepository.save(linea));
    }
    /**
     * Borrar una linea de pedido del sistema.
     * @param id id del pedido a borrar.
     */
    public void delete(long idVendedor, long idCliente, long idPedido, long idLinea) {
        Vendedor vendedor = vendedorRepository.findById(idVendedor);
        if (vendedor == null)
            return;
        Cliente cliente = vendedor.getClientes().stream().filter(c -> c.getId()==idCliente).findFirst().orElse(null);
        if (cliente == null)
            return;
        Pedido pedido = cliente.getPedidos().stream().filter(p -> p.getId()==idPedido).findFirst().orElse(null);
        if (pedido == null || pedido.isFinalizado())
            return;
        LineaPedido linea = pedido.getLineas().stream().filter(l -> l.getId()==idLinea).findFirst().orElse(null);

        if (linea != null){
            lineaPedidoRepository.deleteById(idLinea);
        }

    }


}
