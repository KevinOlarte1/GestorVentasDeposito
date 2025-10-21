package com.gestorventas.deposito.specifications;

import com.gestorventas.deposito.models.LineaPedido;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que nos permite realizar Query dinámicas sobre las líneas de pedido.
 * @author Kevin
 */
public class LineaPedidoSpecifications {
    public static Specification<LineaPedido> filter(
            Long idLinea, Long pedidoId, Long vendedorId, Long clienteId) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (idLinea != null) {
                predicates.add(cb.equal(root.get("id"), idLinea));
            }
            if (pedidoId != null) {
                predicates.add(cb.equal(root.get("pedido").get("id"), pedidoId));
            }
            if (vendedorId != null) {
                // se accede al vendedor a través del cliente del pedido
                predicates.add(cb.equal(
                        root.get("pedido").get("cliente").get("vendedor").get("id"), vendedorId
                ));
            }
            if (clienteId != null) {
                predicates.add(cb.equal(root.get("pedido").get("cliente").get("id"), clienteId));
            }

            // 🔹 Une todos los predicados con AND (todas las condiciones deben cumplirse)
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
