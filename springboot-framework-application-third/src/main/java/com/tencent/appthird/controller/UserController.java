package com.tencent.appthird.controller;

import com.tencent.appthird.entity.User;
import com.tencent.appthird.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * hello world
 */

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "user",method = RequestMethod.GET)
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @RequestMapping(value = "user/{id}",method = RequestMethod.GET)
    public User findById(@PathVariable int id) {
        return userRepository.findById(id).get();
    }

    @RequestMapping(value = "user",method = RequestMethod.POST)
    public User saveUser(@ModelAttribute User user) {
        return userRepository.save(user);
    }

    @RequestMapping(value = "user",method = RequestMethod.PUT)
    public User updateUser(@ModelAttribute User user) {
        return userRepository.saveAndFlush(user);
    }

    @RequestMapping(value = "user/{id}",method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable int id) {
        userRepository.deleteById(id);
    }
}
