package com.gestorventas.deposito.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representan los productos que se venden en la empresa
 */
@Entity
@Table(name = "productos")
@Getter
@Setter
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Pequeña descripcion que nos ayuda sobre el producto
     */
    @Column(nullable = false)
    private String descripcion;

    /**
     * Precio base del producto, precio modificable en la LineaPedido
     */
    @Column(nullable = false)
    private Double precio;


    public Producto() {}

    public Producto(String descripcion, Double precio) {
        this.descripcion = descripcion;
        this.precio = precio;
    }
}
