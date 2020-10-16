package com.project.trade.user;

import com.project.trade.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/trade")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public void allAccess(){
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
       return userService.getAllUsers();
    }

    @GetMapping("/admin/allItems")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Item> getAllItems(){
        return userService.getAllItems();
    }

    @DeleteMapping("/admin/{id}/deleteUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> deleteUserByAdmin(@PathVariable String id){
        return userService.deleteUser(id);
    }

    @DeleteMapping("/admin/{itemId}/deleteItem")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> deleteItemByAdmin(@PathVariable String itemId){
        return userService.deleteItem(itemId);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id){
       return userService.getUser(id);
    }
}
