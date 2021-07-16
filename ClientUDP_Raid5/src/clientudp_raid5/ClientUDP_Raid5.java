/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientudp_raid5;

import Client.Client;
import GUI.MainWindow;

/**
 *
 * @author mario
 */
public class ClientUDP_Raid5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        Create client in port 5000
        */
        Client client = new Client(5000);
        /*
        Create Window and send for parameters the client
        */
        MainWindow window = new MainWindow(client);
    }
    
}
