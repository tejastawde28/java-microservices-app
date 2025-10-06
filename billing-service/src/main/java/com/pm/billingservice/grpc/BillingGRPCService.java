package com.pm.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;

import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGRPCService extends BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGRPCService.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
        log.info("createBillingAccount request received {}", billingRequest.toString());

        //Business logic - e.g. save to database, perform calculations etc.

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId("12453")
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
