import (link unavailable)*;
import (link unavailable)*;
import java.util.*;

public class ChatServer {
    private ServerSocket serverSocket;
    private List<Socket> clients;

    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
    }

    public void start() throws IOException {
        System.out.println("Chat server started. Waiting for clients...");

        while (true) {
            Socket client = serverSocket.accept();
            clients.add(client);

            System.out.println("New client connected");

            Thread thread = new Thread(new ClientHandler(client));
            thread.start();
        }
    }

    private class ClientHandler implements Runnable {
        private Socket client;

        public ClientHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter writer = new PrintWriter(client.getOutputStream(), true);

                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received message from client: " + message);

                    // Broadcast message to all clients
                    for (Socket socket : clients) {
                        if (socket != client) {
                            PrintWriter broadcastWriter = new PrintWriter(socket.getOutputStream(), true);
                            broadcastWriter.println(message);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    client.close();
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ChatServer server = new ChatServer(8000);
        server.start();
    }
}


# Client Code (ChatClient.java)

import (link unavailable)*;
import (link unavailable)*;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;
    private Scanner scanner;

    public ChatClient(String hostname, int port) throws UnknownHostException, IOException {
        socket = new Socket(hostname, port);
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        System.out.println("Connected to chat server");

        Thread thread = new Thread(new MessageReceiver());
        thread.start();

        while (true) {
            System.out.print("Enter message: ");
            String message = scanner.nextLine();

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println(message);
        }
    }

    private class MessageReceiver implements Runnable {
        @Override
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received message: " + message);
                }
            } catch (IOException e) {
                System.out.println("Error receiving message: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        ChatClient client = new ChatClient("localhost", 8000);
        client.start();
    }
}
