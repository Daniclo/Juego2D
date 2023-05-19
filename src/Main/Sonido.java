package Main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sonido {

    //Esta clase sirve para controlar y manejar la incorporación de música y efectos de sonido.

    Clip clip;
    URL[] sonidosURL = new URL[30]; //Este array va a contener las rutas de todos los sonidos y música

    public Sonido(){ //Carga los sonidos en el juego por medio del array

        sonidosURL[0] = getClass().getResource("/sonidos/Tema-mundo-1.wav");
        sonidosURL[1] = getClass().getResource("/sonidos/Recoger objeto.wav");

    }

    public void setFile(int i){ //Obtiene la URL de un sonido y lo convierte en un objeto de la clase clip preparado para reproducirse

        try{

            AudioInputStream ais = AudioSystem.getAudioInputStream(sonidosURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }

    public void play(){ //Reproduce un sonido

        clip.start();

    }

    public void loopMundo1(){ //Crea el loop de la canción del mundo 1

        clip.setLoopPoints(141252,-1); //Se puede ajustar para que quede mejor el loop
        clip.loop(Clip.LOOP_CONTINUOUSLY);


    }

    public void stop(){ //Detiene un sonido que se esté reproduciendo

        clip.stop();

    }

    public int getFrames(){

        int x = (int) clip.getLongFramePosition();
        return x;

    }
}
