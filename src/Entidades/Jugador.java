package Entidades;

import Main.GamePanel;
import Main.InputsTeclado;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Jugador extends Entidad{

    //Esta clase controla lo relacionado con el personaje protagonista del juego, manejado por el jugador, y sus
    //interacciones con el resto del juego.

    GamePanel gamePanel;
    public InputsTeclado inputs;

    public final int xCamara;
    public final int yCamara;

    //OBJETOS QUE PUEDE CONSEGUIR EL JUGADOR
    public boolean tienePezGlobo = false;

    public Jugador(GamePanel gamePanel, InputsTeclado inputs){ //Instanciamos los inputs y el panel de juego para poder
                                                                //utilizarlos en esta clase

        this.gamePanel = gamePanel;
        this.inputs = inputs;

        xCamara = gamePanel.anchoPantalla/2 - (gamePanel.tamanyoFinalSprites/2);
        yCamara = gamePanel.altoPantalla/2 - - (gamePanel.tamanyoFinalSprites/2);

        areaColision = new Rectangle(8,16,32,32); //Estos valores definen el area de colisión del jugador
        //Los valores del área de colisión van a alterar, así que los almacenamos en otras variables para poder acceder de nuevo a ellos
        areaColisionDefaultX = areaColision.x;
        areaColisionDefaultY = areaColision.y;

        valoresPorDefecto(); //Cuando se construye un objeto jugador, se establecen estos valores
        getSpritesJugador(); //Se llama al método cada vez que se construye un jugador para asignarle sprites
    }

    public void valoresPorDefecto(){ //Definimos una serie de atributos iniciales para el jugador
        xMundo = 744;
        yMundo = 909;
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

    public void interactuar(){

    }

    public void actualizar(){
        if (inputs.upPressed || inputs.downPressed || inputs.rightPressed || inputs.leftPressed){ //Este if hace que las animaciones de movimiento no se activen mientras
                                                                                                    //el personaje está quieto.

            if (inputs.upPressed){ //Apuntar arriba. Comprueba que no hay colisiones antes de avanzar
                apuntandoA = "arriba";
            }
            if (inputs.downPressed){ //Apuntar abajo. Comprueba que no hay colisiones antes de avanzar
                apuntandoA = "abajo";
            }
            if (inputs.leftPressed){ //Apuntar izquierda. Comprueba que no hay colisiones antes de avanzar
                apuntandoA = "izquierda";
            }
            if (inputs.rightPressed){ //Apuntar derecha. Comprueba que no hay colisiones antes de avanzar
                apuntandoA = "derecha";
            }
            if (inputs.spacePressed){ //Correr más rápido al pulsar espacio
                speed = 7;
            }else {
                speed = 4;
            }

            System.out.println("x: " + xMundo);
            System.out.println("y: " + yMundo);

            colisionOn = false;

            gamePanel.checkColisiones.checkTile( this); //Le pasamos esta entidad (en este caso el jugaddor) al check de colisiones de tiles en cada actualización

            //Le pasamos esta entidad al check de colisiones de objetos.
            gamePanel.checkColisiones.checkItem(this);

            if (!colisionOn){ //Solo habilitamos que se mueva el jugador mientras que el valor de colisión sea falso
                if (inputs.upPressed){
                    yMundo -= speed;
                }
                if (inputs.downPressed){
                    yMundo += speed;
                }
                if (inputs.leftPressed){
                    xMundo -= speed;
                }
                if (inputs.rightPressed){
                    xMundo += speed;
                }
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
        if (inputs.ePressed){ //Cuando se pulsa el botón E de interacción, se comprueba si existe delante un objeto
            //con el que interactuar

            gamePanel.checkInteraccion.checkItem(this);
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
        g2.drawImage(image, xCamara, yCamara, gamePanel.tamanyoFinalSprites,gamePanel.tamanyoFinalSprites, null);
    }
}