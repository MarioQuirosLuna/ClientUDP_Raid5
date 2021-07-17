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
    private String[] books;

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
            message = new String(petition.getData(),0,petition.getLength());
            System.out.println(message);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
    This method send the action to the server, for stock the file
    */
    public void stockFile() {
        send(MyUtility.STOCKFILE);
    }
    
    /*
    This method send the action to the server, for get the file names
    */
    public void getFilesNames(){
        send(MyUtility.GETFILENAMES);
    }
    

    /**
     * This method send the meessage, whatever string
     * @param msj : the message to send
     */
    public void send(String msj) {
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
    
    /**
     *  This method receive the actions and sizes
     */
    public String receive() {
        try {
            petition = new DatagramPacket(buffer, buffer.length);
            UDPSocket.receive(petition);
            message = new String(petition.getData(),0,petition.getLength());
            System.out.println(message);
            return message;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * This method receive the files and return it
     * @return 
     */
    public String[] receiveFiles() {
        getFilesNames();
        int size = Integer.parseInt(receive());
        this.books = new String[size];
        for(int i = 0 ; i < size ; i++){
            this.books[i] = receive();
        }
        return this.books;
    }

    public void closeSocket() {
        UDPSocket.close();
    }

}
