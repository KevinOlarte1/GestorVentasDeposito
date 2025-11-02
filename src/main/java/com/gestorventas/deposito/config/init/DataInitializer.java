package com.gestorventas.deposito.config.init;

import com.gestorventas.deposito.enums.Role;
import com.gestorventas.deposito.repositories.VendedorRepository;
import com.gestorventas.deposito.services.VendedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final VendedorRepository userRepository;
    private final VendedorService userService;

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
    }
}
