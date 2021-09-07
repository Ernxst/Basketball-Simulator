package com.example.api.controllers.account.requests;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class DeleteUserRequest {
    @NotNull
    private String password;
}
