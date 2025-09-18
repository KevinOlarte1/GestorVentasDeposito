package com.gestorventas.deposito.specifications;

import com.gestorventas.deposito.models.LineaPedido;
import org.springframework.data.jpa.domain.Specification;

/**
 * Clase que nos permite realizar Query dinamicas sobre las lineas de pedido.
 */
public class LineaPedidoSpecifications {
    public static Specification<LineaPedido> filter(
            Long id, Long pedidoId, Long vendedorId, Long clienteId) {

        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (id != null) {
                predicates.getExpressions().add(cb.equal(root.get("id"), id));
            }
            if (pedidoId != null) {
                predicates.getExpressions().add(cb.equal(root.get("pedido").get("id"), pedidoId));
            }
            if (vendedorId != null) {
                predicates.getExpressions().add(cb.equal(root.get("pedido").get("vendedor").get("id"), vendedorId));
            }
            if (clienteId != null) {
                predicates.getExpressions().add(cb.equal(root.get("pedido").get("cliente").get("id"), clienteId));
            }

            return predicates;
        };
    }
}
