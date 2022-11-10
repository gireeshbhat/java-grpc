package org.npci.sdk.server;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.npci.sdk.server.service.GreetServiceImpl;

public class GrpcServerApplication {
    public static final int MOVIE_CONTROLLER_SERVICE_PORT = 9090;

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(MOVIE_CONTROLLER_SERVICE_PORT)
                .addService(new GreetServiceImpl())
                .addService(ProtoReflectionService.newInstance())
                .build();
        server.start();
        System.out.println("Server Started at port: " + MOVIE_CONTROLLER_SERVICE_PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
            System.out.println("Successfully stopped the server");
        }));
        server.awaitTermination();
    }

}
