package com.gestorventas.deposito.repository;

import com.gestorventas.deposito.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para acceder y gestionar entidades {@link Pedido}
 * <p>
 *     Permite realizar operaciones CRUD
 * </p>
 * @author Kevin William Olarte Braun.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {
}
