package com.project.trade.request;

import com.project.trade.bid.Bid;
import com.project.trade.item.Item;
import com.project.trade.passwordValidation.ValidPassword;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    @ValidPassword
    private String password;


}
