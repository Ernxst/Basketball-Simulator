package com.example.api.controllers.account.requests;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ChangePasswordRequest {
    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;
}
