package com.project.trade.item;

import com.project.trade.bid.Bid;
import com.project.trade.bid.BidRepository;
import com.project.trade.user.User;
import com.project.trade.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BidRepository bidRepository;

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public ResponseEntity<Item> getItemById(String id){
        Optional<Item> item=itemRepository.findById(id);
        return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Set<Bid>> getBidsByItemId(String id){
        Optional<Item> item=itemRepository.findById(id);
        return item.map(value -> new ResponseEntity<>(value.getBids(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Set<Item>> getItemsByUserId(String userId){
        Optional<User> user=userRepository.findById(userId);
        return user.map(value -> new ResponseEntity<>(value.getItems(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Item> getItemByUserId(String userId,String id){
        Optional<User> user=userRepository.findById(userId);
        Optional<Item> item=itemRepository.findById(id);
        if(user.isPresent() && item.isPresent()){
            return new ResponseEntity<>(item.get(),HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Set<Bid>> getBidsForMyItem(String userId,String id){
        Optional<User> user=userRepository.findById(userId);
        Optional<Item> item=itemRepository.findById(id);
        if(user.isPresent() && item.isPresent()){
            return new ResponseEntity<>(item.get().getBids(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Item> addItem(Item item,String userId){
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent()){
            try {
                Item newItem = itemRepository.save(new Item(item.getImage(),item.getItemName(),item.getDescription()));

                Set<Item> items=user.get().getItems();
                items.add(newItem);
                user.get().setItems(items);
                userRepository.save(user.get());
                return new ResponseEntity<>(newItem, HttpStatus.CREATED);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Item> updateItem(String id,String userId,Item item){
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent()) {
            Optional<Item> itemData = itemRepository.findById(id);
            if (itemData.isPresent()) {
                Item updatedItem = itemData.get();
                updatedItem.setItemName(item.getItemName());
                updatedItem.setDescription(item.getDescription());
                updatedItem.setImage(item.getImage());
                updatedItem.setActive(item.isActive());
                return new ResponseEntity<>(itemRepository.save(updatedItem), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<HttpStatus> deleteItem(String userId,String id){
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent()) {
            try {
                Optional<Item> item=itemRepository.findById(id);
                if(item.isPresent()) {
                    Set<Bid> bids = item.get().getBids();
                    bidRepository.deleteAll(bids);
                    Optional<Bid> bid = bidRepository.findBidByItemOffered(item.get());
                    bid.ifPresent(value -> bidRepository.deleteById(value.getId()));
                }
                itemRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
