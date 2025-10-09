package com.gestorventas.deposito.services;

import com.gestorventas.deposito.dto.out.VendedorResponseDto;
import com.gestorventas.deposito.models.Vendedor;
import com.gestorventas.deposito.repositories.VendedorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de gestionar la logica del negocio relacionado con los vendedores.
 * <p>
 *     Permite registrar, consultar, actualizar y eliminar vendedores.
 * </p>
 * @author Kevin William Olarte Braun
 */
@Service
@AllArgsConstructor
public class VendedorService {

    private VendedorRepository vendedorRepository;

    /**
     * Guardar un nuevo vendedor en el sistema.
     * @param nombre nombre que recibira el vendedor
     * @param password contraseña para seguridad
     * @return DTO con los datos guardados visibles.
     * @throws IllegalArgumentException valores invalidos.
     */
    public VendedorResponseDto add(String nombre, String password, String email) {
        if (nombre == null || password == null || nombre.isEmpty() || password.isEmpty() || email == null || email.isEmpty())
            throw new IllegalArgumentException();

        if (!MailService.esEmailValido(email))
            throw new IllegalArgumentException("El email no es valido");
        Vendedor vendedor = new Vendedor();
        vendedor.setNombre(nombre);
        vendedor.setPassword(password);
        vendedor.setEmail(email);
        return new VendedorResponseDto(vendedorRepository.save(vendedor));

    }

    /**
     * Obtener un vendedor por su id.
     * @param id id que representa el identificador unico
     * @return DTO con los datos guardados visibles.
     */
    public VendedorResponseDto get(long id) {
        Vendedor vendedor = vendedorRepository.findById(id);
        if (vendedor == null)
            return null;
        return new VendedorResponseDto(vendedor);
    }

    /**
     * Obtener listado de todos los vendedores registrados en el sistema
     * @return listado con los vendedores en formato DTO.
     */
    public List<VendedorResponseDto> getAll() {
        return vendedorRepository.findAll().stream()
                .map(VendedorResponseDto::new)
                .toList();
    }

    /**
     * Actualizar los datos de un vendedor segun el identificador id
     * @param id identificador que se encargara cual vendedor se actualizara
     * @param nombre nombre nuevo a actualizar
     * @param password cambio de la contraseña
     * @return DTO con los datos guardados visibles.
     * @throws RuntimeException no existe en el sistema un vendedor con ese id.
     */
    public VendedorResponseDto update(long id, String nombre, String password, String email) {

        Vendedor vendedor = vendedorRepository.findById(id);
        if (vendedor == null)
            return  null;

        if (nombre != null && !nombre.isEmpty()) {
            vendedor.setNombre(nombre);
        }
        if (password != null && !password.isEmpty()) {
            vendedor.setPassword(password);
        }
        if (email != null && !email.isEmpty()) {
            if (MailService.esEmailValido(email))
                vendedor.setEmail(email);
        }
        return new VendedorResponseDto(vendedorRepository.save(vendedor));
    }

    /**
     * Borrar un vendedor del sistema en cascada con sus relaciones
     * @param id id del vendedor a borrar.
     */
    public void delete(long id) {

        vendedorRepository.deleteById(id);
    }
}
