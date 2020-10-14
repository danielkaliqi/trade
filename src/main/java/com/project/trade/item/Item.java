package com.project.trade.item;

import com.project.trade.bid.Bid;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Data
@Document(collection = "items")
public class Item {

    @Id
    private String id;

    @NotNull
    private String image;

    @NotNull
    @Size(min=3,max=10)
    private String itemName;

    @NotNull
    private boolean active;

    @NotNull
    @Size(min=5,max=100)
    private String description;
    @DBRef
    private Set<Bid> bids=new HashSet<>();


    public Item(){

    }
    public Item(String image,String itemName,String description){
        this.itemName=itemName;
        this.image=image;
        this.description=description;
    }
}
