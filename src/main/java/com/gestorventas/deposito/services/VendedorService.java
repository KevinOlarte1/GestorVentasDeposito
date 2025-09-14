package com.gestorventas.deposito.services;

import com.gestorventas.deposito.dto.out.VendedorResponseDto;
import com.gestorventas.deposito.models.Vendedor;
import com.gestorventas.deposito.repository.VendedorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VendedorService {

    VendedorRepository vendedorRepository;

    /**
     * Guardar un nuevo vendedor en el sistema.
     * @param nombre nombre que recibira el vendedor
     * @param password contraseña para seguridad
     * @return DTO con los datos guardados visibles.
     * @throws IllegalArgumentException valores invalidos.
     */
    public VendedorResponseDto add(String nombre, String password) {
        if (nombre == null || password == null || nombre.isEmpty() || password.isEmpty())
            throw new IllegalArgumentException();

        Vendedor vendedor = new Vendedor();
        vendedor.setNombre(nombre);
        vendedor.setPassword(password);
        return new VendedorResponseDto(vendedorRepository.save(vendedor));

    }

    /**
     * Obtener un vendedor por su id.
     * @param id id que representa el identificador unico
     * @return DTO con los datos guardados visibles.
     * @throws IllegalArgumentException id null.
     */
    public VendedorResponseDto get(Long id) {
        if (id == null)
            throw new IllegalArgumentException();

        Optional<Vendedor> vendedor = vendedorRepository.findById(id);
        return vendedor.map(VendedorResponseDto::new).orElse(null);
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
     * @throws IllegalArgumentException  id nulo o no existe ene el sistema un vendedor con ese id.
     */
    public VendedorResponseDto update(Long id, String nombre, String password) {
        if (id == null)
            throw new IllegalArgumentException();
        Vendedor vendedor = vendedorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vendedor no encontrado con id: " + id));

        if (nombre != null && !nombre.isEmpty()) {
            vendedor.setNombre(nombre);
        }
        if (password != null && !password.isEmpty()) {
            vendedor.setPassword(password);
        }
        return new VendedorResponseDto(vendedorRepository.save(vendedor));
    }

    /**
     * Borrar un vendedor del sistema en cascada con sus relaciones
     * @param id id del vendedor a borrar.
     * @throws IllegalArgumentException si el id es nulo
     */
    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException();
        vendedorRepository.deleteById(id);
    }
}
