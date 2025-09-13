package com.gestorventas.deposito.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa la persona a la que se vende el producto.
 */
@Entity
@Table(name = "clientes")
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del cliente que representa en nuestra aplicacion
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Vendedor al que esta asociado el cliente, quien le vendio el  producto.
     */
    @ManyToOne
    @JoinColumn(name = "fk_vendedor", nullable = false)
    private Vendedor vendedor;



    public Cliente() {}
    public Cliente(String nombre){
        this.nombre = nombre;
    }
}
