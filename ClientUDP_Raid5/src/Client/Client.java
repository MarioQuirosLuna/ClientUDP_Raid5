/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Utility.MyUtility;
import java.io.File;
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
    private String[] books;
    
    /*
    Variables for the connection to the server.
    */
    InetAddress serverAddress;
    DatagramSocket socketUDP;
    DatagramPacket question;
    DatagramPacket petition;
    String message;
    

    public Client(int port) {
        try {
            /**
             * Inicializate the variables with the srver information.
             */
            this.SERVER_PORT = port;
            serverAddress = InetAddress.getByName("localhost");
            socketUDP = new DatagramSocket();

        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method send the action to the server, for stock the file
     */
    public void stockFile() {
        send(MyUtility.STOCKFILE);
    }
    
    /**
     * This method send the action to the server, for get the file names
     */
    public void getFilesNames(){
        send(MyUtility.GETFILENAMES);
    }
    
    /**
     * This method send the action for get the file.
     */
    public void getFile(){
        send(MyUtility.GETFILE);
    }
    
    /**
     * This method send the action for search the file name.
     */
    public void getFileName(){
        send(MyUtility.GETFILENAME);
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
            socketUDP.send(question);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *  This method receive the actions and sizes
     */
    public String receive() {
        buffer = new byte[1024];
        try {
            petition = new DatagramPacket(buffer, buffer.length);
            socketUDP.receive(petition);
            message = new String(petition.getData(),0,petition.getLength());
            System.out.println(message);
            return message;
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * This method send the action, receive the files and return it
     * @return file names
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
    
    /**
     * This method send the action, send the book name and receive and return the book content
     */
    public String receiveFile(String fileName) {
        getFile();
        send(fileName);
        String book = receive();
        return book;
    }
    
    /**
     * This method send the action, receive the files and return it
     * @return 
     */
    public String[] receiveFileName(String fileName) {
        getFileName();
        send(fileName);
        int size = Integer.parseInt(receive());
        this.books = new String[size];
        for(int i = 0 ; i < size ; i++){
            this.books[i] = receive();
        }
        return this.books;
    }
    
    /**
     * This method close the socket connection.
     */
    public void closeSocket() {
        socketUDP.close();
    }

}
