/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

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
    InetAddress direccionServidor;
    DatagramSocket socketUDP;
    DatagramPacket pregunta;
    DatagramPacket peticion;
    String mensaje;
    
    public Client(int port){
        try {
            this.SERVER_PORT = port;
            buffer = new byte[1024];
            direccionServidor = InetAddress.getByName("localhost");
            socketUDP = new DatagramSocket();
            
            sendHello();
            receiveHello();
            closeSocket();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendHello(){
        try {
            mensaje = "¡Hola mundo desde el cliente!";
            buffer = mensaje.getBytes();
            pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, SERVER_PORT);
            socketUDP.send(pregunta);         
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    public void receiveHello(){
        try {
            peticion = new DatagramPacket(buffer, buffer.length);
            socketUDP.receive(peticion);
            mensaje = new String(peticion.getData());
            System.out.println(mensaje);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeSocket(){
        socketUDP.close();
    }
}
