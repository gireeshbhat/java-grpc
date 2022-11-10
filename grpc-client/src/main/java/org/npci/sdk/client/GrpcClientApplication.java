package org.npci.sdk.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.npci.sdk.common.GreetServiceGrpc;
import org.npci.sdk.common.Greeting;
import org.npci.sdk.common.Type;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcClientApplication {
    public static final int GREET_SERVICE_PORT = 9090;

    static StreamObserver<Greeting> greetingResponseObserver = new StreamObserver<>() {
        @Override
        public void onNext(Greeting greeting) {
            System.out.println("Response from server: " + greeting);
        }

        @Override
        public void onError(Throwable throwable) {
            System.err.println("Exception: " + throwable.getMessage());
        }

        @Override
        public void onCompleted() {
            System.out.println("Server response completed");
        }
    };


    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", GREET_SERVICE_PORT)
                .usePlaintext()
                .build();

//        GreetServiceGrpc.GreetServiceBlockingStub client = GreetServiceGrpc.newBlockingStub(channel);
        GreetServiceGrpc.GreetServiceStub client = GreetServiceGrpc.newStub(channel);

        Greeting greetingResponse = null;
        try {
            Greeting greetingRequest = Greeting.newBuilder()
                    .setText("greetings")
                    .setType(Type.REQUEST)
                    .build();
//            greetingResponse = client.greetMe(greetingRequest);
            client.greetMe(greetingRequest, greetingResponseObserver);


        } catch (StatusRuntimeException exception) {
            System.err.println("Error response from server: Exception" + exception.getMessage());
            return;
        }

        System.out.println("Response from server " + greetingResponse);
    }

}
