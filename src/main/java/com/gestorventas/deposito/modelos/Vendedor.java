package com.gestorventas.deposito.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Entidad que representa el Vendedor/Usuario de nuestro programa
 * Estas entidades son las que se loggearan y tendran acceso a la app.
 */
@Entity
@Table(name = "vendedores")
@Getter
@Setter
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del vendedor.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Contraseña del vendedor
     */
    @Column(nullable = false)
    private String password;

    /**
     * Lista de pedidos que tiene que gestionar este vendedor asociados.
     */
    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Pedido> pedidos = new LinkedHashSet<>();


    public Vendedor() {}

    public Vendedor(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

}
