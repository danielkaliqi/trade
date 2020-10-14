package com.project.trade.item;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface ItemRepository extends MongoRepository<Item,String> {
    @Query("{'user.id': ?0}")
    Item findByUserIdAndId(String userId,String id);
    @Query("{user.items}: ?0")
    void deleteAll(Item items);
}
