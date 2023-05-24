package Main;

import Items.Item;
import Items.ItemPezVida;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UserInterface {

    GamePanel gamePanel;

    //Estas 2 imagenes son para mostrar la salud del personaje
    BufferedImage pezCompleto;
    BufferedImage pezVacio;

    //No hay que hacer una nueva instancia de fuente en el método dibujar porque no queremos que se hagan 60 intancias
    //de font por segundo.
    Font arial_40;
    Graphics2D g2;

    public UserInterface(GamePanel gamePanel){

        this.gamePanel = gamePanel;
        arial_40 = new Font("Arial", Font.PLAIN,40);

        //Vida del jugador
        Item pez = new ItemPezVida(gamePanel);
        pezCompleto = pez.sprite;
        pezVacio = pez.sprite2;


    }

    public void dibujar(Graphics2D g2){

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);

        //DEBUG
        //System.out.println(gamePanel.gameState);

        if (gamePanel.gameState == gamePanel.playState){
            //Play State
        }
        if (gamePanel.gameState == gamePanel.pauseState){
            dibujarMenuPausa();
        }

        dibujarVida(); //Vida del jugador sobre la pantalla.

    }

    public void dibujarMenuPausa() {

        String textoPausa = "PAUSA";
        int x ;
        x = getXparaTextoCentrado(textoPausa);
        int y = gamePanel.altoPantalla/2;

        g2.drawString(textoPausa,x,y);

    }

    public int getXparaTextoCentrado(String texto){ //Realiza el cálculo para sacar la x para mostrar texto centrado en pantalla según la longitud del texto

        int longitud = (int) g2.getFontMetrics().getStringBounds(texto,g2).getWidth();
        return gamePanel.anchoPantalla/2 - longitud/2;

    }

    public void dibujarVida(){

        int x = gamePanel.tamanyoFinalSprites/2;
        int y = gamePanel.tamanyoFinalSprites/2;
        int i = 0;

        //Dibujar vida en blanco
        while (i < gamePanel.jugador.vidaMaxima){ //Este bucle va dibujando la vida, moviendo cada "corazon" a la derecha 16 tiles según la vida máxima del jugador

            g2.drawImage(pezVacio,x,y,null);
            i++;
            x += gamePanel.tamanyoFinalSprites;

        }

        //Reseteo de los valores al finalizar el bucle
        x = gamePanel.tamanyoFinalSprites/2;
        i = 0;

        //Dibujar vida actual
        while (i < gamePanel.jugador.vida){
            g2.drawImage(pezCompleto,x,y,null);
            i++;
            x += gamePanel.tamanyoFinalSprites;
        }
    }
}