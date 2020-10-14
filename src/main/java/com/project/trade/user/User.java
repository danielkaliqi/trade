package com.project.trade.user;


import com.project.trade.bid.Bid;
import com.project.trade.item.Item;
import com.project.trade.passwordValidation.ValidPassword;
import com.project.trade.role.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Document(collection = "users")
public class User {

    @Id
    private String id;

    @NotNull
    @Size(min = 3,max = 20)
    private String username;

    @NotNull
    @Size(max=30)
    @Email
    private String email;

    @NotNull
    @Size(max=120)
    private String password;

    @DBRef
    private Set<Role> roles = new HashSet<>();
    @DBRef
    private Set<Item> items=new HashSet<>();
    @DBRef
    private Set<Bid> bids=new HashSet<>();
    public User() {

    }
    public User(String username,String email,String password) {
        this.username=username;
        this.email=email;
        this.password=password;
    }
}
