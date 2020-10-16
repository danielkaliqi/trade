package com.project.trade.user;

import com.project.trade.bid.Bid;
import com.project.trade.bid.BidRepository;
import com.project.trade.item.Item;
import com.project.trade.item.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BidRepository bidRepository;

    public List<User> getAllUsers(){
       return userRepository.findAll();
    }

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public ResponseEntity<HttpStatus> deleteUser(String id){
        try{
            Optional<User> user=userRepository.findById(id);
            if(user.isPresent()){
                Set<Bid> bids=user.get().getBids();
                bidRepository.deleteAll(bids);
                Set<Item> items=user.get().getItems();
                itemRepository.deleteAll(items);
                userRepository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteItem(String itemId){
        try{
            Optional<Item> item=itemRepository.findById(itemId);
            if(item.isPresent()){
                bidRepository.deleteAllByItemId(itemId);
                itemRepository.deleteById(itemId);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<User> getUser(String id){
        Optional<User> user=userRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
