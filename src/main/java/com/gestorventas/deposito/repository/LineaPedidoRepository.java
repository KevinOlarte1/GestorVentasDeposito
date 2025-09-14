package com.gestorventas.deposito.repository;

import com.gestorventas.deposito.models.LineaPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para acceder y gestionar entidades {@link LineaPedido}
 * <p>
 *     Permite realizar operaciones CRUD
 * </p>
 * @author Kevin William Olarte Braun.
 */
@Repository
public interface LineaPedidoRepository extends JpaRepository<LineaPedido,Long> {
}
