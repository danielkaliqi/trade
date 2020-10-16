package com.project.trade.bid;

import com.project.trade.item.Item;
import com.project.trade.item.ItemRepository;
import com.project.trade.user.User;
import com.project.trade.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/trade")
public class BidController {

    @Autowired
    private BidService bidService;

    @GetMapping("/myBids/{userId}")
    public ResponseEntity<Set<Bid>> getAllByUserId(@PathVariable String userId){
        return bidService.getAllByUserId(userId);
    }

    @GetMapping("/myBids/{userId}/{id}")
    public ResponseEntity<Bid> getBidByUserIdAndId(@PathVariable String userId,@PathVariable String id){
        return bidService.getBidByUserIdAndId(userId,id);
    }

    @PostMapping("/items/{itemId}/{userId}/addBid")
    public ResponseEntity<Bid> addBid(@PathVariable String itemId,@PathVariable String userId, @Valid @RequestBody Bid bid){
        return bidService.addBid(itemId,userId,bid);
    }

    @PutMapping("/myBids/{userId}/{id}")
    public ResponseEntity<Bid> updateBid(@PathVariable String userId,@PathVariable String id,@Valid @RequestBody Bid bid){
       return bidService.updateBid(userId,id,bid);
    }

    @DeleteMapping("/myBids/{userId}/{id}")
    public ResponseEntity<HttpStatus> deleteBid(@PathVariable String userId,@PathVariable String id){
        return bidService.deleteBid(userId,id);
    }
}
