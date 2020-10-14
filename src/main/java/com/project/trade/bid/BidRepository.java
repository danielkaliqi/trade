package com.project.trade.bid;

import com.project.trade.item.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface BidRepository extends MongoRepository<Bid,String> {

    Optional<Bid> findBidByItemOffered(Item itemOffered);
    void deleteAllByItemId(String itemId);
}
