package Lab2UDP;

import CMPC3M06.AudioPlayer;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Iterator;
import java.util.Vector;

public class audioReceiver {
    static DatagramSocket receiving_socket;

    public static void main(String[] args) throws LineUnavailableException {
        //***************************************************
        //Port to open socket on
        int PORT = 55555;
        //***************************************************

        //***************************************************
        //Open a socket to receive from on port PORT

        //DatagramSocket receiving_socket;
        try {
            receiving_socket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            System.out.println("ERROR: Lab2UDP.TextReceiver: Could not open UDP socket to receive from.");
            e.printStackTrace();
            System.exit(0);
        }
        //***************************************************

        //***************************************************
        //Main loop.

        boolean running = true;
        AudioPlayer player = new AudioPlayer();

        while (running) {

            try {
                // Receive audio data and add to voiceVector
                byte[] buffer = new byte[512];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                receiving_socket.receive(packet);
                Vector<byte[]> voiceVector = new Vector<byte[]>();
                voiceVector.add(buffer);

                // play out audio
                //Iterate through voiceVector and play out each audio block
                System.out.println("Playing Audio...");

                Iterator<byte[]> voiceItr = voiceVector.iterator();
                while (voiceItr.hasNext()) {
                    player.playBlock(voiceItr.next());
                }

                //Close audio output
                player.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Close the socket
        receiving_socket.close();
        //***************************************************

    }
}


