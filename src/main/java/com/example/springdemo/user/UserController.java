package com.example.springdemo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/get")
    public List<User> getDetails(){
    
        return userService.getDetails();
    }

    @RequestMapping(method = RequestMethod.POST, value="/get")
    public void addDetails(@RequestBody User user){
        userService.addDetails(user);

    }

    @RequestMapping(method = RequestMethod.PUT, value="/get/{id}")
    public void editDetails(@RequestBody User user, @PathVariable String id){
        userService.editDetails(user,id);
    }




}
