package org.cf.card.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.cf.card.model.User;
import org.cf.card.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dell on 1/17/2015.
 */
@RestController
@RequestMapping(value = "/login")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public List<User> getLogins() {
        List<User> users = new ArrayList<>();
        users.addAll(userService.findAll());
        return users;
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    User addCouple(@RequestBody User user) {
        return userService.save(user);
    }

    @RequestMapping(method = DELETE, value = "{id}")
    public @ResponseBody void deleteUser(@PathVariable("id") long id) {

        userService.deleteById(id);
    }
    /**
     * Authenticate.
     * @param password
     * @return
     */
    @RequestMapping(value = "/authenticate/{password}",produces= APPLICATION_JSON_VALUE)
    public @ResponseBody User authenticate(@PathVariable(value = "password") String password){
    	return userService.findByPassword(password);
    }
    
}
