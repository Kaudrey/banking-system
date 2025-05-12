package com.bnr.bank.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.bnr.bank.enums.ERole;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name="last_name")
    private String lastName;


    @Column(name = "password")
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String mobile;

    @Column(nullable = false)
    private Date dob;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false)
    private Double balance = 0.0;

    @Column(name = "updateAt")
    private LocalDateTime lastUpdateDateTime;

    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.EAGER, targetClass = ERole.class)
    @CollectionTable(name = "customer_roles", joinColumns = @JoinColumn(name = "id"))
    @Enumerated(EnumType.STRING)
    private Set<ERole> role;

}
