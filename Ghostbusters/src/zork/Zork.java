package zork;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.Serializable;

public class Zork {
  public static void main(String[] args) {
    Game game = new Game();
    
    try {
      SimpleAudioPlayer.filePath="src/zork/resources/music.wav";
      SimpleAudioPlayer music = new SimpleAudioPlayer();
      
      music.play();
    } catch (Exception e) {
      System.out.println("Error when playing music");
      e.printStackTrace();
    }
    

      game.play();
  }
}  
