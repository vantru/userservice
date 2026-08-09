package com.etore.userservice.query.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etore.userservice.query.UserEventsHandler;
import com.trutran.estore.core.models.User;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/Users")
public class UserController {
    @Autowired
    private final QueryGateway queryGateway;

    public UserController( QueryGateway queryGateway){
        this.queryGateway = queryGateway;
    }

    @GetMapping("/{userId}/payment-details")
    public User getUserPaymentDetails(@PathVariable String userId) {
        UserEventsHandler userQuery = new UserEventsHandler();
        var user = queryGateway.query(userQuery, ResponseTypes.instanceOf(User.class)).join();
        return user;
    }
    
}
