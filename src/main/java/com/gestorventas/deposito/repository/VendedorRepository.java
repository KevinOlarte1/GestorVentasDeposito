package com.gestorventas.deposito.repository;

import com.gestorventas.deposito.models.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para acceder y gestionar entidades {@link Vendedor}
 * <p>
 *     Permite realizar operaciones CRUD
 * </p>
 * @author Kevin William Olarte Braun.
 */
@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    public Vendedor findById(long id);
}
