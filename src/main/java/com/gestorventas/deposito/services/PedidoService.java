package com.gestorventas.deposito.services;

import com.gestorventas.deposito.dto.out.PedidoResponseDto;
import com.gestorventas.deposito.models.Cliente;
import com.gestorventas.deposito.models.Vendedor;
import com.gestorventas.deposito.specifications.PedidoSpecifications;
import com.gestorventas.deposito.models.Pedido;
import com.gestorventas.deposito.repositories.ClienteRepository;
import com.gestorventas.deposito.repositories.PedidoRepository;
import com.gestorventas.deposito.repositories.VendedorRepository;
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

    private final MailService mailService;
    private PedidoRepository pedidoRepository;
    private VendedorRepository vendedorRepository;
    private ClienteRepository clienteRepository;

    /**
     * Guardar un nuevo pedido en el sistema.
     * @param idCliente identificador a quien se le va retribuir el peiddo.
     * @return DTO con los datos guardados visibles.
     * @throws RuntimeException entidades inexistentes.
     */
    public PedidoResponseDto add( long idCliente, long idVendedor) {
        Cliente cliente = clienteRepository.findById(idCliente);
        if(cliente==null)
            throw new RuntimeException("Cliente inexistente");
        if (cliente.getVendedor().getId()!=idVendedor)
            throw new RuntimeException("Cliente inexistente");

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);

        pedidoRepository.save(pedido);
        return new PedidoResponseDto(pedido);
    }

    /**
     * Obtener un pedido por su id.
     * @param id id que representa el identificador unico
     * @return DTO con los datos guardados visibles.
     */
    public PedidoResponseDto get(long id, long idCliente, long idVendedor) {
        Pedido pedido = pedidoRepository.findById(id);
        if(pedido==null)
            return null;
        if (pedido.getCliente().getId()!=idCliente)
            return null;
        if (pedido.getCliente().getId()!=idVendedor)
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
     * @param idCliente cleinte que se le vendera el pedido
     * @param fecha fecha que se realizo el pedido actualizado
     * @return peiddo actualizado.
     * @throws RuntimeException referencia no existe.
     */
    public PedidoResponseDto update(long id, long idVendedor, long idCliente, LocalDate fecha) {
        Pedido pedido = pedidoRepository.findById(id);

        if(pedido==null)
            return null;

        if (pedido.getCliente().getId()!=idCliente)
            return null;

        if(pedido.getCliente().getVendedor().getId()!=idVendedor)
            return null;

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


    /**
     * Cerrar un pedido ya registrado.
     * @param idVendedor identificador del vendedor que realizo el pedido.
     * @param idCliente identificador del cliente que realizo el pedido.
     * @param idPedido identificador del pedido que se va a cerrar.
     * @return DTO con los datos del pedido cerrado.
     * @throws RuntimeException entidades inexistentes.
     */
    public PedidoResponseDto cerrarPedido(long idVendedor, long idCliente, long idPedido) {
        Vendedor vendedor = vendedorRepository.findById(idVendedor);
        if (vendedor == null)
            throw new RuntimeException("Vendedor inexistente");
        Cliente cliente = vendedor.getClientes().stream().filter(c -> c.getId()==idCliente).findFirst().orElse(null);
        if (cliente == null)
            throw new RuntimeException("Cliente inexistente");
        Pedido pedido = cliente.getPedidos().stream().filter(p -> p.getId()==idPedido).findFirst().orElse(null);
        if (pedido == null)
            throw new RuntimeException("Pedido inexistente");

        pedido.setFinalizado(true);

        pedido = pedidoRepository.save(pedido);

        try{
            mailService.enviarCorreoPedido(vendedor.getEmail(), pedido);
        } catch (Exception e){
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
            System.out.println(e.getMessage());
        }
        return new PedidoResponseDto(pedido);



    }
}
