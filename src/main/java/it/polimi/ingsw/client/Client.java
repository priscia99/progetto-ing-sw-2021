package it.polimi.ingsw.client;

import java.util.*;
import java.io.*;
import java.net.Socket;
import it.polimi.ingsw.client.view.ui.*;
import it.polimi.ingsw.network.auth_data.*;
import it.polimi.ingsw.network.message.Message;

public class Client {
    private UI userInterface;               // type of chosen UI (CLI, GUI)
    private final String ip;                // server IP
    private final int port;                 // server port
    private boolean active = true;          // check connection status
    ObjectInputStream socketIn = null;      // input socket
    ObjectOutputStream socketOut = null;    // output socket

    public Client(String ip, int port, UI userInterface){
        this.ip = ip;
        this.port = port;
        this.userInterface = userInterface;
    }

    public synchronized boolean isActive(){
        return active;
    }

    public synchronized void setActive(boolean active){
        this.active = active;
    }

    /**
     * Read asynchronously messages from the socket until the client is active.
     * @return reading thread
     * @throws IllegalArgumentException if the message format is unexpected
     * @throws Exception if a generic exception happens shut down the server
     */
    public Thread asyncReadFromSocket(){
        Thread t = new Thread(() -> {
                try {
                    while (isActive()) {
                        Object inputObject = socketIn.readObject();
                        if(inputObject instanceof String){
                            manageAuth((String) inputObject);   // re-directing to authentication manager;
                        }
                        else if(inputObject instanceof Message){
                            // TODO call update execute() function
                        }
                        else{
                            throw new IllegalArgumentException("Unknow, type of received message from client");
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    System.err.println("There was an error. Application will be closed.");
                    setActive(false);
                }
        });
        t.start();
        return t;
    }

    /**
     * Write asynchronously a message in the socket.
     * @param stdin input stream from which read
     * @param socketOut output stream in which write
     * @return writing thread
     * @throws Exception if a generic exception happens shut down the server
     */
    public Thread asyncWriteToSocket(final Scanner stdin, final PrintWriter socketOut){
        Thread t = new Thread(() -> {
                try {
                    while (isActive()) {
                        String inputLine = stdin.nextLine();
                        socketOut.println(inputLine);
                        socketOut.flush();
                    }
                } catch(Exception e){
                    setActive(false);
                }
        });
        t.start();
        return t;
    }

    /**
     * Thread-like run function.
     * @throws IOException
     * @throws NoSuchElementException
     */
    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try{
            Thread asyncRead = asyncReadFromSocket();
            asyncRead.join();
            //t1.join();
        } catch(InterruptedException | NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

    public Thread manageAuth(final String authInfo){
        Thread t = new Thread(() -> {
            String[] command = authInfo.split("#");
            switch (command[0]){
                case "auth_request":
                    AuthData authData = userInterface.requestAuth();
                    sendToSocket(authData);
                    break;
                case "auth_joined":
                    userInterface.displayLobbyJoined(command[1]);
                    break;
                case "auth_created":
                    userInterface.displayLobbyCreated(command[1]);
                    break;
                case "auth_error":
                    userInterface.displayAuthFail(command[1]);
                    break;
                default: break;
            }
        });
        t.start();
        return t;
    }

    public Thread sendToSocket(Object objToSend){
        Thread t = new Thread(() -> {
            try {
                socketOut.writeObject(objToSend);
            } catch (IOException e) {
                setActive(false);
            }
        });
        t.start();
        return t;
    }
}
