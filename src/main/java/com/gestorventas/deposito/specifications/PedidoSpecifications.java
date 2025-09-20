package com.gestorventas.deposito.specifications;

import com.gestorventas.deposito.models.Pedido;
import org.springframework.data.jpa.domain.Specification;

/**
 * Clase que nos permite realizar Query dinamicas sobre los pedidos.
 * @author Kevin William Olarte Braun
 */
public class PedidoSpecifications {

    public static Specification<Pedido> filter(Long idVendedor, Long idCliente) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();

            if (idVendedor != null) {
                // Se accede al vendedor a través del cliente
                predicate = cb.and(predicate,
                        cb.equal(root.get("cliente").get("vendedor").get("id"), idVendedor));
            }

            if (idCliente != null) {
                predicate = cb.and(predicate, cb.equal(root.get("cliente").get("id"), idCliente));
            }

            return predicate;
        };
    }
}
