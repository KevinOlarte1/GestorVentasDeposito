package com.gestorventas.deposito.services;

import com.gestorventas.deposito.dto.out.LineaPedidoResponseDto;
import com.gestorventas.deposito.dto.out.PedidoResponseDto;
import com.gestorventas.deposito.dto.out.VendedorResponseDto;
import com.gestorventas.deposito.models.*;
import com.gestorventas.deposito.repository.*;
import com.gestorventas.deposito.specifications.LineaPedidoSpecifications;
import com.gestorventas.deposito.specifications.PedidoSpecifications;
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
     *  Guardar una nueva linea de pedido en el sistema.
     * @param idPedido id del pedido que se le asignara la nueva linea
     * @param idProducto prodcuto de la linea
     * @param cantidad cantidad del producto
     * @param precio precio total de la linea
     * @return DTO con los datos guardados visibles.
     * @throws RuntimeException entidades inexistentes.
     */
    public LineaPedidoResponseDto add(long idPedido, long idProducto, int cantidad, double precio){
        Pedido pedido = pedidoRepository.findById(idPedido);
        if(pedido == null){
            throw new RuntimeException("Pedido no encontrado");
        }
        Vendedor vendedor = vendedorRepository.findById(idProducto);
        if(vendedor == null){
            throw new RuntimeException("Vendedor no encontrado");
        }
        Cliente cliente = clienteRepository.findById(idPedido);
        if(cliente == null){
            throw new RuntimeException("Cliente no encontrado");
        }
        Producto pedidoProducto = productoRepository.findById(idPedido);
        if(pedidoProducto == null){
            throw new RuntimeException("Producto no encontrado");
        }
        if (cantidad <= 0 && precio <= 0){
            throw new RuntimeException("Cantidad negativo no permitida");
        }

        LineaPedido pedidoLinea = new LineaPedido();
        pedidoLinea.setPedido(pedido);
        pedidoLinea.setProducto(pedidoProducto);
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
    public List<LineaPedidoResponseDto> getAll(Long id,Long idPedido, Long idVendedor, Long idCliente) {
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
    public void delete(long id) {
        lineaPedidoRepository.deleteById(id);

    }


}
