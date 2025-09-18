package com.gestorventas.deposito.repository;

import com.gestorventas.deposito.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para acceder y gestionar entidades {@link Producto}
 * <p>
 *     Permite realizar operaciones CRUD.
 * </p>
 * @author Kevin William Olarte Braun.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Producto findById(long id);
}
