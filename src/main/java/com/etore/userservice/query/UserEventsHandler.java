package com.etore.userservice.query;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.trutran.estore.core.models.PaymentDetails;
import com.trutran.estore.core.models.User;
import com.trutran.estore.core.query.FetchUserPaymentDetailsQuery;

@Component
public class UserEventsHandler {
    
    @QueryHandler
    public User findUserPaymentDetails(FetchUserPaymentDetailsQuery query){
        PaymentDetails paymentDetails = PaymentDetails.builder()
        .cardNumber("abc123")
        .cvv("123")
        .name("SERGEY")
        .validUntilMonth(12)
        .validUntilYear(2030).build();

        User user = User.builder()
        .firstName("Sergey")
        .lastName("Kargopolov")
        .UserId(query.getUserId())
        .paymentDetails(paymentDetails).build();
        return user;
    }
}
