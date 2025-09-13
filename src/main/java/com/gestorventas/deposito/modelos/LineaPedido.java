package com.gestorventas.deposito.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lineas_pedido")
@Getter
@Setter
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Pedido al que pertenece esta línea.
     */
    @ManyToOne
    @JoinColumn(name = "fk_pedido", nullable = false)
    private Pedido pedido;

    /**
     * Producto elegido en esta línea.
     */
    @ManyToOne
    @JoinColumn(name = "fk_producto", nullable = false)
    private Producto producto;

    /**
     * Cantidad de producto en esta línea.
     */
    @Column(nullable = false)
    private Integer cantidad;

    /**
     * Precio aplicado en esta línea (puede diferir del precio base).
     */
    @Column(nullable = false)
    private Double precio;

    public LineaPedido() {}
    public LineaPedido(Integer cantidad){
        this.cantidad = cantidad;
        this.precio = null;
    }
    public LineaPedido(Integer cantidad, Double precio) {
        this.cantidad = cantidad;
        this.precio = precio;
    }
}
