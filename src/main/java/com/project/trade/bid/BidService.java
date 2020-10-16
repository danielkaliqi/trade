package com.project.trade.bid;

import com.project.trade.item.Item;
import com.project.trade.item.ItemRepository;
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
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class BidService {

    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Set<Bid>> getAllByUserId(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(value -> new ResponseEntity<>(value.getBids(), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Bid> getBidByUserIdAndId(String userId,String id){
        Optional<Bid> bid=bidRepository.findById(id);
        return bid.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Bid> addBid(String itemId,String userId,Bid bid){
        Optional<User> user=userRepository.findById(userId);
        Optional<Item> item=itemRepository.findById(itemId);
        if(user.isPresent()){
            Bid newBid=bidRepository.save(new Bid(itemId,bid.getContact(),bid.getItemOffered(),bid.getStatus()));

            if(item.isPresent()) {
                Set<Bid> bids = item.get().getBids();
                bids.add(newBid);
                item.get().setBids(bids);
                itemRepository.save(item.get());
            }
            Set<Bid> bidsUser=user.get().getBids();
            bidsUser.add(newBid);
            user.get().setBids(bidsUser);
            userRepository.save(user.get());
            return new ResponseEntity<>(newBid,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Bid> updateBid(String userId,String id,Bid bid){
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent()){
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

    public ResponseEntity<HttpStatus> deleteBid(String userId,String id){
        Optional<User> user=userRepository.findById(userId);
        if(user.isPresent()){
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
