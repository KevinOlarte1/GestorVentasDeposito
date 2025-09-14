package com.gestorventas.deposito.dto.out;

import com.gestorventas.deposito.models.Vendedor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO de salida para representar los datos públicos de un vendedor del sistema.
 * <p>
 * Esta clase se utiliza para devolver información básica del vendedor en las respuestas
 * de la API, sin incluir detalles.
 * </p>
 *
 * @author Kevin William Olarte Braun
 */
@Getter
@Setter
public class VendedorResponseDto {
    private Long id;
    private String nombre;

    public VendedorResponseDto(Vendedor vendedor) {
        this.id = vendedor.getId();
        this.nombre = vendedor.getNombre();
    }
}
