package com.gestorventas.deposito.repository;

import com.gestorventas.deposito.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para acceder y gestionar entidades {@link Cliente}
 * <p>
 *     Permite realizar operaciones CRUD
 * </p>
 * @author Kevin William Olarte Braun.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    public Cliente findById(long id);
}
