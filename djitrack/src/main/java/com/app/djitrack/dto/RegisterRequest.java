package com.app.djitrack.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String nom;
    private String email;
    private String telephone;
    private String password;
    private String role; // ROLE_ADMIN, ROLE_AGENT, ROLE_ABONNE

}
