package com.project.trade.item;

import com.project.trade.bid.Bid;
import com.project.trade.bid.BidRepository;
import com.project.trade.user.User;
import com.project.trade.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/trade")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BidRepository bidRepository;


    @GetMapping("/items/all")
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }
    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id){
        Optional<Item> item=itemRepository.findById(id);
        if(item.isPresent()){
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/items/{id}/bids")
    public ResponseEntity<Set<Bid>> getAllBidsByItemId(@PathVariable String id){
        if(itemRepository.existsById(id)){
            return new ResponseEntity<>(itemRepository.findById(id).get().getBids(), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/myItems/{userId}")
    public ResponseEntity<Set<Item>> getAllItemsByUserId(@PathVariable String userId){
        if(userRepository.existsById(userId)) {
            return new ResponseEntity<>(userRepository.findById(userId).get().getItems(),HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/myItems/{userId}/{id}")
    public ResponseEntity<Item> getItemByUserId(@PathVariable String userId,@PathVariable String id){
        if(userRepository.existsById(userId) && itemRepository.existsById(id)){
            return new ResponseEntity<>(itemRepository.findById(id).get(),HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/myItems/{userId}/{id}/bids")
    public ResponseEntity<Set<Bid>> getBidsForMyItem(@PathVariable String userId,@PathVariable String id){
        if(userRepository.existsById(userId) && itemRepository.existsById(id)){
            return new ResponseEntity<>(itemRepository.findById(id).get().getBids(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/myItems/{userId}/addItem")
    public ResponseEntity<Item> addItem(@Valid @RequestBody Item item,@PathVariable String userId) {
        if(userRepository.existsById(userId)){
        try {
            Item newItem = itemRepository.save(new Item(item.getImage(),item.getItemName(),item.getDescription()));

            User user=userRepository.findById(userId).get();
            Set<Item> items=user.getItems();
            items.add(newItem);
            user.setItems(items);
            userRepository.save(user);
            return new ResponseEntity<>(newItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/myItems/{userId}/{id}/update")
    public ResponseEntity<Item> updateItem(@PathVariable String id,@PathVariable String userId,@Valid @RequestBody Item item){
        if(userRepository.existsById(userId)) {
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
    @DeleteMapping("/myItems/{userId}/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable String userId,@PathVariable String id){
        if(userRepository.existsById(userId)) {
            try {
                Optional<Item> item=itemRepository.findById(id);
                if(item.isPresent()) {
                    Set<Bid> bids = item.get().getBids();
                    bidRepository.deleteAll(bids);
                    Optional<Bid> bid = bidRepository.findBidByItemOffered(item.get());
                    if (bid.isPresent()){
                        bidRepository.deleteById(bid.get().getId());
                    }
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
