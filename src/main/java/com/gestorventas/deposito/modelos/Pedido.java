package com.gestorventas.deposito.modelos;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Fecha y hora del pedido.
     */
    @Column(nullable = false)
    private LocalDateTime fecha;

    /**
     * Cliente que realizó el pedido.
     */
    @ManyToOne
    @JoinColumn(name = "fk_cliente", nullable = false)
    private Cliente cliente;

    /**
     * Vendedor que ha realizado el pedido
     */
    @ManyToOne
    @JoinColumn(name = "fk_vendedor", nullable = false)
    private Vendedor vendedor;

    /**
     * Líneas de pedido asociadas.
     */
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LineaPedido> lineas = new LinkedHashSet<>();

    public Pedido(){
        this.fecha = LocalDateTime.now();
    }


}
