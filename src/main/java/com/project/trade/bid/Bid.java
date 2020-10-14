package com.project.trade.bid;

import com.project.trade.item.Item;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.integration.annotation.Default;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Document(collection = "bids")
public class Bid {

    @Id
    private String id;

    private String itemId;

    @NotNull
    @Email
    @Size(max=30)
    private String contact;

    @NotNull
    private String status;

    @DBRef
    private Item itemOffered;


    public Bid(String itemId,String contact,Item itemOffered,String status){
        this.contact=contact;
        this.itemOffered=itemOffered;
        this.status=status;
        this.itemId=itemId;
    }
}
