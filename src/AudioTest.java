import java.util.Random;
import java.util.Vector;
import java.util.Iterator;

//Uncomment the following if using JAR package:

import CMPC3M06.AudioPlayer;
import CMPC3M06.AudioRecorder;


/**
 * CMPC3M06 Audio Test
 *
 *  This class is designed to test the audio player and recorder.
 *
 * @author Philip Harding
 */
public class AudioTest {
    public static void main(String args[]) throws Exception {
        //Vector used to store audio blocks (32ms/512bytes each)
        Vector<byte[]> voiceVector = new Vector<byte[]>();

        //Initialise AudioPlayer and AudioRecorder objects
        AudioRecorder recorder = new AudioRecorder();
        AudioPlayer player = new AudioPlayer();

        //Recording time in seconds
        int recordTime = 10;

        //Capture audio data and add to voiceVector
        System.out.println("Recording Audio...");

        for (int i = 0; i < Math.ceil(recordTime / 0.032); i++) {
            byte[] block = recorder.getBlock();
                voiceVector.add(block);
//                System.out.println(block); // this was used for debugging
        }

        //Close audio input
        recorder.close();

        // simulates the effect packet loss, for set every tenth audio frame to be zero
//        for(int i = 0; i < voiceVector.size(); i++){
//            if(i%10==0)
//                voiceVector.set(i, new byte[0]);
//        }

        // simulates the effect of packet loss randomly this is 50% chance because choosing between 1 number out of 2
        // if wanted 10% could choose 1/10
//        for(int i = 0; i < voiceVector.size(); i++) {
//            Random random = new Random();
//            int randomInt = random.nextInt(2);
//            if (randomInt == 1) {
//                voiceVector.set(i, new byte[0]);
//            }
//        }

        //Iterate through voiceVector and play out each audio block
        System.out.println("Playing Audio...");

        Iterator<byte[]> voiceItr = voiceVector.iterator();
        while (voiceItr.hasNext()) {
            player.playBlock(voiceItr.next());
        }

        //Close audio output
        player.close();
    }
}
