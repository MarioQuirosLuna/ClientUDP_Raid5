/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Utility.MyUtility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mario
 */
public class Client {

    private int SERVER_PORT;
    private byte[] buffer;

    /*
    Variables for the connection to the server.
    */
    InetAddress serverAddress;
    DatagramSocket UDPSocket;
    DatagramPacket question;
    DatagramPacket petition;
    String message;

    public Client(int port) {
        try {
            /*
            Inicializate the variables with the srver information.
            */
            this.SERVER_PORT = port;
            buffer = new byte[1024];
            serverAddress = InetAddress.getByName("localhost");
            UDPSocket = new DatagramSocket();

        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendHello() {
        try {
            message = "¡Hello world from the client!";
            buffer = message.getBytes();
            question = new DatagramPacket(buffer, buffer.length, serverAddress, SERVER_PORT);
            UDPSocket.send(question);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void receiveHello() {
        try {
            petition = new DatagramPacket(buffer, buffer.length);
            UDPSocket.receive(petition);
            message = new String(petition.getData());
            System.out.println(message);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void stockFile() {
        /*
        This method send the action to the server, for stock the file
        */
        send(MyUtility.STOCKFILE);
    }
    
    public void send(String msj) {
        /*
        This method send the meessage, whatever string
        */
        try {
            buffer = new byte[msj.length()];
            message = msj;
            buffer = message.getBytes();
            question = new DatagramPacket(buffer, buffer.length, serverAddress, SERVER_PORT);
            UDPSocket.send(question);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeSocket() {
        UDPSocket.close();
    }

}
