package com.gestorventas.deposito.config;

import com.gestorventas.deposito.models.Cliente;
import com.gestorventas.deposito.models.Producto;
import com.gestorventas.deposito.repositories.ClienteRepository;
import com.gestorventas.deposito.repositories.ProductoRepository;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(ProductoRepository productoRepository,
                                   ClienteRepository clienteRepository) {
        return args -> {
            Faker f = new Faker();
            Random random = new Random();
          for (int i = 1; i <= 25; i++) {
              Cliente cliente = new Cliente(f.name().firstName());
              Producto p = new Producto(f.commerce().productName(), (1 + (50 - 1) * random.nextDouble()));
              clienteRepository.save(cliente);
              productoRepository.save(p);

          }

        };
    }
}
