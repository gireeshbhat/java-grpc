package org.npci.sdk.server.service;

import java.time.LocalDateTime;

import io.grpc.stub.StreamObserver;
import org.npci.sdk.common.GreetServiceGrpc;
import org.npci.sdk.common.Greeting;
import org.npci.sdk.common.Type;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    @Override
    public void greetMe(Greeting request, StreamObserver<Greeting> responseObserver) {
        System.out.println("Request received at " + LocalDateTime.now());
        for (int i = 0; i < 10; i++) {
            Greeting response = Greeting.newBuilder()
                    .setText(request.getText() + " " + i)
                    .setType(Type.RESPONSE)
                    .build();

            responseObserver.onNext(response);
            System.out.println("Response " + i + " at " + LocalDateTime.now());

            try {
                Thread.sleep(10000);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("Request Completed");
        responseObserver.onCompleted();
    }
}
