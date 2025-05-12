package com.bnr.bank.config;


import com.bnr.bank.enums.ERole;
import com.bnr.bank.models.Customer;
import com.bnr.bank.repositories.CustomerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CustomerRepo customerRepo,
                           PasswordEncoder passwordEncoder) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!customerRepo.existsByEmail("admin@rw.com")) {
            Customer admin = new Customer();
            admin.setFirstName("System");
            admin.setLastName("Admin");
            admin.setEmail("admin@rw.com");
            admin.setMobile("123456789");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Set.of(ERole.ADMIN));

            // Set DOB using LocalDate
            admin.setDob(java.sql.Date.valueOf("2003-04-12"));

            // Generate account number
            admin.setAccount(java.util.UUID.randomUUID().toString());

            admin.setBalance(0.5);

            // Set update timestamp
            admin.setLastUpdateDateTime(java.time.LocalDateTime.now());

            customerRepo.save(admin);
            System.out.println("✅ Admin user created!");
        }
    }
}