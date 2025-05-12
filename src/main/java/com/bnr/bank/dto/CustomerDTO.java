package com.bnr.bank.dto;

import com.bnr.bank.enums.ERole;
import lombok.Data;

import java.util.UUID;

@Data
public class CustomerDTO {
    UUID id;
    String firstName;
    String lastName;
    String email;
    String mobilePhone;
    String password;
}
