package com.project.trade.user;

import com.project.trade.bid.Bid;
import com.project.trade.bid.BidRepository;
import com.project.trade.item.Item;
import com.project.trade.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/trade")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BidRepository bidRepository;

    @GetMapping("/home")
    public void allAccess(){
        return;
    }

    @GetMapping("/main")
    @PreAuthorize("hasAuthority('USER')")
    public void userAccess(){
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void adminAccess() {
    }

    @GetMapping("/admin/allUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/admin/allItems")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    @DeleteMapping("/admin/{id}/deleteUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> deleteUserByAdmin(@PathVariable String id){
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


    @DeleteMapping("/admin/{itemId}/deleteItem")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> deleteItemByAdmin(@PathVariable String itemId){
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

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id){
        Optional<User> user=userRepository.findById(id);
        if(user.isPresent()){
            return new ResponseEntity<>(user.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
