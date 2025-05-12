package com.bnr.bank.dto;

import com.bnr.bank.enums.ERole;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterDTO{
        String firstName;
        String lastName;
        String email;
        String mobilePhone;
        String dob;
        String password;
}