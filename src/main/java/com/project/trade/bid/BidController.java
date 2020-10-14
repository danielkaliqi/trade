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
    private BidRepository bidRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/items/{itemId}/{userId}/addBid")
    public ResponseEntity<Bid> addBid(@PathVariable String itemId,@PathVariable String userId, @Valid @RequestBody Bid bid){
        if(userRepository.existsById(userId)){
            Bid newBid=bidRepository.save(new Bid(itemId,bid.getContact(),bid.getItemOffered(),bid.getStatus()));

            Item item=itemRepository.findById(itemId).get();
            Set<Bid> bids=item.getBids();
            bids.add(newBid);
            item.setBids(bids);
            itemRepository.save(item);

            User user=userRepository.findById(userId).get();
            Set<Bid> bidsUser=user.getBids();
            bidsUser.add(newBid);
            user.setBids(bidsUser);
            userRepository.save(user);
            return new ResponseEntity<>(newBid,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/myBids/{userId}")
    public ResponseEntity<Set<Bid>> getAllByUserId(@PathVariable String userId){
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get().getBids(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/myBids/{userId}/{id}")
    public ResponseEntity<Bid> getBidByUserIdAndId(@PathVariable String userId,@PathVariable String id){
        Optional<Bid> bid=bidRepository.findById(id);
        if(bid.isPresent())
        {
            return new ResponseEntity<>(bid.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/myBids/{userId}/{id}")
    public ResponseEntity<Bid> updateBid(@PathVariable String userId,@PathVariable String id,@Valid @RequestBody Bid bid){
        if(userRepository.existsById(userId)){
            Optional<Bid> bidData=bidRepository.findById(id);
            if(bidData.isPresent()){
                Bid newBid=bidData.get();
                newBid.setContact(bid.getContact());
                newBid.setStatus(bid.getStatus());
                return new ResponseEntity<>(bidRepository.save(newBid),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/myBids/{userId}/{id}")
    public ResponseEntity<HttpStatus> deleteBid(@PathVariable String userId,@PathVariable String id){
        if(userRepository.existsById(userId)){
            try{
                bidRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            catch (Exception e){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
