package com.gestorventas.deposito.services;

import com.gestorventas.deposito.dto.out.ClienteResponseDto;
import com.gestorventas.deposito.models.Cliente;
import com.gestorventas.deposito.models.Vendedor;
import com.gestorventas.deposito.repositories.ClienteRepository;
import com.gestorventas.deposito.repositories.VendedorRepository;
import com.gestorventas.deposito.specifications.ClienteSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de gestionar la logica del negocio relacionado con los clientes.
 * <p>
 *     Permite registrar, consultar, actualizar y eliminar clientes.
 * </p>
 * @author Kevin William Olarte Braun
 */
@Service
@AllArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final VendedorRepository vendedorRepository;

    /**
     * Guardar un cliente nuevo en el sistema.
     * @param nombre nombre del cleinte
     * @param vendedorId a quien  le pertenece ese cleinte
     * @return DTO con los datos guardados visibles.
     * @throws IllegalArgumentException datos erroneos.
     */
    public ClienteResponseDto add(String nombre, long vendedorId) {

        if (nombre == null || nombre.isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacío");

        Vendedor vendedor = vendedorRepository.findById(vendedorId);
        if (vendedor == null)
            throw new RuntimeException("Vendedor no encontrado");

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setVendedor(vendedor);

        return new ClienteResponseDto(clienteRepository.save(cliente));
    }

    /**
     * Obtener una linea de pedudo por su id.
     * @param id identificador del cliente a buscar
     * @return DTO con los datos guardados visibles.
     */
    public ClienteResponseDto get(long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null)
            return null;
        return new ClienteResponseDto(cliente);
    }

    /**
     * Obtener una linea de pedudo por su id.
     * @param id identificador del cliente a buscar
     * @return DTO con los datos guardados visibles.
     */
    public ClienteResponseDto get(long idVendedor, long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null)
            return null;
        if (cliente.getVendedor().getId() != idVendedor)
            return null;
        return new ClienteResponseDto(cliente);
    }

    /**
     * Obtener listado de todos los lineasdePedido registrados en el sistema.
     * se le puede añadir los siguentes idVendedor
     * @param idVendedor vendedor al que pertenece
     * @return Listado DTO con todos los clientes.
     */
    public List<ClienteResponseDto> getAll(Long idVendedor) {
        return clienteRepository.findAll(ClienteSpecifications.filter(idVendedor)).stream().map(ClienteResponseDto::new).toList();
    }

    /**
     * Actualizar los datos de un cliente existente.
     *
     * @param id         identificador del cliente a actualizar
     * @param nombre     nuevo nombre del cliente (opcional)
     * @param vendedorId identificador del nuevo vendedor (opcional)
     * @return la entidad {@link Cliente} actualizada
     * @throws RuntimeException si el cliente o vendedor no existen
     */
    public ClienteResponseDto update(long id, String nombre, long vendedorId) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null)
            return null;

        if (cliente.getVendedor().getId() != vendedorId)
            return null;


        if (nombre != null && !nombre.isEmpty()) {
            cliente.setNombre(nombre);
        }

        return new ClienteResponseDto(clienteRepository.save(cliente));
    }

    /**
     * Eliminar un cliente del sistema por su identificador único.
     *
     * @param id identificador del cliente a eliminar
     */
    public void delete(long id) {
        clienteRepository.deleteById(id);
    }

    public void delete(long id, long idVendedor) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente != null){
            if (cliente.getVendedor().getId() == idVendedor)
                clienteRepository.deleteById(id);
        }
    }
}
