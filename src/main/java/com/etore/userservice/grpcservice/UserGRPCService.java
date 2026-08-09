package com.etore.userservice.grpcservice;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;

import com.estore.user.grpc.GetUserRequest;
import com.estore.user.grpc.PaymentDetails;
import com.estore.user.grpc.UserResponse;
import com.estore.user.grpc.UserServiceGrpc;
import com.etore.userservice.query.UserEventsHandler;
import com.trutran.estore.core.models.User;
import com.trutran.estore.core.query.FetchUserPaymentDetailsQuery;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;


@GrpcService
public class UserGRPCService extends UserServiceGrpc.UserServiceImplBase {
    
    private final QueryGateway queryGateway;

    public UserGRPCService(QueryGateway queryGateway){
        this.queryGateway = queryGateway;
    }

    @Override 
    public void getUser(GetUserRequest request, StreamObserver<UserResponse> reStreamObserver){

        var user = queryGateway.query(new FetchUserPaymentDetailsQuery(request.getUserId()), ResponseTypes.instanceOf(User.class)).join();
        
        UserResponse response = UserResponse.newBuilder()
        .setUserId(user.getUserId())
        .setFirstName(user.getFirstName())
        .setLastName(user.getLastName())
        .setPaymentDetails(PaymentDetails.newBuilder()
        .setCardNumber(user.getPaymentDetails().getCardNumber())
            .setCvv(user.getPaymentDetails().getCvv())
            .setName(user.getPaymentDetails().getName())
            .setValidUntilMonth(user.getPaymentDetails().getValidUntilMonth())
            .setValidUntilYear(user.getPaymentDetails().getValidUntilYear()).build()
        ).build();

        reStreamObserver.onNext(response);
        reStreamObserver.onCompleted();
    }
}
