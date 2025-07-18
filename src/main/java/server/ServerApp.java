/*package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        int port = 5555;
        ExecutorService pool = Executors.newCachedThreadPool(); // multithreading
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server listening on " + port);
            while (true) {
                Socket client = server.accept();
                pool.submit(new ClientHandler(client));
            }
        }
    }
}*/
