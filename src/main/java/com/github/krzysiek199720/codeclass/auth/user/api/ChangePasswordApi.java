package com.github.krzysiek199720.codeclass.auth.user.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordApi {
    private String oldPassword;
    private String newPassword;
}
