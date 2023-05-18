package Entidades;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entidad {

    //Esta clase será la clase padre de las clases del jugador, npcs y enemigos
    public int xMundo, yMundo; //Posición de la entidad en el mundo
    public int speed; //Velocidad a la que se desplaza la entidad
    public BufferedImage arriba1, arriba2, abajo1, abajo2, izquierda1, izquierda2, derecha1, derecha2; //Almacena las animaciones de movimiento de las entidades
    public String apuntandoA; //Almacena la información de hacia donde está mirando la entidad
    public int contadorSprite = 0; //Este contador permite cambiar de sprites cada cierto tiempo
    public int spriteActual = 1; //Indica que sprite está usando la entidad actualmente
    public Rectangle areaColision; //Define el area de colisiones de la entidad para determinar cuando choca contra otros elementos
    public int areaColisionDefaultX, areaColisionDefaultY;
    public boolean colisionOn = false; //
}