package Lab2UDP;

import CMPC3M06.AudioPlayer;
import CMPC3M06.AudioRecorder;

import javax.sound.sampled.LineUnavailableException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Vector;

public class audioSender {

    static DatagramSocket sending_socket;

    public static void main (String[] args) throws LineUnavailableException {

        //***************************************************
        //Port to send to
        int PORT = 55555;
        //IP ADDRESS to send to
        InetAddress clientIP = null;
        try {
            clientIP = InetAddress.getByName("192.168.1.67");
        } catch (UnknownHostException e) {
            System.out.println("ERROR: Lab2UDP.TextSender: Could not find client IP");
            e.printStackTrace();
            System.exit(0);
        }
        //***************************************************

        //***************************************************
        //Open a socket to send from
        //We dont need to know its port number as we never send anything to it.
        //We need the try and catch block to make sure no errors occur.

        //DatagramSocket sending_socket;
        try{
            sending_socket = new DatagramSocket();
        } catch (SocketException e){
            System.out.println("ERROR: Lab2UDP.TextSender: Could not open UDP socket to send from.");
            e.printStackTrace();
            System.exit(0);
        }
        //***************************************************

        //***************************************************
        //Get a handle to the Standard Input (console) so we can read user input

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //***************************************************

        //***************************************************
        //Main loop.

        boolean running = true;
        // Initialise AudioRecorder objects
        AudioRecorder recorder = new AudioRecorder();
        while (running) {
            try {
                // Recording time in seconds
                int recordTime = 10;
                // Capture audio data and add to voiceVector
                System.out.println("Recording Audio...");

                // Vector used to store audio blocks (32ms/512bytes each)
                Vector<byte[]> voiceVector = new Vector<byte[]>();

                for (int i = 0; i < Math.ceil(recordTime / 0.032); i++) {
                    byte[] block = recorder.getBlock();
                    voiceVector.add(block);
                }

                // Convert voiceVector into an array of DatagramPackets
                for (byte[] block : voiceVector) {
                    DatagramPacket packet = new DatagramPacket(block, block.length, clientIP, PORT);
                    sending_socket.send(packet);
                }
            } catch (IOException e) {
                System.out.println("ERROR: Lab2UDP.TextSender: Some random IO error occurred!");
                e.printStackTrace();
            }

        }


//        while (running){
//            try{
////                //Read in a string from the standard input
////                String str = in.readLine();
//                // need to be reading in audio
//                //Vector used to store audio blocks (32ms/512bytes each)
//                Vector<byte[]> voiceVector = new Vector<byte[]>();
//
//                //Initialise AudioRecorder objects
//                AudioRecorder recorder = new AudioRecorder();
//
//                //Recording time in seconds
//                int recordTime = 10;
//
//                //Capture audio data and add to voiceVector
//                System.out.println("Recording Audio...");
//
//                for (int i = 0; i < Math.ceil(recordTime / 0.032); i++) {
//                    byte[] block = recorder.getBlock();
//                    voiceVector.add(block);
//
//                }
//
//                //Close audio input
//                recorder.close();
//
//                //Convert it to an array of bytes
//                byte[] buffer = block.getBytes();
//
//                //Make a DatagramPacket from it, with client address and port number
//                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, clientIP, PORT);
//
//                //Send it
//                sending_socket.send(packet);
//
////                //The user can type EXIT to quit
////                if (str.equals("EXIT")){
////                    running=false;
////                }
//
//            } catch (IOException e){
//                System.out.println("ERROR: Lab2UDP.TextSender: Some random IO error occured!");
//                e.printStackTrace();
//            } catch (LineUnavailableException e) {
//                e.printStackTrace();
//            }
//        }
        //Close the socket
        sending_socket.close();
        //***************************************************
    }


}
