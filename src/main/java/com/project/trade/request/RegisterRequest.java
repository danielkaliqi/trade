package com.project.trade.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    @Size(min = 3,max = 20)
    private String username;

    @NotNull
    @Size(max=30)
    @Email
    private String email;

    private Set<String> roles;

    @NotNull
    @Size(max=120)
    private String password;


}
