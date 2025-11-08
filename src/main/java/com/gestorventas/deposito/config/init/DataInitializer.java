package com.gestorventas.deposito.config.init;

import com.gestorventas.deposito.enums.CategoriaProducto;
import com.gestorventas.deposito.enums.Role;
import com.gestorventas.deposito.models.Producto;
import com.gestorventas.deposito.models.Vendedor;
import com.gestorventas.deposito.repositories.ProductoRepository;
import com.gestorventas.deposito.repositories.VendedorRepository;
import com.gestorventas.deposito.services.VendedorService;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final VendedorRepository userRepository;
    private final VendedorService userService;
    private final ProductoRepository productoRepository;
    private final VendedorRepository vendedorRepository;
    private static final Faker faker = new Faker();
    private static final Random random = new Random();
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;
    @Value("${app.admin.name}")
    private String adminName;
    @Value("${app.admin.password}")
    private String adminPassword;


    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            userService.add(adminName,adminPassword , adminEmail, Role.ADMIN);
            System.out.println("Admin creado: " + adminEmail);
        }
        randomProducts(100);
        randomVendedores(10);
    }

    private void randomProducts(int cantidad){
        for (int i = 0; i < cantidad; i++) {
            createProduct(faker.commerce().productName(),
                    Math.round(Double.parseDouble(faker.commerce().price(1.0, 200.0)) * 100.0) / 100.0,
                          CategoriaProducto.values()[random.nextInt(CategoriaProducto.values().length)]);
        }
    }
    private void randomVendedores(int cantidad){
        for (int i = 0; i < cantidad; i++) {
            createVendedor(
                    faker.name().firstName().toLowerCase(Locale.ROOT),
                    passwordEncoder.encode("1234"),
                    "vendedor" + i + "@gmail.com",
                    Role.USER
            );
        }
    }
    private void createVendedor(String name, String password,String email,Role role){
        vendedorRepository.save(Vendedor.builder()
                .nombre(name)
                .password(password)
                .email(email)
                .roles(Set.of(role))
                .build());
    }

    private void createProduct(String name, double price, CategoriaProducto categoriaProducto){
        productoRepository.save(Producto.builder()
                .descripcion(name)
                .precio(price)
                .categoria(categoriaProducto)
                .build());
    }
}
