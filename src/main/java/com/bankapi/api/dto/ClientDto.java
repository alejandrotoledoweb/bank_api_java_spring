package com.bankapi.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientDto {
    private int id;
    @NotNull @NotBlank(message = "Password is mandatory")
    private String password;
    @NotNull(message = "Status is mandatory")
    private Boolean status;
    @NotNull @NotBlank(message = "name is mandatory")
    private String name;
    @NotNull @NotBlank(message = "gender is mandatory")
    private String gender;
    @NotNull(message = "age is mandatory")
    private int age;
    @NotNull(message = "identification is mandatory")
    private Long identification;
    @NotNull @NotBlank(message = "address is mandatory")
    private String address;
    @NotNull(message = "phoneNumber is mandatory")
    private Long phoneNumber;
}
