package Lab2UDP;/*
 * Lab2UDP.TextReceiver.java
 */

/**
 *
 * @author  abj
 */
import java.net.*;
import java.io.*;

import static java.lang.Thread.sleep;

public class TextReceiver {

    static DatagramSocket receiving_socket;

    public static void main (String[] args){

        //***************************************************
        //Port to open socket on
        int PORT = 55555;
        //***************************************************

        //***************************************************
        //Open a socket to receive from on port PORT

        //DatagramSocket receiving_socket;
        try{
            receiving_socket = new DatagramSocket(PORT);
        } catch (SocketException e){
            System.out.println("ERROR: Lab2UDP.TextReceiver: Could not open UDP socket to receive from.");
            e.printStackTrace();
            System.exit(0);
        }
        //***************************************************

        //***************************************************
        //Main loop.

        boolean running = true;

        while (running){

            try{
                //Receive a DatagramPacket (note that the string cant be more than 80 chars)
                byte[] buffer = new byte[80];
                DatagramPacket packet = new DatagramPacket(buffer, 0, 80);

                //sleep(500);
                receiving_socket.setSoTimeout(500);
                receiving_socket.receive(packet);


                //Get a string from the byte buffer
                String str = new String(buffer);
                //Display it
                System.out.print(str + "\n");

                //The user can type EXIT to quit
                if (str.substring(0,4).equals("EXIT")){
                    running=false;
                }
            } catch(SocketTimeoutException e){
                System.out.println(".");
            }catch (IOException e) {
                System.out.println("ERROR: Lab2UDP.TextReceiver: Some random IO error occured!");
                e.printStackTrace();
            }
        }
        //Close the socket
        receiving_socket.close();
        //***************************************************
    }
}