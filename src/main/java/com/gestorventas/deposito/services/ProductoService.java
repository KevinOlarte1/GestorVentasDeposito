package com.gestorventas.deposito.services;

import com.gestorventas.deposito.dto.out.ProductoResponseDto;
import com.gestorventas.deposito.models.Producto;
import com.gestorventas.deposito.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de gestionar la logica del negocio relacionado con los Productos.
 * <p>
 *     Permite registrar, consultar, actualizar y eliminar productos.
 * </p>
 * @author Kevin William Olarte Braun
 */
@Service
@AllArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    /**
     * Guardar un nuevo producto en el sistema.
     * @param descripcion breve descrpcion del producto, puede ser tanto el nombre como una descripción.
     * @param precio precio base del producto, luego varuia en la linea del pedido
     * @return DTO con los datos guardados visibles.
     * @throws RuntimeException entidades inexistentes.
     */
    public ProductoResponseDto add(String descripcion, double precio) {
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("El descripcion es obligatorio");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio es obligatorio");
        }

        Producto producto = new Producto();
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto = productoRepository.save(producto);
        return new ProductoResponseDto(producto);
    }

    /**
     * Listado con todos los priductos del sistema.
     * @return listado con los productos
     */
    public List<ProductoResponseDto> getAll() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(ProductoResponseDto::new).toList();
    }

    /**
     * Devuelve un producto especifico, mediante su identificador
     * @param id identificador numerico que se usara para buscar
     * @return producto buscado mediante su identificado.
     */
    public ProductoResponseDto get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id es obligatorio");
        }
        Producto producto = productoRepository.findById((long) id);
        if (producto == null) {
            return null;
        }
        return new ProductoResponseDto(producto);
    }

    /**
     * Metodo para eliminar un producto del sistema.
     * @param id identificador numerico que se usara para buscar
     */
    public void remove(Long id) {
        productoRepository.deleteById(id);
    }
}
