package zork;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.Serializable;

public class Zork {
  public static void main(String[] args) {
    Game game = new Game();
    
    try {
      SimpleAudioPlayer music = new SimpleAudioPlayer();
      music.play();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    

      game.play();
  }
}  
