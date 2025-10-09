package com.gestorventas.deposito.models;

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

    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Lista de clientes que gestiona este vendedor.
     */
    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Cliente> clientes = new LinkedHashSet<>();


    public Vendedor() {}

    public Vendedor(String nombre, String password,String email) {
        this.nombre = nombre;
        this.password = password;
        this.email = email;
    }

}
