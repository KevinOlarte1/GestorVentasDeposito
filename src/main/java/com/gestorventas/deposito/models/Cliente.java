package com.gestorventas.deposito.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

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
     * Lista de pedidos que tiene este cliente asociados.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Pedido> pedidos = new LinkedHashSet<>();


    public Cliente() {}
    public Cliente(String nombre){
        this.nombre = nombre;
    }
}
