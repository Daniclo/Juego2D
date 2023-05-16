package Entidades;

import Main.GamePanel;
import Main.InputsTeclado;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Jugador extends Entidad{

    GamePanel gamePanel;
    InputsTeclado inputs;

    public Jugador(GamePanel gamePanel, InputsTeclado inputs){ //Instanciamos los inputs y el panel de juego para poder
                                                                //utilizarlos en esta clase

        this.gamePanel = gamePanel;
        this.inputs = inputs;

        valoresPorDefecto(); //Cuando se construye un objeto jugador, se establecen estos valores
        getSpritesJugador(); //Se llama al método cada vez que se construye un jugador para asignarle sprites
    }

    public void valoresPorDefecto(){ //Definimos una serie de atributos iniciales para el jugador
        x = 100;
        y = 100;
        speed = 4;
        apuntandoA = "abajo";
    }

    public void getSpritesJugador(){ //Inicializamos los sprites del jugador

        try{

            abajo1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/jugador/PJ_Abajo_1.png")));
            abajo2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/jugador/PJ_Abajo_2.png")));
            arriba1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/jugador/PJ_Arriba_1.png")));
            arriba2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/jugador/PJ_Arriba_2.png")));
            izquierda1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/jugador/PJ_Izquierda_1.png")));
            izquierda2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/jugador/PJ_Izquierda_2.png")));
            derecha1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/jugador/PJ_Derecha_1.png")));
            derecha2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/jugador/PJ_Derecha_2.png")));

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void actualizar(){
        if (inputs.upPressed || inputs.downPressed || inputs.rightPressed || inputs.leftPressed){ //Este if hace que las animaciones de movimiento no se activen mientras
                                                                                                    //el personaje está quieto.
            if (inputs.upPressed){ //Mover arriba
                apuntandoA = "arriba";
                y -= speed;
            }
            if (inputs.downPressed){ //Mover abajo
                apuntandoA = "abajo";
                y += speed;
            }
            if (inputs.leftPressed){ //Mover izquierda
                apuntandoA = "izquierda";
                x -= speed;
            }
            if (inputs.rightPressed){ //Mover derecha
                apuntandoA = "derecha";
                x += speed;
            }
            if (inputs.spacePressed){ //Correr más rápido al pulsar espacio
                speed = 8;
            }else {
                speed = 4;
            }

            contadorSprite++; //Esto nos permite cambiar al siguiente sprite después de cada movimiento para simular la animación de caminar
            if (contadorSprite > 15){ //Este número determina la velocidad a la que cambian los sprites. En este caso cada 15 frames.
                if (spriteActual == 1){
                    spriteActual = 2;
                }else if (spriteActual == 2){
                    spriteActual = 1;
                }
                contadorSprite = 0;
            }
        }
    }
    public void dibujar(Graphics2D g2){ //Asignamos los sprites adecuados según la dirección en cada actualización

        BufferedImage image = null;
        switch (apuntandoA) {
            case "arriba" -> {
                if (spriteActual == 1) { //Comprueba cual de los 2 sprites tiene que usar ahora y lo asigna en cada actualización para cada dirección
                    image = arriba1;
                }
                if (spriteActual == 2) {
                    image = arriba2;
                }
            }
            case "abajo" -> {
                if (spriteActual == 1) {
                    image = abajo1;
                }
                if (spriteActual == 2) {
                    image = abajo2;
                }
            }
            case "izquierda" -> {
                if (spriteActual == 1) {
                    image = izquierda1;
                }
                if (spriteActual == 2) {
                    image = izquierda2;
                }
            }
            case "derecha" -> {
                if (spriteActual == 1) {
                    image = derecha1;
                }
                if (spriteActual == 2) {
                    image = derecha2;
                }
            }
        }
        g2.drawImage(image,x,y, gamePanel.tamanyoFinalSprites,gamePanel.tamanyoFinalSprites, null);
    }
}