package com.example.api.controllers.account.requests;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
