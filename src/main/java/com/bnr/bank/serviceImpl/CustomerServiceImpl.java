package com.bnr.bank.serviceImpl;

import com.bnr.bank.dto.CustomerDTO;
import com.bnr.bank.dto.LoginDTO;
import com.bnr.bank.dto.RegisterDTO;
import com.bnr.bank.enums.ERole;
import com.bnr.bank.models.Customer;
import com.bnr.bank.repositories.CustomerRepo;
import com.bnr.bank.services.CustomerService;
import com.bnr.bank.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;


@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    CustomerServiceImpl(CustomerRepo customerRepo, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.customerRepo = customerRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public RegisterDTO registerUser(RegisterDTO registerDTO, Set<ERole> role) {
        if(customerRepo.existsByEmail(registerDTO.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Customer customer = new Customer();
        customer.setFirstName(registerDTO.getFirstName());
        customer.setLastName(registerDTO.getLastName());
        customer.setEmail(registerDTO.getEmail());
        customer.setMobile(registerDTO.getMobilePhone());
        customer.setDob(java.sql.Date.valueOf(registerDTO.getDob())); // assuming dob is "yyyy-MM-dd"
        customer.setAccount(UUID.randomUUID().toString());
        customer.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        customer.setBalance(0.0);
        customer.setLastUpdateDateTime(java.time.LocalDateTime.now());
        customer.setRole(role);
        customerRepo.save(customer);
        return registerDTO;
    }

    @Override
    public String loginUser(String email, String password ) {
        Customer customer = customerRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, customer.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtTokenUtil.generateToken(customer);
    }

    @Override
    public Customer getCustomerById(UUID id) {
        return customerRepo.getCustomerById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
    }


    @Override
    public Customer getCustomerByAccount(String account) {
        return null;
    }

    @Override
    public CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));

        // ✅ Update the fields
        existingCustomer.setFirstName(customerDTO.getFirstName());
        existingCustomer.setLastName(customerDTO.getLastName());
        existingCustomer.setEmail(customerDTO.getEmail());
        existingCustomer.setMobile(customerDTO.getMobilePhone());
        existingCustomer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));

        customerRepo.save(existingCustomer);
        return customerDTO;
    }

    @Override
    public void deleteCustomer(UUID id) {
        Customer existingCustomer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));

        customerRepo.delete(existingCustomer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }


    @Override
    public boolean hasRole(String roleName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals(roleName));
        }
        return false;
    }
}
