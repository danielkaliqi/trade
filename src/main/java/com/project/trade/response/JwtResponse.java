package com.project.trade.response;

import com.project.trade.bid.Bid;
import com.project.trade.item.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
    private List<String> roles;
    private Set<Item> items;
    private Set<Bid> bids;


    public JwtResponse(String accessToken, String id, String username, String email, List<String> roles,Set<Item> items,Set<Bid> bids) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.items=items;
        this.bids=bids;
    }
}
