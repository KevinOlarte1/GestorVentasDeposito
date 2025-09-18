package com.gestorventas.deposito.services;

import com.gestorventas.deposito.dto.out.PedidoResponseDto;
import com.gestorventas.deposito.models.Cliente;
import com.gestorventas.deposito.specifications.PedidoSpecifications;
import com.gestorventas.deposito.models.Pedido;
import com.gestorventas.deposito.models.Vendedor;
import com.gestorventas.deposito.repository.ClienteRepository;
import com.gestorventas.deposito.repository.PedidoRepository;
import com.gestorventas.deposito.repository.VendedorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio encargado de gestionar la logica del negocio relacionado con los pedidos.
 * <p>
 *     Permite registrar, consultar, actualizar y eliminar pedidos.
 * </p>
 * @author Kevin William Olarte Braun
 */
@Service
@AllArgsConstructor
public class PedidoService {

    private PedidoRepository pedidoRepository;
    private VendedorRepository vendedorRepository;
    private ClienteRepository clienteRepository;

    /**
     * Guardar un nuevo pedido en el sistema.
     * @param idVendedor identificador de quien realiza el pedido
     * @param idCliente identificador a quien se le va retribuir el peiddo.
     * @return DTO con los datos guardados visibles.
     * @throws RuntimeException entidades inexistentes.
     */
    public PedidoResponseDto add(long idVendedor, long idCliente) {
        Vendedor vendedor = vendedorRepository.findById(idVendedor);
        if(vendedor==null)
            throw new RuntimeException("Vendedor inexistente");

        Cliente cliente = clienteRepository.findById(idCliente);
        if(cliente==null)
            throw new RuntimeException("Cliente inexistente");

        Pedido pedido = new Pedido();
        pedido.setVendedor(vendedor);
        pedido.setCliente(cliente);

        pedidoRepository.save(pedido);
        return new PedidoResponseDto(pedido);
    }

    /**
     * Obtener un pedido por su id.
     * @param id id que representa el identificador unico
     * @return DTO con los datos guardados visibles.
     */
    public PedidoResponseDto get(long id) {
        Pedido pedido = pedidoRepository.findById(id);
        if(pedido==null)
            return null;
        return new PedidoResponseDto(pedido);
    }


    /**
     * Obtener listado de todos los Pedidos registrados en el sistema.
     * se le puede añadir los siguentes filtrados, idVendedor, idCliente
     * @param idVendedor filtrado opcionar sacar por vendedor.
     * @param idCliente filtrado opcional sacar por cliente.
     * @return Listado DTO con todos los pedidos.
     */
    public List<PedidoResponseDto> getAll(Long idVendedor, Long idCliente) {
        return pedidoRepository.findAll(PedidoSpecifications.filter(idVendedor, idCliente)).stream()
                .map(PedidoResponseDto::new)
                .toList();
    }

    /**
     * Actualizar los datos de un pedido segun el identificador id
     * @param id id del peiddo a modificar.
     * @param idVendedor vendedor que se le asignara nuevamente
     * @param idCliente cleinte que se le vendera el pedido
     * @param fecha fecha que se realizo el pedido actualizado
     * @return peiddo actualizado.
     * @throws RuntimeException referencia no existe.
     */
    public PedidoResponseDto update(long id, Long idVendedor, Long idCliente, LocalDate fecha) {
        Pedido pedido = pedidoRepository.findById(id);

        if(pedido==null)
            throw new RuntimeException("Pedido no encontrado");
        if (idVendedor != null){
            Vendedor vendedor = vendedorRepository.findById(id);
            if(vendedor==null)
                throw new RuntimeException("Vendedor inexistente");
            pedido.setVendedor(vendedor);
        }
        if (idCliente != null){
            Cliente cliente = clienteRepository.findById(id);
            if(cliente==null)
                throw new RuntimeException("Cliente inexistente");
            pedido.setCliente(cliente);
        }
        if (fecha != null){
            pedido.setFecha(fecha);
        }
        pedidoRepository.save(pedido);
        return new PedidoResponseDto(pedido);
    }

    /**
     * Borrar un pedido del sistema en cascada con sus relaciones
     * @param id id del pedido a borrar.
     */
    public void delete(long id) {
        pedidoRepository.deleteById(id);

    }



}
