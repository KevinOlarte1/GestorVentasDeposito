package com.gestorventas.deposito.specifications;

import com.gestorventas.deposito.models.Cliente;
import org.springframework.data.jpa.domain.Specification;

/**
 * Clase que nos permite realizar Query dinamicas sobre los clientes.
 * @author Kevin William Olarte Braun
 */
public class ClienteSpecifications {
    /**
     * Construye una especificación dinámica para filtrar clientes.
     *
     * @param idVendedor identificador del vendedor asociado (opcional)
     * @return {@link Specification} que aplica los filtros proporcionados
     */
    public static Specification<Cliente> filter(Long idVendedor) {
        return (root, query, cb) -> {
            var predicate = cb.conjunction();
            if (idVendedor != null) {
                predicate = cb.and(predicate,
                        cb.equal(root.get("vendedor").get("id"), idVendedor));
            }

            return predicate;
        };
    }
}
