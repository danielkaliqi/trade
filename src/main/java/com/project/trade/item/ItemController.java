package com.project.trade.item;

import com.project.trade.bid.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/trade")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items/all")
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }
    @GetMapping("/items/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id){
        return itemService.getItemById(id);
    }
    @GetMapping("/items/{id}/bids")
    public ResponseEntity<Set<Bid>> getAllBidsByItemId(@PathVariable String id){
        return itemService.getBidsByItemId(id);
    }
    @GetMapping("/myItems/{userId}")
    public ResponseEntity<Set<Item>> getAllItemsByUserId(@PathVariable String userId){
        return itemService.getItemsByUserId(userId);
    }
    @GetMapping("/myItems/{userId}/{id}")
    public ResponseEntity<Item> getItemByUserId(@PathVariable String userId,@PathVariable String id){
        return itemService.getItemByUserId(userId,id);
    }
    @GetMapping("/myItems/{userId}/{id}/bids")
    public ResponseEntity<Set<Bid>> getBidsForMyItem(@PathVariable String userId,@PathVariable String id){
        return itemService.getBidsForMyItem(userId,id);
    }
    @PostMapping("/myItems/{userId}/addItem")
    public ResponseEntity<Item> addItem(@Valid @RequestBody Item item,@PathVariable String userId) {
        return itemService.addItem(item,userId);
    }

    @PutMapping("/myItems/{userId}/{id}/update")
    public ResponseEntity<Item> updateItem(@PathVariable String id,@PathVariable String userId,@Valid @RequestBody Item item){
        return itemService.updateItem(id,userId,item);
    }
    @DeleteMapping("/myItems/{userId}/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable String userId,@PathVariable String id){
        return itemService.deleteItem(userId,id);
    }
}
